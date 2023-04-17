package fr.tangv.applimed.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import fr.tangv.applimed.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());

        //this.setSupportActionBar(this.binding.toolbar); disable app bar
    }

        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        this.appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, this.appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, this.appBarConfiguration) || super.onSupportNavigateUp();
    }*/

    public static void refreshCurrentFragment(NavController navController) {
        Integer id = navController.getCurrentDestination().getId();
        navController.popBackStack(id,true);
        navController.navigate(id);
    }

}