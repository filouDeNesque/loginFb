package com.example.paul.log_in.Player;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Pierre on 21/11/2017.
 */

public class PlayerData {
    private int id;
    private String token;
    private String birthday;
    private String mail;
    private String name;
    private int score;
    private Date when;
    private int tip;
    private int pack;
    private int level;

    public PlayerData(){

    }

    public PlayerData(int id, String token, String birthday,
                      String mail, String name,
                      int score,int tip,
                      int pack, int level, Date when){
     this.setId(id);
     this.setToken(token);
     this.setBirthday(birthday);
     this.setMail(mail);
     this.setName(name);
     this.setScore(score);
     this.setWhen(when);
     this.setTip(tip);
     this.setPack(pack);
     this.setLevel(level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPack() {
        return pack;
    }

    public void setPack(int pack) {
        this.pack = pack;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String toString(){
        return id + ": " +"token : "+token+" : "+birthday
                + name +" score :"+" -> " + score + " at " + when.toString()
                +" tip : "+tip+" pack : "+ pack+" level : "+level;
    }
    public PlayerData getDefault(PlayerData playerData){

        score=100;
        tip=20;
        pack=1;
        level=1;
        return playerData;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(token);
        dest.writeString(birthday);
        dest.writeString(mail);
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeLong(when != null ? when.getTime() : -1);
        dest.writeInt(tip);
        dest.writeInt(pack);
        dest.writeInt(level);

    }

    public static final Parcelable.Creator<PlayerData> CREATOR = new Parcelable.Creator<PlayerData>()
    {
        @Override
        public PlayerData createFromParcel(Parcel source)
        {
            return new PlayerData(source);
        }

        @Override
        public PlayerData[] newArray(int size)
        {
            return new PlayerData[size];
        }
    };

    public PlayerData(Parcel in) {
        this.id = in.readInt();
        this.token = in.readString();
        this.birthday = in.readString();
        this.mail = in.readString();
        this.name = in.readString();
        this.score = in.readInt();
        long tmpDate = in.readLong();
        this.when = tmpDate == -1 ? null : new Date(tmpDate);
        this.tip = in.readInt();
        this.pack = in.readInt();
        this.level = in.readInt();
    }
}
