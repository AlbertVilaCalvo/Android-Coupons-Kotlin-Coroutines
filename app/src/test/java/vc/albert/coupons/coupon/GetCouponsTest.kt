package vc.albert.coupons.coupon

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import vc.albert.coupons.coupon.api.CouponApi
import vc.albert.coupons.coupon.cache.CouponsCache

/**
 * Created by Albert Vila Calvo on 2020-02-16.
 */
@ExperimentalCoroutinesApi
class GetCouponsTest {

    private lateinit var getCoupons: GetCoupons

    private val api: CouponApi = mock()
    private val couponsCache: CouponsCache = mock()

    @Before
    fun setUp() {
        getCoupons = GetCoupons(api, couponsCache)
    }

    @Test
    fun `when the cache is empty it should get coupons from API`() = runBlockingTest {
        whenever(couponsCache.getCoupons()).thenReturn(null)
        getCoupons.execute()
        verify(api).getCoupons()
    }

    @Test
    fun `when the cache is empty it should save the coupons from the API into the cache`() = runBlockingTest {
        whenever(couponsCache.getCoupons()).thenReturn(null)
        whenever(api.getCoupons()).thenReturn(fakeCoupons)
        getCoupons.execute()
        verify(couponsCache).setCoupons(fakeCoupons)
    }

    @Test
    fun `when the cache is not empty it should return the cached coupons and not hit the API`() = runBlockingTest {
        whenever(couponsCache.getCoupons()).thenReturn(fakeCoupons)
        val actualCoupons = getCoupons.execute()
        Assert.assertEquals(fakeCoupons, actualCoupons)
        verifyZeroInteractions(api)
    }

}
