@file:Suppress("DEPRECATION")

package ru.gidline.app.screen.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import org.jetbrains.anko.inputMethodManager
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import ru.gidline.app.extension.activityCallback

abstract class BaseDialogFragment : DialogFragment(), KodeinAware {

    override val kodein by closestKodein()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BaseDialog(requireActivity())
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, root: ViewGroup?, bundle: Bundle?): View? {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return null
    }

    @CallSuper
    override fun dismiss() {
        context?.inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
        super.dismiss()
    }

    inline fun <reified T> activityCallback(action: T.() -> Unit) {
        context?.activityCallback(action)
    }

    inline fun <reified T> parentCallback(nullableView: Boolean = false, action: T.() -> Unit) {
        parentFragment?.let {
            if (it is T && (nullableView || it.view != null)) {
                action(it)
            }
        }
    }
}