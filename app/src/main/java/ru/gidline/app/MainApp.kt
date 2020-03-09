package ru.gidline.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import coil.Coil
import coil.ImageLoader
import com.jakewharton.threetenabp.AndroidThreeTen
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import okhttp3.OkHttpClient
import org.jetbrains.anko.notificationManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import ru.gidline.app.extension.isOreoPlus
import ru.gidline.app.local.localModule
import timber.log.Timber

@Suppress("unused")
class MainApp : Application(), KodeinAware {

    override val kodein by Kodein.lazy {

        bind<Context>() with provider {
            applicationContext
        }

        import(localModule)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Class.forName("com.facebook.stetho.Stetho")
                .getDeclaredMethod("initializeWithDefaults", Context::class.java)
                .invoke(null, applicationContext)
        }
        if (isOreoPlus()) {
            notificationManager.createNotificationChannel(
                NotificationChannel("main", "Фоновое", NotificationManager.IMPORTANCE_LOW)
            )
        }
        AndroidThreeTen.init(this)
        Coil.setDefaultImageLoader(ImageLoader(applicationContext) {
            availableMemoryPercentage(0.5)
            bitmapPoolPercentage(0.5)
            crossfade(true)
            okHttpClient(
                OkHttpClient.Builder()
                    //.cache(CoilUtils.createDefaultCache(applicationContext))
                    .cache(null)
                    .build()
            )
        })
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("font/Arial.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }
}