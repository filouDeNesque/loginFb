package com.example.paul.log_in;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.paul.log_in.Database.Database;
import com.example.paul.log_in.Player.PlayerData;

import java.util.List;

public class Main3Activity extends AppCompatActivity {

    private TextView scoresView;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        scoresView = findViewById(R.id.txtlistPlayer);
        db = new Database(this);

        Bundle pid = getIntent().getExtras();
        final Boolean newUser = pid.getBoolean("newUser");


        final int id = pid.getInt("playerdataId");
        final String token = pid.getString("token");
        final String birthday = pid.getString("playerdataBirthday");
        final String mail = pid.getString("playerdataMail");
        final String name = pid.getString("playerdataName");
        final int scorePlayer = pid.getInt("playerdatascore");
        final int tip = pid.getInt("playerdataTip");
        final int pack = pid.getInt("playerdataPack");
        final int level = pid.getInt("playerdataLevel");


        //verifie le contenue a afficher si premiere connexion
        if (newUser == true){
            // affichage de la liste de joueur present en bd
            List<PlayerData> scores = db.readPlayer();
            for (PlayerData score : scores){

                scoresView.append(score.toString()+ "\n\n");
            }
            db.close();
        }
        else {



            scoresView.setText("Re-bonjour "+name+" \n Votre score : "+scorePlayer
                                +" \n Votre level : "+level);

        }


    }



}
