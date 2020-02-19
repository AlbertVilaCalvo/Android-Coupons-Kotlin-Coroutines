package vc.albert.coupons.ui.couponlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import vc.albert.coupons.R
import vc.albert.coupons.coupon.Coupon

/**
 * Created by Albert Vila Calvo on 2020-02-07.
 */
class CouponsAdapter(
    private val listener: Listener
) : ListAdapter<Coupon, CouponsAdapter.ViewHolder>(
    ItemCallback()
) {

    private class ItemCallback : DiffUtil.ItemCallback<Coupon>() {
        override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
            return oldItem.isActivated == newItem.isActivated
        }
    }

    interface Listener {
        fun onCouponClick(coupon: Coupon)
        fun onCouponActivateClick(coupon: Coupon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coupon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.item_coupon_imageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.item_coupon_name_textView)
        private val expireTextView: TextView =
            itemView.findViewById(R.id.item_coupon_expire_textView)
        private val activateButton: Button =
            itemView.findViewById(R.id.item_coupon_activate_button)

        init {
            itemView.setOnClickListener {
                listener.onCouponClick(coupon)
            }
            activateButton.setOnClickListener {
                listener.onCouponActivateClick(coupon)
            }
        }

        private lateinit var coupon: Coupon

        internal fun bind(coupon: Coupon) {
            this.coupon = coupon
            imageView.load(coupon.imageUrl)
            nameTextView.text = coupon.name
            expireTextView.text = coupon.expire
            if (coupon.isActivated) {
                activateButton.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                activateButton.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black_80
                    )
                )
                activateButton.text = itemView.context.getString(R.string.activated)
            } else {
                activateButton.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.blue
                    )
                )
                activateButton.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                activateButton.text = itemView.context.getString(R.string.activate)
            }
        }
    }

}
