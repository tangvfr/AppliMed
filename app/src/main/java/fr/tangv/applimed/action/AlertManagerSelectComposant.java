package fr.tangv.applimed.action;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import fr.tangv.applimed.R;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.model.Composant;

public class AlertManagerSelectComposant extends AlertManagerComposant {

    //gobal
    private final TextView field;
    private final List<Composant> formListComposant;

    //local
    private List<Composant> listComposant;
    private Set<Composant> choiceComposant;

    public AlertManagerSelectComposant(View view, AMDatabase db, List<Composant> formListComposant, TextView field) {
        super(view, db);
        this.formListComposant = formListComposant;
        this.field = field;
    }

    public void editListComposants() {
        //initalisation des valeurs
        this.choiceComposant = new HashSet<>(this.formListComposant.size());//copie des element selection dans une liste temporaire
        this.listComposant = this.getDb().getComposantDAO().findAllComposants();
        int nbComp = this.listComposant.size();

        //creation de la list de composant selectionner
        String[] nameList = new String[nbComp];
        boolean[] checkedList = new boolean[nbComp];
        Composant comp;
        boolean checked;

        for (int i = 0; i < nbComp; i++) {//pour chaque composant
            comp = this.listComposant.get(i);
            nameList[i] = comp.getLibelle();

            checked = this.formListComposant.contains(comp);
            checkedList[i] = checked;

            if (checked) {
                this.choiceComposant.add(comp);
            }
        }

        //creation de l'alert
        new AlertDialog.Builder(this.getContext())
                .setTitle(R.string.form_edit_med_comp)
                .setCancelable(false)
                .setMultiChoiceItems(nameList, checkedList, this::onClick)
                .setNegativeButton(R.string.form_edit_med_ch_comp_clear, this::clearAction)
                .setNeutralButton(R.string.form_edit_med_ch_comp_cancel, this::cancelAction)
                .setPositiveButton(R.string.form_edit_med_ch_comp_valid, this::validAction)
                .show();
    }

    /**
     * Action quand on clique sur une ligne de la liste
     * @param dialog le dialog sur lequelle on clique
     * @param which le numéro du bouton cliquer sur le dialog
     * @param checked si la check box est cocher ou non
     */
    private void onClick(DialogInterface dialog, int which, boolean checked) {
        Composant comp = this.listComposant.get(which);
        if (checked) {// check condition
            this.choiceComposant.add(comp);
        } else {
            this.choiceComposant.remove(comp);
        }
    }

    /**
     * Action quand on clique sur le bouton pour valider
     * @param dialog le dialog sur lequelle on clique
     * @param which le numéro du bouton cliquer sur le dialog
     */
    private void validAction(DialogInterface dialog, int which) {
        this.formListComposant.clear();
        this.formListComposant.addAll(this.choiceComposant);

        //met a jour l'affichage
        this.field.setText(
            AlertManagerSelectComposant.composantListToString(formListComposant, this.getContext())
        );
    }



    /**
     * Action quand on clique sur le bouton pour cancel
     * @param dialog le dialog sur lequelle on clique
     * @param which le numéro du bouton cliquer sur le dialog
     */
    private void cancelAction(DialogInterface dialog, int which) {
        dialog.dismiss();
    }

    /**
     * Action quand on clique sur le bouton pour clear All
     * @param dialog le dialog sur lequelle on clique
     * @param which le numéro du bouton cliquer sur le dialog
     */
    private void clearAction(DialogInterface dialog, int which) {
        this.formListComposant.clear();

        //met a jour l'affichage
        this.field.setText("");
    }

    /**
     * Permet de faire l'affiche d'une liste de composant en chaine de caractère
     * @param composantList une liste de composant
     * @param context un context d'application
     * @return le texte corespondant a la liste de composant séparé par une chaine de caractère défini (R.string.form_edit_med_ch_separator_comp)
     */
    public static String composantListToString(List<Composant> composantList, Context context) {
        //cretion de l'affichage
        final String SEPARATOR_COMP = context.getString(R.string.form_edit_med_ch_separator_comp);
        Iterator<Composant> it = composantList.iterator();
        StringBuilder str = new StringBuilder();
        Composant comp;
        boolean hasNext = it.hasNext();

        //boucle
        while (hasNext) {//pour chaque composant
            comp = it.next();
            str.append(comp.getLibelle());

            hasNext = it.hasNext();
            if (hasNext) {
                str.append(SEPARATOR_COMP);
            }
        }

        //retourne l'affichage
        return str.toString();
    }

}
