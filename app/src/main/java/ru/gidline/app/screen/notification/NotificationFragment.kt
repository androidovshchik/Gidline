package ru.gidline.app.screen.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class NotificationFragment : BaseFragment<NotificationContract.Presenter>(), NotificationContract.View {

    override val presenter: NotificationPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_notification, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

        fun newInstance(id: Int): NotificationFragment {
            return NotificationFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}