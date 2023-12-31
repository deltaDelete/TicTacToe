package ru.deltadelete.tictactoe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.Arrays;

public class GameManager {
    private final int sideLength;
    private final Context context;
    private final LinearLayout layout;
    private CellState[] board;
    private CellState _currentPlayer = CellState.X;
    private ImageButton[] buttons;
    private ColorStateList defaultColor;
    private ColorStateList winColor;
    private ColorStateList drawColor;

    public GameManager(int sideLength, Context context, LinearLayout layout) {
        this.sideLength = sideLength;
        this.context = context;
        this.layout = layout;
        board = new CellState[this.sideLength * this.sideLength];
        buttons = new ImageButton[this.sideLength * this.sideLength];
        Arrays.fill(board, CellState.EMPTY);
        drawColor = context.getColorStateList(R.color.md_theme_dark_error);
        winColor = context.getColorStateList(R.color.md_theme_dark_tertiary);
        defaultColor = context.getColorStateList(R.color.md_theme_dark_primary);
    }

    public void fillBoard() {
        for (int i = 0; i < sideLength; i++) {
            LinearLayout row = new LinearLayout(context);
            row.setOrientation(LinearLayout.HORIZONTAL);

            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            row.setLayoutParams(params);

            for (int j = 0; j < sideLength; j++) {
                ImageButton btn = createButton(sideLength * i + j);
                buttons[sideLength * i + j] = btn;
                row.addView(btn);
            }
            layout.addView(row);
        }
    }

    private ImageButton createButton(int index) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (100 * scale + 0.5f);
        ImageButton btn = new ImageButton(new ContextThemeWrapper(context, R.style.Theme_AppTheme));
        btn.setMaxWidth(pixels);
        btn.setMaxHeight(pixels);
        btn.setMinimumWidth(pixels);
        btn.setMinimumHeight(pixels);
        btn.setBackground(AppCompatResources.getDrawable(context, R.drawable.baseline_circle_24));
        btn.setBackgroundTintList(defaultColor);

        btn.setOnClickListener((view) -> {
            if (board[index] == CellState.EMPTY) {
                board[index] = _currentPlayer;
                _currentPlayer = _currentPlayer == CellState.X ? CellState.O : CellState.X;
            }
            btn.setImageDrawable(board[index].toIcon(context));
            btn.setEnabled(false);
            checkWin();
        });

        return btn;
    }

    private void checkWin() {
        for (int[] winCombination : winCombinations) {
            int xCount = 0;
            int oCount = 0;
            for (int i : winCombination) {
                if (board[i] == CellState.X) {
                    xCount++;
                } else if (board[i] == CellState.O) {
                    oCount++;
                }
            }
            if (xCount == sideLength) {
                // победа
                String title = context.getResources().getString(R.string.x_won);
                Arrays.stream(winCombination).forEach(i -> {
                    buttons[i].setBackgroundTintList(winColor);
                });
                winDialog(title);
            } else if (oCount == sideLength) {
                // победа
                Arrays.stream(winCombination).forEach(i -> {
                    buttons[i].setBackgroundTintList(winColor);
                });
                winDialog(context.getResources().getString(R.string.o_won));
            }
        }
        if (Arrays.stream(board).allMatch(it -> it != CellState.EMPTY)) {
            // ничья
            Arrays.stream(buttons).forEach(it -> {
                it.setBackgroundTintList(drawColor);
            });
            winDialog(context.getResources().getString(R.string.friendship_won));
        }
    }

    final int[][] winCombinations = new int[][]{
            new int[]{0, 1, 2},
            new int[]{3, 4, 5},
            new int[]{6, 7, 8},
            new int[]{0, 4, 8},
            new int[]{2, 4, 6},
            new int[]{0, 3, 6},
            new int[]{1, 4, 7},
            new int[]{2, 5, 8},
    };

    private void winDialog(String title) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton(context.getString(R.string.play_again), (dialog, id) -> {
                })
                .setOnDismissListener((dialog -> {
                    restart();
                }))
                .create()
                .show();
    }

    public void restart() {
        Arrays.fill(board, CellState.EMPTY);
        layout.removeAllViews();
        this.selectPlayerDialog().show();
    }

    public AlertDialog selectPlayerDialog() {
        final CellState[] sides = new CellState[]{CellState.X, CellState.O};
        final CharSequence[] sidesStrings = {context.getString(R.string.x), context.getString(R.string.o)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choose_side);
        builder.setOnDismissListener(dialog -> {
            this.fillBoard();
        });
        builder.setSingleChoiceItems(sidesStrings, 0, (dialog, which) -> {
            _currentPlayer = sides[which];
        });
        builder.setPositiveButton(R.string.ok, (dialog, player) -> {
        });
        return builder
                .create();
    }
}
