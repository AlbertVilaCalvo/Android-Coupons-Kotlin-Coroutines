package vc.albert.coupons.ui.coupondetail

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import vc.albert.coupons.coupon.Coupon
import vc.albert.coupons.coupon.GetCoupons
import vc.albert.coupons.coupon.ToggleCouponActivated
import java.util.*

private const val COUPON_ID = "couponId"
private val COUPONS = listOf(
    Coupon(
        id = COUPON_ID,
        name = "Platano de Canarias"
    ),
    Coupon(
        id = UUID.randomUUID().toString(),
        name = "Patatas sabor jamón"
    )
)

/**
 * Created by Albert Vila Calvo on 2020-02-09.
 */
@ExperimentalCoroutinesApi
class CouponDetailPresenterTest {

    private lateinit var presenter: CouponDetailPresenter

    private val view: CouponDetailPresenter.View = mock()
    private val getCoupons: GetCoupons = mock()
    private val toggleCouponActivated: ToggleCouponActivated = mock()

    @Before
    fun setUp() {
        presenter = CouponDetailPresenter(
            getCoupons,
            toggleCouponActivated,
            uiDispatcher = Dispatchers.Unconfined,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun `on start should hide progress`() = runBlockingTest {
        whenever(getCoupons.execute()).thenReturn(COUPONS)
        presenter.start(view, COUPON_ID)
        verify(view, times(2)).hideProgress()
    }

    @Test
    fun `on start should execute getCoupons`() = runBlockingTest {
        whenever(getCoupons.execute()).thenReturn(COUPONS)
        presenter.start(view, COUPON_ID)
        verify(getCoupons).execute()
    }

    @Test
    fun `on start should render coupon with given ID`() = runBlockingTest {
        whenever(getCoupons.execute()).thenReturn(COUPONS)
        presenter.start(view, COUPON_ID)
        verify(view).render(COUPONS[0])
    }

    @Test
    fun `on activated click should notify that activated changed`() = runBlockingTest {
        whenever(getCoupons.execute()).thenReturn(COUPONS)
        presenter.start(view, COUPON_ID)
        presenter.onActivateClick(COUPON_ID)
        verify(view).notifyActivatedChanged()
    }

}
