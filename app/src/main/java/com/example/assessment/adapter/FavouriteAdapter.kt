package com.example.assessment.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assessment.ProductsItem
import com.example.assessment.R
import com.example.assessment.databinding.ItemProductBinding
import kotlinx.android.synthetic.main.item_product.view.*

class FavouriteAdapter(private val mList: List<ProductsItem>, val btnlistener: BtnClickListener) :
    RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    companion object {
        var checkBoxStateArray = SparseBooleanArray()
        var mClickListener: BtnClickListener? = null
    }

    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(get: ProductsItem) {

            binding.textViewName.text = get.title
            binding.textViewPrice.text = "Price : " + get.price?.get(0)?.value.toString() + " Rs"
            Glide.with(binding.root).load(get.imageURL).placeholder(R.drawable.ic_launcher_background).into(binding.imageview)

            binding.imgFav.setOnClickListener {
                if (checkBoxStateArray.get(adapterPosition, false)) {
                    binding.imgFav.isChecked = true
                    checkBoxStateArray.put(adapterPosition, true)
                } else {
                    binding.imgFav.isChecked = false
                    checkBoxStateArray.put(adapterPosition, false)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mClickListener = btnlistener
        holder.bind(mList.get(position))

        holder.binding.imgFav.isChecked = mList[position].isChecked == true
        holder.binding.imgFav.setOnCheckedChangeListener { _, isChecked ->
            mList[holder.adapterPosition].isChecked = isChecked
        }

        holder.binding.btnAddToCart.setOnClickListener {
            mClickListener?.onBtnClick(position)
        }
        holder.binding.imgFav.setOnClickListener {
            mClickListener?.onFavClick(position, holder.itemView.imgFav)
        }
        holder.itemView.setOnClickListener {
            mClickListener?.onItemClick(position)

        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    open interface BtnClickListener {
        fun onBtnClick(position: Int)
        fun onItemClick(position: Int)
        fun onFavClick(position: Int, imgFav: CheckBox)
    }
}