package ru.gidline.app.screen.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_catalog.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class CatalogFragment : BaseFragment<CatalogContract.Presenter>(), CatalogContract.View {

    override val presenter: CatalogPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_catalog, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tl_catalog.also {
            it.addTab(it.newTab().setText("СПИСОК"))
            it.addTab(it.newTab().setText("КАРТА"))
            it.addOnTabSelectedListener(this)
        }
        hideFragment(R.id.f_map)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabSelected(tab: TabLayout.Tab) {
        when (tl_catalog.selectedTabPosition) {
            0 -> {
                hideFragment(R.id.f_map)
                showFragment(R.id.f_places)
            }
            else -> {
                hideFragment(R.id.f_places)
                showFragment(R.id.f_map)
            }
        }
    }

    override fun onDestroyView() {
        tl_catalog.removeOnTabSelectedListener(this)
        super.onDestroyView()
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