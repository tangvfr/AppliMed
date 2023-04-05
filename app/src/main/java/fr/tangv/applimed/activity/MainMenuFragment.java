package fr.tangv.applimed.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return this.binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //bouton composant
        this.binding.btnComp.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(MainMenuFragment.this)
                    .navigate(R.id.action_mainMenuFragment_to_First2Fragment);
        });

        //bouton quitter
        this.binding.btnQuit.setOnClickListener((View v) -> {
            MainMenuFragment.this.getActivity().finish();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}