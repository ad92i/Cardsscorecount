package fr.adrienrogliano.cardsscorecount;

import java.util.List;

public abstract class Game {
    private String partyName;

    public Game(String partyName){
        this.partyName = partyName;
    }

    public Game(){
        this("rename");
    }


    public abstract Player victoryPlayer(Lobby lobby);


    public String getPartyName() {
        return partyName;
    }

    public String getGameName(){
        return "Jeu";
    }

    @Override
    public String toString() {
        return getGameName();
    }
}
