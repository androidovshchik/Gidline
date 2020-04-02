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
import kotlin.math.max

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

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            val scrollY = nsv_places.scrollY
            if (scrollY < context?.dip(100) ?: 0) {
                nsv_places.scrollTo(0, 0)
            }
        }
    }

    override fun onFilterUpdate() {
        catalogFilter?.let {
            if (!pl_consulate.isVisible) {
                pl_consulate.updateData(true)
            }
            pl_consulate.isVisible = it.typeId == R.id.ib_all || it.typeId == R.id.ib_consulate
            if (!pl_migration.isVisible) {
                pl_migration.updateData(true)
            }
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
                nsv_places.smoothScrollTo(
                    0, max(
                        0, when {
                            scrollY > migrationY -> migrationY
                            else -> 0
                        } - requireContext().dip(10)
                    )
                )
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