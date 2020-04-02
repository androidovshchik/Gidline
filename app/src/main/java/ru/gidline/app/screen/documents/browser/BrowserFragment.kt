package ru.gidline.app.screen.documents.browser

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_browser.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.*
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.main.MainContract

class BrowserFragment : BaseFragment<BrowserContract.Presenter>(), BrowserContract.View {

    override val presenter: BrowserPresenter by instance()

    private val scrollChangedListener = ViewTreeObserver.OnScrollChangedListener {
        srl_web.isEnabled = wv_web.scrollY == 0
    }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        activityCallback<MainContract.View> {
            setTitle(args.getString("name").orEmpty())
        }
        return inflater.inflate(R.layout.fragment_browser, root, false)
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        srl_web.setColorSchemeColors(color)
        pb_web.progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        srl_web.setOnRefreshListener {
            wv_web.reload()
            srl_web.isRefreshing = false
        }
        wv_web.loadUrl(args.getString("url"))
    }

    override fun onStart() {
        super.onStart()
        srl_web.viewTreeObserver.addOnScrollChangedListener(scrollChangedListener)
    }

    override fun onProgress(progress: Int) {
        pb_web.apply {
            val max = when {
                isPiePlus() -> 100
                isOreoPlus() -> 90
                isNougatPlus() -> 80
                isMarshmallowPlus() -> 70
                isLollipopPlus() -> 60
                else -> 50
            }
            if (progress >= max) {
                isVisible = false
            } else {
                setProgress(progress)
                isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        wv_web.destroy()
        super.onDestroyView()
    }

    override fun onStop() {
        srl_web.viewTreeObserver.removeOnScrollChangedListener(scrollChangedListener)
        super.onStop()
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