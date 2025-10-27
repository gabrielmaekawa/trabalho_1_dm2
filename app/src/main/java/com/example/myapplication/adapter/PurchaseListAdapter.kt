package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemPurchaseBinding
import com.example.myapplication.model.Purchase
import java.text.SimpleDateFormat
import java.util.Locale

class PurchaseListAdapter(
    private val context: Context,
    private val purchases: List<Purchase>
) : BaseAdapter() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun getCount(): Int = purchases.size

    override fun getItem(position: Int): Purchase = purchases[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = if (convertView?.tag != null) {
            convertView.tag as ItemPurchaseBinding
        } else {
            val inflater = LayoutInflater.from(context)
            val newBinding = ItemPurchaseBinding.inflate(inflater, parent, false)
            newBinding.root.tag = newBinding
            newBinding
        }

        val purchase = getItem(position)
        
        binding.apply {
            textViewPurchaseName.text = purchase.itemName
            textViewPurchasePrice.text = "R$ ${String.format("%.2f", purchase.itemValue)}"
            textViewPurchaseDate.text = dateFormat.format(purchase.timestamp)
            
            Glide.with(context)
                .load(purchase.itemPhoto)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .fallback(R.mipmap.ic_launcher)
                .into(imageViewPurchase)
        }

        return binding.root
    }
}
