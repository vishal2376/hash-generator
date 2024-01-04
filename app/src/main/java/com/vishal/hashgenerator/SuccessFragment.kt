package com.vishal.hashgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.vishal.hashgenerator.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessFragment : Fragment() {

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)

        //set the hash value
        binding.tvHash.text = HomeFragment.hashValue

        //copy button
        binding.btnCopy.setOnClickListener {
            lifecycleScope.launch {
                copyToClipboard(HomeFragment.hashValue)
                applyAnimations()
            }
        }

        return binding.root
    }

    // copy the hash value to clipboard
    private fun copyToClipboard(hashValue: String) {
        val clipboardManager =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encrypted Text", hashValue)
        clipboardManager.setPrimaryClip(clipData)
    }

    private suspend fun applyAnimations() {

        binding.copiedLayout.messageBg.animate().translationY(80f).duration = 200L
        binding.copiedLayout.tvCopied.animate().translationY(80f).duration = 200L

        delay(2000L)

        binding.copiedLayout.messageBg.animate().translationY(-80f).duration = 500L
        binding.copiedLayout.tvCopied.animate().translationY(-80f).duration = 500L

    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}