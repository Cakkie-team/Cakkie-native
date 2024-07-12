package com.cakkie.utill


object Endpoints {

    /**
     * Base url
     **/
    //    private const val BASE_URL = "http://64.227.6.106/api/v1"  /*test base url*/
    private const val BASE_URL = "https://api.cakkie.com" /*val live base url*/
    const val SOCKET_URL =
        BASE_URL /*val live base url*/
    private const val KEY = "AIzaSyDu70nrGBsZTGWdxGm0rLPvAzDwlwMy_B8"

    /**
     * Authentication
     **/
    val CHECK_EMAIL = { email: String -> "$BASE_URL/auth/check-email/$email" } /* GET request */
    const val LOGIN = "$BASE_URL/auth/sign-in" /* POST request */
    const val SIGNUP = "$BASE_URL/auth/sign-up" /* POST request */
    const val VERIFY_OTP = "$BASE_URL/auth/verify-account" /* POST request */
    val RESEND_OTP = { email: String -> "$BASE_URL/auth/resend-otp/$email" } /* GET request */
    const val FORGET_PASSWORD = "$BASE_URL/auth/forgot-password" /*FORGET_PASSWORD POST request*/
    const val RESET_PASSWORD = "$BASE_URL/auth/reset-password" /* RESET_PASSWORD POST request */

    /**
     * Pin
     **/
    val VERIFY_PIN = { pin: String -> "$BASE_URL/account/verify-pin/$pin" } /* verify pin */
    const val RESET_PIN = "$BASE_URL/auth/reset-pin" /* set pin */


    /**
     *User
     **/
    val VERIFY_EMAIL = { email: String -> "$BASE_URL/verify-email?email=$email" }  /*get all users*/
    const val ACCOUNT = "$BASE_URL/account/profile" /* Get request */
    const val UPDATE_PROFILE = "$BASE_URL/account/update-profile" /* Put request */
    const val VERIFY_USERNAME = "$BASE_URL/verify-username?username=" /* verify tag */
    val DELETE_ACCOUNT = { reason: String -> "$BASE_URL/account/$reason" } /* delete account */
    const val REACTIVATE_ACCOUNT = "$BASE_URL/account/reactivate" /* reactivate account */
    const val GET_REFERRALS = "$BASE_URL/account/referrals" /* get referrals */
    val SEND_FCM_TOKEN =
        { token: String -> "$BASE_URL/account/set-fcm-token/$token" } /* send fcm token */
    const val LOGOUT = "$BASE_URL/auth/logout" /* logout */


    /**
     * Shop
     * */
    const val CREATE_SHOP = "$BASE_URL/shop" /* create shop  POST*/
    val GET_SHOP = { id: String -> "$BASE_URL/shop/$id" } /* get shop  GET*/
    const val UPDATE_SHOP = "$BASE_URL/shop"  /* update shop  PUT*/
    val VERIFY_SHOP_NAME =
        { name: String -> "$BASE_URL/shop/verify/$name" } /* verify shop name  GET*/
    val GET_REQUESTS =
        { page: Int, size: Int -> "$BASE_URL/shop/requests?page=$page&pageSize=$size" }/* get my request  GET*/
    val GET_CONTRACTS =
        { page: Int, size: Int -> "$BASE_URL/shop/contracts?page=$page&pageSize=$size" }/* get my contracts  GET*/

    /**
     * Listing
     * */
    const val CREATE_LISTING = "$BASE_URL/listing" /* create listing  POST*/
    val GET_MY_LISTINGS =
        { page: Int, size: Int -> "$BASE_URL/listing/me?page=$page&pageSize=$size" }/* get my listings  GET*/
    val GET_LISTINGS =
        { page: Int, size: Int -> "$BASE_URL/listing?page=$page&pageSize=$size" }/* get listings  GET*/
    val GET_CAKESPIRATIONS =
        { page: Int, size: Int -> "$BASE_URL/listing/cakespiration?page=$page&pageSize=$size" }/* get cakespiratins  GET*/
    val GET_COMMENTS =
        { id: String, page: Int, size: Int -> "$BASE_URL/listing/comments/$id?page=$page&pageSize=$size" }/* get listings  GET*/
    val GET_LISTING = { id: String -> "$BASE_URL/listing/$id" } /* get listing*/
    val GET_SHOP_LISTINGS =
        { id: String, page: Int, size: Int -> "$BASE_URL/listing/shop/$id?page=$page&pageSize=$size" }/* get shop listings  GET*/


