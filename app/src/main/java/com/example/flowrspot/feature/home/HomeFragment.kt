package com.example.flowrspot.feature.home

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flowrspot.R
import com.example.flowrspot.databinding.FragmentHomeBinding
import com.example.flowrspot.databinding.ItemFlowerBinding
import com.example.flowrspot.domain.model.FlowerModel
import com.example.flowrspot.utility.databinding.BindingInterface
import com.example.flowrspot.utility.databinding.CustomAdapter
import com.example.flowrspot.utility.databinding.delegate.OnItemClick
import com.example.flowrspot.utility.extension.collectLifecycleFlow
import com.example.flowrspot.utility.extension.hideKeyboard
import com.example.flowrspot.utility.extension.progressDialog
import com.example.flowrspot.utility.extension.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), TextWatcher {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var viewBinding: FragmentHomeBinding

    /**
     * Used to show a progress bar when consuming RestAPI call.
     */
    private lateinit var progressDialog: Dialog

    /**
     * A custom adapter that supports data binding.
     * It can be used for every time of model we want.
     * Shows the available or searched flower to UI.
     */
    private lateinit var adapter: CustomAdapter<FlowerModel, ItemFlowerBinding>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentHomeBinding.bind(view)
        //
        progressDialog = requireContext().progressDialog()
        viewBinding.etSearch.addTextChangedListener(this)
        viewBinding.imgSearch.setOnClickListener { performSearch() }
        //
        initFlowersRV()
        collectState()
    }

    override fun onStart() {
        super.onStart()
        viewModel.retrieveFlowers()
    }

    /**
     * TextWatcher implementation
     */
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun afterTextChanged(p0: Editable?) {
        if (p0.toString().isEmpty()) {
            viewModel.showTheAvailableFlowers()
            viewBinding.etSearch.clearFocus()
            viewBinding.etSearch.hideKeyboard()
        }
    }

    private fun initFlowersRV() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, newState: Int, dy: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                // used for detecting the end of scrolling
                val totalItemCount: Int = gridLayoutManager.itemCount
                val visibleItemCount: Int = gridLayoutManager.childCount
                val pastVisibleItems: Int = gridLayoutManager.findFirstVisibleItemPosition()
                //
                val endOfScroll: Boolean = pastVisibleItems + visibleItemCount >= totalItemCount

                if (endOfScroll && !viewModel.userHasSearched && !viewModel.noMorePagination) {
                    viewModel.retrieveFlowers()
                }
            }
        }
        //
        viewBinding.rvFlowers.layoutManager = gridLayoutManager
        viewBinding.rvFlowers.addOnScrollListener(scrollListener)
        //
        adapter = CustomAdapter(viewModel.mainList, R.layout.item_flower, object : BindingInterface<FlowerModel, ItemFlowerBinding> {
            override fun bindData(t: FlowerModel, vm: ItemFlowerBinding) {
                vm.flowerModel = t
                vm.onItemClick = object : OnItemClick<FlowerModel> {
                    override fun onItemClick(v: View, t: FlowerModel) {
                        findNavController().navigate(HomeFragmentDirections.navigateToFlowerDetails())
                    }
                }
            }
        })
        viewBinding.rvFlowers.adapter = adapter
    }

    private fun collectState() {
        collectLifecycleFlow(viewModel.state) { state ->
            when(state) {
                is HomeState.ShowProgressBar -> progressDialog.show()
                is HomeState.UpdateFlowersView -> updateFlowers()
                is HomeState.ToastMessage -> showWarning(state.message)
            }
        }
    }

    private fun updateFlowers() {
        progressDialog.dismiss()
        //
        adapter.update()
    }

    private fun showWarning(message: String) {
        progressDialog.dismiss()
        requireContext().shortToast(message)
    }

    private fun performSearch() {
        viewBinding.etSearch.clearFocus()
        viewBinding.etSearch.hideKeyboard()
        //
        val value = viewBinding.etSearch.text.toString()
        viewModel.searchFlowers(value)
    }
}