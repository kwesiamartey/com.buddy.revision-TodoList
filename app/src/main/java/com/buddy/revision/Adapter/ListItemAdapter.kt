package com.buddy.revision.Adapter


import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.buddy.revision.Entities.ItemsEntity
import com.buddy.revision.R
import com.buddy.revision.ViewModels.ItemsViewModel
import kotlinx.android.synthetic.main.activity_completed_task_toast.*
import kotlinx.android.synthetic.main.activity_list_item_single_layout.view.*
import kotlinx.android.synthetic.main.activity_toast_menu_for_list.*
import kotlinx.android.synthetic.main.fragment_update_item.*
import java.util.*

class ListItemAdapter(
    val context: Context,
    val listItemsEntity: MutableList<ItemsEntity>,
    val viewModelStoreOwner: ViewModelStoreOwner,
    val themeColor:String,
    val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ListItemAdapter.MyViewHolder>() {

    private lateinit var itemsViewModel: ItemsViewModel

    inner class MyViewHolder(val itemsView: View) : RecyclerView.ViewHolder(itemsView) {

        private var checked = false
        var dates: String? = null
        var themeColor:String=""

        init {
            itemsViewModel = ViewModelProvider(viewModelStoreOwner).get(ItemsViewModel::class.java)
            itemsViewModel.getRegistrationDaoo!!.observe(
                lifecycleOwner,
                androidx.lifecycle.Observer { n ->
                    themeColor = n[0].theme_color
                })
        }

        fun setData(listItemsEntity: ItemsEntity, position: Int) {
            if (listItemsEntity.status == 2 || listItemsEntity.status == 1) {

                val t = itemView.list_title as TextView
                t.text = listItemsEntity.title
                t.paintFlags = t.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                itemView.list_description.text = listItemsEntity.description
                itemView.list_date.text = "Date: "+listItemsEntity.date
                itemsView.chk_box.visibility = View.GONE
                itemsView.radio_status_completed.visibility = View.VISIBLE

            } else {

                itemView.list_title.text = listItemsEntity.title
                itemView.list_description.text = listItemsEntity.description
                itemView.list_date.text = "Date: "+listItemsEntity.date
            }

            if (listItemsEntity.status == 0) {

                itemsView.status_pending1.visibility = View.VISIBLE
                itemView.status_completed1.visibility = View.GONE
                itemView.status_canceled1.visibility = View.GONE
                itemsView.chk_box.visibility = View.VISIBLE
                itemsView.radio_status_completed.visibility = View.GONE

            } else if (listItemsEntity.status == 2) {

                itemView.status_canceled1.visibility = View.VISIBLE
                itemView.status_completed1.visibility = View.GONE
                itemView.status_pending1.visibility = View.GONE
                itemsView.chk_box.visibility = View.VISIBLE
                itemsView.radio_status_completed.visibility = View.GONE

            } else {
                //itemView.status_completed1.visibility = View.VISIBLE
                itemView.status_pending1.visibility = View.GONE
                itemView.status_canceled1.visibility = View.GONE
                itemsView.chk_box.visibility = View.GONE
                itemsView.radio_status_completed.visibility = View.VISIBLE
            }

            itemView.chk_box.setOnClickListener {
                context.run {
                    itemsViewModel.updateYourItems(1, listItemsEntity.id)
                }
                val alertDialog1 = Dialog(context)
                alertDialog1.setContentView(R.layout.activity_completed_task_toast)
                alertDialog1.show()
                alertDialog1.hide_congrate.setOnClickListener { alertDialog1.hide() }
            }

            itemsView.radio_status_completed.setOnClickListener {
                context.run {
                    itemsViewModel.updateYourItems(0, listItemsEntity.id)
                }
            }
        }

        fun clickable(listItemsEntity: ItemsEntity, position: Int) {

            itemView.list_items_menu_ellipse.setOnClickListener { view ->
                val alertDialog = Dialog(context)
                alertDialog.setContentView(R.layout.activity_toast_menu_for_list)
                alertDialog.m_menu_title.setBackgroundDrawable(ColorDrawable(Color.parseColor(themeColor)))
                alertDialog.m_menu_title.text = "List Menu"
                alertDialog.txt_completed_an_item.setOnClickListener { ne ->
                    context.run {
                        itemsViewModel.updateYourItems(1, listItemsEntity.id)
                      /*val alertDialog1 = Dialog(context)
                        alertDialog1.setContentView(R.layout.activity_completed_task_toast)
                        alertDialog1.show()
                        alertDialog1.hide_congrate.setOnClickListener {
                            alertDialog1.hide()
                        }*/
                    }
                    alertDialog.hide()
                }
                alertDialog.txt_update_an_item.setOnClickListener {
                    itemsViewModel.getRegistrationDaoo!!.observe(lifecycleOwner, androidx.lifecycle.Observer { s ->
                        val dialog = Dialog(context)
                        dialog.setContentView(R.layout.fragment_update_item)
                        dialog.updateItemTitle.setBackgroundColor(Color.parseColor(s[0].theme_color))
                        dialog.txt_update_title.setText(listItemsEntity.title)
                        dialog.txt_update_description.setText(listItemsEntity.description)
                        dialog.txt_update_calender.setText(listItemsEntity.date)
                        dialog.txt_update_calender.setOnClickListener {
                            val calendar = Calendar.getInstance()
                            val year = calendar.get(Calendar.YEAR)
                            val month = calendar.get(Calendar.MONTH)
                            val day = calendar.get(Calendar.DAY_OF_MONTH)

                            val datePickerDialog = DatePickerDialog(
                                context, DatePickerDialog.OnDateSetListener
                                { view, year, month, day ->
                                    dates = "$year-${month + 1}-$day"
                                    dialog.txt_update_calender.setText("$year-${month + 1}-$day")
                                }, year, month, day
                            )
                            datePickerDialog.show()
                        }
                        dialog.todo_update_button.setOnClickListener {
                            when {
                                dates == "" -> {
                                    Toast.makeText(context, "You must update the date",Toast.LENGTH_LONG).show()
                                    dialog.hide()
                                }
                                dates == itemsViewModel.txt_update_calender -> {
                                    Toast.makeText(context, "Date shouldn't be the same as previous date", Toast.LENGTH_LONG).show()
                                    dialog.hide()
                                }
                                else -> {
                                    val tit = dialog.txt_update_title.text.toString()
                                    val desc = dialog.txt_update_description.text.toString()
                                    updateItemTodos(listItemsEntity.id, title = tit, description = desc, dates!!, 0, type = "list", 0)
                                    Toast.makeText(context, "Item successfully updated", Toast.LENGTH_LONG).show()
                                    dialog.hide()
                                }
                            }
                        }
                        dialog.show()
                        alertDialog.hide()
                    })
                }
                alertDialog.txt_remove_an_item.setOnClickListener {

                    context.run {
                        itemsViewModel.removeYourItem(listItemsEntity)
                    }
                    alertDialog.hide()
                }
                alertDialog.txt_cancel_an_item.setOnClickListener {
                    context.run {
                        itemsViewModel.updateYourItems(2, listItemsEntity.id)
                    }
                    alertDialog.hide()
                }
                alertDialog.show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_list_item_single_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hold = listItemsEntity[position]
        holder.setData(hold, position)
        holder.clickable(hold, position)
    }

    override fun getItemCount(): Int {
        return listItemsEntity.size
    }

    fun updateItemTodos(
        id: Int,
        title: String,
        description: String,
        date: String,
        status: Int,
        type: String,
        reminderr: Int
    ) {
        val itemsEntity = ItemsEntity(id, type, title, description, date, status, reminderr)
        itemsViewModel.updateYourSingleItem(itemsEntity)
    }
}