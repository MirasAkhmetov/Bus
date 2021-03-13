package com.thousand.bus.main.presentation.common.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bus.R
import com.thousand.bus.app.BusApp
import com.thousand.bus.entity.User
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.*
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.global.extension.replaceFragmentWithBackStack
import com.thousand.bus.global.utils.AppConstants
import com.thousand.bus.global.utils.LocalStorage
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.activity.MainActivity
import com.thousand.bus.main.presentation.auth.sign_in.SignInFragment
import com.thousand.bus.main.presentation.common.web.WebViewFragment
import com.thousand.bus.main.presentation.customer.edit.EditProfileCustomerFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import timber.log.Timber

class ProfileFragment : BaseFragment(), ProfileView {

    companion object{

        val TAG = "ProfileFragment"

        private val BUNDLE_ROLE = "bundle_role"

        fun newInstance(role: String): ProfileFragment =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_ROLE, role)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        getKoin().getScopeOrNull(MainScope.PROFILE_SCOPE)?.close()
        val scope = getKoin().createScope(MainScope.PROFILE_SCOPE, named(MainScope.PROFILE_SCOPE))
        val role = arguments?.getString(BUNDLE_ROLE)
        return scope.get { parametersOf(role) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_profile

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.onFirstInit()
        rootProfile?.setOnClickListener {  }
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = getString(R.string.profile)
        txtEditProfile?.setOnClickListener { presenter.onEditBtnClicked() }
        txtAboutProfile?.setOnClickListener { presenter.onAboutBtnClicked() }
        txtSignOutProfile?.setOnClickListener { presenter.onSignOutBtnClicked() }
        txtLanguageProfile?.setOnClickListener { showLanguageDialog() }
        txtShareApp?.setOnClickListener{ onShareAppBtnClicked()}

        Timber.i("SASDAS=${LocalStorage.getLanguage()}")
        when(LocalStorage.getLanguage()){
            AppConstants.LANG_RU -> txtLanguageValueProfile.text = getString(R.string.profile_ru)
            AppConstants.LANG_KZ -> txtLanguageValueProfile.text = getString(R.string.profile_kz)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.PROFILE_SCOPE)?.close()
    }

    @SuppressLint("SetTextI18n")
    override fun showUserInfo(user: User) {
        txtNameProfile?.text = "${user.name ?: ""} ${user.surname ?: ""}"
        imgAvatarProfile?.setCircleImageUrl(user.avatar)
        if (user.role == AppConstants.ROLE_DRIVER)
            txtTypeProfile?.text = getString(R.string.driver)
        else
            txtTypeProfile?.text = getString(R.string.passenger)

        switchNotificationProfile?.isChecked = (user.push ?: "0") == "1"
        switchSoundProfile?.isChecked = (user.sound ?: "0") == "1"

        switchNotificationProfile?.setOnCheckedChangeListener { _, isChecked -> presenter.onNotificationChecked(isChecked) }
        switchSoundProfile?.setOnCheckedChangeListener { _, isChecked -> presenter.onSoundChecked(isChecked) }
    }

    override fun openEditProfileCustomerFragment() {
        activity?.replaceFragmentWithBackStack(
            R.id.container,
            EditProfileCustomerFragment.newInstance(),
            EditProfileCustomerFragment.TAG
        )
    }

    override fun openWebViewFragment(content: String) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            WebViewFragment.newInstance(content),
            WebViewFragment.TAG
        )
    }

    override fun openSignInFragment() {
        activity?.addFragment(
            R.id.container,
            SignInFragment.newInstance(),
            SignInFragment.TAG
        )
    }

    private fun showLanguageDialog(){
        context?.let {
            val list: MutableList<String> = mutableListOf()
            list.add(getString(R.string.profile_ru))
            list.add(getString(R.string.profile_kz))
            AlertDialog.Builder(it)
                .setItems(list.toTypedArray())
                { dialog, which ->
                    when(which){
                        0 -> {
                            LocalStorage.setLanguage(AppConstants.LANG_RU)
                            Handler().postDelayed({setNewLocale(it, AppConstants.LANG_RU)}, 500)
                        }
                        1 -> {
                            LocalStorage.setLanguage(AppConstants.LANG_KZ)
                            Handler().postDelayed({setNewLocale(it, AppConstants.LANG_KZ)}, 500)
                        }
                    }
                    dialog.dismiss()
                }.show()
        }
    }

    private fun onShareAppBtnClicked(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Скачай крутое приложение SaparLine \nhttp://onelink.to/y3898n"
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun setNewLocale(context: Context, language: String): Boolean {
        BusApp.localeManager?.setNewLocale(context, language)
        activity?.startActivity(Intent(activity, MainActivity::class.java))
        System.exit(0)
        return true
    }
}