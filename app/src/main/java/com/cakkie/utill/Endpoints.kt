package com.cakkie.utill


object Endpoints {

    /**
     * Base url
     **/
    //    private const val BASE_URL = "http://64.227.6.106/api/v1"  /*test base url*/
    private const val BASE_URL = "https://api.cakkie.com" /*val live base url*/
    const val SOCKET_URL =
        BASE_URL /*val live base url*/

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
    const val VERIFY_PIN = "$BASE_URL/user/verify-pin" /* verify pin */


    /**
     * Transactions
     */
    const val GET_WALLET_BALANCE = "$BASE_URL/account/wallet/balance" /* get wallet balance */
    const val GET_TRANSACTIONS = "$BASE_URL/account/wallet/transactions" /* get transactions */


    /**
     *User
     **/
    val VERIFY_EMAIL = { email: String -> "$BASE_URL/verify-email?email=$email" }  /*get all users*/
    const val ACCOUNT = "$BASE_URL/account/profile" /* Get request */
    const val UPDATE_PROFILE_IMAGE = "$BASE_URL/account/update-profile-image" /* Put request */
    const val VERIFY_USERNAME = "$BASE_URL/verify-username?username=" /* verify tag */


    /**
     * Shop
     * */
    const val CREATE_SHOP = "$BASE_URL/shop" /* create shop  POST*/

    /**
     * Listing
     * */
    const val CREATE_LISTING = "$BASE_URL/listing" /* create listing  POST*/
    val GET_MY_LISTINGS =
        { page: Int, size: Int -> "$BASE_URL/listing/me?page=$page&pageSize=$size" }/* get my listings  GET*/
    val GET_LISTINGS =
        { page: Int, size: Int -> "$BASE_URL/listing?page=$page&pageSize=$size" }/* get listings  GET*/
    val GET_COMMENTS =
        { id: String, page: Int, size: Int -> "$BASE_URL/listing/comments/$id?page=$page&pageSize=$size" }/* get listings  GET*/
    val GET_LISTING = { id: String -> "$BASE_URL/listing/$id" } /* get listing*/


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


}