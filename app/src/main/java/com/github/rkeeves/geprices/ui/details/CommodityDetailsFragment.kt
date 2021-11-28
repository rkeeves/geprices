package com.github.rkeeves.geprices.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.rkeeves.geprices.R
import com.github.rkeeves.geprices.data.local.CommodityDatabase
import com.github.rkeeves.geprices.data.remote.CommodityApiInstance
import com.github.rkeeves.geprices.databinding.FragmentCommodityDetailsBinding
import com.github.rkeeves.geprices.repo.DefaultCommodityRepository

class CommodityDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentCommodityDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_commodity_details, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = CommodityDetailsFragmentArgs.fromBundle(requireArguments())

        val commodityDao = CommodityDatabase.getInstance(application).commodityDao
        val repository = DefaultCommodityRepository(application, commodityDao, CommodityApiInstance.api)
        val viewModelFactory = CommodityDetailsViewModelFactory(arguments.commodityId, repository)

        val commodityDetailsViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CommodityDetailsViewModel::class.java)
        binding.commodityDetailsViewModel = commodityDetailsViewModel
        binding.lifecycleOwner = this

        commodityDetailsViewModel.navigateToList.observe(viewLifecycleOwner, {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    CommodityDetailsFragmentDirections.actionCommodityDetailsFragmentToCommodityListFragment())
                commodityDetailsViewModel.doneNavigating()
            }
        })
        return binding.root
    }
}