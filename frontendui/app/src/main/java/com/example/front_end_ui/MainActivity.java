// MainActivity.java - Place this in app/src/main/java/com/example/front_end_ui/MainActivity.java
package com.example.front_end_ui; // Corrected package name

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure R.layout.activity_main is correctly resolved after package name correction
        setContentView(R.layout.activity_main);

        // Initialize BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        // Define the top-level destinations for the AppBarConfiguration.
        // These are the IDs of the fragments that will be directly accessible from the bottom navigation.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_contacts, R.id.navigation_deals, R.id.navigation_more)
                .build();

        // Get the NavController for the NavHostFragment
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Set up ActionBar with NavController (optional, but good for consistent back button behavior)
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Link BottomNavigationView with NavController
        NavigationUI.setupWithNavController(navView, navController);
    }
}
