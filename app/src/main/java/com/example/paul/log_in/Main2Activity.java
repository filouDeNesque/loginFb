package com.example.paul.log_in;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paul.log_in.Database.Database;
import com.example.paul.log_in.Player.PlayerData;
import com.facebook.AccessToken;

import java.util.Objects;

public class Main2Activity extends AppCompatActivity {

    TextView txtInfo;
    EditText viewName;
    LinearLayout linearAdd;
    Button btnAddPlayer;
    Database db = new Database(this);
    Boolean newUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle pid = getIntent().getExtras();
        final String mail = pid.getString("mail");
        final String birthday = pid.getString("birthday");
        final String token = pid.getString("token");

        txtInfo = findViewById(R.id.txtInfo);
        btnAddPlayer = findViewById(R.id.btnAddPlayer);
        viewName = findViewById(R.id.viewName);

        //Todo: Choose name for first or compare find and do nothing
        if (mail == null){
            Log.d("Dab12","comparaison mail =null");
            txtInfo.setText("Rebonjour vous en reprendrez bien un peu");

            newUser = false;
            Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
            intent.putExtra("token",token);
            intent.putExtra("newUser", newUser);
            startActivity(intent);
        }
        else {
            Log.d("Dab12","comparaison mail different de null");

                txtInfo.setText("Votre Email : "+ mail
                        + "Votre anniverssaire : "+ birthday
                        + "Votre token : " + token);
                linearAdd = findViewById(R.id.layoutAdd);
                linearAdd.setVisibility(View.VISIBLE);
                btnAddPlayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String txtName = viewName.getText().toString();

                        //Stocke valeur
                        PlayerData playerdata = new PlayerData();
                        playerdata.setToken(token);
                        playerdata.setLevel(1);
                        playerdata.setName(txtName);
                        playerdata.setScore(100);
                        playerdata.setTip(20);
                        playerdata.setPack(1);
                        playerdata.setBirthday(birthday);
                        playerdata.setMail(mail);

                        //appelle la base de donnee

                        boolean createSucesfull = db.create(playerdata);
                        if (createSucesfull) {
                            Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                            intent.putExtra("newUser", newUser);
                            intent.putExtra("token", AccessToken.getCurrentAccessToken().getUserId());
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }

        }

    public void newDefault() {
        PlayerData playerdata = new PlayerData();
        playerdata.setToken("001");
        playerdata.setLevel(1);
        playerdata.setName("default");
        playerdata.setScore(100);
        playerdata.setTip(20);
        playerdata.setPack(1);
        playerdata.setBirthday("default");
        playerdata.setMail("default");

        //appelle la base de donnee

        db.create(playerdata);
    }
}
