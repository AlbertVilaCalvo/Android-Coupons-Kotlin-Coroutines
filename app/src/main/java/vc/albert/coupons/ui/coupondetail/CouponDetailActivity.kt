package vc.albert.coupons.ui.coupondetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.api.load
import org.koin.android.ext.android.inject
import vc.albert.coupons.R
import vc.albert.coupons.coupon.Coupon
import vc.albert.coupons.ui.misc.makeGone
import vc.albert.coupons.ui.misc.makeVisible

private const val EXTRA_COUPON_ID = "EXTRA_COUPON_ID"

class CouponDetailActivity : AppCompatActivity(), CouponDetailPresenter.View {

    companion object {
        fun newIntent(packageContext: Context, couponId: String): Intent {
            val intent = Intent(packageContext, CouponDetailActivity::class.java)
            intent.putExtra(EXTRA_COUPON_ID, couponId)
            return intent
        }
    }

    private lateinit var progress: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var percentDiscountTextView: TextView
    private lateinit var activatedTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var expireTextView: TextView
    private lateinit var codeTextView: TextView
    private lateinit var activateButton: Button

    private val presenter: CouponDetailPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_detail)

        setSupportActionBar(findViewById(R.id.coupon_detail_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val couponId = intent.getStringExtra(EXTRA_COUPON_ID) as String

        progress = findViewById(R.id.coupon_detail_progress)
        imageView = findViewById(R.id.coupon_detail_imageView)
        percentDiscountTextView = findViewById(R.id.coupon_detail_percent_discount_textView)
        activatedTextView = findViewById(R.id.coupon_detail_activated_textView)
        nameTextView = findViewById(R.id.coupon_detail_name_textView)
        descriptionTextView = findViewById(R.id.coupon_detail_description_textView)
        expireTextView = findViewById(R.id.coupon_detail_expire_textView)
        codeTextView = findViewById(R.id.coupon_detail_code_textView)
        activateButton = findViewById(R.id.coupon_detail_activate_button)
        activateButton.setOnClickListener {
            presenter.onActivateClick(couponId)
        }

        lifecycle.addObserver(presenter)
        presenter.start(this, couponId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // CouponDetailPresenter.View

    override fun showProgress() {
        progress.makeVisible()
    }

    override fun hideProgress() {
        progress.makeGone()
    }

    override fun render(coupon: Coupon) {
        imageView.load(coupon.imageUrl)
        percentDiscountTextView.text = getString(R.string.percent_discount, coupon.percentDiscount)
        if (coupon.isActivated) {
            activateButton.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            activateButton.setTextColor(ContextCompat.getColor(this, R.color.black_80))
            activateButton.text = getString(R.string.activated)
            activatedTextView.text = getString(R.string.activated)
        } else {
            activateButton.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
            activateButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            activateButton.text = getString(R.string.activate)
            activatedTextView.text = getString(R.string.not_activated)
        }
        nameTextView.text = coupon.name
        descriptionTextView.text = coupon.description
        expireTextView.text = coupon.expire
        codeTextView.text = coupon.code
    }

    override fun notifyActivatedChanged() {
        setResult(Activity.RESULT_OK)
    }

}
