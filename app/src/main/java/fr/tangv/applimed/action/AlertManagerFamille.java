package fr.tangv.applimed.action;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import fr.tangv.applimed.R;
import fr.tangv.applimed.activity.MainActivity;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.database.FamilleDAO;
import fr.tangv.applimed.model.Famille;

/**
 * Permet de gérer l'affichage de l'éditeur/supréseur d'une famille de médicament
 */
public class AlertManagerFamille extends AbstractAlertManager {

    private String currentFamCode = null;
    private EditText currentCodeField = null;
    private EditText currentLibField = null;

    /**
     * Constructeur du gestionnaire d'etion d'une famille de médicament
     * @param view vue qui execute l'action
     * @param db la base de données
     */
    public AlertManagerFamille(View view, AMDatabase db) {
        super(view, db);
    }

    /**
     * Permet d'afficher le dialog de modification d'une famille de médicament
     * @param code le code de la famille de médicament modifier
     */
    public void editFamille(String code) {
        //récupération de la famille
        this.currentFamCode = code;//on défini la famille actuellement utiliser
        Famille fam = this.getDb().getFamilleDAO().findFamille(code);

        //gestionaire de layout
        View panel = this.getInflater().inflate(R.layout.view_code_and_lib, null);
        //labels of panel
        ((TextView) panel.findViewById(R.id.codeLabel)).setText(R.string.code_label);
        ((TextView) panel.findViewById(R.id.libLabel)).setText(R.string.lib_label);
        //get fields of panel
        this.currentCodeField = ((EditText) panel.findViewById(R.id.codeField));
        this.currentLibField = ((EditText) panel.findViewById(R.id.libField));

        //set fields of panel
        this.currentCodeField.setText(fam.getCode());
        this.currentLibField.setText(fam.getLibelle());

        //alert pour edit la famille
        Context context = this.getContext();
        AlertDialog alert = new AlertDialog.Builder(context)
                .setMessage(context.getText(R.string.fam_editor_title))
                .setView(panel)
                .setNeutralButton(context.getText(R.string.editor_view), this::viewAction)
                .setNegativeButton(context.getText(R.string.editor_delete), this::deleteAction)
                .setPositiveButton(context.getText(R.string.editor_edit), this::editAction)
                .setOnDismissListener(this::dismissAction)
                .create();
        alert.show();
    }

    /**
     * Action quand on clique sur le bouton pour consulter
     * @param dialog le dialog sur lequelle on clique
     * @param which le numéro du bouton cliquer sur le dialog
     */
    private void viewAction(DialogInterface dialog, int which) {
        Toast.makeText(
                this.getContext(),
                "a finir",
                Toast.LENGTH_LONG
        ).show();

        //args to next fragement
        Bundle bundle = new Bundle();
        bundle.putString("famCode", this.currentFamCode);

        //affichage de la liste de médicament de la famille
        //this.getNav().navigate(R.id.action_mainMenuFragment_to_familleListFragment, bundle);
    }

    /**
     * Action quand on clique sur le bouton pour suprimer
     * @param dialog le dialog sur lequelle on clique
     * @param which le numéro du bouton cliquer sur le dialog
     */
    private void deleteAction(DialogInterface dialog, int which) {
        AMDatabase db = this.getDb();
        if (db.getMedicamentDAO().findAllMedicamentsByFamille(this.currentFamCode).isEmpty()) {//test fam is used
            //delete fam
            db.getFamilleDAO().deleteFamille(this.currentFamCode);
        } else {
            //error msg fam is used
            Context context = this.getContext();
            Toast.makeText(
                    context,
                    context.getText(R.string.editor_err_msg_fam_have_med),
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
        Famille familleOfField = new Famille(
                this.currentCodeField.getText().toString(),
                this.currentLibField.getText().toString()
        );

        //var utile
        AMDatabase db = this.getDb();
        FamilleDAO familleDAO = db.getFamilleDAO();
        String newCode = familleOfField.getCode();
        Context context = this.getContext();
        String error = null;

        //update fam
        if (this.currentFamCode.equals(newCode)) {//if primary key change
            familleDAO.updateFamille(familleOfField);
        } else {
            if (newCode.isBlank()) {
                error = context.getString(R.string.editor_err_msg_empty_pk_fam);
            } else {
                if (familleDAO.findFamille(newCode) != null) {
                    error = context.getString(R.string.editor_err_msg_already_pk_fam);
                } else {
                    familleDAO.insertFamille(familleOfField);
                    db.getMedicamentDAO().updateFamCode(this.currentFamCode, newCode);
                    familleDAO.deleteFamille(this.currentFamCode);
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
        }
    }

}
