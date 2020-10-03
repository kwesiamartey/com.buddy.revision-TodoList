package com.buddy.revision

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.buddy.revision.ViewModels.ItemsViewModel


class SplashScreenFragment : Fragment() {

    private lateinit var itemViewModel: ItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_splash_screen, container, false)
        (activity as AppCompatActivity).supportActionBar!!.hide()
        itemViewModel = ViewModelProvider(requireActivity()).get(ItemsViewModel::class.java)

        itemViewModel.getRegistrationDaoo!!.observe(requireActivity(), Observer {

            //Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()

            if (it.size == 0) {
                Handler().postDelayed({
                    findNavController().navigate(R.id.loginFragment)
                }, 1900)
            } else if (it.size != 0 && it[0].isLoggedInt == 0) {
                Handler().postDelayed({
                    findNavController().navigate(R.id.loginFragment)
                }, 1900)

            } else if (it.size != 0 && it[0].isLoggedInt == 1) {
                Handler().postDelayed({
                    findNavController().navigate(R.id.dashBoardFragment)
                }, 1900)

            }
        })

        return view
    }
}