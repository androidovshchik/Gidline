package ru.gidline.app.screen.resume.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class OrderFragment : BaseFragment<OrderContract.Presenter>(), OrderContract.View {

    override val presenter: OrderPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    companion object {

        fun newInstance(): OrderFragment {
            return OrderFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}