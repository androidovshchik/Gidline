package ru.gidline.app.screen.browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class BrowserFragment : BaseFragment<BrowserContract.Presenter>(), BrowserContract.View {

    override val presenter: BrowserPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_notifications, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

        fun newInstance(): BrowserFragment {
            return BrowserFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}