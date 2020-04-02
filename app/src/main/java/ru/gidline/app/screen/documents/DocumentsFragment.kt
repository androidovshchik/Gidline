package ru.gidline.app.screen.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_documents.*
import kotlinx.android.synthetic.main.merge_category.view.*
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.documents.browser.BrowserFragment

class DocumentsFragment : BaseFragment<DocumentsContract.Presenter>(),
    DocumentsContract.View {

    override val presenter: DocumentsPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_documents, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cl_hand_patent.setOnClickListener(this)
        cl_doc_home.setOnClickListener(this)
        cl_daw_home.setOnClickListener(this)
        cl_globe_pas.setOnClickListener(this)
        cl_ban.setOnClickListener(this)
        cl_inn.setOnClickListener(this)
        cl_sheriff.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        activityCallback<IView> {
            val name = v.tv_name.text.toString()
            val url = when (v.id) {
                R.id.cl_hand_patent -> "http://xn--b1afk4ade4e.xn--b1ab2a0a.xn--b1aew.xn--p1ai/info-service.htm?sid=2060"
                R.id.cl_doc_home -> "https://xn--b1ab2a0a.xn--b1aew.xn--p1ai/services/residence"
                R.id.cl_daw_home -> "https://xn--b1ab2a0a.xn--b1aew.xn--p1ai/services/trp"
                R.id.cl_globe_pas -> "http://xn--b1afk4ade4e.xn--b1ab2a0a.xn--b1aew.xn--p1ai/info-service.htm?sid=2061"
                R.id.cl_ban -> "http://xn--b1afk4ade4e.xn--b1ab2a0a.xn--b1aew.xn--p1ai/info-service.htm?sid=3000"
                R.id.cl_inn -> "https://service.nalog.ru/static/personal-data.html?svc=inn&from=%2Finn.do"
                R.id.cl_sheriff -> "http://fssprus.ru/"
                else -> return
            }
            putFragment(BrowserFragment.newInstance(name, url))
        }
    }

    companion object {

        fun newInstance(): DocumentsFragment {
            return DocumentsFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}