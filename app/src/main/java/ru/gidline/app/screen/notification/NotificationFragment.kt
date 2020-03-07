package ru.gidline.app.screen.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.fragment_notification.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.BellRepository
import ru.gidline.app.screen.base.BaseFragment

class NotificationFragment : BaseFragment<NotificationContract.Presenter>(), NotificationContract.View {

    override val presenter: NotificationPresenter by instance()

    private val bellRepository: BellRepository by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_notification, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bell = bellRepository.getById(args.getInt("id"))
        tv_title.text = bell.title
        tv_vacancy.text = bell.vacancy
        tv_subtext.text = bell.subtitle
        tv_html.text = HtmlCompat.fromHtml(bell.html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        fab_phone
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