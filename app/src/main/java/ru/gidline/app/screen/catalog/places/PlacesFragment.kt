package ru.gidline.app.screen.catalog.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import kotlinx.android.synthetic.main.fragment_places.*
import org.jetbrains.anko.dip
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
        iv_up.setOnClickListener(this)
        nsv_places.setOnScrollChangeListener { _: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
            iv_up.isVisible = scrollY > 0
        }
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_up -> {
                val scrollY = nsv_places.scrollY
                val migrationY = pl_migration.y.toInt()
                when {
                    scrollY > migrationY ->
                        nsv_places.smoothScrollTo(0, migrationY - requireContext().dip(6))
                    else -> nsv_places.smoothScrollTo(0, 0)
                }
            }
        }
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