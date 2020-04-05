package ru.gidline.app.screen.catalog.map

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_sheet_place.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.screen.base.BaseSheetFragment

class PlaceSheetFragment : BaseSheetFragment() {

    private val placeRepository: PlaceRepository by instance()

    private var alertDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sheet_place, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val place = placeRepository.getById(arguments!!.getInt("id", 0))
        iv_icon.setImageResource(place.icon)
        tv_name.text = place.name
        tv_address.text = place.address
        val schedule = place.schedule.substringBefore("\n").let {
            SpannableStringBuilder(it).apply {
                val index = it.indexOf("с ")
                if (index > 0) {
                    setSpan(StyleSpan(Typeface.BOLD), 0, index, SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
        tv_schedule1.text = schedule
        tv_schedule2.text = schedule
        val phones = place.phones.split("\n")
            .filter { numberRegex.matches(it) }
            .map { it.trim() }
        if (phones.isNotEmpty()) {
            fab_phone.isVisible = true
            fab_phone.setOnClickListener {
                if (phones.size > 1) {
                    var position = 0
                    alertDialog = AlertDialog.Builder(requireContext())
                        .setTitle("Выберите номер")
                        .setSingleChoiceItems(phones.toTypedArray(), position) { _, which ->
                            position = which
                        }
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            dialPhone(phones[position])
                        }
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()
                    alertDialog?.show()
                } else {
                    dialPhone(phones[0])
                }
            }
        }
    }

    private fun dialPhone(phone: String) {
        startActivity(Intent.createChooser(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.fromParts("tel", phone, null)
        }, "Выберите приложение"))
    }

    override fun onDestroyView() {
        alertDialog?.dismiss()
        super.onDestroyView()
    }

    companion object {

        private val numberRegex = Regex(".*\\d.*")

        fun newInstance(id: Int): PlaceSheetFragment {
            return PlaceSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    }
}