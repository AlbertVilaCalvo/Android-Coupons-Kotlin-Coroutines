package vc.albert.coupons.ui.couponlist

import androidx.annotation.UiThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import vc.albert.coupons.coupon.Coupon
import vc.albert.coupons.coupon.GetCoupons
import vc.albert.coupons.coupon.ToggleCouponActivated

/**
 * Created by Albert Vila Calvo on 2020-02-08.
 */
class CouponListPresenter(
    private val getCoupons: GetCoupons,
    private val toggleCouponActivated: ToggleCouponActivated
) : LifecycleObserver, CoroutineScope by CoroutineScope(Dispatchers.IO) {

    @UiThread
    interface View {
        fun showProgress()
        fun hideProgress()
        fun render(coupons: List<Coupon>, activatedCouponCount: Int)
    }

    private var view: View? = null

    fun start(view: View) {
        this.view = view
        render()
    }

    @UiThread
    private fun render() {
        view?.showProgress()
        launch {
            val coupons = getCoupons.execute()
            val activatedCouponCount = coupons.filter { it.isActivated }.count()
            withContext(Dispatchers.Main) {
                view?.hideProgress()
                view?.render(coupons, activatedCouponCount)
            }
        }
    }

    fun reloadCoupons() {
        render()
    }

    fun onCouponActivateClick(coupon: Coupon) {
        view?.showProgress()
        launch {
            toggleCouponActivated.execute(coupon.id)
            withContext(Dispatchers.Main) {
                render()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroy() {
        view = null
        cancel() // CoroutineScope
    }

}
