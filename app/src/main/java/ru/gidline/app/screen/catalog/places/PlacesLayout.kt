package ru.gidline.app.screen.catalog.places

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.merge_places.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.activityCallback
import ru.gidline.app.extension.use
import ru.gidline.app.local.model.Place
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.screen.base.listener.IView
import ru.gidline.app.screen.base.shape.ShapeRelativeLayout
import ru.gidline.app.screen.catalog.CatalogContract
import ru.gidline.app.screen.catalog.places.adapter.PlacesAdapter
import ru.gidline.app.screen.catalog.places.adapter.PlacesDecoration

class PlacesLayout : ShapeRelativeLayout, KodeinAware, PlacesContract.Recycler {

    override val kodein by closestKodein()

    private val placeRepository: PlaceRepository by instance()

    private val adapter = PlacesAdapter(this)

    private var type: String? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init2(attrs)
    }

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init2(attrs)
    }

    @Suppress("UNUSED_PARAMETER")
    @SuppressLint("Recycle", "SetTextI18n")
    private fun init2(attrs: AttributeSet?) {
        setBackgroundResource(R.drawable.background_place)
        View.inflate(context, R.layout.merge_places, this)
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.PlacesLayout).use {
                getString(R.styleable.PlacesLayout_text)?.let {
                    tv_name.text = it
                    type = when (it) {
                        resources.getString(R.string.places_consulate) -> Place.CONSULATE
                        resources.getString(R.string.places_migration) -> Place.MIGRATION
                        else -> null
                    }
                    adapter.apply {
                        iconId = when (type) {
                            Place.CONSULATE -> R.drawable.ic_consulate
                            Place.MIGRATION -> R.drawable.ic_migration
                            else -> 0
                        }
                        items.addAll(placeRepository.getByType(type))
                    }
                }
            }
        }
        updateToggle()
        c_toggle.setOnClickListener {
            adapter.apply {
                limited = !limited
                notifyDataSetChanged()
            }
            updateToggle()
        }
        rv_places.also {
            it.isNestedScrollingEnabled = false
            it.addItemDecoration(PlacesDecoration(context))
            it.adapter = adapter
        }
    }

    override fun onItemSelected(position: Int, item: Place) {
        context.activityCallback<IView> {
            when (val topFragment = topFragment) {
                is CatalogContract.View -> {
                    topFragment.onItemSelected(position, item)
                }
            }
        }
    }

    fun updateData(limit: Boolean = false) {
        adapter.apply {
            if (limit) {
                limited = true
            }
            items.clear()
            items.addAll(placeRepository.getByType(type))
            notifyDataSetChanged()
            updateToggle()
        }
    }

    private fun updateToggle() {
        val count = adapter.items.size
        if (count > 3) {
            c_toggle.apply {
                isVisible = true
                text = if (adapter.limited) "смотреть все $count" else "СВЕРНУТЬ"
            }
        }
    }

    override fun hasOverlappingRendering() = false
}