    /**
     * Bunny
     * */
    val UPLOAD_IMAGE =
        { path: String, fileName: String -> "https://jh.storage.bunnycdn.com/cakkie-photos/$path/$fileName" } /* upload image  POST*/
    val FILE_URL =
        { fileName: String -> "https://cdn.cakkie.com/${fileName}" } /* access image  GET*/


    /**
     * Notification
     */
    val GET_NOTIFICATIONS =
        { page: Int, size: Int -> "$BASE_URL/notification?page=$page&pageSize=$size" }/* get notifications  GET*/


    /**
     * Jobs
     */
    val GET_JOBS =
        { page: Int, size: Int -> "$BASE_URL/job?page=$page&pageSize=$size" }/* get jobs  GET*/
    val GET_MY_JOBS =
        { page: Int, size: Int -> "$BASE_URL/job/me?page=$page&pageSize=$size" }/* get jobs  GET*/
    val GET_JOB = { id: String -> "$BASE_URL/job/$id" } /* get job  GET*/
    const val CREATE_JOB = "$BASE_URL/job" /* create job  POST*/
    val SUBMIT_PROPOSAL = { id: String -> "$BASE_URL/job/$id/proposal" } /* submit proposal  POST*/


    /**
     * Wallet
     */
    const val GET_BALANCE = "$BASE_URL/wallet/balance" /* get balance  GET*/
    const val DEPOSIT = "$BASE_URL/wallet/fund-wallet" /* deposit  POST*/
    val GET_TRANSACTION =
        { currencyId: String?, page: Int, size: Int ->
            "$BASE_URL/wallet/transactions?page=$page&pageSize=$size&currencyId=$currencyId"
        } /* get transaction  GET*/
    val GET_ALL_TRANSACTION =
        { page: Int, size: Int ->
            "$BASE_URL/wallet/transactions?page=$page&pageSize=$size"
        } /* get transaction  GET*/
    const val MINE = "$BASE_URL/wallet/mine" /* mine  POST*/
    val GET_REWARD = { title: String -> "$BASE_URL/wallet/get-reward/$title" } /* get reward  GET*/
    val GET_CONVERSION_RATE =
        { symbol: String, amount: Double -> "$BASE_URL/wallet/conversion-rate/$symbol/$amount" } /* get conversion  GET*/
    val GET_LEADERBOARD =
        { page: Int, size: Int -> "$BASE_URL/wallet/leaderboard?page=$page" }/* get leaderboard  GET*/ /*todo:  fix page size*/
    const val GET_PREFERENCE = "$BASE_URL/wallet/preference" /* get preference  GET*/
    const val RESOLVE_COUPON = "$BASE_URL/wallet/resolve-coupon" /* resolve coupon  POST*/
    const val SUBSCRIBE_SHOP = "$BASE_URL/wallet/subscribe-shop" /* subscribe shop  POST*/

    /**
     * Chat
     */
    const val START_CHAT = "$BASE_URL/chat/conversations" /* start chat  POST*/
    val GET_CONV =
        { search: String, page: Int, size: Int -> "$BASE_URL/chat/conversations?page=$page&search=$search&pageSize=$size" }/* get conversation  GET*/


    /**
     * Order
     */
    const val CREATE_ORDER = "$BASE_URL/order" /* create order  POST*/
    val GET_ORDERS =
        { status: String?, page: Int, size: Int ->
            "$BASE_URL/order?page=$page&pageSize=$size${
                if (status.isNullOrEmpty()) "" else "&status=${status.uppercase()}"
            }"
        } /* get orders  GET*/
    val CANCEL_ORDER = { id: String -> "$BASE_URL/order/cancel/$id" } /* cancel order */
    val DECLINE_ORDER = { id: String -> "$BASE_URL/order/reject/$id" } /* reject order */
    val ACCEPT_ORDER =
        { id: String, time: String -> "$BASE_URL/order/accept/$id/$time" } /* accept order */
    val GET_ORDER = { id: String -> "$BASE_URL/order/$id" } /* get order */
    val READY_ORDER = { id: String -> "$BASE_URL/order/ready/$id" } /* ready order */


    /**
     * Location
     * */
    val GET_LOCATION =
        { lat: Double, lng: Double -> "https://maps.googleapis.com/maps/api/geocode/json?address=$lat,$lng&key=$KEY" } /* get location  GET*/
    val SEARCH_LOCATION =
        { query: String, lat: Double, lng: Double -> "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=$query&location=$lat%2C$lng&radius=500&key=$KEY" } /* search location  GET*/
    val GET_ADDRESS =
        { address: String -> "https://maps.googleapis.com/maps/api/geocode/json?address=$address&sensor=true&key=$KEY" } /* get address  GET*/
}