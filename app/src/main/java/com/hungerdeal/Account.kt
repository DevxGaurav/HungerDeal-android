package com.hungerdeal

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.fragment_account.view.*

class Account:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val manager=Manager(context!!)
        val view= inflater.inflate(R.layout.fragment_account, container, false)
        val signin_opt = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestId().requestEmail().requestProfile().build()
        val client= GoogleSignIn.getClient(context!!, signin_opt)
        view.address_main.text="Hi "+manager.getUsername()!!.split(" ")[0]+"!"
        view.logout.setOnClickListener {
            val builder=AlertDialog.Builder(context!!)
            builder.setTitle("Logout from HungerDeal")
            builder.setPositiveButton("Logout"
            ) { p0, p1 ->
                client.signOut()
                manager.deleteAppData()
                startActivity(Intent(context!!, MainActivity::class.java))
            }.setNegativeButton("Cancel"
            ){ p0, p1 ->
                p0.cancel()
            }
            val alert=builder.create()
            alert.show()
        }
        return view
    }
}