package ru.geekbrains.android2.openliblight.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.math.roundToInt

@Suppress("IMPLICIT_CAST_TO_ANY")
fun TextView.setStartDrawableImageFromUri(uri: String, placeholder: Int = 0) {
    val glideUrl = if (uri.isEmpty()) placeholder else GlideUrl(uri)

    Glide.with(context)
        .load(glideUrl)
        .placeholder(placeholder)
        .apply(
            RequestOptions
                .centerInsideTransform()
                //       .circleCropTransform()
                .override(80.dp(this.context))
        )
        .into(object : CustomViewTarget<TextView, Drawable>(this) {

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                view.setCompoundDrawable(resource, null, null, null)
            }

            override fun onResourceCleared(placeholder: Drawable?) {
                view.setCompoundDrawable(placeholder, null, null, null)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                view.setCompoundDrawable(errorDrawable, null, null, null)
            }

        })
}

fun TextView.setCompoundDrawable(
    left: Drawable? = null,
    top: Drawable? = null,
    right: Drawable? = null,
    bottom: Drawable? = null
) {
    setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
}

fun Int.dp(context: Context): Int =
    TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            context.resources.displayMetrics
        )
        .roundToInt()

@Suppress("IMPLICIT_CAST_TO_ANY")
fun ImageView.setImageFromUri(uri: String, placeholder: Int = 0) {
    val glideUrl = if (uri.isEmpty()) placeholder else GlideUrl(uri)
    Glide.with(context)
        .load(glideUrl)
        .placeholder(placeholder)
        .fitCenter()
        .into(this)

}
