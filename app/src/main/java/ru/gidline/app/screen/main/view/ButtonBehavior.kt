package ru.gidline.app.screen.main.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import kotlin.math.min

class ButtonBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<MaterialButton>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: MaterialButton,
        dependency: View
    ): Boolean {
        return dependency is SnackbarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: MaterialButton,
        dependency: View
    ): Boolean {
        child.translationY = min(0f, dependency.translationY - dependency.height)
        return true
    }

    override fun onDependentViewRemoved(
        parent: CoordinatorLayout,
        child: MaterialButton,
        dependency: View
    ) {
        child.translationY = min(0, parent.bottom - child.bottom).toFloat()
    }
}