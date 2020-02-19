package vc.albert.coupons.coupon.cache

import vc.albert.coupons.coupon.Coupon

/**
 * Created by Albert Vila Calvo on 2020-02-08.
 */
class CouponsMemoryCache : CouponsCache {

    private var coupons: List<Coupon>? = null

    @Synchronized
    override fun getCoupons(): List<Coupon>? {
        return coupons
    }

    @Synchronized
    override fun setCoupons(coupons: List<Coupon>) {
        this.coupons = coupons
    }

}
