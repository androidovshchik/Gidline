package ru.gidline.app.screen.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.gidline.app.R
import ru.gidline.app.screen.base.BaseDialogFragment

class IntroDialogFragment : BaseDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_intro, root, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    companion object {

        fun newInstance(): IntroDialogFragment {
            return IntroDialogFragment().apply {
                arguments = Bundle().apply {
                }
            }
        }
    }
}