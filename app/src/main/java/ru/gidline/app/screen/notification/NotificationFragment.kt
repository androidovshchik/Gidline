package ru.gidline.app.screen.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_notification.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.BellRepository
import ru.gidline.app.local.dto.BellType
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.main.MainContract

class NotificationFragment : BaseFragment<NotificationContract.Presenter>(), NotificationContract.View {

    override val presenter: NotificationPresenter by instance()

    private val bellRepository: BellRepository by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        bellRepository.getById(args.getInt("id"))?.let {
            makeCallback<MainContract.View> {
                setTitle(it.type.caption)
            }
        }
        return inflater.inflate(R.layout.fragment_notification, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bellRepository.getById(args.getInt("id"))?.let {
            tv_title.text = it.title
            it.vacancy?.let { vacancy ->
                tv_vacancy.apply {
                    isVisible = true
                    text = vacancy
                }
            }
            tv_subtext.text = it.subtitle
            tv_html.text = HtmlCompat.fromHtml(it.html, HtmlCompat.FROM_HTML_MODE_LEGACY)
            fab_phone.isVisible = it.type == BellType.INVITATION
        }
    }

    companion object {

        fun newInstance(id: Int): NotificationFragment {
            return NotificationFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    }
}