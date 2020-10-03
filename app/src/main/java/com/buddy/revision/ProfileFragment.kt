package com.buddy.revision

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.buddy.revision.ViewModels.ItemsViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var itemViewModel: ItemsViewModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        itemViewModel = ViewModelProvider(requireActivity()).get(ItemsViewModel::class.java)

        itemViewModel.getRegistrationDaoo!!.observe(requireActivity(), Observer {
             view.f_name.text = it[0].fullname
             view.p_email.text = it[0].email
            view.profile_top_background.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(it[0].theme_color)))
        })

        itemViewModel.getRepoAllItemDao("todos")!!.observe(requireActivity(), Observer {
            view.total_todos.text = "Total Todos Recorded: "+ it.size

        } )
        itemViewModel.getRepoAllItemDao("list")!!.observe(requireActivity(), Observer {
            view.total_list.text = "Total List Recorded: "+ it.size

        } )



        view.bk_arrow.setOnClickListener {
            findNavController().navigate(R.id.dashBoardFragment)
        }

        return view
    }
}