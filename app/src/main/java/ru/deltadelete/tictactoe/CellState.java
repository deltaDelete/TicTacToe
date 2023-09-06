package ru.deltadelete.tictactoe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.content.res.AppCompatResources;

public enum CellState {
    EMPTY,
    X,
    O;
    public Drawable toIcon(Context context) {
        switch (this) {
            case X:
                return AppCompatResources.getDrawable(context, R.drawable.baseline_close_24);
            case O:
                return AppCompatResources.getDrawable(context, R.drawable.outline_circle_24);
            default:
                return null;
        }
    }
}
