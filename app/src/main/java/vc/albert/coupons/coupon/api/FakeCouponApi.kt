package vc.albert.coupons.coupon.api

import kotlinx.coroutines.delay
import vc.albert.coupons.coupon.Coupon
import vc.albert.coupons.coupon.fakeCoupons

/**
 * Created by Albert Vila Calvo on 2020-02-08.
 */
class FakeCouponApi : CouponApi {

    override suspend fun getCoupons(): List<Coupon> {
        delay(500)
        return fakeCoupons
    }

    override suspend fun activateCoupon(id: String) {
        delay(500)
    }

    override suspend fun deactivateCoupon(id: String) {
        delay(500)
    }

}
