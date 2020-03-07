package ru.gidline.app.screen.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_notifications.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.BellRepository
import ru.gidline.app.local.dto.Bell
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.notification.NotificationFragment
import ru.gidline.app.screen.notifications.adapter.NotificationsAdapter
import ru.gidline.app.screen.notifications.adapter.NotificationsDecoration

class NotificationsFragment : BaseFragment<NotificationsContract.Presenter>(), NotificationsContract.View {

    override val presenter: NotificationsPresenter by instance()

    private val bellRepository: BellRepository by instance()

    private val adapter = NotificationsAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_notifications, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onItemDeleted(-1)
        adapter.items.addAll(bellRepository.getAll())
        rv_notifications.also {
            it.addItemDecoration(NotificationsDecoration(requireContext()))
            it.adapter = adapter
        }
    }

    override fun onItemDeleted(id: Int) {
        if (id > 0) {
            bellRepository.deleteById(id)
        }
        val count = bellRepository.unreadCount
        if (count > 0) {
            tv_new.apply {
                text = resources.getQuantityText(R.plurals.notifications, count)
                isVisible = true
            }
        } else {
            tv_new.isVisible = false
        }
    }

    override fun onItemSelected(position: Int, item: Bell) {
        makeCallback<IView> {
            addFragment(NotificationFragment.newInstance(item.id))
        }
    }

    companion object {

        fun newInstance(): NotificationsFragment {
            return NotificationsFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}