package ru.gidline.app.screen.search.f04

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class F04Fragment : BaseFragment<F04Contract.Presenter>(), F04Contract.View {

    override val presenter: F04Presenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_404, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

    companion object {

        fun newInstance(): F04Fragment {
            return F04Fragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}