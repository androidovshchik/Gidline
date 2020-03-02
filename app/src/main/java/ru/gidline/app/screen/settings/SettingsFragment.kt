package ru.gidline.app.screen.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseFragment

class SettingsFragment : BaseFragment<SettingsContract.Presenter>(), SettingsContract.View {

    override val presenter: SettingsPresenter by instance()

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View {
        return inflater.inflate(R.layout.fragment_settings, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

        fun newInstance(id: Int): SettingsFragment {
            return SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}