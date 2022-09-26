package com.vishal.hashgenerator

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.vishal.hashgenerator.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()

        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithm)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, hashAlgorithms)
        binding.tvAutoComplete.setAdapter(arrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)


        //generate button
        binding.btnGenerate.setOnClickListener {
            if (binding.etPlainText.text.isEmpty()) {
                showSnackBar("Field Empty.")
            } else {
                lifecycleScope.launch() {
                    applyAnimations()
                    getHashData()
                    loadFragment(SuccessFragment())
                }
            }
        }

        return binding.root
    }

    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(
            binding.rootLayout,
            message,
            Snackbar.LENGTH_SHORT
        )
        snackbar.setAction("OK") {}
        snackbar.show()
    }

    private suspend fun applyAnimations() {
        binding.btnGenerate.isClickable = false
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_menu) {
            binding.etPlainText.text.clear()
            showSnackBar("Cleared.")
        }
        return true
    }

    private fun getHashData(): String {
        val algorithm = binding.tvAutoComplete.text.toString()
        val plainText = binding.etPlainText.text.toString()
        return homeViewModel.getHash(plainText, algorithm)
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