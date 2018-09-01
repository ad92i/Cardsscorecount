package fr.adrienrogliano.cardsscorecount.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.adrienrogliano.cardsscorecount.utils.Lobby;
import fr.adrienrogliano.cardsscorecount.utils.Player;
import fr.adrienrogliano.cardsscorecount.R;
import fr.adrienrogliano.cardsscorecount.games.Belote;
import fr.adrienrogliano.cardsscorecount.utils.Game;
import fr.adrienrogliano.cardsscorecount.games.Molkky;
import fr.adrienrogliano.cardsscorecount.games.Rami;

public class LobbyActivity extends AppCompatActivity {

    // Permet l'affichage d'un menu lors du swipe de la gauche vers la droite de l'écran.
    private DrawerLayout mDrawerLayout = null;

    // Contiendra une instance de Lobby.
    private Lobby mLobby = null;

    // Contiendra la liste des scores de tous les joueurs.
    private List<String> mScoreList = null;

    // Récupère l'objet GridView utilisé dans le XML, sert à afficher la liste des scores.
    private GridView mGridViewScore = null;

    // Permet de faire la liaison entre la liste des scores et l'objet GridView.
    private ArrayAdapter<String> mAdapterScore = null;

    // Contiendra une instance du jeu choisi (tous les jeux héritant de Game).
    private Game mGameChoose = null;

    // Gère l'affiche de la boîte de dialogue "Ajouter un score".
    private void addScoreDialog(final Player player) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_add_score, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(LobbyActivity.this)
                .setTitle(player.getPseudo())
                .setView(view)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = view.findViewById(R.id.dialog_add_score_input);
                        mLobby.addScorePlayer(player, editText.getText().toString());
                        mScoreList = mLobby.getGame().setScoringList(mLobby);
                        mAdapterScore = new ArrayAdapter<>(LobbyActivity.this, android.R.layout.simple_list_item_1, mScoreList);
                        mGridViewScore.setAdapter(mAdapterScore);

                    }
                });

        dialog.show();
    }


    // Récupère les joueurs passés dans le paramètre de l'activité.
    private List<Player> getIntentPlayers() {
        List<Player> players = new ArrayList<>();
        int nbPlayers;
        Player playerInIntent;
        Intent intent = getIntent();
        nbPlayers = intent.getIntExtra(PreLobbyActivity.NB_PLAYERS, 0);
        if (nbPlayers != 0) {
            for (int i = 0; i < nbPlayers; i++) {
                playerInIntent = intent.getParcelableExtra(PreLobbyActivity.EXTRA[i]);
                playerInIntent.hasParticipate();
                players.add(playerInIntent);
            }
        }

        return players;
    }

    private Game getIntentGame() {
        Game game = null;

        switch (getIntent().getStringExtra(PreLobbyActivity.GAME_CHOOSE)) {
            case "Rami":
                game = new Rami(getIntent().getStringExtra(PreLobbyActivity.PARTY_NAME));
                break;
            case "Mölkky":
                game = new Molkky(getIntent().getStringExtra(PreLobbyActivity.PARTY_NAME));
                break;
            case "Belote":
                game = new Belote(getIntent().getStringExtra(PreLobbyActivity.PARTY_NAME));
                break;

        }
        return game;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_main);


        // Permet de connaitre le jeu sur la précédente activité.

        // On initialise nos différentes variables d'instance.
        if (getIntent().getBooleanExtra(MainMenuActivity.gameType, false)){
            try {
                mLobby = Lobby.loadLobby(this);
                Log.d("Score", "onCreate: "+mLobby.getScorePlayer(mLobby.getPlayers().get(0), 0));
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            mLobby = new Lobby(getIntentPlayers(), getIntentGame());
            mLobby.initializeScore();
        }


        // Permet l'affichage d'une barre d'information ou sera situé différents boutons.
        Toolbar toolbar = findViewById(R.id.score_display_toolbar);
        toolbar.setTitle(mLobby.getPartyName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout_score_display);

        NavigationView navigationView = findViewById(R.id.nav_view_score_display);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(false);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

        mScoreList = mLobby.getGame().setScoringList(mLobby);

        // Permet l'affichage de la liste des scores en fonction du nombre de joueur.
        mGridViewScore = findViewById(R.id.grid_score_position);
        mGridViewScore.setNumColumns(mLobby.getPlayers().size());

        mAdapterScore = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mScoreList);
        mGridViewScore.setAdapter(mAdapterScore);


    }

    // Permet d'afficher les différents icones de bouton présent sur la barre d'information.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_score_display, menu);

        return true;
    }

    // Permet de gérer les appuis sur la barre d'information.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean return_value;

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return_value = true;
                break;

            case R.id.action_bar_add_score:
                for (Player player : mLobby.getPlayers()) {
                    addScoreDialog(player);
                }
                return_value = true;
                break;

            case R.id.action_bar_save_game:
                Toast.makeText(this, "Vous avez appuyer sur le 'save'", Toast.LENGTH_SHORT).show();

                try {
                    mLobby.saveLobby(LobbyActivity.this);
                } catch(IOException e) {

                }
                return_value = true;
                break;
            case R.id.action_bar_done:

                AlertDialog.Builder alertWinner = new AlertDialog.Builder(LobbyActivity.this);
                alertWinner.setTitle("Bravo !")
                        .setMessage(mLobby.getGame().victoryPlayer(mLobby).getPseudo() + "! wouhouuuuu")
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                try {
                                    File save = new File(getApplicationContext().openFileOutput("lobby__" + mLobby.getPartyName(), Context.MODE_APPEND).toString());
                                    if (save.delete())
                                        Toast.makeText(LobbyActivity.this, "Suppresion réussi !", Toast.LENGTH_LONG).show();
                                }
                                catch ( IOException e) {
                                    Log.i("a", "onCancel: LA PARITE N'AVAIT PAS ÉTÉ SAUVERGARDÉ !");

                                }
                                setResult(RESULT_OK);
                                LobbyActivity.this.finish();
                            }
                        })
                        .show();




                return_value = true;
                break;

            default:
                return_value = super.onOptionsItemSelected(item);
                break;
        }
        return return_value;
    }

}