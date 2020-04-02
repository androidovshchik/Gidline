package ru.gidline.app.screen.settings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import coil.api.load
import coil.transform.CircleCropTransformation
import com.chibatching.kotpref.bulk
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_settings.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.areGranted
import ru.gidline.app.local.Preferences
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.base.listener.IView
import timber.log.Timber

class SettingsFragment : BaseFragment<SettingsContract.Presenter>(), SettingsContract.View {

    override val presenter: SettingsPresenter by instance()

    private val preferences: Preferences by instance()

    private val countryAdapter: ArrayAdapter<String> by instance(arg = R.layout.item_spinner_caps)

    private val languageAdapter: ArrayAdapter<String> by instance(arg = R.layout.item_spinner_caps)

    private var alertDialog: AlertDialog? = null

    private var avatar: String? = null

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_settings, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ll_settings.requestFocus()
        iv_camera.setOnClickListener(this)
        ib_man.setOnClickListener(this)
        ib_woman.setOnClickListener(this)
        s_citizenship.also {
            it.adapter = countryAdapter.apply {
                add("")
                addAll(*resources.getStringArray(R.array.countries))
            }
            it.onItemSelectedListener = this
        }
        val phoneFormat = "+7([000]) [000]-[00]-[00]"
        val affineFormats = listOf(
            "+992 [000]-[00]-[00]",
            "+998 [000]-[00]-[00]",
            "+996 [000]-[00]-[00]"
        )
        MaskedTextChangedListener.installOn(et_phone, phoneFormat, affineFormats)
        MaskedTextChangedListener.installOn(et_whatsapp, phoneFormat, affineFormats)
        val dateFormat = "[00]{.}[00]{.}[0000]"
        MaskedTextChangedListener.installOn(et_date1, dateFormat)
        MaskedTextChangedListener.installOn(et_date2, dateFormat)
        s_language.also {
            it.adapter = languageAdapter.apply {
                addAll(*resources.getStringArray(R.array.languages))
            }
            it.onItemSelectedListener = this
        }
        tv_save.setOnClickListener(this)
        preferences.run {
            avatar = avatarPath
            updateGender(isMan)
            et_name.setText(username)
            et_surname.setText(surname)
            s_citizenship.setSelection(citizenship, false)
            phone?.let {
                et_phone.setText(it)
            }
            whatsapp?.let {
                et_whatsapp.setText(it)
            }
            et_email.setText(email)
            s_language.setSelection(language, false)
            et_date1.setText(dateEntryRussia)
            et_date2.setText(dateFirstPatent)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_camera -> {
                val context = requireContext()
                if (!context.areGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_STORAGE
                    )
                    return
                }
                alertDialog = AlertDialog.Builder(context)
                    .setItems(arrayOf("Сделать снимок", "Открыть галерею")) { _, which ->
                        val requestCode = if (which == 0) REQUEST_CAMERA else REQUEST_GALLERY
                        startActivityForResult(Intent.createChooser(Intent().apply {
                            if (which == 0) {
                                action = MediaStore.ACTION_IMAGE_CAPTURE
                                putExtra(MediaStore.EXTRA_OUTPUT, PathCompat.getPhotoUri(context))
                                putExtra("android.intent.extra.quickCapture", true)
                                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                            } else {
                                action = Intent.ACTION_GET_CONTENT
                                type = "image/*"
                            }
                        }, "Выберите приложение"), requestCode)
                    }
                    .create()
                alertDialog?.show()
            }
            R.id.ib_man, R.id.ib_woman -> {
                updateGender(!ib_man.isChecked)
            }
            R.id.tv_save -> {
                preferences.bulk {
                    avatarPath = avatar
                    username = et_name.text.toString()
                    surname = et_surname.text.toString()
                    isMan = ib_man.isChecked
                    val country = s_citizenship.selectedItemPosition
                    citizenship = country
                    phone = et_phone.text.toString().ifEmpty { null }
                    whatsapp = et_whatsapp.text.toString().ifEmpty { null }
                    email = et_email.text.toString()
                    language = s_language.selectedItemPosition
                    hasMigrationData = country in 2..3
                    dateEntryRussia = et_date1.text.toString()
                    dateFirstPatent = et_date2.text.toString()
                }
                activityCallback<IView> {
                    popFragment(null, false)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.s_citizenship -> {
                mcv_migration.isVisible = position in 2..3
            }
        }
    }

    override fun onPhotoPath(path: String?) {
        Timber.d("Photo path: $path")
        if (!path.isNullOrBlank()) {
            avatar = path
        }
        iv_avatar.load(Uri.parse("file://$avatar")) {
            error(if (ib_man.isChecked) R.drawable.avatar_man else R.drawable.avatar_woman)
            transformations(CircleCropTransformation())
        }
    }

    private fun updateGender(isMan: Boolean) {
        ib_man.isChecked = isMan
        ib_woman.isChecked = !isMan
        onPhotoPath("")
    }

    override fun onRequestPermissionsResult(requestCode: Int, p: Array<out String>, r: IntArray) {
        when (requestCode) {
            REQUEST_STORAGE -> iv_camera?.performClick()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            context?.apply {
                when (requestCode) {
                    REQUEST_CAMERA ->
                        onPhotoPath(PathCompat.getPhotoFile(applicationContext)?.path)
                    REQUEST_GALLERY ->
                        presenter.getGalleryPath(applicationContext, data?.data ?: return)
                }
            }
        }
    }

    override fun onDestroyView() {
        alertDialog?.dismiss()
        super.onDestroyView()
    }

    companion object {

        private const val REQUEST_CAMERA = 100

        private const val REQUEST_GALLERY = 101

        private const val REQUEST_STORAGE = 1000

        fun newInstance(): SettingsFragment {
            return SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}