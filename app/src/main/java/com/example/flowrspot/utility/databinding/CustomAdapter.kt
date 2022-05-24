package com.example.flowrspot.utility.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Used to bind data and implement view modifications
 * for each element of the adapter.
 */
interface BindingInterface<T, VM : ViewDataBinding?> {
    fun bindData(t: T, vm: VM)
}

class CustomAdapter<T, VM : ViewDataBinding>(
    list: MutableList<T>,
    private val resource: Int,
    private val bindingInterface: BindingInterface<T, VM>
) : RecyclerView.Adapter<CustomViewHolder<T, VM>>() {

    private var mainList: MutableList<T> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<T, VM> {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(resource, parent, false),
            bindingInterface
        )
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder<T, VM>, position: Int) {
        holder.bindData(mainList[position])
    }

    fun update() {
        this.notifyDataSetChanged()
    }
}

class CustomViewHolder<T, VM : ViewDataBinding>(
    itemView: View,
    private val bindingInterface: BindingInterface<T, VM>
) : RecyclerView.ViewHolder(itemView) {

    private val binding: VM = DataBindingUtil.bind(itemView)!!

    fun bindData(model: T) {
        bindingInterface.bindData(model, binding)
        binding.executePendingBindings()
    }
}