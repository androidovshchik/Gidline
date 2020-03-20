package ru.gidline.app.screen.catalog.places

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.merge_places.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import ru.gidline.app.R
import ru.gidline.app.extension.use
import ru.gidline.app.local.model.Place
import ru.gidline.app.local.repository.PlaceRepository
import ru.gidline.app.screen.catalog.places.adapter.PlacesAdapter

class PlacesLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr), KodeinAware, PlacesContract.Recycler {

    override val kodein by closestKodein()

    private val placeRepository: PlaceRepository by instance()

    private val adapter = PlacesAdapter(this)

    init {
        init(attrs)
    }

    @SuppressLint("Recycle")
    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.merge_places, this)
        attrs?.let { set ->
            context.obtainStyledAttributes(set, R.styleable.PlacesLayout).use {
                getString(R.styleable.PlacesLayout_text)?.let {
                    tv_name.text = it
                    adapter.items.addAll(placeRepository.getByType(it))
                }
            }
        }
        rv_places.also {
            it.adapter = adapter
        }
    }

    override fun onItemSelected(position: Int, item: Place) {

    }

    override fun hasOverlappingRendering() = false
}