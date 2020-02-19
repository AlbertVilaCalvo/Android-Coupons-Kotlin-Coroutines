package vc.albert.coupons.ui.misc;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * Adds space to the 4 sides of the RecyclerView item. It's for a RecyclerView with VERTICAL
 * orientation since it only adds space at the top to the first item.
 * <p>
 * From https://stackoverflow.com/a/27664023/4034572
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int spaceInPx;

    public SpaceItemDecoration(Context context, @DimenRes int spaceDimension) {
        this.spaceInPx = (int) context.getResources().getDimension(spaceDimension);
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view,
                               @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        outRect.left = spaceInPx;
        outRect.right = spaceInPx;
        outRect.bottom = spaceInPx;

        // Add top margin only for the first item to avoid double spaceInPx between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spaceInPx;
        }
    }

}
