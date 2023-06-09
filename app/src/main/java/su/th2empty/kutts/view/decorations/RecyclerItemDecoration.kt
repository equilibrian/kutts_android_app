/*
 * Copyright (c) 2023 Denis <th2empty> Novikov
 *
 * This file is part of KUTTS.
 * KUTTS is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License version 3, as published by the Free Software Foundation.
 * KUTTS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License version 3 for more details.
 * You should have received a copy of the GNU General Public License version 3 along with KUTTS. If not, see <https://www.gnu.org/licenses/gpl-3.0.html>.
 *
 */

package su.th2empty.kutts.view.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Item decoration for RecyclerView to add spacing around each item.
 * @param top    The top spacing in pixels.
 * @param left   The left spacing in pixels.
 * @param right  The right spacing in pixels.
 * @param bottom The bottom spacing in pixels.
 */
class RecyclerItemDecoration(
        private val top: Int = 0,
        private val left: Int = 0,
        private val right: Int = 0,
        private val bottom: Int = 0
    ) : RecyclerView.ItemDecoration() {

    /**
     * Adds item offsets to provide spacing around each item in the RecyclerView.
     * @param outRect The Rect object to receive the item offsets.
     * @param view    The child view being decorated.
     * @param parent  The RecyclerView.
     * @param state   The current state of RecyclerView.
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = top
        outRect.left = left
        outRect.right = right
        outRect.bottom = bottom
    }
}