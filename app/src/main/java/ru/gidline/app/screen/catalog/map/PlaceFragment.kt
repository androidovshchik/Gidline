package ru.gidline.app.screen.catalog.map

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_place.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.repository.PlaceRepository

class PlaceFragment : BottomSheetDialogFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val placeRepository: PlaceRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialogTheme);
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_place, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val place = placeRepository.getById(arguments!!.getInt("id", 0))
        iv_icon.setImageResource(place.icon)
        tv_name.text = place.name
        tv_address.text = place.address
        tv_schedule1.text = place.schedule
        tv_schedule2.text = place.schedule
        val phone = place.phones.split("\n").firstOrNull()
        if (!phone.isNullOrBlank()) {
            fab_phone.isVisible = true
            fab_phone.setOnClickListener {
                startActivity(Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.fromParts("tel", phone, null)
                })
            }
        }
    }

    companion object {

        fun newInstance(id: Int): PlaceFragment {
            return PlaceFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    }
}