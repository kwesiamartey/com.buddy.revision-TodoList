package com.buddy.revision

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.buddy.revision.ViewModels.ItemsViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_toast_colors.*
import kotlinx.android.synthetic.main.fragment_dash_board.*
import kotlinx.android.synthetic.main.fragment_dash_board.view.*


class DashBoardFragment : Fragment() {

    private lateinit var itemsViewModel: ItemsViewModel
    var dates: String = ""
    var user_id: Int? = null
    var fullnname: String = ""
    var purple = "#420498"
    var cerulean = "#2a52be"
    var baby_pink = "#f4c2c2"
    var red = "#B71C1C"
    var green = "#20b2aa"
    var yellow = "#f7ca18"
    var black = "#333333"
    var grey = "#67809f"
    var orange = "#f9690e"
    var mauve = "#FFCBB1CF"

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)

        itemsViewModel = ViewModelProvider(requireActivity()).get(ItemsViewModel::class.java)

        itemsViewModel.getRegistrationDaoo!!.observe(viewLifecycleOwner, Observer {
            view.dash_username.text = it[0].fullname.toString()
            user_id = it[0].id
            fullnname = it[0].fullname
            view.top_wrapper.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(it[0].theme_color)))
            //view.todos_tab_bg.setBackgroundDrawable(ColorDrawable(Color.parseColor(it[0].theme_color)))
            //view.list_tab_bg.setBackgroundDrawable(ColorDrawable(Color.parseColor(it[0].theme_color)))
        })

        val adView = AdView(requireActivity())
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-2350070540824577~2142704220"

        MobileAds.initialize(requireContext(), getString(R.string.admob_app_id))
        val adRequest = AdRequest.Builder().build()
        view.adView.loadAd(adRequest)

        view.btn_show_todos.setOnClickListener {
            view.desc_list.visibility = View.VISIBLE
            view.list_itemlayout.visibility = View.GONE
        }

        view.btn_show_list_items.setOnClickListener {
            view.desc_list.visibility = View.GONE
            view.list_itemlayout.visibility = View.VISIBLE
        }

        view.btn_search_item.setOnClickListener {
            if(searh_input_edit.text.toString() == ""){
                Snackbar.make(view, "You must type a word to continue.", Snackbar.LENGTH_LONG).show()
            }else{
                itemsViewModel.key_word = searh_input_edit.text.toString()
                //Toast.makeText(requireContext(), itemsViewModel.key_word.toString(), Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.searchFragment)
            }
        }

        /*view.btn_show_todos_drop_down.setOnClickListener {
            val dialod = Dialog(requireContext())
            dialod.setContentView(R.layout.activity_items_sort_list)
            dialod.show()

            dialod.s_date.setOnClickListener {
                desc_list.visibility = View.GONE
                asc_list.visibility = View.VISIBLE
                dialod.hide()
            }

            dialod.s_myOrder.setOnClickListener {
                desc_list.visibility = View.VISIBLE
                asc_list.visibility = View.GONE
                dialod.hide()
            }
        } */

        return view;
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        itemsViewModel.getRegistrationDaoo!!.observe(viewLifecycleOwner, Observer {
            //view.dash_username.text = it[0].fullname.toString()
            fullnname = it[0].fullname
            (requireActivity() as AppCompatActivity).supportActionBar!!.title =
                "Buddy Todo List" //it[0].fullname.toString()
            val cd =
            (requireActivity() as AppCompatActivity).supportActionBar!!.setBackgroundDrawable(

                        ColorDrawable(Color.parseColor(it[0].theme_color))
            )
        })
        setHasOptionsMenu(true)


        (requireActivity() as AppCompatActivity).supportActionBar!!.show()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.my_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_theme -> {
                val alertD = Dialog(requireActivity())
                alertD.setContentView(R.layout.activity_toast_colors)
                alertD.show()
                alertD.purple.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(purple, user_id!!)
                    //findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
                alertD.cerulean.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(cerulean, user_id!!)
                    //findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
                alertD.baby_pink.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(baby_pink, user_id!!)
                    //findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
                alertD.Grey.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(grey, user_id!!)
                    //findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
                alertD.yellow.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(yellow, user_id!!)
                    //findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
                alertD.red.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(red, user_id!!)
                    //findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
                alertD.green.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(green, user_id!!)
                    //findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
                alertD.dark.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(black, user_id!!)
                    //findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
                alertD.orange.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(orange, user_id!!)
                    //findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
                alertD.mauve.setOnClickListener {
                    itemsViewModel.updatethemeDaoRepo(mauve, user_id!!)
                    findNavController().navigate(R.id.dashBoardFragment)
                    alertD.hide()
                }
            }
            R.id.del_all_item -> {

                val alertD = AlertDialog.Builder(requireActivity())
                alertD.setTitle("Delete All Items")
                alertD.setMessage("Are you sure you want to delete all items? ")
                alertD.setNegativeButton("cancel") { _, _ ->

                }
                alertD.setPositiveButton("Delete") { _, _ ->
                    itemsViewModel.deleteAllItems()
                }
                alertD.show()
            }
            R.id.logout_dash -> {

                val alertD = AlertDialog.Builder(requireActivity())
                alertD.setTitle("Log out!!")
                alertD.setMessage("Are you sure you want to logout dashboard? ")
                alertD.setNegativeButton("cancel") { _, _ ->

                }
                alertD.setPositiveButton("Continue") { _, _ ->

                    itemsViewModel.updateIsLogged(0, user_id!!)
                    findNavController().navigate(R.id.loginFragment)
                }
                alertD.show()
            }
            R.id.iProfile -> findNavController().navigate(R.id.profileFragment)
        }
        return super.onOptionsItemSelected(item)
    }

}