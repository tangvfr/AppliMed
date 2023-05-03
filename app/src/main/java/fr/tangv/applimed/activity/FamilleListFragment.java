package fr.tangv.applimed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.tangv.applimed.R;
import fr.tangv.applimed.action.AlertManagerFamille;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.database.FamilleDAO;
import fr.tangv.applimed.databinding.FragmentFamilleListBinding;
import fr.tangv.applimed.model.Famille;

/**
 * Controlleur qui permet de gérer l'affichage de la liste des familles
 */
public class FamilleListFragment extends Fragment {

    private FragmentFamilleListBinding binding;
    private AMDatabase db;

    /**
     * Méthode appeler lors de la creation de la vue.
     * Initialise les liaisons avec la base de donnée et la vue
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        this.binding = FragmentFamilleListBinding.inflate(inflater, container, false);
        //titre
        this.binding.topPad.setTitleBarName(
                this.getString(R.string.fam_title)
        );
        //text a gauche
        this.binding.topPad.setFirstBarName(
                this.getString(R.string.fam_code)
        );
        //text a droite
        this.binding.topPad.setSecondBarName(
                this.getString(R.string.fam_lib)
        );
        //create connection to db
        this.db = AMDatabase.getInstance(container.getContext());
        return binding.getRoot();
    }

    /**
     * Méthode appeler apres la creation de la vue.
     * Charge et afficher les données dans la vue et affecte les actions des boutons
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //définition de l'action sur un item de la liste
        this.binding.familleList.viewListContainer.setOnItemClickListener(this::clickOnItemAction);

        //rafraichisement de la liste de famille
        this.refreshFamList();
    }

    /*@Override
    public void onResume() {
        super.onResume();

        //rafraichisement de la liste de famille
        this.refreshFamList();
    }*/

    /**
     * Action quand on click sur un item de la liste des familles
     * @param adapterView l'adaptater de la vue
     * @param view la vue sur laquelle l'action est faite
     * @param i un entier
     * @param l un entier
     */
    private void clickOnItemAction(AdapterView<?> adapterView, View view, int i, long l) {
        //code de famille
        String code = ((Map<String,String>) adapterView.getItemAtPosition(i)).get("code");
        //affichage menu pour modifier la famille
        new AlertManagerFamille(view, this.db).editFamille(code);
    }

    /**
     * Permet de rafraichir la list des familles de médicament sur la vue
     */
    private void refreshFamList() {
        //déclaration variable
        FamilleDAO famDAO = this.db.getFamilleDAO();
        ListView listView = this.binding.familleList.viewListContainer;
        List<Map<String,String>> famList = this.getFamilles(famDAO);
        boolean empty = famList.isEmpty();

        //envoie a la vue si vide
        this.binding.familleList.setIsEmpty(empty);

        if (empty) {//affichage message
            this.binding.familleList.setEmptyMessage(
                    this.getString(R.string.no_fam)
            );
        } else {
            //definition de l'adaptateur
            String[] from = {"code","lib"};
            int[] to = {R.id.leftText,R.id.rightText};
            //creation de l'adaptateur
            SimpleAdapter simpleAdapter = new SimpleAdapter(
                    this.getContext(),
                    famList,
                    R.layout.two_text_list_item,
                    from,
                    to
            );
            listView.setAdapter(simpleAdapter);
        }
    }

    /**
     * Récupère toutes les familles de produit dans la base de donnée
     * et les renvoie sous forme de list de list d'assocation clé valeur pour une famille
     * @param dao DAO de famille
     * @return list des familles
     */
    private List<Map<String,String>> getFamilles(FamilleDAO dao) {
        List<Map<String,String>> famList = new ArrayList<>();

        for (Famille fam : dao.findAllFamilles())//pour toutes les familles de médicament
        {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("code", fam.getCode());
            hashMap.put("lib", fam.getLibelle());
            famList.add(hashMap);
        }

        return famList;
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