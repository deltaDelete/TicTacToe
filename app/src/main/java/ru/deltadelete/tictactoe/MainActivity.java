package ru.deltadelete.tictactoe;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layout;
    public GameManager gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.board);

        gm = new GameManagerBuilder()
                .setSideLength(3)
                .setContext(this)
                .setLayout(layout)
                .createGameManager();

        gm.selectPlayerDialog().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}