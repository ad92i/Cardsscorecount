package fr.adrienrogliano.cardsscorecount.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.adrienrogliano.cardsscorecount.Player;
import fr.adrienrogliano.cardsscorecount.R;
import fr.adrienrogliano.cardsscorecount.games.Game;
import fr.adrienrogliano.cardsscorecount.games.Rami;

public class PreLobbyActivity extends AppCompatActivity {




    // Constantes permettant d'identifier les valeurs qui seront passées dans l'activité Lobby.
    public static final String[] EXTRA = {"PARCEL_VALUE_1", "PARCEL_VALUE_2", "PARCEL_VALUE_3", "PARCEL_VALUE_4",
            "PARCEL_VALUE_5", "PARCEL_VALUE_6", "PARCEL_VALUE_7", "PARCEL_VALUE_8"};
    public static final String NB_PLAYERS = "INT_NB_PLAYERS";
    public static final String PARTY_NAME = "STRING_PARTY_NAME";
    public static final String GAME_CHOOSE = "GAME_CHOOSE";

    public static  final int CODE_FINISH = 0;


    // Contiendra la liste de tous les joueurs enregistrés.
    private List<Player> mPlayers = null;

    // Contiendra la liste de tous les jeux disponibles.
    private List<Game> mGames = null;

    // Contiendra le nouveau joueur, si l'utilisateur souhaite en ajouté.
    private Player mNewPlayer = null;

    // Récupère l'objet Spinner utilisé dans le XML, sert à afficher le menu déroulant de la sélection d'un jeu.
    private Spinner mSpinnerGame = null;

    // Récupère l'objet ListView utilisé dans le XML, sert à afficher la liste des joueurs disponibles.
    private ListView mListPlayers = null;

    // Permet de faire la liaison entre la liste de joueur et l'objet ListView.
    private ArrayAdapter<Player> mAdapterPlayer = null;

    // Gère le bouton de validation déclarée dans le XML.
    private Button mValidateButton = null;

    // Contiendra la liste des joueurs participant au jeu choisit
    private List<Player> mFinalPlayersList = null;


    // Gère l'affiche de la boîte de dialogue "Ajouter un joueur".
    private void newPlayerAlertDialog() {
        mNewPlayer = null;

        final View displayView = getLayoutInflater().inflate(R.layout.dialog_new_players, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title_new_player)
                .setView(displayView)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText text = displayView.findViewById(R.id.dialog_player_name_input);
                        EditText age = displayView.findViewById(R.id.dialog_birthday_input);


                        RadioGroup radioGroup = displayView.findViewById(R.id.dialog_radio_group);

                        Player.SexualAffiliation playerSexualAffiliation = null;

                        switch (radioGroup.getCheckedRadioButtonId()) {
                            case R.id.dialog_radio_male:
                                playerSexualAffiliation = Player.SexualAffiliation.MALE;
                                break;
                            case R.id.dialog_radio_female:
                                playerSexualAffiliation = Player.SexualAffiliation.FEMALE;
                                break;
                            case R.id.dialog_radio_cisgender:
                                playerSexualAffiliation = Player.SexualAffiliation.CISGENDER;
                                break;

                            default:
                                dialog.dismiss();
                                break;
                        }


                        if (text.getText() != null && age.getText() != null) {
                            mNewPlayer = new Player(
                                    text.getText().toString(),
                                    Integer.valueOf(age.getText().toString()),
                                    playerSexualAffiliation);

                            mPlayers.add(mNewPlayer);
                            try {
                                mNewPlayer.savePlayer(getApplicationContext());
                                mAdapterPlayer.add(mNewPlayer);
                                mListPlayers.setAdapter(mAdapterPlayer);

                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            dialog.dismiss();
                        }

                    }
                })

                .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        dialog.show();
    }

    // Permet de dé-sérialiser les joueurs déjà créent lors de précédente partie.
    private List<Player> createPlayerList() {
        List<Player> players = new ArrayList<>();
        String[] list = this.getApplicationContext().getFileStreamPath("").list();

        for (String playerString : list) {
            if (playerString.contains("player__"))
                try {
                    players.add(Player.loadPlayer(this, playerString));
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
        }

        return players;
    }

    // Crée la liste de jeux
    private List<Game> createGamesList() {
        List<Game> games = new ArrayList<>();

        games.add(new Rami(""));

        return games;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_lobby);

        // Permet l'affichage d'une barre d'information ou sera situé le bouton ajouté un joueur.
        Toolbar toolbar = findViewById(R.id.pre_lobby_toolbar);
        toolbar.setTitle(R.string.action_bar_tittle);
        setSupportActionBar(toolbar);

        // On initialise nos différentes variables d'instance.
        mPlayers = createPlayerList();
        mGames = createGamesList();
        mSpinnerGame = findViewById(R.id.pre_lobby_spinner);
        mListPlayers = findViewById(R.id.pre_lobby_list);
        mValidateButton = findViewById(R.id.pre_lobby_button);

        mFinalPlayersList = new ArrayList<>();

        // Permet l'affichage du menu déroulant proposant la sélection d'un jeu.
        ArrayAdapter<Game> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mGames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGame.setAdapter(arrayAdapter);

        // Permet l'affichage de la liste des joueurs.
        mAdapterPlayer = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, mPlayers);
        mListPlayers.setAdapter(mAdapterPlayer);


        // On définit quoi faire lors de l'appui sur le bouton de validation.
        mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.pre_lobby_party_name_input);

                if (mListPlayers.getCheckedItemCount() > 0 && mListPlayers.getCheckedItemCount() < 8 && !(editText.getText().toString().equals(""))) {
                    mFinalPlayersList.clear();
                    for (int i = 0; i < mPlayers.size(); i++) {
                        if (mListPlayers.getCheckedItemPositions().get(i))
                            mFinalPlayersList.add(mPlayers.get(i));
                    }

                    // Permet de passer dans l'activité Lobby qui sera lancé, le nom de la partie, le nombre de joueur, le jeu.
                    Intent intent = new Intent(PreLobbyActivity.this, LobbyActivity.class);
                    intent.putExtra(PreLobbyActivity.NB_PLAYERS, mFinalPlayersList.size());
                    intent.putExtra(PreLobbyActivity.PARTY_NAME, editText.getText().toString());
                    intent.putExtra(PreLobbyActivity.GAME_CHOOSE, mSpinnerGame.getSelectedItem().toString());

                    // Permet de passer l'objet Player correspondant à chaque joueur sélectionner.
                    for (int i = 0; i < mFinalPlayersList.size(); i++)
                        intent.putExtra(PreLobbyActivity.EXTRA[i], (Parcelable) mFinalPlayersList.get(i));

                    startActivityForResult(intent, PreLobbyActivity.CODE_FINISH);

                } else {
                    if (!(editText.getText().toString().equals(""))){
                        Toast.makeText(PreLobbyActivity.this, R.string.invalid_input_party_name, Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(PreLobbyActivity.this, R.string.invalid_input_players, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Permet d'afficher l'icone pour ajouter un joueur sur la barre d'information.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_pre_lobby, menu);
        return true;
    }

    // Permet de gérer les appuis sur la barre d'information.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean return_value;

        switch (item.getItemId()) {
            case R.id.action_bar_add_players:
                newPlayerAlertDialog();

                mPlayers = createPlayerList();

                return_value = true;
                break;

            default:
                return_value = super.onOptionsItemSelected(item);
                break;
        }
        return return_value;
    }


    // Permet de finir l'activité lors de la victoire d'un joueur.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_FINISH)
            if (resultCode == RESULT_OK)
                finish();

    }

}
