package ru.gidline.app.screen.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_categories.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.BellRepository
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.base.listeners.IView
import ru.gidline.app.screen.main.MainContract
import ru.gidline.app.screen.search.SearchFragment

class CategoriesFragment : BaseFragment<CategoriesContract.Presenter>(), CategoriesContract.View {

    override val presenter: CategoriesPresenter by instance()

    private val bellRepository: BellRepository by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        makeCallback<MainContract.View> {
            setTitle(getString(R.string.app_name))
            notifyBell(bellRepository.count)
            toggleBottomNav(true)
        }
        return inflater.inflate(R.layout.fragment_categories, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cl_search.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cl_search -> {
                makeCallback<IView> {
                    putFragment(SearchFragment.newInstance())
                }
            }
        }
    }

    override fun onDestroyView() {
        makeCallback<MainContract.View> {
            notifyBell(null)
            toggleBottomNav(false)
        }
        super.onDestroyView()
    }

    companion object {

        fun newInstance(): CategoriesFragment {
            return CategoriesFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}