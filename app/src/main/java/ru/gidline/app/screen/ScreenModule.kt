package ru.gidline.app.screen

import android.app.Activity
import android.widget.ArrayAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import ru.gidline.app.screen.common.ToastPopup
import ru.gidline.app.screen.main.MainActivity
import ru.gidline.app.screen.main.MenuPopup
import ru.gidline.app.screen.search.ChipsPopup
import ru.gidline.app.screen.search.SearchFragment

val screenModule = Kodein.Module("screen") {

    bind<FusedLocationProviderClient>() with provider {
        LocationServices.getFusedLocationProviderClient(instance())
    }

    bind<ArrayAdapter<String>>() with factory { layout: Int ->
        ArrayAdapter(instance(), layout, mutableListOf<String>())
    }

    bind<ToastPopup>() with contexted<Activity>().factory { text: String ->
        ToastPopup(text, context)
    }

    bind<MenuPopup>() with contexted<MainActivity>().provider {
        MenuPopup(context)
    }

    bind<ChipsPopup>() with contexted<SearchFragment>().provider {
        ChipsPopup(context.requireContext())
    }
}