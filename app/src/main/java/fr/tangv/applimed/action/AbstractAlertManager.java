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

    private final Context context;
    private final AMDatabase db;

    private final LayoutInflater inflater;
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

    /**
     * Action quand le dialog disparait, qui va rafraichir le fragement courant
     * @param dialog le dialog qui disparait
     */
    public void dismissAction(DialogInterface dialog) {
        MainActivity.refreshCurrentFragment(this.nav);
    }

}
