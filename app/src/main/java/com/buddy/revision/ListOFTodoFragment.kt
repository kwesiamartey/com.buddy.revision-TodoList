package com.buddy.revision

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.buddy.revision.Adapter.ItemRecyclerView
import com.buddy.revision.Entities.ItemsEntity
import com.buddy.revision.Model.ItemData
import com.buddy.revision.ViewModels.ItemsViewModel
import kotlinx.android.synthetic.main.fragment_list_o_f_todo.view.*
import kotlinx.android.synthetic.main.fragment_list_o_f_todo.view.show_no_todos
import kotlinx.android.synthetic.main.fragment_toast_adding_item.*
import java.util.*

class ListOFTodoFragment : Fragment() {

    var dates: String = ""
    private lateinit var itemsViewModel: ItemsViewModel
    private var itemsdata:MutableList<ItemData> = mutableListOf()
    var themeColors:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_list_o_f_todo, container, false)
        itemsViewModel = ViewModelProvider(this).get(ItemsViewModel::class.java)
        itemsViewModel.getRegistrationDaoo!!.observe(
            requireActivity(), Observer {
                themeColors = it[0].theme_color
                // Toast.makeText(requireContext(), themeColors, Toast.LENGTH_LONG).show()
            }
        )

        view.my_list_recycler.setHasFixedSize(true)
        val layoutInflater = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        view.my_list_recycler.layoutManager = layoutInflater
        itemsViewModel.getRepoAllItemDao("todos")!!.observe(
            viewLifecycleOwner, Observer {
                if(it.size == 0){
                    view.my_list_recycler.visibility = View.GONE
                    view.show_no_todos.visibility = View.VISIBLE
                }else{
                    view.show_no_todos.visibility = View.GONE
                    view.my_list_recycler.visibility = View.VISIBLE
                    val recyclerview = ItemRecyclerView(
                            requireContext(),
                            it,
                            requireActivity(),
                            requireActivity(),
                            themeColors!!
                        )
                    view.my_list_recycler.adapter = recyclerview
                }
            }
        )
        itemsViewModel.getRegistrationDaoo!!.observe(
            viewLifecycleOwner, Observer {
                view. btn_floating_add_item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(it[0].theme_color)))
            }
        )

        view.btn_floating_add_item.setOnClickListener {
            val layout = Dialog(requireActivity())
            layout.requestWindowFeature(Window.FEATURE_NO_TITLE)
            layout.setContentView(R.layout.fragment_toast_adding_item)
            layout.toasting_form.text = "ADD YOUR TODOS"
            itemsViewModel.getRegistrationDaoo!!.observe(
                viewLifecycleOwner, Observer {
                    layout.btn_addItem.setBackgroundDrawable(ColorDrawable(Color.parseColor(it[0].theme_color)))
                }
            )
            layout.show()

            layout.item_date.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireActivity(), DatePickerDialog.OnDateSetListener
                    { view, year, month, day ->
                        dates = "$year-${month + 1}-$day"
                        //Toast.makeText(requireContext(), dates, Toast.LENGTH_LONG).show()
                        layout.item_date.setText(dates)
                    }, year, month, day
                )
                datePickerDialog.show()
            }

            var mTitle = layout.txt_title.text
            var desc = layout.txt_description.text

            layout.btn_addItem.setOnClickListener {
                var itemsEntity = ItemsEntity(
                    0,
                    type = "todos",
                    title = mTitle.toString(),
                    description = desc.toString(),
                    date = dates,
                    status = 0,
                    0
                )

                if (mTitle.toString().length != 0 && desc.toString().length != 0 && dates.length != 0) {
                    itemsViewModel.insertYourItems(itemsEntity)
                    layout.hide()
                } else {
                    val alert = AlertDialog.Builder(requireContext())
                    alert.setMessage("You must provide all fields to add an item")

                    alert.setNegativeButton("Hide") { _, _ ->

                    }
                    alert.show()
                }
            }
        }
        return view
    }
}