package fr.tangv.applimed.action;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.tangv.applimed.R;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.model.Famille;

public class AlertManagerFamille {

    private final Context context;
    private final AMDatabase db;

    private final LayoutInflater inflater;

    public AlertManagerFamille(Context context, AMDatabase db) {
        this.context = context;
        this.db = db;
        this.inflater = this.context.getSystemService(LayoutInflater.class);
    }

    public void editFamille(String code) {
        //récupération de la famille
        Famille fam = this.db.getFamilleDAO().findFamille(code);

        View panel = this.inflater.inflate(R.layout.view_code_and_lib, null);
        //labels of panel
        ((TextView) panel.findViewById(R.id.codeLabel)).setText(R.string.code_label);
        ((TextView) panel.findViewById(R.id.libLabel)).setText(R.string.lib_label);
        //fields of panel
        ((TextView) panel.findViewById(R.id.codeField)).setText(fam.getCode());
        ((TextView) panel.findViewById(R.id.libField)).setText(fam.getLibelle());

        //alert pour edit la famille
        AlertDialog alert = new AlertDialog.Builder(this.context)
                .setMessage("Testage: ")
                .setView(panel)
                .setPositiveButton("Ok", (dialogInterface, i1) -> {
                    Toast.makeText(
                            this.context,
                            "héhéhéhéhéh",
                            Toast.LENGTH_LONG
                    ).show();
                })
                .create();
        alert.show();
    }

}
