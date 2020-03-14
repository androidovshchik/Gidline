package ru.gidline.app.screen.catalog.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

@Suppress("MemberVisibilityCanBePrivate")
class PlacesFragment : BaseFragment<PlacesContract.Presenter>(), PlacesContract.View {

    override val presenter: PlacesPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_places, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

        fun newInstance(): PlacesFragment {
            return PlacesFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}