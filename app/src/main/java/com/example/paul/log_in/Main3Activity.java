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
        final String token = pid.getString("token");
        PlayerData playerData = getIntent().getExtras().getParcelable("playerdata");

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

            playerData.setToken(token);

            playerData = db.compare(playerData);

            scoresView.setText("Re-bonjour "+playerData.getName()+" \n Votre score : "+playerData.getScore()
                                +" \n Votre level : "+playerData.getLevel());

        }


    }

}
