package com.nyan.speedonyan.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nyan.speedonyan.databinding.FragmentDashboardBinding
import com.nyan.speedonyan.model.LocationModel
import com.nyan.speedonyan.utils.PermissionManager
import com.nyan.speedonyan.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    companion object {
        private val TAG = "DashboardFragment"
    }

    private val viewModel: DashboardViewModel by viewModels()

    @Inject
    lateinit var permissionManager: PermissionManager

    private lateinit var binding : FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        Log.d(TAG, "setupViewModel: ")
        with(viewModel) {
            location.observe(viewLifecycleOwner, Observer {
                onLocationModel(it)
            })
            navigation.observe(viewLifecycleOwner, Observer {
                onNavigation(it)
            })
            updateCounter.observe(viewLifecycleOwner, Observer {
                tv_ping.text = "$it ms"
            })
        }

        requestLocationPermission()
    }

    private fun onLocationModel(location: LocationModel?) {
        Log.d(TAG, "onLocationModel: ")
        location?.speed.let {
            val value = it!!.toFloat().roundToInt().times(3.6)
            binding.tvSpeedo.text = value.toInt().toString()
            binding.tvLatitude.text = "Lat ${location?.latitude} Lon ${location?.longitude}"
        }
    }

    private fun onNavigation(navigation: DashboardViewModel.Navigation?) {
        Log.d(TAG, "onNavigation: ")
        when(navigation) {
            DashboardViewModel.Navigation.Finish -> findNavController().navigateUp()
        }
    }

    private fun requestLocationPermission() {
        Log.d(TAG, "requestLocationPermission: ")
        if (permissionManager.isLocationPermissionGranted(requireContext())) {
            viewModel.onLocationPermissionGranted()
        } else {
            permissionManager.requestLocationPermission(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionsResult: ")
        viewModel.onRequestPermissionResult(requestCode, grantResults)
    }
}