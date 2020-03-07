package ru.gidline.app.screen.splash

import android.os.Bundle
import android.os.Handler
import org.jetbrains.anko.startActivity
import org.kodein.di.generic.instance
import ru.gidline.app.BuildConfig
import ru.gidline.app.local.BellRepository
import ru.gidline.app.screen.base.BaseActivity
import ru.gidline.app.screen.main.MainActivity

class SplashActivity : BaseActivity<SplashContract.Presenter>(), SplashContract.View {

    override val presenter: SplashPresenter by instance()

    private val bellRepository: BellRepository by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bellRepository.setDummyData()
        Handler().postDelayed({
            startActivity<MainActivity>()
            finish()
        }, if (BuildConfig.DEBUG) 200L else 2000L)
    }

    override fun onBackPressed() {}
}
