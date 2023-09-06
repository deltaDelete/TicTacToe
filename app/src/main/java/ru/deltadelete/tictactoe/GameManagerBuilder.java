package ru.deltadelete.tictactoe;

import android.content.Context;
import android.widget.LinearLayout;

public class GameManagerBuilder {
    private int sideLength = 3;
    private Context context;
    private LinearLayout layout;

    public GameManagerBuilder setSideLength(int sideLength) {
        this.sideLength = sideLength;
        return this;
    }

    public GameManagerBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public GameManager createGameManager() {
        return new GameManager(sideLength, context, layout);
    }

    public GameManagerBuilder setLayout(LinearLayout layout) {
        this.layout = layout;
        return this;
    }
}