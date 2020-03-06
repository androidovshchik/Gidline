package ru.gidline.app.screen.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.makeCallback
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.main.MainContract

class CategoriesFragment : BaseFragment<CategoriesContract.Presenter>(), CategoriesContract.View {

    override val presenter: CategoriesPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        context?.makeCallback<MainContract.View> {
            setTitle(getString(R.string.app_name))
            toggleBottomNav(true)
        }
        return inflater.inflate(R.layout.fragment_categories, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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