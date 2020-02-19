package vc.albert.coupons

import org.koin.dsl.module
import vc.albert.coupons.coupon.GetCoupons
import vc.albert.coupons.coupon.ToggleCouponActivated
import vc.albert.coupons.coupon.api.CouponApi
import vc.albert.coupons.coupon.api.FakeCouponApi
import vc.albert.coupons.coupon.cache.CouponsCache
import vc.albert.coupons.coupon.cache.CouponsMemoryCache
import vc.albert.coupons.ui.coupondetail.CouponDetailPresenter
import vc.albert.coupons.ui.couponlist.CouponListPresenter

/**
 * Created by Albert Vila Calvo on 2020-02-08.
 */

val uiModule = module {

    factory { CouponListPresenter(get(), get()) }

    factory { CouponDetailPresenter(get(), get()) }

}

val couponsModule = module {

    single<CouponApi> { FakeCouponApi() }

    single<CouponsCache> { CouponsMemoryCache() }

    factory { GetCoupons(get(), get()) }

    factory { ToggleCouponActivated(get(), get()) }

}
