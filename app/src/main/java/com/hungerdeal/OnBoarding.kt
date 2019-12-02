package com.hungerdeal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.tabs.TabLayout
import com.hungerdeal.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoarding : AppCompatActivity() {

    private val manager=Manager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = view_pager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = tabs
        tabs.setupWithViewPager(viewPager)
        val signin_opt = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestId().requestEmail().requestProfile().build()
        val client= GoogleSignIn.getClient(this, signin_opt)
        val account= GoogleSignIn.getLastSignedInAccount(this)
        if (account!=null) {
            UpdateUI(account)
        }

        continue_onboarding.setOnClickListener {
            val signin = client.signInIntent
            startActivityForResult(signin, 101)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==101) {
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            val account=task.getResult(ApiException::class.java)
            UpdateUI(account!!)
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        manager.setEmail(account.email!!)
        manager.setUsername(account.displayName!!)
        Toast.makeText(this, "Welcome "+manager.getUsername(), Toast.LENGTH_LONG).show()
        startActivity(Intent(this, DeliveryLocation::class.java))
        finish()
    }
}