package ru.gidline.app.screen.main.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.api.load
import coil.transform.CircleCropTransformation
import kotlinx.android.synthetic.main.merge_header.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.temporal.ChronoUnit
import ru.gidline.app.R
import ru.gidline.app.local.Preferences
import timber.log.Timber

class HeaderLayout : RelativeLayout, KodeinAware {

    override val kodein by closestKodein()

    private val preferences: Preferences by instance()

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    @SuppressLint("Recycle")
    private fun init(attrs: AttributeSet?) {
        setBackgroundResource(R.drawable.background_header)
        View.inflate(context, R.layout.merge_header, this)
    }

    @Suppress("DEPRECATION")
    fun updateData() = preferences.also {
        val genderDrawable = if (it.isMan) R.drawable.avatar_man else R.drawable.avatar_woman
        iv_avatar.load(Uri.parse("file://${it.avatarPath}")) {
            error(ContextCompat.getDrawable(context, genderDrawable)!!.mutate().apply {
                setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
            })
            transformations(CircleCropTransformation())
        }
        tv_name.text = it.username
        tv_surname.text = it.surname
        if (it.hasMigrationData) {
            val now = LocalDate.now()
            val formatter = DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yy"))
                .toFormatter()
            val entryRussia = it.dateEntryRussia
            if (entryRussia != null) {
                try {
                    val date = LocalDate.parse(entryRussia, formatter)
                        .plusDays(364)
                    val days = ChronoUnit.DAYS.between(now, date).toInt()
                    tv_border_days.text = resources.getQuantityString(R.plurals.days, days, days)
                    toggleViews(true, tv_before_border, tv_border_days)
                } catch (e: Throwable) {
                    Timber.e(e)
                    toggleViews(false, tv_before_border, tv_border_days)
                }
            } else {
                toggleViews(false, tv_before_border, tv_border_days)
            }
            val firstPatent = it.dateFirstPatent
            if (firstPatent != null) {
                try {
                    val date = LocalDate.parse(firstPatent, formatter)
                        .plusDays(364)
                    val days = ChronoUnit.DAYS.between(now, date).toInt()
                    tv_patent_days.text = resources.getQuantityString(R.plurals.days, days, days)
                    toggleViews(true, tv_before_patent, tv_patent_days)
                } catch (e: Throwable) {
                    Timber.e(e)
                    toggleViews(false, tv_before_patent, tv_patent_days)
                }
            } else {
                toggleViews(false, tv_before_patent, tv_patent_days)
            }
        } else {
            toggleViews(false, tv_before_border, tv_border_days)
            toggleViews(false, tv_before_patent, tv_patent_days)
        }
    }

    private fun toggleViews(show: Boolean, vararg views: View) {
        views.forEach {
            it.isVisible = show
        }
    }

    override fun hasOverlappingRendering() = false
}