package com.example.shipsapi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.shipsapi.R
import com.example.shipsapi.adapters.ShipsAdapter
import com.example.shipsapi.databinding.FragmentShipsBinding
import com.example.shipsapi.utils.DataStatus
import com.example.shipsapi.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShipsFragment : Fragment(R.layout.fragment_ships) {

    private lateinit var binding: FragmentShipsBinding
    private val viewModel by viewModels<MainViewModel>()
    private val shipsAdapter by lazy { ShipsAdapter() }
    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShipsBinding.inflate(inflater,container,false)

        searchView = binding.searchView


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupShipsRecView()
        searchShips()



        lifecycleScope.launch {
            binding.apply {
                viewModel.getShipsList()
                viewModel.shipsList.observe(viewLifecycleOwner){
                    when(it.status){
                        DataStatus.Status.LOADING ->{
                            progressBar.visibility = View.VISIBLE
                        }
                        DataStatus.Status.SUCCESS ->{
                            progressBar.visibility = View.GONE
                            shipsAdapter.differ.submitList(it.data)
                        }
                        DataStatus.Status.ERROR ->{
                            progressBar.visibility = View.VISIBLE
                            Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun searchShips() {


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(query: String?): Boolean {
                TODO("Not yet implemented")
            }
        })
    }




    private fun setupShipsRecView() {
        binding.shipRecyclerview.apply {
         layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = shipsAdapter
        }
    }

}