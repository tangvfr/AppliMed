package fr.tangv.applimed.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import fr.tangv.applimed.R;
import fr.tangv.applimed.action.AlertManagerSelectComposant;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.database.ConsituerDAO;
import fr.tangv.applimed.database.MedicamentDAO;
import fr.tangv.applimed.databinding.EditMedicamentFormBinding;
import fr.tangv.applimed.databinding.FragmentMedicamentEditBinding;
import fr.tangv.applimed.model.Composant;
import fr.tangv.applimed.model.Famille;
import fr.tangv.applimed.model.Medicament;

/**
 * Controlleur qui permet de gérer la modification d'un médicament
 */
public class MedicamentEditFragment extends Fragment {

    private FragmentMedicamentEditBinding binding;
    private AMDatabase db;
    private Medicament currentMed = null;
    private Famille currentFam = null;
    private Set<Composant> currentComps = null;
    private Set<Composant> etitedComps = null;

    /**
     * Méthode appeler lors de la creation de la vue.
     * Initialise les liaisons avec la base de donnée et la vue.
     * Et récupère le médicament à afficher.
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle saveBundle
    ) {
        this.binding = FragmentMedicamentEditBinding.inflate(inflater, container, false);
        //create connection to db
        this.db = AMDatabase.getInstance(container.getContext());

        //parametre du fragement
        Bundle arg = this.getArguments();
        if (arg != null) {
            String medName = arg.getString("medName");
            if (medName != null) {
                this.currentMed = this.db.getMedicamentDAO().findMedicamentByName(medName);
            }
        }

        //check si argument correct
        if (this.currentMed == null) {
            throw new IllegalArgumentException("Bundle, medName or this.currentMed is invalid !");
        } else {
            //valeur d'un medicament
            this.currentFam = this.db.getFamilleDAO().findFamille(this.currentMed.getFamCode());
            this.currentComps = new HashSet<>(this.db.getConsituerDAO().findComposantByDepot(this.currentMed.getDepotLegal()));
            this.etitedComps = new HashSet<>(this.currentComps);
        }

        //titre
        this.binding.topPad.setTitleBarName(
                this.getString(R.string.med_editor_title)
        );
        //text center
        this.binding.topPad.setFirstBarName(
                this.currentMed.getNomCommercial()
        );
        return binding.getRoot();
    }

    /**
     * Méthode appeler apres la creation de la vue.
     * Charge et afficher les données dans les champs de la vue et affecte les actions des boutons
     */
    public void onViewCreated(@NonNull View view, Bundle saveBundle) {
        super.onViewCreated(view, saveBundle);

        //bouton edit
        this.binding.medForm.editBtn.setOnClickListener(this::onEdit);

        //bouton refresh
        this.binding.medForm.refreshBtn.setOnClickListener((View v) -> {
            MainActivity.refreshCurrentFragment(
                    Navigation.findNavController(v),
                    MedicamentEditFragment.this.getArguments()
            );
        });

        //bouton delete
        this.binding.medForm.deleteBtn.setOnClickListener((View v) -> {
            //supresiosn du medicament
            String depot = this.currentMed.getDepotLegal();
            this.db.getConsituerDAO().deleteConsituerByDepotLegal(depot);
            this.db.getMedicamentDAO().deleteMedicament(depot);

            //retour en arriver sur la liste des médicaments
            Navigation.findNavController(v).popBackStack();
        });

        //affichage du médicament
        this.showMed();
    }

