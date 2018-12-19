package com.example.prakaypon.myrx



data class Coupon  (
    var store:String,
    var coupon:String,
    var expiryDate:String,
    var couponCode:String
)


data class StroeCoupon(var store: String,
                       var totalCoupon:String,
                       var maxCashback:String,
                       var coupons:List<Coupon>) {

}