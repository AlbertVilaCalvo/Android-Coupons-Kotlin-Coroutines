package vc.albert.coupons.coupon

import androidx.annotation.WorkerThread
import vc.albert.coupons.coupon.api.CouponApi
import vc.albert.coupons.coupon.cache.CouponsCache

/**
 * Toggle coupon activated state in server and cache.
 */
@WorkerThread
class ToggleCouponActivated(
    private val api: CouponApi,
    private val couponsCache: CouponsCache
) {

    suspend fun execute(couponId: String) {
        val coupon = couponsCache.getCoupons()?.find { it.id == couponId }
            ?: throw Exception("Could not get coupon with id $couponId from cache")
        if (coupon.isActivated) {
            api.deactivateCoupon(couponId)
        } else {
            api.activateCoupon(couponId)
        }
        couponsCache.getCoupons()?.let { coupons ->
            val updatedCoupons = coupons.map {
                if (couponId == it.id) {
                    it.copy(isActivated = !coupon.isActivated)
                } else {
                    it
                }
            }
            couponsCache.setCoupons(updatedCoupons)
        }
    }

}
