package fr.tangv.applimed.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.tangv.applimed.R;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.database.MedicamentDAO;
import fr.tangv.applimed.databinding.FragmentMedicamentListBinding;
import fr.tangv.applimed.model.Medicament;

/**
 * Controlleur qui permet de gérer l'affichage de la liste des médicaments
 */
public class MedicamentListFragment extends Fragment {

    private FragmentMedicamentListBinding binding;
    private AMDatabase db;

    /**
     * Méthode appeler lors de la creation de la vue.
     * Initialise les liaisons avec la base de donnée et la vue
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle saveBundle
    ) {
        this.binding = FragmentMedicamentListBinding.inflate(inflater, container, false);
        //titre
        this.binding.topPad.setTitleBarName(
                this.getString(R.string.med_title)
        );
        //text center
        this.binding.topPad.setFirstBarName(
                this.getString(R.string.med_name)
        );
        //create connection to db
        this.db = AMDatabase.getInstance(container.getContext());
        return binding.getRoot();
    }

    /**
     * Méthode appeler apres la creation de la vue.
     * Charge et afficher les données dans la vue et affecte les actions des boutons
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle saveBundle) {
        super.onViewCreated(view, saveBundle);

        //définition de l'action sur un item de la liste
        this.binding.medicamentList.viewListContainer.setOnItemClickListener(this::clickOnItemAction);

        //bundle critère de recherche
        Bundle arg = this.getArguments();
        String famCode = null;
        String compCode = null;

        if (arg != null) {//si bundle est défini (avoir des arguments)
            famCode = arg.getString("famCode");
            compCode = arg.getString("compCode");
        }

        //rafraichisement de la liste de medicament
        this.refreshMedList(famCode, compCode);
    }

    /**
     * Action quand on click sur un item de la liste des medicament
     * @param adapterView l'adaptater de la vue
     * @param view la vue sur laquelle l'action est faite
     * @param i un entier
     * @param l un entier
     */
    private void clickOnItemAction(AdapterView<?> adapterView, View view, int i, long l) {
        //on récupère le nomDuMédicament
        String nameMed = (String) adapterView.getItemAtPosition(i);

        //construction de l'argument pour la prochaine vue
        Bundle bundle = new Bundle();
        bundle.putString("medName", nameMed);

        //affichage menu pour modifier le médicament
        Navigation.findNavController(view)
                .navigate(R.id.action_medicamentListFragment_to_medicamentEditFragment, bundle);
    }

    /**
     * Permet de rafraichir la list des medicaments de médicament sur la vue
     * (famCode et compCode ne sont pas cumulable)
     * @param famCode code de la famille si recherche par famille, sinon null
     * @param compCode code d'un composant si recherche par composant, sinon null
     */
    private void refreshMedList(String famCode, String compCode) {
        //déclaration variable
        MedicamentDAO medDAO = this.db.getMedicamentDAO();
        ListView listView = this.binding.medicamentList.viewListContainer;
        String[] meds = this.getMedicament(medDAO, famCode, compCode);
        boolean empty = (meds.length == 0);

        //envoie a la vue si vide
        this.binding.medicamentList.setIsEmpty(empty);

        if (empty) {//affichage message
            this.binding.medicamentList.setEmptyMessage(
                    this.getString(R.string.no_med)
            );
        } else {
            //creation de l'adaptateur
            ArrayAdapter<String> simpleAdapter = new ArrayAdapter<>(
                    this.getContext(),
                    R.layout.one_text_list_item,
                    R.id.centerText,
                    meds
            );
            listView.setAdapter(simpleAdapter);
        }
    }

    /**
     * Récupère toutes les medicaments de produit dans la base de donnée
     * et les renvoie sous forme de list de list d'assocation clé valeur pour une medicament
     * (famCode et compCode ne sont pas cumulable)
     * @param dao DAO de medicament
     * @param famCode code de la famille si recherche par famille, sinon null
     * @param compCode code d'un composant si recherche par composant, sinon null
     * @return list des medicaments
     */
    private String[] getMedicament(@NotNull MedicamentDAO dao, String famCode, String compCode) {
        //test compatibiliter composent
        if (famCode != null && compCode != null) {
            throw new IllegalArgumentException("famCode and compCode parameters aren't compatible between them !");
        }

        //on récupère la liste de médicament en fonction du critère
        List<Medicament> meds;
        if (famCode != null) {
            meds = dao.findAllMedicamentsByFamille(famCode);
        } else if (compCode != null) {
            meds = dao.findAllMedicamentsByComposant(compCode);
        } else {
            meds = dao.findAllMedicaments();
        }

        //convert med list to med array
        String[] list = new String[meds.size()];
        int i = 0;

        for (Medicament med : meds) {//pour chaque medicament
            list[i] = med.getNomCommercial();
            i++;
        }

        //return result
        return list;
    }

    /**
     * Méthode appeler lors de la destruction de la vue.
     * Libère les variables qui stock la liaison avec la page et la connexion avec la base de données
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
        this.db = null;
    }

}