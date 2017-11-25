package com.example.paul.log_in;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button btnPlay;
    private Database db;
    private String tag = "main3activity";
    private String token ;
    private PlayerData playerData = new PlayerData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //vue du haut
        nameView = findViewById(R.id.txtName);
        scoresView = findViewById(R.id.txtSore);
        packageView = findViewById(R.id.txtPackage);
        levelView = findViewById(R.id.txtlevel);
        tipView = findViewById(R.id.txttip);
        //button
        btnPlay =findViewById(R.id.btnPlay);
        //Database
        db = new Database(this);
        //Bundle
        Bundle pid = getIntent().getExtras();
        //Values player final
        final int id = pid.getInt("playerdataId");
        token = pid.getString("token");
        final String birthday = pid.getString("playerdataBirthday");
        final String mail = pid.getString("playerdataMail");
        final String name = pid.getString("playerdataName");
        final int scorePlayer = pid.getInt("playerdatascore");
        final int tip = pid.getInt("playerdataTip");
        final int pack = pid.getInt("playerdataPack");
        final int level = pid.getInt("playerdataLevel");
        //set text de base
        nameView.setText("Player: "+name);
        scoresView.setText("Score: "+scorePlayer+"pt");
        tipView.setText("Tip: "+tip+"$");
        packageView.setText("Package: 0/"+pack);
        levelView.setText("Level: "+level);
        //Buttonplayaction
        btnPlay.setOnClickListener(btnListConnectListener);
        //ajout data playerdata


    }

//    private PlayerData SetPlayerdata(int scorePlayer, String name, int tip, int level, int pack) {
//
//        playerData.setScore(scorePlayer);
//        playerData.setName(name);
//        playerData.setTip(tip);
//        playerData.setLevel(level);
//        playerData.setPack(pack);
//        return playerData;
//    }

    private View.OnClickListener btnListConnectListener = new View.OnClickListener(){
        @Override
        public void onClick(View c){

            Intent intent1 = intentPlay(playerData);
            startActivity(intent1);
        }
    };

    public Intent intentPlay(PlayerData playerData) {
        Intent intent = new Intent(Main3Activity.this,Vue1Player.class);
        Database db = new Database(this);
        playerData.setToken(token);
        playerData = db.compare(playerData);
        intent.putExtra("playerdataId",playerData.getId());
        Log.d(tag,"playerdataId "+playerData.getId());
        intent.putExtra("playerdataName",playerData.getName());
        intent.putExtra("playerdatascore",playerData.getScore());
        intent.putExtra("playerdataTip",playerData.getTip());
        intent.putExtra("playerdataPack",playerData.getPack());
        intent.putExtra("playerdataLevel",playerData.getLevel());
        return intent;
    }

//    public void getPlayerData (){
//        String tag = "playermain3";
//
//        Log.d(tag,"score "+playerData.getScore());
//        Log.d(tag,"name "+playerData.getName());
//        Log.d(tag,"level "+playerData.getLevel());
//        Log.d(tag,"mail "+playerData.getMail());
//        Log.d(tag,"token "+playerData.getToken());
//        Log.d(tag,"pack "+playerData.getPack());
//        Log.d(tag,"tip "+playerData.getTip());
//        Log.d(tag,"birthday "+playerData.getBirthday());
//        Log.d(tag,"id "+playerData.getId());
//
//    }
}
