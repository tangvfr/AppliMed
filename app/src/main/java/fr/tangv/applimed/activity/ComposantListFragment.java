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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.tangv.applimed.R;
import fr.tangv.applimed.action.AlertManagerComposant;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.database.ComposantDAO;
import fr.tangv.applimed.databinding.FragmentComposantListBinding;
import fr.tangv.applimed.model.Composant;

public class ComposantListFragment extends Fragment {

    private FragmentComposantListBinding binding;
    private AMDatabase db;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        this.binding = FragmentComposantListBinding.inflate(inflater, container, false);
        //titre
        this.binding.topPad.setTitleBarName(
                this.getString(R.string.comp_title)
        );
        //text a gauche
        this.binding.topPad.setFirstBarName(
                this.getString(R.string.comp_code)
        );
        //text a droite
        this.binding.topPad.setSecondBarName(
                this.getString(R.string.comp_lib)
        );
        //create connection to db
        this.db = AMDatabase.getInstance(container.getContext());
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //définition de l'action sur un item de la liste
        this.binding.composantList.viewListContainer.setOnItemClickListener(this::clickOnItemAction);

        //rafraichisement de la liste de composant
        this.refreshCompList();
    }

    /**
     * Action quand on click sur un item de la liste des composants
     * @param adapterView l'adaptater de la vue
     * @param view la vue sur laquelle l'action est faite
     * @param i un entier
     * @param l un entier
     */
    private void clickOnItemAction(AdapterView<?> adapterView, View view, int i, long l) {
        //code de composant
        String code = ((Map<String,String>) adapterView.getItemAtPosition(i)).get("code");
        //affichage menu pour modifier la composant
        new AlertManagerComposant(view, this.db).editComposant(code);
    }

    /**
     * Permet de rafraichir la list des composants de médicament sur la vue
     */
    private void refreshCompList() {
        //déclaration variable
        ComposantDAO compDAO = this.db.getComposantDAO();
        ListView listView = this.binding.composantList.viewListContainer;
        List<Map<String,String>> compList = this.getComposants(compDAO);
        boolean empty = compList.isEmpty();

        //envoie a la vue si vide
        this.binding.composantList.setIsEmpty(empty);

        if (empty) {//affichage message
            this.binding.composantList.setEmptyMessage(
                    this.getString(R.string.no_comp)
            );
        } else {
            //definition de l'adaptateur
            String[] from = {"code","lib"};
            int[] to = {R.id.leftText,R.id.rightText};
            //creation de l'adaptateur
            SimpleAdapter simpleAdapter = new SimpleAdapter(
                    this.getContext(),
                    compList,
                    R.layout.two_text_list_item,
                    from,
                    to
            );
            listView.setAdapter(simpleAdapter);
        }
    }

    /**
     * Récupère toutes les composants de produit dans la base de donnée
     * et les renvoie sous forme de list de list d'assocation clé valeur pour une composant
     * @param dao DAO de composant
     * @return list des composants
     */
    private List<Map<String,String>> getComposants(ComposantDAO dao) {
        List<Map<String,String>> compList = new ArrayList<>();

        for (Composant comp : dao.findAllComposants())//pour toutes les composants de médicament
        {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("code", comp.getCode());
            hashMap.put("lib", comp.getLibelle());
            compList.add(hashMap);
        }

        return compList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}