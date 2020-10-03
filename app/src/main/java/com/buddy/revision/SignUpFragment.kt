package com.buddy.revision

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.buddy.revision.Entities.RegistrationEntities
import com.buddy.revision.ViewModels.ItemsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*


class SignUpFragment : Fragment() {

    private lateinit var itemsViewModel: ItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_sign_up, container, false)

        itemsViewModel = ViewModelProvider(requireActivity()).get(ItemsViewModel::class.java)

        (activity as  AppCompatActivity).supportActionBar!!.hide()

          view.btn_signup_signin_txt.setOnClickListener {
              findNavController().navigate(R.id.loginFragment)
          }

        view.btn_signup_signup.setOnClickListener {
            signUp(view)
        }
        return view
    }

    fun mySnackbar(view:View,text:String){
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
    }


    fun signUp(view: View){
        val fName = txt_signup_full.text.toString()
        val email = txt_signup_email.text.toString()
        val pass = txt_signup_password.text.toString()
        if(fName == "" || email == "" || pass == ""){
            Toast.makeText(requireContext(), "All the fields must be fille", Toast.LENGTH_LONG).show()
        }else{
            //Toast.makeText(requireContext(), "$fName + $email + $pass", Toast.LENGTH_LONG).show()
            val registrationEntities = RegistrationEntities(
                0, fName, email, pass,1, "#6B4EA3"
            )

           itemsViewModel.insetRegistrationsUser(registrationEntities)
            mySnackbar(view,"Registration Successful")
        }
    }

}