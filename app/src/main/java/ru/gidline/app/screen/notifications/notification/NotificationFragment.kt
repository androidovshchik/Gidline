package ru.gidline.app.screen.notifications.notification

import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import coil.api.load
import kotlinx.android.synthetic.main.fragment_notification.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.model.Bell
import ru.gidline.app.local.repository.BellRepository
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.main.MainContract

class NotificationFragment : BaseFragment<NotificationContract.Presenter>(),
    NotificationContract.View {

    override val presenter: NotificationPresenter by instance()

    private val bellRepository: BellRepository by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        val bell = bellRepository.getById(args.getInt("id"))?.also {
            it.unread = false
        }
        activityCallback<MainContract.View> {
            if (bell != null) {
                setTitle(bell.type)
            } else {
                popFragment(null, false)
            }
        }
        return inflater.inflate(R.layout.fragment_notification, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        iv_background.apply {
            load(R.drawable.background)
            imageMatrix = Matrix().apply {
                setTranslate(0f, -resources.getDimension(R.dimen.toolbar_height))
            }
        }
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
            fab_phone.isVisible = it.type == Bell.INVITATION
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