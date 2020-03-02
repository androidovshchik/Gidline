package ru.gidline.app.screen.settings

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class SettingsPresenter(context: Context) : BasePresenter<SettingsContract.View>(context),
    SettingsContract.Presenter