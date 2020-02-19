package vc.albert.coupons.coupon.cache

import vc.albert.coupons.coupon.Coupon

/**
 * Created by Albert Vila Calvo on 2020-02-08.
 */
interface CouponsCache {
    fun getCoupons(): List<Coupon>?
    fun setCoupons(coupons: List<Coupon>)
}
