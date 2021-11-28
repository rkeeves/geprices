package com.github.rkeeves.geprices.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.rkeeves.geprices.R
import com.github.rkeeves.geprices.data.local.CommodityDatabase
import com.github.rkeeves.geprices.data.remote.CommodityApiInstance
import com.github.rkeeves.geprices.databinding.FragmentCommodityListBinding
import com.github.rkeeves.geprices.repo.DefaultCommodityRepository

class CommodityListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: FragmentCommodityListBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_commodity_list, container, false)

        val application = requireNotNull(this.activity).application

        val commodityDao = CommodityDatabase.getInstance(application).commodityDao
        val repository = DefaultCommodityRepository(application, commodityDao, CommodityApiInstance.api)
        val viewModelFactory = CommodityListViewModelProviderFactory(repository)

        val viewModel = ViewModelProvider(
                        this, viewModelFactory).get(CommodityListViewModel::class.java)

        binding.viewModel = viewModel

        val adapter = CommodityAdapter(CommodityClickListener { commodityId ->
            viewModel.onSleepNightClicked(commodityId)
        })
        binding.sleepList.adapter = adapter

        viewModel.commodities.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this

        binding.btnSearch.setOnClickListener {
            val keyword = binding.etKeyword.text.toString()
            if (keyword.length > 2) {
                viewModel.onSearch(keyword)
            }
        }

        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, {
            if (it == true) {
                Toast.makeText(
                    context,
                        "Loading...",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.doneShowingSnackbar()
            }
        })
        viewModel.navigateToSleepDetail.observe(viewLifecycleOwner, { commodityId ->
            commodityId?.let {

                this.findNavController().navigate(
                        CommodityListFragmentDirections
                                .actionCommodityListFragmentToCommodityDetailsFragment(commodityId))
                viewModel.onSleepDetailNavigated()
            }
        })

        return binding.root
    }
}