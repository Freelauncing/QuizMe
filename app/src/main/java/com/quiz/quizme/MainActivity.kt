package com.quiz.quizme

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.quiz.quizme.data.database.QuizContract

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout : DrawerLayout
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        QuizContract.DatabaseHelper.initDatabaseInstance(this)

        setContentView(R.layout.activity_main)
        setupNavigationDrawer()
        setSupportActionBar(findViewById(R.id.toolbar))

        val navController = findNavController(R.id.myNavHostFragment)

        appBarConfiguration =
            AppBarConfiguration.Builder(
                R.id.homeFragment,
                R.id.aboutFragment,
                R.id.rulesFragment,
                R.id.statisticsFragment,
                R.id.addNewQuestionFragment,
            )
                .setOpenableLayout(drawerLayout)
                .build()

        setupActionBarWithNavController(navController, appBarConfiguration)

        var Role = intent.getStringExtra("Role").toString()

        Toast.makeText(this,Role,Toast.LENGTH_SHORT).show()

        val navView = findViewById<NavigationView>(R.id.navView);

        if(Role.equals("admin")){
            navView.menu.getItem(2).setVisible(false)
        }else{
            navView.menu.getItem(3).setVisible(false)
        }

        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.myNavHostFragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawerLayout))
    }

    override fun onDestroy() {
        super.onDestroy()
        QuizContract.DatabaseHelper.closeDatabase()
    }
}