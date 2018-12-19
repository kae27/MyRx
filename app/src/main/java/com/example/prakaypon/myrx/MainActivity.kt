package com.example.prakaypon.myrx

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import java.text.FieldPosition


class MainActivity : AppCompatActivity() {
    lateinit var retrofit:Retrofit
    lateinit var recyclerView: RecyclerView
    lateinit var bt:Button
    var b :AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById<RecyclerView>(R.id.coupon_rv)
        bt = findViewById<Button>(R.id.bt1)

        recyclerView.layoutManager =LinearLayoutManager(this,LinearLayout.VERTICAL,false)

        val gson = GsonBuilder()
            .setLenient()
            .create()

            retrofit = Retrofit.Builder()
            .baseUrl("http://www.zoftino.com/api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        var dialogLayout = AlertDialog.Builder(this).apply {
            setTitle("Download.")
            setMessage("please wait..")
        }
//        dialogLayout.setTitle("Download.")
//        dialogLayout.setMessage("please wait..")


        b = dialogLayout.create()
        bt.setOnClickListener {

            b!!.show() // un-necessary !! if use lateinit at init type value

            getStoreCouponData()

        }
    }


    @SuppressLint("CheckResult")
    private fun getStoreCouponData() {
        Observable.just<StoreCouponApi>(retrofit.create(StoreCouponApi::class.java!!)).subscribeOn(
            Schedulers.computation()
        )
            .flatMap<Any> { s ->
                val couponsObservable = s.getCoupons("topcoupons").subscribeOn(Schedulers.io())

                val storeInfoObservable = s.getStoreInfo().subscribeOn(Schedulers.io())

                Observable.concatArray<StroeCoupon>(couponsObservable, storeInfoObservable)
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({ this.handleResults(it as StroeCoupon)
                                        b!!.dismiss()}
                , { this.handleError(it) })
//            .subscribe(Consumer<Any> { this.handleResults(it as StroeCoupon)
//                b!!.dismiss()}
//                , Consumer<Throwable> { this.handleError(it) })

    }

    private fun handleResults(storeCoupons: StroeCoupon) {
        if (storeCoupons.coupons!= null) {

            val adapter = CouponsAdapter(storeCoupons.coupons, object :CouponsAdapter.OnItemClickListener
            {
                override fun onItemClick(position: Int)
                {
                    Toast.makeText(applicationContext,"item = "+position,Toast.LENGTH_SHORT).show()

                }
            })
            recyclerView.setAdapter(adapter)


//            Log.i("test"," "+storeCoupons.coupons)
        }
    }

    private fun handleError(t: Throwable) {
        Log.e("Observer", "" + t.toString())
//        Toast.makeText(
//            this, t.toString(),
//            Toast.LENGTH_LONG
//        ).show()
    }
}
