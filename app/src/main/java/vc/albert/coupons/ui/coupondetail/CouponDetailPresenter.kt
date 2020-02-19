package vc.albert.coupons.ui.coupondetail

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
class CouponDetailPresenter(
    private val getCoupons: GetCoupons,
    private val toggleCouponActivated: ToggleCouponActivated,
    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LifecycleObserver, CoroutineScope by CoroutineScope(ioDispatcher) {

    @UiThread
    interface View {
        fun showProgress()
        fun hideProgress()
        fun render(coupon: Coupon)
        /**
         * Notify previous screen that it has to reload the coupons to reflect the new activated state.
         */
        fun notifyActivatedChanged()
    }

    private var view: View? = null

    fun start(view: View, couponId: String) {
        this.view = view
        view.hideProgress()
        render(couponId)
    }

    private fun render(couponId: String) {
        launch {
            val coupon = getCoupons.execute().find { it.id == couponId } ?: return@launch
            withContext(uiDispatcher) {
                view?.hideProgress()
                view?.render(coupon)
            }
        }
    }

    @UiThread
    fun onActivateClick(couponId: String) {
        view?.showProgress()
        launch {
            toggleCouponActivated.execute(couponId)
            withContext(uiDispatcher) {
                view?.notifyActivatedChanged()
            }
            render(couponId)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroy() {
        view = null
        cancel() // CoroutineScope
    }

}
