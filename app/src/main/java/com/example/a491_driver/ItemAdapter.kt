package com.example.a491_driver

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val DELIVERY_EXTRA = "DELIVERY_EXTRA"
class ItemAdapter(private val deliveries: List<Delivery>, private val context: Context) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
        val itemSource: TextView = itemView.findViewById(R.id.itemSourceAddress)
        val itemDestination: TextView = itemView.findViewById(R.id.itemDestinationAddress)
        val itemTip: TextView = itemView.findViewById(R.id.itemTip)
        val itemImage: ImageView = itemView.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val delivery = deliveries[position]
        holder.itemTitle.text = delivery.name
        holder.itemSource.text = "Source Address: " + delivery.source
        holder.itemDestination.text = "Destination Address: " + delivery.destination
        holder.itemTip.text = "Tip: $" + delivery.tip
        Glide.with(holder.itemImage)
            .load(delivery.imageUrl)
//            .load(ContextCompat.getDrawable(context, R.drawable.shekhmus))
            .centerInside()
//            .placeholder(R.drawable.loading) // Loading Image
//            .error(R.drawable.placeholder) // Error Image for when image is isn't found
            .into(holder.itemImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TakeDeliveryActivity::class.java)
            intent.putExtra(DELIVERY_EXTRA, delivery)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return deliveries.size
    }
}
