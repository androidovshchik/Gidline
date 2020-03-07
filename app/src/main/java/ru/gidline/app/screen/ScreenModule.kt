package ru.gidline.app.screen

import android.app.Activity
import android.widget.ArrayAdapter
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import ru.gidline.app.R
import ru.gidline.app.screen.common.ToastPopup
import ru.gidline.app.screen.main.MainActivity
import ru.gidline.app.screen.main.MenuPopup
import ru.gidline.app.screen.search.ChipsPopup
import ru.gidline.app.screen.search.SearchFilter
import ru.gidline.app.screen.search.SearchFragment

val screenModule = Kodein.Module("screen") {

    bind<MenuPopup>() with contexted<MainActivity>().provider {
        MenuPopup(context)
    }

    bind<ChipsPopup>() with contexted<SearchFragment>().provider {
        ChipsPopup(context.requireContext())
    }

    bind<ToastPopup>() with contexted<Activity>().factory { text: String ->
        ToastPopup(text, context)
    }

    bind<ArrayAdapter<String>>() with provider {
        ArrayAdapter(instance(), R.layout.item_spinner, mutableListOf<String>())
    }

    bind<SearchFilter>() with singleton {
        SearchFilter()
    }
}