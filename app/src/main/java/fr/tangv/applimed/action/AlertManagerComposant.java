package fr.tangv.applimed.action;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.tangv.applimed.R;
import fr.tangv.applimed.activity.MainActivity;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.database.ComposantDAO;
import fr.tangv.applimed.model.Composant;

/**
 * Permet de gérer l'affichage de l'éditeur/supréseur d'une composant de médicament
 */
public class AlertManagerComposant extends AbstractAlertManager {

    private String currentCompCode = null;
    private String currentCompLib = null;
    private EditText currentCodeField = null;
    private EditText currentLibField = null;

    /**
     * Constructeur du gestionnaire d'etion d'un composant de médicament
     * @param view vue qui execute l'action
     * @param db la base de données
     */
    public AlertManagerComposant(View view, AMDatabase db) {
        super(view, db);
    }

    /**
     * Permet d'afficher le dialog de modification d'un composant de médicament
     * @param code le code de du composant de médicament modifier
     */
    public void editComposant(String code) {
        //récupération de la composant
        this.currentCompCode = code;//on défini le composant actuellement utiliser
        Composant comp = this.getDb().getComposantDAO().findComposant(code);
        this.currentCompLib = comp.getLibelle();//on défini le nom du composant actuellement utiliser

        //gestionaire de layout
        View panel = this.getInflater().inflate(R.layout.view_code_and_lib, null);
        //labels of panel
        ((TextView) panel.findViewById(R.id.codeLabel)).setText(R.string.code_label);
        ((TextView) panel.findViewById(R.id.libLabel)).setText(R.string.lib_label);
        //get fields of panel
        this.currentCodeField = ((EditText) panel.findViewById(R.id.codeField));
        this.currentLibField = ((EditText) panel.findViewById(R.id.libField));

        //set fields of panel
        this.currentCodeField.setText(comp.getCode());
        this.currentLibField.setText(comp.getLibelle());

        //alert pour edit le composant
        Context context = this.getContext();
        AlertDialog alert = new AlertDialog.Builder(context)
                .setMessage(context.getText(R.string.comp_editor_title))
                .setView(panel)
                .setNeutralButton(context.getText(R.string.editor_view), this::viewAction)
                .setNegativeButton(context.getText(R.string.editor_delete), this::deleteAction)
                .setPositiveButton(context.getText(R.string.editor_edit), this::editAction)
                .create();
        alert.show();
    }

    /**
     * Action quand on clique sur le bouton pour consulter
     * @param dialog le dialog sur lequelle on clique
     * @param which le numéro du bouton cliquer sur le dialog
     */
    private void viewAction(DialogInterface dialog, int which) {
        //args to next fragement
        Bundle bundle = new Bundle();
        bundle.putString("compCode", this.currentCompCode);

        //affichage de la liste de médicament avec le composant
        this.getNav().navigate(R.id.action_composantListFragment_to_medicamentListFragment, bundle);
    }

    /**
     * Action quand on clique sur le bouton pour suprimer
     * @param dialog le dialog sur lequelle on clique
     * @param which le numéro du bouton cliquer sur le dialog
     */
    private void deleteAction(DialogInterface dialog, int which) {
        AMDatabase db = this.getDb();
        if (db.getConsituerDAO().findAllConsituerByComposant(this.currentCompCode).isEmpty()) {//test comp is used
            //delete comp
            db.getComposantDAO().deleteComposant(this.currentCompCode);
            //on rachraichi la page
            MainActivity.refreshCurrentFragment(this.getNav());
        } else {
            //error msg comp is used
            Context context = this.getContext();
            Toast.makeText(
                    context,
                    context.getText(R.string.editor_err_msg_comp_have_med),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    /**
     * Action quand on clique sur le bouton pour modifier
     * @param dialog le dialog sur lequelle on clique
     * @param which le numéro du bouton cliquer sur le dialog
     */
    private void editAction(DialogInterface dialog, int which) {
        //view form to object
        Composant composantOfField = new Composant(
                this.currentCodeField.getText().toString(),
                this.currentLibField.getText().toString()
        );

        //var utile
        AMDatabase db = this.getDb();
        ComposantDAO composantDAO = db.getComposantDAO();
        String newCode = composantOfField.getCode();
        String newLib = composantOfField.getLibelle();
        Context context = this.getContext();
        String error = null;

        //update comp
        //check unique lib
        if (!this.currentCompLib.equals(newLib)) {
            if (newLib.isBlank()) {
                error = context.getString(R.string.editor_err_msg_comp_lib_empty);
            } else {
                if (composantDAO.findComposantByLib(newLib) != null) {
                    error = context.getString(R.string.editor_err_msg_comp_lib_already);
                }
            }
        }

        //pas d'erreur
        if (error != null) {
            //if primary key change
            if (this.currentCompCode.equals(newCode)) {
                composantDAO.updateComposant(composantOfField);
            } else {
                if (newCode.isBlank()) {
                    error = context.getString(R.string.editor_err_msg_empty_pk_comp);
                } else {
                    if (composantDAO.findComposant(newCode) != null) {
                        error = context.getString(R.string.editor_err_msg_already_pk_comp);
                    } else {
                        composantDAO.insertComposant(composantOfField);
                        db.getConsituerDAO().updateCompCode(this.currentCompCode, newCode);
                        composantDAO.deleteComposant(this.currentCompCode);
                    }
                }
            }
        }

        //affichage erreur
        if (error != null) {
            Toast.makeText(
                    context,
                    error,
                    Toast.LENGTH_LONG
            ).show();
        } else {
            //on rachraichi la page
            MainActivity.refreshCurrentFragment(this.getNav());
        }
    }

}
