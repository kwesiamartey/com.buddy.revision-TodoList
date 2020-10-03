package com.buddy.revision

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.buddy.revision.Entities.RegistrationEntities
import com.buddy.revision.ViewModels.ItemsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_forgo_password.view.*


class ForgoPasswordFragment : Fragment() {

    private lateinit var itemsViewModel: ItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_forgo_password, container, false)
        (activity as AppCompatActivity).supportActionBar!!.hide()
        itemsViewModel = ViewModelProvider(requireActivity()).get(ItemsViewModel::class.java)

        itemsViewModel.getRegistrationDaoo!!.observe(requireActivity(), Observer {

            view.forgot_password_retrieve_button.setOnClickListener {me->

                val email = view.txt_forgot_email.text.toString()

                if(it.size ==0 ){
                    Snackbar.make(view, "No account found, Register new one", Snackbar.LENGTH_LONG).show()
                    findNavController().navigate(R.id.signUpFragment)
                }else{
                    if(it[0].email != email){
                        Snackbar.make(view, "Account not found", Snackbar.LENGTH_LONG).show()
                        findNavController().navigate(R.id.signUpFragment)

                    }else{
                        view.forgot_password_retrieve_button.visibility = View.GONE
                        view.forgot_password_reset_button.visibility = View.VISIBLE
                        view.txt_forgot_password.visibility = View.VISIBLE

                        view.forgot_password_reset_button.setOnClickListener {u->
                            val email = view.txt_forgot_email.text.toString()
                            val password = view.txt_forgot_password.text.toString()

                            if(email == "" || password == ""){
                                Snackbar.make(view, "Provide all fields", Snackbar.LENGTH_LONG).show()
                                //Toast.makeText(requireActivity(), email + password, Toast.LENGTH_LONG).show()
                            }else{

                                val registrationEntities = RegistrationEntities(
                                    it[0].id,
                                    it[0].fullname,
                                    it[0].email,
                                    password,
                                    1,
                                    "#6B4EA3"
                                )

                                itemsViewModel.updateRegistrationUser(registrationEntities)

                                Snackbar.make(view, "Welcome ${it[0].fullname}", Snackbar.LENGTH_LONG).show()
                                findNavController().navigate(R.id.dashBoardFragment)
                            }
                        }
                    }
                }
            }
        })


        view.btn_forgot_password_backButton.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        return view
    }

}