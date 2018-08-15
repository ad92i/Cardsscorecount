package fr.adrienrogliano.cardsscorecount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Button mNewLobbyButton;
    private Button mHelpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        mNewLobbyButton = findViewById(R.id.new_score);

        mHelpButton = findViewById(R.id.help);

jknkn

        // zz

        mNewLobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, PreLobbyActivity.class);
                startActivity(intent);
            }
        });


    }
}
