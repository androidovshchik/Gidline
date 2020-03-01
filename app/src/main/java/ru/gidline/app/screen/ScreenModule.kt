package ru.gidline.app.screen

import android.widget.ArrayAdapter
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import ru.gidline.app.R
import ru.gidline.app.screen.filter.FilterFragment
import ru.gidline.app.screen.filter.FilterPresenter
import ru.gidline.app.screen.main.MainActivity
import ru.gidline.app.screen.main.MainPresenter
import ru.gidline.app.screen.search.ChipsPopup
import ru.gidline.app.screen.search.SearchFilter
import ru.gidline.app.screen.search.SearchFragment
import ru.gidline.app.screen.search.SearchPresenter
import ru.gidline.app.screen.search.f04.F04Fragment
import ru.gidline.app.screen.search.f04.F04Presenter
import ru.gidline.app.screen.search.vacancies.VacanciesFragment
import ru.gidline.app.screen.search.vacancies.VacanciesPresenter
import ru.gidline.app.screen.splash.SplashActivity
import ru.gidline.app.screen.splash.SplashPresenter
import ru.gidline.app.screen.vacancy.VacancyFragment
import ru.gidline.app.screen.vacancy.VacancyPresenter

val screenModule = Kodein.Module("screen") {

    bind<SplashPresenter>() with contexted<SplashActivity>().provider {
        SplashPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<MainPresenter>() with contexted<MainActivity>().provider {
        MainPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<F04Presenter>() with contexted<F04Fragment>().provider {
        F04Presenter(instance()).apply {
            attachView(context)
        }
    }

    bind<FilterPresenter>() with contexted<FilterFragment>().provider {
        FilterPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<ArrayAdapter<String>>() with provider {
        ArrayAdapter(instance(), R.layout.item_spinner, mutableListOf<String>())
    }

    bind<SearchPresenter>() with contexted<SearchFragment>().provider {
        SearchPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<SearchFilter>() with singleton {
        SearchFilter()
    }

    bind<ChipsPopup>() with contexted<SearchFragment>().provider {
        ChipsPopup(context.requireContext())
    }

    bind<VacanciesPresenter>() with contexted<VacanciesFragment>().provider {
        VacanciesPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<VacancyPresenter>() with contexted<VacancyFragment>().provider {
        VacancyPresenter(instance()).apply {
            attachView(context)
        }
    }
}