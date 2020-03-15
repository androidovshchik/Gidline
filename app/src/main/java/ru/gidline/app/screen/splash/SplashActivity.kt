package ru.gidline.app.screen.splash

import android.os.Bundle
import org.jetbrains.anko.startActivity
import org.kodein.di.generic.instance
import ru.gidline.app.screen.base.BaseActivity
import ru.gidline.app.screen.main.MainActivity

class SplashActivity : BaseActivity<SplashContract.Presenter>(), SplashContract.View {

    override val presenter: SplashPresenter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initRepos(applicationContext)
    }

    override fun closeSplash() {
        startActivity<MainActivity>()
        finish()
    }

    override fun onBackPressed() {}
}
