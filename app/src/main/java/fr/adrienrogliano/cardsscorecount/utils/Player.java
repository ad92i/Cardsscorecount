package fr.adrienrogliano.cardsscorecount.utils;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Player implements Parcelable, Serializable {

    public enum SexualAffiliation {
        MALE, FEMALE, CISGENDER
    }

    private String pseudo;
    private int age;
    private int numberVictory;
    private int numberParticipation;
    private SexualAffiliation sexe;


    public Player(String pseudo, int age, SexualAffiliation sexe){
        this.pseudo = pseudo;
        this.age = age;
        this.sexe = sexe;

        this.numberParticipation = 0;
        this.numberVictory = 0;

    }

    public String getPseudo(){
        return pseudo;
    }

    public int getAge(){
        return age;
    }

    public SexualAffiliation getSexe(){
        return sexe;
    }


    public void hasParticipate() {
        this.numberParticipation++;
    }

    public static Player loadPlayer(Context context, String pseudo) throws IOException, ClassNotFoundException {
        ObjectInputStream load = new ObjectInputStream(context.openFileInput(pseudo));
        Player player = (Player) load.readObject();
        load.close();
        return player;
    }

    public void savePlayer(Context context) throws IOException {
        ObjectOutputStream save = new ObjectOutputStream(context.openFileOutput("player__" + getPseudo(), Context.MODE_PRIVATE));
        save.writeObject(this);
        save.close();
    }

    // ----------------------------------------------------- Method for PARCEL implementation -----------------------------------------------------

    public Player(Parcel in) {
        pseudo = in.readString();
        age = in.readInt();
        numberVictory = in.readInt();
        numberParticipation = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pseudo);
        dest.writeInt(age);
        dest.writeInt(numberVictory);
        dest.writeInt(numberParticipation);
    }

    // --------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public String toString(){
        return this.getPseudo();
    }

}
