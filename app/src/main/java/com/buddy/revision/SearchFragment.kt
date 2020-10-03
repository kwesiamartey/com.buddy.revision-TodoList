package com.buddy.revision

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buddy.revision.Adapter.ListItemAdapter
import com.buddy.revision.Adapter.SearchRecyclerView
import com.buddy.revision.Entities.ItemsEntity
import com.buddy.revision.ViewModels.ItemsViewModel
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    private lateinit var itemViewModel: ItemsViewModel
    var themeColors:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_search, container, false)
            itemViewModel = ViewModelProvider(requireActivity()).get(ItemsViewModel::class.java)
        itemViewModel.getRegistrationDaoo!!.observe(
            requireActivity(), Observer {
                themeColors = it[0].theme_color
                // Toast.makeText(requireContext(), themeColors, Toast.LENGTH_LONG).show()
            }
        )

        //Toast.makeText(requireContext(), itemViewModel.key_word.toString(), Toast.LENGTH_LONG).show()
            itemViewModel.getQueryKeyWord(itemViewModel.key_word!!).observe(requireActivity(), Observer {
                val layoutManager = LinearLayoutManager(requireContext())
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                view.seacrh_recyclerview.layoutManager = layoutManager
                val adapter = SearchRecyclerView(
                    requireContext(),
                    it as MutableList<ItemsEntity>,
                    requireActivity(),themeColors,
                    requireActivity()
                )
                view.seacrh_recyclerview.adapter = adapter
            })

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar!!.title = "Search an item "
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)
        itemViewModel.getRegistrationDaoo!!.observe(requireActivity(), Observer {
            (activity as AppCompatActivity).supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(it[0].theme_color)))
        })

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search_back_button -> findNavController().navigate(R.id.dashBoardFragment)
        }

        return super.onOptionsItemSelected(item)
    }

}