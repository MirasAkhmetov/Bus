package com.thousand.bus.main.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.app.BusApp
import com.thousand.bus.global.extension.replaceFragment
import com.thousand.bus.global.extension.visible
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.auth.customer.confirm.CustomerConfirmFragment
import com.thousand.bus.main.presentation.auth.driver.confirm.DriverConfirmFragment
import com.thousand.bus.main.presentation.auth.sign_in.SignInFragment
import com.thousand.bus.main.presentation.common.dialog.FeedbackDialogFragment
import com.thousand.bus.main.presentation.customer.main.MainCustomerFragment
import com.thousand.bus.main.presentation.customer.ticket.TicketCustomerFragment
import com.thousand.bus.main.presentation.driver.main.MainDriverFragment
import com.thousand.bus.main.presentation.driver.passenger.PassengerDriverFragment
import com.thousand.bus.main.presentation.welcome.WelcomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import timber.log.Timber


class MainActivity : MvpAppCompatActivity(), MainActivityView {

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @ProvidePresenter
    fun providePresenter(): MainActivityPresenter {
        getKoin().getScopeOrNull(MainScope.MAIN_ACTIVITY_SCOPE)?.close()
        return getKoin().createScope(MainScope.MAIN_ACTIVITY_SCOPE, named(MainScope.MAIN_ACTIVITY_SCOPE)).get()
    }

    override fun attachBaseContext(newBase: Context) {
        val newContext: Context?
        Timber.i("SADQQQQFKEF")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val displayMetrics =
                newBase?.resources?.displayMetrics
            val configuration =
                newBase?.resources?.configuration
            if (displayMetrics?.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE) {
                // Current density is different from Default Density. Override it
                displayMetrics?.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE
                configuration?.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE
                newContext = newBase //baseContext.createConfigurationContext(configuration);
            } else {
                // Same density. Just use same context
                newContext = newBase
            }
        } else {
            // Old API. Screen zoom not supported
            newContext = newBase
        }

        super.attachBaseContext(BusApp.localeManager?.setLocale(newContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onFirstInit()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLongMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgressBar(show: Boolean) {
        progressBarMain.visible(show)
    }

    override fun openSignInFragment() {
        supportFragmentManager.replaceFragment(
            R.id.container,
            SignInFragment.newInstance(),
            SignInFragment.TAG
        )
    }

    override fun openMainCustomerFragment() {
        supportFragmentManager.replaceFragment(
            R.id.container,
            MainCustomerFragment.newInstance(),
            MainCustomerFragment.TAG
        )
    }

    override fun openMainDriverFragment() {
        supportFragmentManager.replaceFragment(
            R.id.container,
            MainDriverFragment.newInstance(),
            MainDriverFragment.TAG
        )
    }

    override fun openCustomerConfirmFragment() {
        replaceFragment(
            R.id.container,
            CustomerConfirmFragment.newInstance(),
            CustomerConfirmFragment.TAG
        )
    }

    override fun openDriverConfirmFragment() {
        replaceFragment(
            R.id.container,
            DriverConfirmFragment.newInstance(),
            DriverConfirmFragment.TAG
        )
    }

    override fun openWelcomeFragment() {
        replaceFragment(
            R.id.container,
            WelcomeFragment.newInstance(),
            WelcomeFragment.TAG
        )
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0){
            super.onBackPressed()
        }else{
            val dialog = AlertDialog.Builder(this)
                .setMessage(getString(R.string.out))
                .setPositiveButton(getString(R.string.yes)){ dialog, _ ->
                    dialog.dismiss()
                    super.onBackPressed()
                }
                .setNegativeButton(getString(R.string.no)){ dialog, _ ->
                    dialog.dismiss()
                }
                .create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            val type = intent.getStringExtra("typeNotifications")
            val carId = intent.getIntExtra("carIdNotification", -1)
            val carId2 = intent.getIntExtra("carIdFeedback", -1)
            Log.d("Miras", "type data c $type")

            when(type){
                "driver_confirmation" -> {
                    val fragment: Fragment = PassengerDriverFragment.newInstance(carId)
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                        .addToBackStack(null).commit()
                }
                "reservation", "place_take" -> {
                    val fragment: Fragment = MainCustomerFragment.newInstance(type)
                    supportFragmentManager.beginTransaction().add(R.id.container, fragment)
                        .addToBackStack(null).commit()
                }
                "feedback"-> {
                    val fragment: Fragment = FeedbackDialogFragment.newInstance(carId2)
                    supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
                        .addToBackStack(null).commit()
                }

            }
        }
    }

}