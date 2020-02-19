package vc.albert.coupons.coupon.api

import vc.albert.coupons.coupon.Coupon

/**
 * Created by Albert Vila Calvo on 2020-02-08.
 */
interface CouponApi {

    suspend fun getCoupons(): List<Coupon>
    suspend fun activateCoupon(id: String)
    suspend fun deactivateCoupon(id: String)

}
