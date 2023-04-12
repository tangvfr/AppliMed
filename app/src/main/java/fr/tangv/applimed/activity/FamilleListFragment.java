package fr.tangv.applimed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.tangv.applimed.R;
import fr.tangv.applimed.database.AMDatabase;
import fr.tangv.applimed.database.FamilleDAO;
import fr.tangv.applimed.databinding.FragmentFamilleListBinding;
import fr.tangv.applimed.model.Famille;

public class FamilleListFragment extends Fragment {

    private FragmentFamilleListBinding binding;
    private AMDatabase db;

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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //déclaration variable
        FamilleDAO famDAO = this.db.getFamilleDAO();
        ListView listView = this.binding.familleList.listContainer;
        List<Map<String,String>> famList = this.getFamilles(famDAO);
        boolean empty = famList.isEmpty();

        //envoie a la vue si vide
        this.binding.familleList.setIsEmpty(empty);

        if (empty) {//affichage message
            this.binding.familleList.setEmptyMessage(
                    this.getString(R.string.no_fam)
            );
        } else {//definition de l'adaptateur
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
        //perform listView item click event
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),fruitsNames[i],Toast.LENGTH_LONG).show();//show the selected image in toast according to position
            }
        });*/
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}