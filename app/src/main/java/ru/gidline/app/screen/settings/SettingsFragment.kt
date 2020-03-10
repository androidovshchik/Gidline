package ru.gidline.app.screen.settings

import android.Manifest
import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.fragment_settings.*
import org.jetbrains.anko.telephonyManager
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
        tv_name.text = "Хуршед"
        tv_surname.text = "Хасанов"
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
        s_language.also {
            it.adapter = languageAdapter.apply {
                addAll(*resources.getStringArray(R.array.languages))
            }
            it.onItemSelectedListener = this
        }
        tv_save.setOnClickListener(this)
        preferences.run {
            avatar = avatarPath
            onPhotoPath(avatarPath)
            updateGender(isMan)
            s_citizenship.setSelection(citizenship, false)
            et_phone.setText(phone)
            et_whatsapp.setText(whatsapp)
            et_email.setText(email)
            s_language.setSelection(language, false)
            if (phone == null) {
                requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), REQUEST_PHONE)
            }
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
                    isMan = ib_man.isChecked
                    val country = s_citizenship.selectedItemPosition
                    citizenship = country
                    phone = et_phone.text.toString()
                    whatsapp = et_whatsapp.text.toString()
                    email = et_email.text.toString()
                    language = s_language.selectedItemPosition
                    hasMigrationData = country in 2..3
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
            //preferences.avatarPath = path
        }
        val genderDrawable =
            if (ib_man.isChecked) R.drawable.avatar_man else R.drawable.avatar_woman
        iv_avatar.load(Uri.parse("file://$path")) {
            error(genderDrawable)
            transformations(CircleCropTransformation())
        }
    }

    private fun updateGender(isMan: Boolean) {
        ib_man.isChecked = isMan
        ib_woman.isChecked = !isMan
        if (preferences.avatarPath == null) {
            //preferences.isMan = isMan
            onPhotoPath("")
        }
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PHONE -> context?.run {
                if (areGranted(Manifest.permission.READ_PHONE_STATE)) {
                    val phone = telephonyManager.line1Number
                    Timber.d("User phone: $phone")
                    preferences.phone = phone
                }
            }
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

        private const val REQUEST_PHONE = 98

        private const val REQUEST_STORAGE = 99

        private const val REQUEST_CAMERA = 100

        private const val REQUEST_GALLERY = 101

        fun newInstance(): SettingsFragment {
            return SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}