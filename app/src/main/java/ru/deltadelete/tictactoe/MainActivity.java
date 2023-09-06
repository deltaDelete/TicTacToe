package ru.deltadelete.tictactoe;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

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
//                .setContext(this.getBaseContext())
                .setContext(this)
                .setLayout(layout)
                .createGameManager();

    }

    @Override
    protected void onStart() {
        super.onStart();
//        gm.selectPlaya();
        gm.fillBoard();
    }
}