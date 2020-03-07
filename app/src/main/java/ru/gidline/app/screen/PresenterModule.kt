package ru.gidline.app.screen

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.contexted
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import ru.gidline.app.screen.categories.CategoriesFragment
import ru.gidline.app.screen.categories.CategoriesPresenter
import ru.gidline.app.screen.filter.FilterFragment
import ru.gidline.app.screen.filter.FilterPresenter
import ru.gidline.app.screen.main.MainActivity
import ru.gidline.app.screen.main.MainPresenter
import ru.gidline.app.screen.notification.NotificationFragment
import ru.gidline.app.screen.notification.NotificationPresenter
import ru.gidline.app.screen.notifications.NotificationsFragment
import ru.gidline.app.screen.notifications.NotificationsPresenter
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

val presenterModule = Kodein.Module("presenter") {

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

    bind<CategoriesPresenter>() with contexted<CategoriesFragment>().provider {
        CategoriesPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<NotificationsPresenter>() with contexted<NotificationsFragment>().provider {
        NotificationsPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<NotificationPresenter>() with contexted<NotificationFragment>().provider {
        NotificationPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<SearchPresenter>() with contexted<SearchFragment>().provider {
        SearchPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<FilterPresenter>() with contexted<FilterFragment>().provider {
        FilterPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<F04Presenter>() with contexted<F04Fragment>().provider {
        F04Presenter(instance()).apply {
            attachView(context)
        }
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