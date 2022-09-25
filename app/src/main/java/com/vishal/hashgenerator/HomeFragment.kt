package com.vishal.hashgenerator

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.vishal.hashgenerator.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithm)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, hashAlgorithms)
        binding.tvAutoComplete.setAdapter(arrayAdapter)

        //generate button
        binding.btnGenerate.setOnClickListener {
            lifecycleScope.launch() {
                applyAnimations()
                loadFragment(SuccessFragment())
            }
        }

        return binding.root
    }

    private suspend fun applyAnimations() {
        binding.tvTitle.animate().alpha(0f).duration = 400L
        binding.btnGenerate.animate().alpha(0f).duration = 400L
        binding.txtInputLayout.animate()
            .alpha(0f)
            .translationXBy(1000f)
            .duration = 400L
        binding.etPlainText.animate()
            .alpha(0f)
            .translationXBy(-1000f)
            .duration = 400L

        delay(300L)

        binding.viewSuccess.animate().alpha(1f).duration = 400L
        binding.imgSuccess.animate().alpha(1f).duration = 1000L

        delay(1500L)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainContainer, fragment)
        transaction.commit()
    }
}