package vc.albert.coupons.coupon

import androidx.annotation.WorkerThread
import vc.albert.coupons.coupon.api.CouponApi
import vc.albert.coupons.coupon.cache.CouponsCache

/**
 * Get all coupons from cache if existing, otherwise from server while saving them into the cache.
 */
@WorkerThread
class GetCoupons(
    private val api: CouponApi,
    private val couponsCache: CouponsCache
) {

    suspend fun execute(): List<Coupon> {
        val cachedCoupons = couponsCache.getCoupons()
        return if (cachedCoupons != null) {
            cachedCoupons
        } else {
            val coupons = api.getCoupons()
            couponsCache.setCoupons(coupons)
            coupons
        }
    }

}
