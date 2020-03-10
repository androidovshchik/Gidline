package ru.gidline.app.screen.main.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_categories.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.search.SearchFragment
import ru.gidline.app.screen.settings.SettingsFragment

class CategoriesFragment : BaseFragment<CategoriesContract.Presenter>(), CategoriesContract.View {

    override val presenter: CategoriesPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_categories, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cl_search.setOnClickListener(this)
        ib_settings.setOnClickListener(this)
        ib_map.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cl_search -> {
                activityCallback<IView> {
                    addFragment(SearchFragment.newInstance())
                }
            }
            R.id.ib_settings -> {
                activityCallback<IView> {
                    putFragment(SettingsFragment.newInstance())
                }
            }
        }
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