package ru.gidline.app.screen.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class DocumentsFragment : BaseFragment<DocumentsContract.Presenter>(),
    DocumentsContract.View {

    override val presenter: DocumentsPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_documents, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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