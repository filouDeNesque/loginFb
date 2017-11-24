package com.example.paul.log_in;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paul.log_in.Database.Database;
import com.example.paul.log_in.Player.PlayerData;

import org.w3c.dom.Text;

import java.util.List;

public class Choose_player extends AppCompatActivity {

    Button add;
    Button choose;
    TextView txtInfo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);

        add = (Button) findViewById(R.id.btnAdd);
        add.setOnClickListener(btnaddPlayerListener);
        choose = (Button) findViewById(R.id.btnChoose);
        choose.setOnClickListener(btnPlayConnectListener);

    }

    //listener pour choose
    private View.OnClickListener btnPlayConnectListener = new View.OnClickListener(){
        @Override
        public void onClick(View c){
            final Database databaseManager;
            final Context context = c.getRootView().getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            final View formElementsView = inflater.inflate(R.layout.choose_player_form,null,true);

            final Spinner spinId = (Spinner) formElementsView.findViewById(R.id.spnPlayer);

            databaseManager = new Database(context);
            final List<PlayerData> idList = databaseManager.readPlayer();

            ArrayAdapter<PlayerData> adapter =new ArrayAdapter<PlayerData>(c.getRootView().getContext(),android.R.layout.simple_spinner_item, idList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinId.setAdapter(adapter);


            new AlertDialog.Builder(context)
                    .setView(formElementsView)
                    .setTitle("Choose player")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){

                                    final int tid = spinId.getSelectedItemPosition();
                                    final int j = Integer.parseInt(String.valueOf(idList.get(tid)));

                                    PlayerData playerData= new PlayerData();
                                    playerData.setId(j);


                                    Toast.makeText(context,"Player was choose "+j, Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(Choose_player.this, Main2Activity.class);
                                    intent1.putExtra("id",j);
                                    startActivity(intent1);
                                    databaseManager.close();
                                    dialog.cancel();
                                }

                            }).show();
        }
    };

    //listener pour add
    private View.OnClickListener btnaddPlayerListener = new View.OnClickListener(){
        public void onClick (View c){

        final Context context = c.getRootView().getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.add_player_form, null, true);

        final EditText txtname = (EditText) formElementsView.findViewById(R.id.txtName);
        final EditText txtbirth = (EditText) formElementsView.findViewById(R.id.txtBirthday);
        final EditText txtmail = (EditText) formElementsView.findViewById(R.id.txtEmail);


        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Create player")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String dbtxtname = txtname.getText().toString();
                                String dbtxtbirth = txtbirth.getText().toString();
                                String dbtxtEmail = txtmail.getText().toString();

                                Bundle pid = getIntent().getExtras();
                                final String mail = pid.getString("mail");
                                final String birthday = pid.getString("birthday");
                                final String token = pid.getString("token");

                                PlayerData playerData = new PlayerData();
                                playerData.setName(dbtxtname);
                                playerData.setToken(token);
                                playerData.setLevel(1);
                                playerData.setScore(100);
                                playerData.setTip(20);
                                playerData.setPack(1);
                                playerData.setBirthday(dbtxtbirth);
                                playerData.setMail(dbtxtEmail);


                                boolean createSucessful = new Database(context).create(playerData);
                                if (createSucessful) {

                                    Toast.makeText(context, "Score was saved.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Choose_player.this,Main3Activity.class);
                                    //todo: Mettre en place parcelable pour transmettre directement l'object (playerdata) entre activity

                                } else {
                                    Toast.makeText(context, "unable to save player information.", Toast.LENGTH_SHORT).show();
                                }
                                dialog.cancel();
                            }

                        }).show();
    }
    };
}
