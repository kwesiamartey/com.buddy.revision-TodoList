package com.buddy.revision

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.buddy.revision.Entities.ItemsEntity
import com.buddy.revision.ViewModels.ItemsViewModel


import kotlinx.android.synthetic.main.fragment_dash_board.view.*
import kotlinx.android.synthetic.main.fragment_toast_adding_item.*
import kotlinx.android.synthetic.main.fragment_update_item.*
import kotlinx.android.synthetic.main.fragment_update_item.view.*
import kotlinx.coroutines.runBlocking
import java.util.*


class UpdateItemFragment : Fragment() {


    private lateinit var itemsViewModel: ItemsViewModel

    var dates: String = ""
    var user_id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update_item, container, false)

        itemsViewModel = ViewModelProvider(requireActivity()).get(ItemsViewModel::class.java)


        runBlocking {
           view.txt_update_title.setText(itemsViewModel.txt_update_title)
           view.txt_update_description.setText(itemsViewModel.txt_update_description)
           view.txt_update_calender.setText(itemsViewModel.txt_update_calender)
       }

        view.txt_update_calender.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireActivity(), DatePickerDialog.OnDateSetListener
                { view, year, month, day ->
                    if (month < 9 && day < 9) {
                        dates = "$year-0${month + 1}-0$day"
                        view.txt_update_calender.setText("$year-0${month + 1}-0$day")
                    } else if (month > 9 && day > 9) {
                        dates = "$year-${month + 1}-$day"
                        view.txt_update_calender.setText("$year-${month + 1}-$day")
                    } else if (month > 9 && day < 9) {
                        dates = "$year-${month + 1}-0$day"
                        view.txt_update_calender.setText("$year-${month + 1}-0$day")
                    } else if (month < 9 && day > 9) {
                        dates = "$year-0${month + 1}-$day"
                        txt_update_calender.setText("$year-0${month + 1}-$day")
                    }
                }, year, month, day
            )
            datePickerDialog.show()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.title = "Update An Item"

        (activity as AppCompatActivity).supportActionBar!!.setLogo(R.drawable.ic_launcher)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id = itemsViewModel.item_id
        val title = txt_update_title.text.toString()
        val description = txt_update_description.text.toString()
        val calender = txt_update_calender.text.toString()
        val status = 0
        val itemsEnt = ItemsEntity(id!!.toInt(),itemsViewModel.type!!, title, description, calender, status,0)

        when(item.itemId){
            R.id.save_update -> {
                when {
                    dates == "" -> Toast.makeText(
                        requireContext(),
                        "You must update the date",
                        Toast.LENGTH_LONG
                    ).show()
                    dates == itemsViewModel.txt_update_calender ->
                        Toast.makeText(
                            requireContext(),
                            "Date shouldnt be the same as previouse date",
                            Toast.LENGTH_LONG
                        ).show()
                    else ->{
                        Toast.makeText(requireContext(), "Item successfully updated", Toast.LENGTH_LONG).show()
                     update_an_item(itemsEnt) }
                }
            }
            R.id.uodate_back ->{
                findNavController().navigate(R.id.dashBoardFragment)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun update_an_item(itemsEntity: ItemsEntity){
        //Toast.makeText(requireContext(), itemsEntity.toString(), Toast.LENGTH_LONG).show()
        itemsViewModel.updateYourSingleItem(itemsEntity)
        findNavController().navigate(R.id.dashBoardFragment)
    }
}