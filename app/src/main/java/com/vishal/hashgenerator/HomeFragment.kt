package com.vishal.hashgenerator

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.vishal.hashgenerator.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding:FragmentHomeBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithm)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.drop_down_item,hashAlgorithms)
        binding.tvAutoComplete.setAdapter(arrayAdapter)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}