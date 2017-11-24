package com.example.paul.log_in.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.paul.log_in.Player.PlayerData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pierre on 21/11/2017.
 */

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Debug1";

    private static final String DATABASE_NaME = "Game.db";
    private static final int DATABASE_VERSION = 4;

    public Database(Context context) {
        super(context, DATABASE_NaME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "create table T_Users("
                +" id integer primary key autoincrement,"
                +" token text,"
                +" birthday text ,"
                +" mail text ,"
                +" name text not null,"
                +" score integer,"
                +" tip integer,"
                +" package integer,"
                +" level integer,"
                +" when_ long not null"
                +")";
        db.execSQL(strSql);
        Log.i("DATABASE", "On create database invoked");
    }

    public boolean create(PlayerData playerData){

        ContentValues values= new ContentValues();

        values.put("token", playerData.getToken());
        Log.d(TAG, "token = "+ playerData.getToken());
        values.put("birthday", playerData.getBirthday());
        Log.d(TAG, "birthday = "+ playerData.getBirthday());
        values.put("mail", playerData.getMail());
        Log.d(TAG, "mail = "+ playerData.getMail());
        values.put("name", playerData.getName());
        Log.d(TAG, "name = "+ playerData.getName());
        values.put("score", playerData.getScore());
        Log.d(TAG, "score = "+ playerData.getScore());
        values.put("tip", playerData.getTip());
        Log.d(TAG, "tip = "+ playerData.getTip());
        values.put("package", playerData.getPack());
        Log.d(TAG, "package = "+ playerData.getPack());
        values.put("level", playerData.getLevel());
        Log.d(TAG, "level = "+ playerData.getLevel());
        values.put("when_", new Date().getTime());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("T_Users", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String strql = "drop table T_Users";
        db.execSQL(strql);
        this.onCreate(db);
        Log.i("DATABASE", "On Upgrade database invoked");
    }

    public boolean update(PlayerData playerData){
        ContentValues values = new ContentValues();
        values.put("token",playerData.getToken());
        values.put("birthday",playerData.getBirthday());
        values.put("mail",playerData.getMail());
        values.put("name", playerData.getName());
        values.put("score", playerData.getScore());
        values.put("tip", playerData.getTip());
        values.put("package", playerData.getPack());
        values.put("level", playerData.getLevel());
        values.put("when_", new Date().getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update("T_Users",values,"id = ?", new String[]{String.valueOf(playerData.getId())});
        db.close();

        return true;
    }

    public int count(){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM T_Users";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;
    }

    // fonction compare token renvoie joueur
    public PlayerData compare(PlayerData playerData){
        PlayerData player2Data = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String mQuery = "SELECT * FROM T_Users WHERE  token = ? ";
        String[] args = {playerData.getToken()};
        Log.d("Database","playerdataGetMail = "+playerData.getToken());
        Cursor cursor = db.rawQuery(mQuery, args);
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            player2Data = new PlayerData(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),
                    cursor.getInt(5), cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    new Date(cursor.getInt(9))
            );
            return player2Data;
        }
        else {
            cursor.close();
            db.close();
            return player2Data;
        }

    }

    // fonction compare mail renvoie joueur
    public PlayerData compareMail(PlayerData playerData){

        SQLiteDatabase db = this.getReadableDatabase();
        String mQuery = "SELECT * FROM T_Users WHERE  mail = ? ";
        String[] args = {playerData.getMail()};
        Log.d("Database","playerdataGetMail = "+playerData.getMail());
        Cursor cursor = db.rawQuery(mQuery, args);
        if (cursor.moveToFirst()&& cursor.getCount()>=1) {

            playerData = new PlayerData(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),
                    cursor.getInt(5), cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    new Date(cursor.getInt(9))
            );
            cursor.close();
            db.close();
            return playerData;
        }
        else{
            cursor.close();
            db.close();
            return playerData;
        }

    }

    public List<PlayerData> readPlayer(){
        List<PlayerData> player = new ArrayList<>();

        //1er technique : SQL
        String strSql = "select * from T_Users order by id asc limit 10";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while (! cursor.isAfterLast()){
            PlayerData playerData = new PlayerData( cursor.getInt(0),
                                                    cursor.getString(1), cursor.getString(2),
                                                    cursor.getString(3),cursor.getString(4),
                                                    cursor.getInt(5), cursor.getInt(6),
                                                    cursor.getInt(7),cursor.getInt(8),
                                                    new Date(cursor.getInt(9))
                                                    );
            player.add(playerData);
            cursor.moveToNext();
        }
        cursor.close();
        return player;
    }


}
