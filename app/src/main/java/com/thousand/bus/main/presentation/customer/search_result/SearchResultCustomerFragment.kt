package com.thousand.bus.main.presentation.customer.search_result

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Window
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.tabs.TabLayout
import com.synnapps.carouselview.CarouselView
import com.thousand.bus.R
import com.thousand.bus.entity.Travel
import com.thousand.bus.entity.TravelListQuery
import com.thousand.bus.global.base.BaseFragment
import com.thousand.bus.global.extension.addFragmentWithBackStack
import com.thousand.bus.global.extension.getFormattedDate
import com.thousand.bus.global.extension.setImageUrl
import com.thousand.bus.global.extension.visible
import com.thousand.bus.main.di.MainScope
import com.thousand.bus.main.presentation.common.TravelAdapter
import com.thousand.bus.main.presentation.customer.booking.BookingCustomerFragment
import kotlinx.android.synthetic.main.fragment_customer_search_result.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class SearchResultCustomerFragment : BaseFragment(), SearchResultCustomerView {


    companion object {

        val TAG = "SearchResultCustomerFragment"

        private val BUNDLE_TRAVEL_LIST_QUERY = "travel_list_query"

        fun newInstance(travelListQuery: TravelListQuery): SearchResultCustomerFragment =
            SearchResultCustomerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_TRAVEL_LIST_QUERY, travelListQuery)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: SearchResultCustomerPresenter



    private val adapter = TravelAdapter(
        { presenter.loadDataNextPage() },
        { presenter.onOrderItemSelected(it) },
        { presenter.onOpenImageDialog(it) }
    )

    @ProvidePresenter
    fun providePresenter(): SearchResultCustomerPresenter {
        getKoin().getScopeOrNull(MainScope.SEARCH_RESULT_CUSTOMER_SCOPE)?.close()
        val scope = getKoin().createScope(
            MainScope.SEARCH_RESULT_CUSTOMER_SCOPE,
            named(MainScope.SEARCH_RESULT_CUSTOMER_SCOPE)
        )
        val travelListQuery: TravelListQuery? = arguments?.getParcelable(BUNDLE_TRAVEL_LIST_QUERY)
        return scope.get { parametersOf(travelListQuery) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_customer_search_result


    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbar?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbar?.text = getString(R.string.search_result)
        presenter.onFirstInit()
        recyclerCSR?.adapter = adapter
        tabLayout.addTab(tabLayout.newTab().setText("Автобус"))
        tabLayout.addTab(tabLayout.newTab().setText("Минивэн"))
        tabLayout.addTab(tabLayout.newTab().setText("Такси"))
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_bus)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_minibus_icon)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_taxi)


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tabLayout.selectedTabPosition == 1) {
                    presenter.onAlphardInit()
                } else if (tabLayout.selectedTabPosition == 2) {
                    presenter.onTaxiInit()
                } else {
                    presenter.onFirstInit()
                }
            }
        })

        btnSort.setOnClickListener {
            val builder1: AlertDialog.Builder = AlertDialog.Builder(context)
            val sort = arrayOf("По рейтингу")
            builder1.setItems(sort,
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        0 -> {
                            dialog.dismiss()
                            presenter.loadDataByFilter()
                            btnSort.text = "По рейтингу"
                        }
                    }
                })
            val dialog: AlertDialog = builder1.create()
            dialog.show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        getKoin().getScopeOrNull(MainScope.SEARCH_RESULT_CUSTOMER_SCOPE)?.close()
    }

    override fun showTravelData(dataList: List<Travel>) {
        adapter.submitData(dataList)
        txtEmptyCSR.visible(dataList.isEmpty())
    }

    override fun openBookingCustomerFragment(travelId: Int, carId: Int, carState : String) {
        activity?.addFragmentWithBackStack(
            R.id.container_main,
            BookingCustomerFragment.newInstance(travelId, carId, carState),
            BookingCustomerFragment.TAG
        )
    }

    override fun openImageDialog(travel: Travel) {
        val images = arrayOf(travel.car?.avatar, travel.car?.image, travel.car?.image1, travel.car?.image2)
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_image)
        val image: CarouselView  = dialog.findViewById(R.id.imageDialog) as CarouselView
        image.pageCount = images.size
        image.setImageListener { position, imageView ->
            imageView.setImageUrl(images[position])
        }
        dialog.show()

    }


    override fun showTravelInfo(travelListQuery: TravelListQuery) {
        txtPeopleCountCSR?.text =
            getString(R.string.order_people, travelListQuery.placeCount.toString())
        txtDateCountCSR?.text = travelListQuery.time.getFormattedDate()
        txtFromCSR?.text = travelListQuery.fromCity
        txtFromStationCSR?.text = travelListQuery.fromStation
        txtToCSR?.text = travelListQuery.toCity
        txtToStationCSR?.text = travelListQuery.toStation
    }

    override fun setCountTab(count: Int, isBus: Int?) {
        when (isBus) {
            1 -> tabLayout.getTabAt(0)?.text = "Автобус ($count)"
            0 -> tabLayout.getTabAt(1)?.text = "Минивэн ($count)"
            2 -> tabLayout.getTabAt(2)?.text = "Такси ($count)"
        }
    }

}