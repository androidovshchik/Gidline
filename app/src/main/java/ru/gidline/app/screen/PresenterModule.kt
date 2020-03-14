package ru.gidline.app.screen

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.contexted
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import ru.gidline.app.screen.catalog.CatalogFragment
import ru.gidline.app.screen.catalog.CatalogPresenter
import ru.gidline.app.screen.catalog.map.MapFragment
import ru.gidline.app.screen.catalog.map.MapPresenter
import ru.gidline.app.screen.catalog.places.PlacesFragment
import ru.gidline.app.screen.catalog.places.PlacesPresenter
import ru.gidline.app.screen.documents.DocumentsFragment
import ru.gidline.app.screen.documents.DocumentsPresenter
import ru.gidline.app.screen.documents.browser.BrowserFragment
import ru.gidline.app.screen.documents.browser.BrowserPresenter
import ru.gidline.app.screen.main.MainActivity
import ru.gidline.app.screen.main.MainPresenter
import ru.gidline.app.screen.main.categories.CategoriesFragment
import ru.gidline.app.screen.main.categories.CategoriesPresenter
import ru.gidline.app.screen.notifications.NotificationsFragment
import ru.gidline.app.screen.notifications.NotificationsPresenter
import ru.gidline.app.screen.notifications.notification.NotificationFragment
import ru.gidline.app.screen.notifications.notification.NotificationPresenter
import ru.gidline.app.screen.search.SearchFragment
import ru.gidline.app.screen.search.SearchPresenter
import ru.gidline.app.screen.search.f04.F04Fragment
import ru.gidline.app.screen.search.f04.F04Presenter
import ru.gidline.app.screen.search.filter.FilterFragment
import ru.gidline.app.screen.search.filter.FilterPresenter
import ru.gidline.app.screen.search.vacancies.VacanciesFragment
import ru.gidline.app.screen.search.vacancies.VacanciesPresenter
import ru.gidline.app.screen.search.vacancies.vacancy.VacancyFragment
import ru.gidline.app.screen.search.vacancies.vacancy.VacancyPresenter
import ru.gidline.app.screen.settings.SettingsFragment
import ru.gidline.app.screen.settings.SettingsPresenter
import ru.gidline.app.screen.splash.SplashActivity
import ru.gidline.app.screen.splash.SplashPresenter

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

    bind<CategoriesPresenter>() with contexted<CategoriesFragment>().provider {
        CategoriesPresenter(instance()).apply {
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

    bind<VacanciesPresenter>() with contexted<VacanciesFragment>().provider {
        VacanciesPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<F04Presenter>() with contexted<F04Fragment>().provider {
        F04Presenter(instance()).apply {
            attachView(context)
        }
    }

    bind<VacancyPresenter>() with contexted<VacancyFragment>().provider {
        VacancyPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<DocumentsPresenter>() with contexted<DocumentsFragment>().provider {
        DocumentsPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<BrowserPresenter>() with contexted<BrowserFragment>().provider {
        BrowserPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<SettingsPresenter>() with contexted<SettingsFragment>().provider {
        SettingsPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<CatalogPresenter>() with contexted<CatalogFragment>().provider {
        CatalogPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<PlacesPresenter>() with contexted<PlacesFragment>().provider {
        PlacesPresenter(instance()).apply {
            attachView(context)
        }
    }

    bind<MapPresenter>() with contexted<MapFragment>().provider {
        MapPresenter(instance()).apply {
            attachView(context)
        }
    }
}