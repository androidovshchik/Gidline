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
import ru.gidline.app.extension.requestPermissions
import ru.gidline.app.screen.base.BaseFragment
import timber.log.Timber

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
                    activity?.requestPermissions(
                        REQUEST_PERMISSIONS,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    return
                }
                AlertDialog.Builder(context)
                    .setItems(arrayOf("Сделать снимок", "Открыть галерею")) { _, which ->
                        if (which == 0) {
                            try {
                                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                                    putExtra(
                                        MediaStore.EXTRA_OUTPUT,
                                        PathCompat.getPhotoUri(context)
                                    )
                                    putExtra("android.intent.extra.quickCapture", true)
                                }, REQUEST_CAMERA)
                            } catch (e: Throwable) {
                                Timber.e(e)
                            }
                        } else {
                            startActivityForResult(Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT).apply {
                                type = "image/*"
                            }, "Выберите приложение"), REQUEST_GALLERY)
                        }
                    }
                    .create()
                    .show()
            }
        }
    }

    override fun onGalleryPath(path: String?) {
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
            when (requestCode) {
                REQUEST_CAMERA -> {
                }
                REQUEST_GALLERY -> {
                    presenter.getGalleryPath(requireContext(), data?.data ?: return)
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