package com.example.criminalintentchapter16

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.criminalintentchapter16.databinding.FragmentCrimeDetailBinding
import com.example.criminalintentchapter16.viewModels.CrimeDetailViewModel
import com.example.criminalintentchapter16.viewModels.CrimeDetailViewModelFactory
import kotlinx.coroutines.launch
import java.util.Date

class CrimeDetailFragment : Fragment() {
    private val args: CrimeDetailFragmentArgs by navArgs()
    private val crimeDetailViewModel : CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.crimeId)
    }
    private var _binding: FragmentCrimeDetailBinding? = null
    private val binding: FragmentCrimeDetailBinding
        get() = checkNotNull(_binding) {
        "CrimeDetailFragment binding should not be null. Is the view visible?"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                crimeDetailViewModel.updateCrime { it.copy(title=text.toString()) }
            }
            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crimeDetailViewModel.updateCrime { it.copy(isSolved = isChecked) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                crimeDetailViewModel.crime.collect {
                    it?.let { updateUI(it) }
                }
            }
        }

        setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE) { _, bundle ->
            val newDate = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            crimeDetailViewModel.updateCrime { it.copy(date=newDate) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(crime: Crime) {
        binding.apply {
            if (crime.title != crimeTitle.text.toString()) {
                crimeTitle.setText(crime.title)
            }
            crimeSolved.isChecked = crime.isSolved
            crimeDate.apply {
                text = crime.date.toString()
                setOnClickListener {
                    findNavController().navigate(
                        CrimeDetailFragmentDirections.selectDate(crime.date)
                    )
                }
            }
        }
    }
}