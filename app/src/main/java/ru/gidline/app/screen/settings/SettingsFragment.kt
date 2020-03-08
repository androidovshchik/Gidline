package ru.gidline.app.screen.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.fragment_settings.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class SettingsFragment : BaseFragment<SettingsContract.Presenter>(), SettingsContract.View {

    override val presenter: SettingsPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_settings, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        iv_camera.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_camera -> {
                AlertDialog.Builder(requireContext())
                    .setItems(arrayOf("Сделать снимок", "Открыть галерею")) { _, which ->
                        when (which) {
                            0 -> {
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                if (intent.resolveActivity(packageManager) != null) {
                                    startActivityForResult(intent.apply {
                                        putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            FileProvider.getUriForFile(
                                                applicationContext,
                                                "$packageName.fileprovider",
                                                externalPhoto
                                            )
                                        )
                                        putExtra("android.intent.extra.quickCapture", true)
                                    }, REQUEST_PHOTO)
                                } else {
                                    toast("Не найдено приложение для фото")
                                    closePreview(RESULT_CANCELED)
                                }
                            }
                            else -> {
                                startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).apply {
                                    type = "image/*"
                                }.withChooser(applicationContext), REQUEST_GALLERY)
                            }
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