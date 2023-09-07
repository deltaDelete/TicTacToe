package ru.deltadelete.tictactoe;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import ru.deltadelete.tictactoe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layout;
    public GameManager gm;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        setSupportActionBar(binding.toolbar);

        layout = findViewById(R.id.board);

        gm = new GameManagerBuilder()
                .setSideLength(3)
                .setContext(this)
                .setLayout(layout)
                .createGameManager();

        gm.selectPlayerDialog().show();
        binding.resetButton.setOnClickListener((btn) -> {
            gm.restart();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_restart) {
            gm.restart();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}