package com.example.paul.log_in;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paul.log_in.Database.Database;
import com.example.paul.log_in.Player.PlayerData;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    TextView txtEmail, txtBirthday, txtFriends;
    ProgressDialog mDialog;
    ImageView imgAvatar;
    String tag = "mainActivvity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();

        txtBirthday = (TextView)findViewById(R.id.txtBirthday);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtFriends = (TextView)findViewById(R.id.txtFriends);

        imgAvatar = (ImageView)findViewById(R.id.imgAvatar);

        final LoginButton loginButton = (LoginButton)findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Retrieving Data...");
                mDialog.show();


                String accesstoken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d(tag,response.toString());
                        Log.d(tag,"on completed");
                        getData(object);
                        JSONObject response2 =response.getJSONObject();

                        try {
                            Log.d(tag,"after get data object (on completed) response = " + response2.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String mail = new String();
                        try {
                            mail = response2.getString("mail");
                            Log.d(tag,"mail = "+mail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String token = new String();
                        try {
                            token = response2.getString("token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //if mail =! null

                        if (!compare(mail)){
                            Log.d(tag,"if mail != de null");
                            PlayerData playerData = new PlayerData();
                            playerData.setMail(mail);
                            playerDatamail(playerData);

                            //if compare bdd(mail)
                            if(mail.equals(playerData.getMail())){
                                Log.d(tag,"mail present en bdd");
                                Intent intent = intentConnect(playerData);
                                startActivity(intent);
                            }

                            //else compare bdd(mail)
                            else{
                                Log.d(tag,"mail absent en bdd");
                                Intent intent = null;
                                try {
                                    intent = intentCreate(object);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }
                        }

                        //else mail =! null
                        else{
                            Log.d(tag,"mail == null");
                            PlayerData playerData = new PlayerData();
                            playerData.setToken(token);
                            playerDataToken(playerData);

                            //avec token en BDD
                            if(!compare(token)){
                                Log.d(tag,"avec token en bdd");
                                Intent intent = intentConnect(playerData);
                                startActivity(intent);
                            }

                            //Sans token en bdd
                            else{
                                Log.d(tag,"sans token en bdd");
                                Intent intent = null;
                                try {
                                    intent = intentCreate(object);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }
                        }
                    }
                });
                //Request Graph API
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,email,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
                Log.d(tag,"request graph api");
                }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });

        //If already login
        if (AccessToken.getCurrentAccessToken() != null){
            //Just set User Id
            txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
            Log.d(tag,"already login");
            Intent intent = new Intent(MainActivity.this,Choose_player.class);
            intent.putExtra("token", AccessToken.getCurrentAccessToken().getUserId());
            startActivity(intent);

        }
    }

    private void getData(JSONObject object) {
        try {
            Log.d(tag,"method get data");

            final String mail = object.getString("mail");
            Log.d(tag, "getdata mail = "+mail);
            final String token = object.getString("token");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //mes methodes
    public Intent intentConnect(PlayerData playerData) {
        Log.d(tag,"intent connect method");
        Intent intent = new Intent(MainActivity.this,Main3Activity.class);
        intent.putExtra("playerdata", (Parcelable) playerData);
        return intent;
    }

    public Intent intentCreate (JSONObject object) throws JSONException{
        Log.d(tag,"intent create method");
        Intent intent = new Intent(MainActivity.this,Choose_player.class);
        intent.putExtra("mail", object.getString("email"));
        intent.putExtra("birthday", object.getString("birthday"));
        intent.putExtra("token", AccessToken.getCurrentAccessToken().getUserId());
        return intent;
    }

    public Boolean compare(String id){
        Log.d(tag,"compare method");
        final boolean compare = "".equals(id);
        return compare;
    }

    public PlayerData playerDatamail (PlayerData playerData){
        Log.d(tag,"playerdatamail method");
        Context context = this;
        Database db = new Database(context);
        db.compareMail(playerData);
        return playerData;
    }
    public PlayerData playerDataToken (PlayerData playerData){
        Log.d(tag,"playerdatatoken method");
        Context context = this;
        Database db = new Database(context);
        db.compare(playerData);
        return playerData;
    }
}
