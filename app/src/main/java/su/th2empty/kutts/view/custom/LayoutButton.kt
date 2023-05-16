/*
 * Copyright (c) 2023 Denis <th2empty> Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.view.custom

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import su.th2empty.kutts.R


/**
 * Custom layout button that displays an icon, text and optional end icon.
 * @param context The context to use.
 * @param attrs The attribute set to apply to this layout.
 * @param defStyleAttr The default style resource to apply to this layout.
 */
class LayoutButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var listener: OnClickListener? = null

    // Properties initialized with default values
    private var iconStartView: ImageView = ImageView(context).apply {
        visibility = View.GONE
    }
    private var textView: TextView = TextView(context)
    private var iconEndView: ImageView = ImageView(context).apply {
        visibility = View.GONE
    }

    init {
        // Get styled attributes from XML layout
        val a = context.obtainStyledAttributes(attrs, R.styleable.LayoutButton, defStyleAttr, 0)

        // Get custom attributes values
        val iconStartRes = a.getResourceId(R.styleable.LayoutButton_iconStart, 0)
        val text = a.getString(R.styleable.LayoutButton_text)
        val iconEndRes = a.getResourceId(R.styleable.LayoutButton_iconEnd, 0)
        val cornerRadius = a.getDimensionPixelSize(R.styleable.LayoutButton_cornerRadius, dpToPx(12))
        val colorSurfaceAttr = com.google.android.material.R.attr.colorBackgroundFloating
        val outValue = TypedValue()
        context.theme.resolveAttribute(colorSurfaceAttr, outValue, true)
        val defaultColor = outValue.data
        val backgroundColor = a.getColor(
            R.styleable.LayoutButton_backgroundColor,
            defaultColor
        )

        // Recycle the typed array
        a.recycle()

        // Inflate custom layout XML
        LayoutInflater.from(context).inflate(R.layout.custom_view_layout_button, this, true)

        // Apply corner radius to background
        val background = GradientDrawable()
        background.cornerRadii = floatArrayOf(
            cornerRadius.toFloat(), cornerRadius.toFloat(),
            cornerRadius.toFloat(), cornerRadius.toFloat(),
            cornerRadius.toFloat(), cornerRadius.toFloat(),
            cornerRadius.toFloat(), cornerRadius.toFloat()
        )
        background.setColor(backgroundColor)
        setBackground(background.mutate())

        // Find views in custom layout XML
        iconStartView = findViewById(R.id.icon_start)
        textView = findViewById(R.id.text)
        iconEndView = findViewById(R.id.icon_end)

        // Set values for custom attributes
        if (iconStartRes != 0) {
            iconStartView.setImageResource(iconStartRes)
            iconStartView.visibility = View.VISIBLE
        }

        if (text != null) {
            textView.text = text
        }

        if (iconEndRes != 0) {
            iconEndView.setImageResource(iconEndRes)
            iconEndView.visibility = View.VISIBLE
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (listener != null) listener!!.onClick(this)
        }
        return super.dispatchTouchEvent(event)
    }


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP && (event.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || event.keyCode == KeyEvent.KEYCODE_ENTER)) {
            if (listener != null) listener!!.onClick(this)
        }
        return super.dispatchKeyEvent(event)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        this.listener = listener
    }

    /**
     * Sets the start icon of this layout button.
     * @param resId The drawable resource ID for the icon to set.
     */
    fun setIconStart(@DrawableRes resId: Int) {
        if (resId != 0) {
            iconStartView.setImageResource(resId)
            iconStartView.visibility = View.VISIBLE
        } else {
            iconStartView.visibility = View.GONE
        }
    }

    /**
     * Sets the text of this layout button.
     * @param text The text to set.
     */
    fun setText(text: String) {
        textView.text = text
    }

    /**
     * Sets the end icon of this layout button.
     * @param resId The drawable resource ID for the icon to set.
     */
    fun setIconEnd(@DrawableRes resId: Int) {
        if (resId != 0) {
            iconEndView.setImageResource(resId)
            iconEndView.visibility = View.VISIBLE
        } else {
            iconEndView.visibility = View.GONE
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }
}