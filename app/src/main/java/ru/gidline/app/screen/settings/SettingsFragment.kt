package ru.gidline.app.screen.settings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_settings.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.areGranted
import ru.gidline.app.screen.base.BaseFragment

class SettingsFragment : BaseFragment<SettingsContract.Presenter>(), SettingsContract.View {

    override val presenter: SettingsPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_settings, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_name.text = "Хуршед"
        tv_surname.text = "Хасанов"
        iv_camera.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_camera -> {
                val context = requireContext()
                if (!context.areGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_PERMISSIONS
                    )
                    return
                }
                AlertDialog.Builder(context)
                    .setItems(arrayOf("Сделать снимок", "Открыть галерею")) { _, which ->
                        startActivityForResult(
                            Intent.createChooser(Intent().apply {
                                if (which == 0) {
                                    action = MediaStore.ACTION_IMAGE_CAPTURE
                                    putExtra(
                                        MediaStore.EXTRA_OUTPUT,
                                        PathCompat.getPhotoUri(context)
                                    )
                                    putExtra("android.intent.extra.quickCapture", true)
                                    addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                                } else {
                                    action = Intent.ACTION_GET_CONTENT
                                type = "image/*"
                                }
                            }, "Выберите приложение"),
                            if (which == 0) REQUEST_CAMERA else REQUEST_GALLERY
                        )
                    }
                    .create()
                    .show()
            }
        }
    }

    override fun onPhotoPath(path: String?) {
        if (path != null) {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (context?.areGranted(Manifest.permission.READ_EXTERNAL_STORAGE) == true) {
                iv_camera.performClick()
            }
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

    companion object {

        private const val REQUEST_PERMISSIONS = 99

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