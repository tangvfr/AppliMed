package fr.tangv.applimed.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import fr.tangv.applimed.databinding.ActivityMainBinding;

/**
 * Classe qui représente l'activité principale de l'application,
 * permet de lier, naviguer les controlleurs et vue ensemble
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    /**
     * Méthode appeler lors de la creation de l'activité de l'application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        //this.setSupportActionBar(this.binding.toolbar); disable app bar
    }

    /**
     * Permet de recharger le fragement surlequelle on est actuellement
     * @param navController un controller de navigation
     */
    public static void refreshCurrentFragment(NavController navController) {
        MainActivity.refreshCurrentFragment(navController, null);
    }

    /**
     * Permet de recharger le fragement surlequelle on est actuellement
     * @param navController un controller de navigation
     * @param args argument pour réaficher un fragement
     */
    public static void refreshCurrentFragment(NavController navController, Bundle args) {
        Integer id = navController.getCurrentDestination().getId();
        navController.popBackStack(id,true);
        navController.navigate(id, args);
    }

}