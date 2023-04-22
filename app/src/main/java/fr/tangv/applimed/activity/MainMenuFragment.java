package fr.tangv.applimed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import fr.tangv.applimed.R;
import fr.tangv.applimed.databinding.FragmentMainMenuBinding;

public class MainMenuFragment extends Fragment {

    private FragmentMainMenuBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        this.binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        //definition du titre du menu
        this.binding.topPad.setFirstBarName(
                this.getString(R.string.main_menu_title)
        );
        return this.binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //bouton famille
        this.binding.mainMenu.btnListFam.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(MainMenuFragment.this)
                    .navigate(R.id.action_mainMenuFragment_to_familleListFragment);
        });

        //bouton medicament
        this.binding.mainMenu.btnListMed.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(MainMenuFragment.this)
                    .navigate(R.id.action_mainMenuFragment_to_medicamentListFragment);
        });

        //bouton composant
        this.binding.mainMenu.btnComp.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(MainMenuFragment.this)
                    .navigate(R.id.action_mainMenuFragment_to_composantListFragment);
        });

        //bouton quitter
        this.binding.mainMenu.btnQuit.setOnClickListener((View v) -> {
            MainMenuFragment.this.getActivity().finish();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}