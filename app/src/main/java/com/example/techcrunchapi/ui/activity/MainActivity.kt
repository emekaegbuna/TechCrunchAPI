package com.example.techcrunchapi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.techcrunchapi.R
import com.example.techcrunchapi.ui.activity.fragment.TechCrunchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment()
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container,
                TechCrunchFragment()
            )
            .commit()
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_menu_refresh, menu)
        return true
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){

            R.id.action_refresh ->{
                addFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/
}
