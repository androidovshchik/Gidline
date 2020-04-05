package ru.gidline.app.screen.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class ResumeFragment : BaseFragment<ResumeContract.Presenter>(), ResumeContract.View {

    override val presenter: ResumePresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    companion object {

        fun newInstance(): ResumeFragment {
            return ResumeFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}