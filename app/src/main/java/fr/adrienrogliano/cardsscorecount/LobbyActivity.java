package fr.adrienrogliano.cardsscorecount;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

    // Contiendra une instance du jeu choisi (tous les jeux hérite de Game).
    private Game mGameChoose = null;

    // TODO : Sauvegarde des parties et ajout de condition de victoire.

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
                        mLobby.addScore(player, editText.getText().toString());
                        mScoreList = fillScoreList();
                        mAdapterScore = new ArrayAdapter<>(LobbyActivity.this, android.R.layout.simple_list_item_1, mScoreList);
                        mGridViewScore.setAdapter(mAdapterScore);

                    }
                });

        dialog.show();
    }


    // Récupère les joueurs passés dans le paramètre de l'activité.
    private List<Player> getIntentPlayers() {
        List<Player> players = new ArrayList<>();
        int nb_players;
        Intent intent = getIntent();
        nb_players = intent.getIntExtra(PreLobbyActivity.NB_PLAYERS, 0);
        if (nb_players != 0) {
            for (int i = 0; i < nb_players; i++) {
                players.add((Player) intent.getParcelableExtra(PreLobbyActivity.EXTRA[i]));
            }
        }

        return players;
    }

    // Renvoie une liste de scores, pour l'affichage.
    private ArrayList<String> fillScoreList() {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < mLobby.getPlayers().size(); i++)
            list.add(mLobby.getPlayers().get(i).toString());

        for (int i = 0; i < mLobby.getTurn(); i++)
            for (int j = 0; j < mLobby.getPlayers().size(); j++)
                list.add(mLobby.getScorePlayer(mLobby.getPlayers().get(j), i));

        for (int i = 0; i < mLobby.getPlayers().size(); i++)
            list.add(String.valueOf(mLobby.totalPlayerScore(mLobby.getPlayers().get(i))));

        return list;
    }

    // TODO : réfléchier si modifier fillScoreList dans la classe qui correspond au jeu choisi afin (Molky) oui ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_main);


        // Permet de connaitre le jeu sur la précédente activité.
        switch (getIntent().getStringExtra(PreLobbyActivity.GAME_CHOOSE)) {
            case "Rami":
                mGameChoose = new Rami(getIntent().getStringExtra(PreLobbyActivity.PARTY_NAME));
                break;
        }

        // On initialise nos différentes variables d'instance.
        mLobby = new Lobby(getIntentPlayers(), mGameChoose);

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

        mLobby.initializeScore();

        mScoreList = fillScoreList();

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
                return_value = true;
                break;
            case R.id.action_bar_done:
                AlertDialog.Builder alertWinner = new AlertDialog.Builder(LobbyActivity.this);
                alertWinner.setTitle("Bravo !")
                        .setMessage(mGameChoose.victoryPlayer(mLobby).getPseudo() + "! wouhouuuuu")
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
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