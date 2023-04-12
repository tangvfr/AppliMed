package fr.tangv.applimed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fr.tangv.applimed.R;
import fr.tangv.applimed.databinding.FragmentFamilleListBinding;

public class FamilleListFragment extends Fragment {

    private FragmentFamilleListBinding binding;

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
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        this.binding.familleList.setIsEmpty(false);
        this.binding.familleList.setEmptyMessage("Testage");



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}