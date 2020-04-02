package ru.gidline.app.screen.documents

import android.content.Context
import ru.gidline.app.screen.base.BasePresenter

class DocumentsPresenter(context: Context) : BasePresenter<DocumentsContract.View>(context),
    DocumentsContract.Presenter