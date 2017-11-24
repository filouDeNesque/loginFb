package com.example.paul.log_in;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.paul.log_in.Database.Database;
import com.example.paul.log_in.Player.PlayerData;

import java.util.List;

public class Main3Activity extends AppCompatActivity {

    private TextView scoresView;
    private TextView nameView;
    private TextView packageView;
    private TextView levelView;
    private TextView tipView;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        nameView = findViewById(R.id.txtName);
        scoresView = findViewById(R.id.txtSore);
        packageView = findViewById(R.id.txtPackage);
        levelView = findViewById(R.id.txtlevel);
        tipView = findViewById(R.id.txttip);
        db = new Database(this);

        Bundle pid = getIntent().getExtras();

        final int id = pid.getInt("playerdataId");
        final String token = pid.getString("token");
        final String birthday = pid.getString("playerdataBirthday");
        final String mail = pid.getString("playerdataMail");
        final String name = pid.getString("playerdataName");
        final int scorePlayer = pid.getInt("playerdatascore");
        final int tip = pid.getInt("playerdataTip");
        final int pack = pid.getInt("playerdataPack");
        final int level = pid.getInt("playerdataLevel");

        nameView.setText("Player: "+name);
        scoresView.setText("Score: "+scorePlayer+"pt");
        tipView.setText("Tip: "+tip+"$");
        packageView.setText("Package: 0/"+pack);
        levelView.setText("Level: "+level);
    }
}
