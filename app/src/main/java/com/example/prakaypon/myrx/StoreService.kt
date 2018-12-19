package com.example.prakaypon.myrx

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class StoreService {

    val BASE_URL:String = "http://www.zoftino.com/api/"
    var retrofit:Retrofit? = null


    fun getCouponClient():Retrofit?
    {
        if (retrofit==null)
        {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
        return retrofit
    }
}


@JvmSuppressWildcards
interface StoreCouponApi
{
    @GET("coupons/")
    fun getCoupons (@Query("status") status:String): Observable<StroeCoupon>

    @GET("             ")
    fun getStoreInfo():Observable<StroeCoupon>
}