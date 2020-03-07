package ru.gidline.app.screen.search.vacancies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.VacancyRepository
import ru.gidline.app.local.dto.Vacancy
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.search.SearchFilter
import ru.gidline.app.screen.search.vacancies.adapter.VacanciesDecoration
import ru.gidline.app.screen.search.vacancies.adapter.VacancyAdapter
import ru.gidline.app.screen.vacancy.VacancyFragment

@Suppress("MemberVisibilityCanBePrivate")
class VacanciesFragment : BaseFragment<VacanciesContract.Presenter>(), VacanciesContract.View {

    override val presenter: VacanciesPresenter by instance()

    private val vacancyRepository: VacancyRepository by instance()

    private val searchFilter: SearchFilter by instance()

    private val adapter = VacancyAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_list, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv_list.also {
            it.setHasFixedSize(true)
            it.addItemDecoration(VacanciesDecoration(requireContext()))
            it.adapter = adapter
        }
        adapter.items.addAll(vacancyRepository.getAll())
        refreshData()
    }

    override fun refreshData(): Boolean {
        (rv_list.layoutManager as LinearLayoutManager).scrollToPosition(0)
        return adapter.run {
            filteredItems.clear()
            filteredItems.addAll(items.filter { it.match(searchFilter) })
            notifyDataSetChanged()
            filteredItems.isNotEmpty()
        }
    }

    override fun onItemSelected(position: Int, item: Vacancy) {
        makeCallback<IView> {
            addFragment(VacancyFragment.newInstance(item.id))
        }
    }

    companion object {

        fun newInstance(): VacanciesFragment {
            return VacanciesFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}