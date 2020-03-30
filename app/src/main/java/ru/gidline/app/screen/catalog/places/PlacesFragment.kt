package ru.gidline.app.screen.catalog.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_places.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.model.Place
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.catalog.CatalogContract
import ru.gidline.app.screen.catalog.CatalogFilter

@Suppress("MemberVisibilityCanBePrivate")
class PlacesFragment : BaseFragment<PlacesContract.Presenter>(), PlacesContract.View {

    override val presenter: PlacesPresenter by instance()

    private var hasUpdatedList = false

    private val catalogFilter: CatalogFilter?
        get() {
            parentCallback<CatalogContract.View>(true) {
                return catalogFilter
            }
            return null
        }

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_places, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun onFilterUpdate() {
        catalogFilter?.let {
            pl_consulate.isVisible = it.typeId == R.id.ib_all || it.typeId == R.id.ib_consulate
            pl_migration.isVisible = it.typeId == R.id.ib_all || it.typeId == R.id.ib_migration
        }
    }

    override fun onNewLocation() {
        if (!hasUpdatedList) {
            hasUpdatedList = true
            pl_consulate.updateData()
            pl_migration.updateData()
        }
    }

    override fun onItemSelected(position: Int, item: Place) {

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