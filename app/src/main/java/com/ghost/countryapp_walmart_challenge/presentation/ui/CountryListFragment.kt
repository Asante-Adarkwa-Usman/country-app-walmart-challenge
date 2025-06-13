package com.ghost.countryapp_walmart_challenge.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ghost.countryapp_walmart_challenge.data.network.ApiReference
import com.ghost.countryapp_walmart_challenge.data.repository.CountryListRepositoryImpl
import com.ghost.countryapp_walmart_challenge.databinding.FragmentCountryListBinding
import com.ghost.countryapp_walmart_challenge.domain.CountryListUseCase
import com.ghost.countryapp_walmart_challenge.presentation.vm.CountryListViewModel
import com.ghost.countryapp_walmart_challenge.utils.CountryListItem
import com.ghost.countryapp_walmart_challenge.utils.UiState


class CountryListFragment : Fragment() {

    private var _binding: FragmentCountryListBinding?= null
    private val binding get() = _binding!!

    private lateinit var viewModel: CountryListViewModel
    private lateinit var adapter: CountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val apiDetails = ApiReference.apiReference
        val repository = CountryListRepositoryImpl(apiDetails)
        val useCase = CountryListUseCase(repository)
        val factory = CountryViewModelFactory(useCase)

        viewModel = ViewModelProvider(this, factory)[CountryListViewModel::class.java]


        adapter = CountryListAdapter(arrayListOf()) //initialize adapter(empty adapter)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.countries.observe(viewLifecycleOwner) { status ->
            when (status) {
                is UiState.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is UiState.Success -> {
                    binding.progressBar.isVisible = false
                    adapter.updateList(status.data as List<CountryListItem>)
                }

                is UiState.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.getAllCountries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
