package ru.gidline.app.screen.browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import org.kodein.di.generic.instance
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.main.MainContract

class BrowserFragment : BaseFragment<BrowserContract.Presenter>(), BrowserContract.View {

    override val presenter: BrowserPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        activityCallback<MainContract.View> {
            setTitle(args.getString("name").orEmpty())
        }
        return WebView(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

        fun newInstance(name: String, url: String): BrowserFragment {
            return BrowserFragment().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("url", url)
                }
            }
        }
    }
}