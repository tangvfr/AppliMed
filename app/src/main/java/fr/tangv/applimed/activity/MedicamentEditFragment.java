package fr.tangv.applimed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import fr.tangv.applimed.R;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.databinding.EditMedicamentFormBinding;
import fr.tangv.applimed.databinding.FragmentMedicamentEditBinding;
import fr.tangv.applimed.model.Composant;
import fr.tangv.applimed.model.Medicament;

public class MedicamentEditFragment extends Fragment {

    private FragmentMedicamentEditBinding binding;
    private AMDatabase db;
    private Medicament currentMed = null;
    private List<Composant> currentComps = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle bundle
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
            this.currentComps = this.db.getConsituerDAO().findComposantByDepot(this.currentMed.getDepotLegal());
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

    public void onViewCreated(@NonNull View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        //définition de l'action sur un item de la liste
        /*this.binding.medicamentList.viewListContainer.setOnItemClickListener(this::clickOnItemAction);

        //bundle critère de recherche
        String famCode = null;
        String compCode = null;

        if (bundle != null) {//si bundle est défini (avoir des arguments)
            famCode = bundle.getString("famCode");
            compCode = bundle.getString("compCode");
        }


        //rafraichisement de la liste de medicament
        this.refreshMedList(famCode, compCode);*/
        this.refreshMed();
    }

    /**
     * Permet de rafraichir le medicament sur la vue
     */
    private void refreshMed() {
        //déclaration variable
        EditMedicamentFormBinding em = this.binding.medForm;
        Medicament med = this.currentMed;

        //affectation champs de saisie
        em.fieldDepot.setText(med.getDepotLegal(), TextView.BufferType.EDITABLE);
        em.fieldName.setText(med.getNomCommercial(), TextView.BufferType.EDITABLE);
        em.fieldEffect.setText(med.getEffets(), TextView.BufferType.EDITABLE);
        em.fieldAlert.setText(med.getContreIndic(), TextView.BufferType.EDITABLE);
        em.fieldPrice.setText(Double.toString(med.getPrixEchantillion()), TextView.BufferType.EDITABLE);
        em.fieldQte.setText(Integer.toString(med.getStocks()), TextView.BufferType.EDITABLE);
//tester si vide le nombre


        //famille

        //composants

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}