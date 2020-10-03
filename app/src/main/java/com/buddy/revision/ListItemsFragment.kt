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
import androidx.recyclerview.widget.LinearLayoutManager
import com.buddy.revision.Adapter.ListItemAdapter
import com.buddy.revision.Entities.ItemsEntity
import com.buddy.revision.ViewModels.ItemsViewModel
import kotlinx.android.synthetic.main.fragment_list_items.view.*
import kotlinx.android.synthetic.main.fragment_toast_adding_item.*
import java.util.*


class ListItemsFragment : Fragment() {

    private lateinit var listItemView: ItemsViewModel
    var dates: String = ""
    var themeColors:String =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list_items, container, false)

        listItemView = ViewModelProvider(requireActivity()).get(ItemsViewModel::class.java)
        listItemView.getRegistrationDaoo!!.observe(
            requireActivity(), Observer {
                themeColors = it[0].theme_color
                // Toast.makeText(requireContext(), themeColors, Toast.LENGTH_LONG).show()
            }
        )
        listItemView.getRepoAllItemDao("list")!!.observe(requireActivity(), Observer {
            val layoutManager = LinearLayoutManager(requireActivity())
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            view.list_items_recyclerview.layoutManager = layoutManager
            if(it.size == 0){
                view.show_no_list.visibility = View.VISIBLE
                view.list_items_recyclerview.visibility = View.GONE
            }else{
                view.show_no_list.visibility = View.GONE
                view.list_items_recyclerview.visibility = View.VISIBLE
                val adapter = ListItemAdapter(requireContext(), it as MutableList<ItemsEntity>, requireActivity(),themeColors, requireActivity())
                view.list_items_recyclerview.adapter = adapter
                adapter.notifyDataSetChanged()
            }

        })
        listItemView.getRegistrationDaoo!!.observe(
            viewLifecycleOwner, Observer {
                view. btn_List_items_floating_add_item.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(it[0].theme_color)))
            }
        )
        view.btn_List_items_floating_add_item.setOnClickListener {
            val layout = Dialog(requireActivity())
            layout.requestWindowFeature(Window.FEATURE_NO_TITLE)
            layout.setContentView(R.layout.fragment_toast_adding_item)
            layout.toasting_form.text = "ADD YOUR LIST ITEMS"
            listItemView.getRegistrationDaoo!!.observe(
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
                        layout.item_date.setText("Date: $year-${month + 1}-$day")
                    }, year, month, day
                )
                datePickerDialog.show()
            }

            var mTitle = layout.txt_title.text
            var desc = layout.txt_description.text

            layout.btn_addItem.setOnClickListener {
                var itemsEntity = ItemsEntity(
                    0,
                    type = "list",
                    title = mTitle.toString(),
                    description = desc.toString(),
                    date = dates,
                    status = 0,
                    reminderr = 0
                )

                if (mTitle.toString().length != 0 && desc.toString().length != 0 && dates.length != 0) {
                    listItemView.insertYourItems(itemsEntity)
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