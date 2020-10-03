package com.buddy.revision

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.buddy.revision.ViewModels.ItemsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_forgo_password.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {

    private lateinit var itemsViewModel: ItemsViewModel
    var idd: Int? = null
    var eemal: String? = null
    var ppassword: String? = null
    var fullname: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        (activity as AppCompatActivity).supportActionBar!!.hide()

        itemsViewModel = ViewModelProvider(requireActivity()).get(ItemsViewModel::class.java)
        itemsViewModel.getRegistrationDaoo!!.observe(requireActivity(), Observer {
           if(it.size == 0){
               idd = null
               eemal = null
               ppassword = null
               fullname = null
           }else{
               idd = it[0].id
               eemal = it[0].email
               ppassword = it[0].password
               fullname = it[0].fullname
           }

        })

        view.btn_login.setOnClickListener { me ->
            signup(view)
        }


        view.btn_sign_up_txt.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
        view.btn_forgot_txt.setOnClickListener {
            findNavController().navigate(R.id.forgoPasswordFragment)
        }


        return view
    }

    fun signup(view: View) {

        val email = view.txt_signin_email.text.toString()
        val password = view.txt_signin_password.text.toString()


        if (eemal != email && ppassword != password) {
            Snackbar.make(view, "Account not found, Register an account", Snackbar.LENGTH_LONG).show()

        } else if (eemal == null && ppassword == null) {
            Snackbar.make(view, "Account not found, Register an account", Snackbar.LENGTH_LONG).show()

        }else {
            Snackbar.make(view, "Welcome ${fullname}", Snackbar.LENGTH_LONG).show()
            itemsViewModel.updateIsLogged(1, idd!!)
            findNavController().navigate(R.id.dashBoardFragment)
        }


    }

}