package fr.tangv.applimed.action;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import fr.tangv.applimed.activity.MainActivity;
import fr.tangv.applimed.database.AMDatabase;
import lombok.Getter;

/**
 * Permet de gérer l'affichage de l'éditeur/supréseur d'un model
 */
@Getter
public class AbstractAlertManager {

    /**
     * Contexte d'application
     */
    private final Context context;
    /**
     * connexion à la base de données
     */
    private final AMDatabase db;
    /**
     * Gestionnaire des interfaces
     */
    private final LayoutInflater inflater;
    /**
     * Gestionnaire de navigation entre fragment
     */
    private final NavController nav;

    /**
     * Constructeur du gestionnaire d'etion du model
     * @param view vue qui execute l'action
     * @param db la base de données
     */
    public AbstractAlertManager(View view, AMDatabase db) {
        this.context = view.getContext();
        this.db = db;
        this.nav = Navigation.findNavController(view);
        this.inflater = this.context.getSystemService(LayoutInflater.class);
    }

}
