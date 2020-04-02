package ru.gidline.app.screen.search.vacancies.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import kotlinx.android.synthetic.main.fragment_vacancy.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.local.repository.VacancyRepository
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.base.listener.IView

class VacancyFragment : BaseFragment<VacancyContract.Presenter>(), VacancyContract.View {

    override val presenter: VacancyPresenter by instance()

    private val vacancyRepository: VacancyRepository by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_vacancy, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vacancy = vacancyRepository.getById(args.getInt("id"))
        iv_logo.load("file:///android_asset/${vacancy.logo}")
        tv_organization.text = vacancy.organization
        tv_vacancy.text = vacancy.vacancy
        tv_payment.text = vacancy.payment
        tv_time.text = vacancy.perTime
        tv_form.apply {
            setBackgroundColor(vacancy.color)
            text = vacancy.form
        }
        al_offer.updateText(vacancy)
        al_responsibility.updateText(vacancy)
        al_requirement.updateText(vacancy)
        tv_place.text = vacancy.location
        mb_respond.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mb_respond -> {
                activityCallback<IView> {
                    popFragment(null, false)
                }
            }
        }
    }

    companion object {

        fun newInstance(id: Int): VacancyFragment {
            return VacancyFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
        }
    }
}