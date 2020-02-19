package vc.albert.coupons.ui.couponlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.koin.android.ext.android.inject
import vc.albert.coupons.R
import vc.albert.coupons.coupon.Coupon
import vc.albert.coupons.ui.coupondetail.CouponDetailActivity
import vc.albert.coupons.ui.misc.SpaceItemDecoration
import vc.albert.coupons.ui.misc.makeGone
import vc.albert.coupons.ui.misc.makeVisible

private const val REQUEST_CODE_COUPON_ACTIVATED_CHANGED = 10

class CouponListActivity : AppCompatActivity(),
    CouponListPresenter.View, CouponsAdapter.Listener {

    private lateinit var toolbar: Toolbar
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: ProgressBar

    private lateinit var couponsAdapter: CouponsAdapter

    private val presenter: CouponListPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_list)

        toolbar = findViewById(R.id.toolbar)
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout)
        recyclerView = findViewById(R.id.coupon_list_recyclerView)
        progress = findViewById(R.id.coupon_list_progress)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupRecyclerView()

        lifecycle.addObserver(presenter)
        presenter.start(this)
    }

    private fun setupRecyclerView() {
        val itemDecoration = SpaceItemDecoration(this, R.dimen.item_coupon_outer_margin)
        recyclerView.addItemDecoration(itemDecoration)
        couponsAdapter = CouponsAdapter(listener = this)
        recyclerView.adapter = couponsAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_COUPON_ACTIVATED_CHANGED && resultCode == Activity.RESULT_OK) {
            // User changed the activated state in the detail -> reload coupon list
            presenter.reloadCoupons()
        }
    }

    // CouponListPresenter.View

    override fun showProgress() {
        progress.makeVisible()
    }

    override fun hideProgress() {
        progress.makeGone()
    }

    override fun render(coupons: List<Coupon>, activatedCouponCount: Int) {
        collapsingToolbarLayout.title =
            resources.getQuantityString(
                R.plurals.activated_coupon_count,
                activatedCouponCount,
                activatedCouponCount
            )
        couponsAdapter.submitList(coupons)
    }

    // CouponsAdapter.Listener

    override fun onCouponClick(coupon: Coupon) {
        startActivityForResult(
            CouponDetailActivity.newIntent(this, coupon.id),
            REQUEST_CODE_COUPON_ACTIVATED_CHANGED
        )
    }

    override fun onCouponActivateClick(coupon: Coupon) {
        presenter.onCouponActivateClick(coupon)
    }

}
