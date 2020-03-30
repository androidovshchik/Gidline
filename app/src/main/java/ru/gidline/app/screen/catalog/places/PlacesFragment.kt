package ru.gidline.app.screen.catalog.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_places.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.catalog.CatalogContract
import ru.gidline.app.screen.catalog.CatalogFilter

@Suppress("MemberVisibilityCanBePrivate")
class PlacesFragment : BaseFragment<PlacesContract.Presenter>(), PlacesContract.View {

    override val presenter: PlacesPresenter by instance()

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
        fab_up.setOnClickListener(this)
        nsv_places.addOnLayoutChangeListener(this)
    }

    override fun onFilterUpdate() {
        catalogFilter?.let {
            pl_consulate.isVisible = it.typeId == R.id.ib_all || it.typeId == R.id.ib_consulate
            pl_migration.isVisible = it.typeId == R.id.ib_all || it.typeId == R.id.ib_migration
        }
    }

    override fun onLocationUpdate() {
        pl_consulate.updateData()
        pl_migration.updateData()
    }

    override fun onLayoutChange(
        v: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
    ) {
        fab_up.isVisible
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_up -> {
                val scrollTo: Int = (childView.getParent()
                    .getParent() as View).top + childView.getTop()
                nsv_places.smoothScrollTo(0, scrollTo)
            }
        }
    }

    override fun onDestroyView() {
        nsv_places.removeOnLayoutChangeListener(this)
        super.onDestroyView()
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