package com.buddy.revision.Adapter

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
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
import kotlinx.android.synthetic.main.activity_item_list_view.view.*
import kotlinx.android.synthetic.main.activity_toast_item_menu.*
import kotlinx.android.synthetic.main.activity_toast_item_menu.txt_cancel_an_item
import kotlinx.android.synthetic.main.activity_toast_item_menu.txt_completed_an_item
import kotlinx.android.synthetic.main.activity_toast_item_menu.txt_remove_an_item
import kotlinx.android.synthetic.main.fragment_update_item.*
import kotlinx.android.synthetic.main.fragment_update_item.view.*
import java.io.IOException
import java.util.*


class ItemRecyclerView(
    val context: Context,
    val items: List<ItemsEntity>,
    val viewModelStoreOwner: ViewModelStoreOwner,
    val lifecycleOwner: LifecycleOwner,
    var themeColor: String ) :
    RecyclerView.Adapter<ItemRecyclerView.MyViewHolder>() {
    val itemsViewModel: ItemsViewModel

    init {
        itemsViewModel = ViewModelProvider(viewModelStoreOwner).get(ItemsViewModel::class.java)
        itemsViewModel.getRegistrationDaoo!!.observe(
            lifecycleOwner,
            androidx.lifecycle.Observer { n ->
                themeColor = n[0].theme_color
            })
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dates: String? = null
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        fun setData(itemData: ItemsEntity, position: Int) {
            itemData?.let {
                //Toast.makeText(context, itemData.reminderr.toString(), Toast.LENGTH_LONG).show()
                if (itemData.status == 2 || itemData.status == 1) {
                    val t = itemView.lt_title as TextView
                    t.text = itemData.title
                    t.paintFlags = t.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    itemView.lt_description.text = itemData.description
                    itemView.selected_txt_date.text = "Date: " + itemData.date
                } else {
                    itemView.lt_title.text = itemData.title
                    itemView.lt_description.text = itemData.description
                    itemView.selected_txt_date.text = "Date: " + itemData.date
                }
                if (itemData.status == 0) {
                    itemView.status_pending.visibility = View.VISIBLE
                    itemView.status_completed.visibility = View.GONE
                    itemView.status_canceled.visibility = View.GONE
                } else if (itemData.status == 2) {
                    itemView.status_canceled.visibility = View.VISIBLE
                    itemView.status_completed.visibility = View.GONE
                    itemView.status_pending.visibility = View.GONE
                } else {
                    itemView.status_completed.visibility = View.VISIBLE
                    itemView.status_pending.visibility = View.GONE
                    itemView.status_canceled.visibility = View.GONE
                }
                if (itemData.reminderr == 1) {
                    itemView.item_alarm_todo.visibility = View.VISIBLE
                    val mediaPlayer = MediaPlayer.create(context, R.raw.alarming1)
                    try {
                        dates = "$year-0${month + 1}-0$day"
                        itemView.selected_txt_date.text = dates
                        if (itemData.date == dates) {
                            //Toast.makeText(context, dates.toString(), Toast.LENGTH_LONG).show()
                            mediaPlayer.start()
                        } else {
                            mediaPlayer.stop()
                        }

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                } else if (itemData.reminderr == 0) {
                    itemView.item_alarm_todo.visibility = View.GONE
                }
            }
        }

        fun clickable(itemData: ItemsEntity, position: Int) {

            itemView.single_item_menu_ellipse.setOnClickListener {
                var alertDialog = Dialog(context)
                alertDialog.setContentView(R.layout.activity_toast_item_menu)
                alertDialog.item_menu_title.setBackgroundColor(Color.parseColor(themeColor))
                alertDialog.show()
                alertDialog.txt_completed_an_item.setOnClickListener { ne ->
                    context.run {
                        itemsViewModel.updateYourItems(1, itemData.id)
                        val alertDialog1 = Dialog(context)
                        alertDialog1.setContentView(R.layout.activity_completed_task_toast)
                        alertDialog1.show()
                        alertDialog1.hide_congrate.setOnClickListener {
                            alertDialog1.hide()
                        }
                    }
                    alertDialog.hide()
                }
                alertDialog.txt_updateeD_an_item.setOnClickListener {
                    //Toast.makeText(context, itemData.date,Toast.LENGTH_LONG).show()
                    /*   updateItemTodos(
                            itemData.title,
                            itemData.description,
                            dates!!,
                            itemData.id,
                            itemData.status,
                            itemData.type
                )*/

                    itemsViewModel.getRegistrationDaoo!!.observe(
                        lifecycleOwner,
                        androidx.lifecycle.Observer { s ->
                            val dialog = Dialog(context)
                            dialog.setContentView(R.layout.fragment_update_item)
                            dialog.updateItemTitle.setBackgroundColor(Color.parseColor(s[0].theme_color))
                            dialog.txt_update_title.setText(itemData.title)
                            dialog.txt_update_description.setText(itemData.description)
                            dialog.txt_update_calender.setText(itemData.date)

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
                                        Toast.makeText(
                                            context,
                                            "You must update the date",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        dialog.hide()
                                    }
                                    dates == itemsViewModel.txt_update_calender -> {
                                        Toast.makeText(
                                            context,
                                            "Date shouldn't be the same as previous date",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        dialog.hide()
                                    }
                                    else -> {
                                        val tit = dialog.txt_update_title.text.toString()
                                        val desc = dialog.txt_update_description.text.toString()

                                        updateItemTodos(
                                            itemData.id,
                                            title = tit,
                                            description = desc,
                                            dates!!,
                                            0, type = "todos",
                                            0
                                        )
                                        Toast.makeText(
                                            context,
                                            "Item successfully updated",
                                            Toast.LENGTH_LONG
                                        ).show()
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
                        itemsViewModel.removeYourItem(itemData)
                        alertDialog.hide()
                    }
                    alertDialog.hide()
                }
                alertDialog.txt_cancel_an_item.setOnClickListener {
                    context.run {
                        itemsViewModel.updateYourItems(2, itemData.id)
                        itemsViewModel.updateSingleItemReminderRepo(0, itemData.id)
                        alertDialog.hide()
                    }
                    alertDialog.hide()
                }
                alertDialog.txt_show_alarm.setOnClickListener {
                    if (itemData.reminderr == 1) {
                        itemsViewModel.updateSingleItemReminderRepo(0, itemData.id)
                        itemView.item_alarm_todo.visibility = View.GONE
                        alertDialog.hide()
                    } else {
                        itemsViewModel.updateSingleItemReminderRepo(1, itemData.id)
                        itemView.item_alarm_todo.visibility = View.VISIBLE
                        alertDialog.hide()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        val layout =
            LayoutInflater.from(context).inflate(R.layout.activity_item_list_view, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hold = items[position]
        holder.setData(hold, position)
        holder.clickable(hold, position)
    }

    override fun getItemCount(): Int {
        return items.size
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