    /**
     * Permet de d'afficher les valeurs d'un medicament sur la vue
     */
    private void showMed() {
        //déclaration variable
        EditMedicamentFormBinding em = this.binding.medForm;
        Medicament med = this.currentMed;

        //affectation champs de saisie
        em.fieldDepot.setText(med.getDepotLegal());
        em.fieldName.setText(med.getNomCommercial());
        em.fieldEffect.setText(med.getEffets());
        em.fieldAlert.setText(med.getContreIndic());
        em.fieldPrice.setText(Objects.toString(med.getPrixEchantillion(), ""));
        em.fieldQte.setText(Objects.toString(med.getStocks(), ""));
        //tester si vide le nombre

        //on défini la liste de famille
        String[] famLibs = this.db.getFamilleDAO().findAllLibFamilles();
        ArrayAdapter<String> famAdapter = new ArrayAdapter<>(
                this.getContext(),
                R.layout.one_text_list_item,
                R.id.centerText,
                famLibs
        );
        em.fieldFam.setAdapter(famAdapter);
        //on définit la famille choisie
        em.fieldFam.setSelection(Arrays.binarySearch(
                famLibs,
                this.currentFam.getLibelle()
        ));

        //liste des composants
        em.fieldComps.setOnClickListener((View view) -> {
            new AlertManagerSelectComposant(
                    view,
                    this.db,
                    this.etitedComps,
                    em.fieldComps
            ).editListComposants();
        });
        em.fieldComps.setText(//affichage de la liste de composant
                AlertManagerSelectComposant.composantListToString(this.etitedComps, this.getContext())
        );
    }

    /**
     * Permet de récupérer le médicament équivalent a la saisie dans le formulaire
     */
    private Medicament getMedFromForm() {
        EditMedicamentFormBinding em = this.binding.medForm;
        Medicament med = new Medicament();

        //affectation champs de saisie
        med.setDepotLegal(em.fieldDepot.getText().toString());
        med.setNomCommercial(em.fieldName.getText().toString());
        med.setEffets(em.fieldEffect.getText().toString());
        med.setContreIndic(em.fieldAlert.getText().toString());
        //prix
        String strPrice = em.fieldPrice.getText().toString();
        Double price = strPrice.isBlank() ? null : Double.parseDouble(strPrice);
        med.setPrixEchantillion(price);
        //stock
        String strQte = em.fieldQte.getText().toString();
        Integer qte = strQte.isBlank() ? null : Integer.parseInt(strQte);
        med.setStocks(qte);

        //on défini la liste de famille
        Famille fam = this.db.getFamilleDAO().findFamilleByLib((String) em.fieldFam.getSelectedItem());
        med.setFamCode(fam.getCode());

        return med;
    }

    /**
     * Action quand on clique sur le bouton modifier
     * @param view la vue surlaquele l'action est realiser
     */
    public void onEdit(View view) {
        MedicamentDAO medDAO = this.db.getMedicamentDAO();
        Context context = this.getContext();
        Medicament med = this.getMedFromForm();
        String error = null;

        //test nom comercial unique
        String newNom = med.getNomCommercial();
        if (!this.currentMed.getNomCommercial().equals(newNom)) {
            if (newNom.isBlank()) {
                error = context.getString(R.string.editor_err_msg_med_name_empty);
            } else {
                if (medDAO.findMedicamentByName(newNom) != null) {
                    error = context.getString(R.string.editor_err_msg_med_name_already);
                }
            }
        }

        //si pas d'erreur alors test clé primaire
        if (error == null) {
            ConsituerDAO consDAO = this.db.getConsituerDAO();
            //if primary key change
            String newDepot = med.getDepotLegal();
            String oldDepot = this.currentMed.getDepotLegal();
            if (oldDepot.equals(newDepot)) {
                //mise a jour normal
                consDAO.deleteConsituerByDepotLegal(oldDepot);
                medDAO.updateMedicament(med);
                consDAO.insertConstituers(newDepot, this.etitedComps);
            } else {
                if (newDepot.isBlank()) {
                    error = context.getString(R.string.editor_err_msg_empty_pk_med);
                } else {
                    if (medDAO.findMedicament(newDepot) != null) {
                        error = context.getString(R.string.editor_err_msg_already_pk_med);
                    } else {
                        //mise a jour nouveau depot
                        consDAO.deleteConsituerByDepotLegal(oldDepot);
                        medDAO.deleteMedicament(oldDepot);
                        medDAO.insertMedicament(med);
                        consDAO.insertConstituers(newDepot, this.etitedComps);
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
            //retour a la liste des medicament
            Navigation.findNavController(view).popBackStack();
        }
    }

    /**
     * Méthode appeler lors de la destruction de la vue.
     * Libère les variables qui stock la liaison avec la page et la connexion avec la base de données, et celle du médicament
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
        this.db = null;
        this.currentMed = null;
        this.currentFam = null;
        this.currentComps = null;
        this.etitedComps = null;
    }

}