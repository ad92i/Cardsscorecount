package fr.adrienrogliano.cardsscorecount.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.adrienrogliano.cardsscorecount.R;

public class MainMenuActivity extends AppCompatActivity {

    public final static String gameType = "NEW_OR_WHAT";


    private Button mNewLobbyButton;
    private Button mContinueButton;
    private Button mHelpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        //Permet de récupérer les ids des différentes vues.
        mNewLobbyButton = findViewById(R.id.new_score);
        mContinueButton = findViewById(R.id.continue_score);
        mHelpButton = findViewById(R.id.help);


        // Lors de l'appui sur le bouton "New Lobby", démarre l'activité PreLobby
        mNewLobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, PreLobbyActivity.class);
                startActivity(intent);
            }
        });

        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, LobbyActivity.class);
                intent.putExtra(MainMenuActivity.gameType, true);
                startActivity(intent);

            }
        });


    }
}
