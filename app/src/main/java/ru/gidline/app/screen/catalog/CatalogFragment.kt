package ru.gidline.app.screen.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class CatalogFragment : BaseFragment<CatalogContract.Presenter>(), CatalogContract.View {

    override val presenter: CatalogPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_search, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

        fun newInstance(): CatalogFragment {
            return CatalogFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}