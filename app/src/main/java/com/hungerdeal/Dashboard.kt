package com.hungerdeal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val home= Home()
        val search= Search()
        val apps= Apps()
        val account= Account()

        val fragmentManager= supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.container_dashboard, account).hide(account).commit()
        fragmentManager.beginTransaction().add(R.id.container_dashboard, apps).hide(apps).commit()
        fragmentManager.beginTransaction().add(R.id.container_dashboard, search).hide(search).commit()
        fragmentManager.beginTransaction().add(R.id.container_dashboard, home).commit()
        var active:Fragment= home
        nav_view_dashboard.setOnNavigationItemSelectedListener { p0 ->
            when (p0.itemId) {
                R.id.navigation_home-> {
                    if (active!=home) {
                        fragmentManager.beginTransaction().hide(active).show(home).commit()
                        active=home
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_search-> {
                    if (active!=search) {
                        fragmentManager.beginTransaction().hide(active).show(search).commit()
                        active=search
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_apps-> {
                    if (active!=apps) {
                        fragmentManager.beginTransaction().hide(active).show(apps).commit()
                        active=apps
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_account-> {
                    if (active!=account) {
                        fragmentManager.beginTransaction().hide(active).show(account).commit()
                        active=account
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }
}
