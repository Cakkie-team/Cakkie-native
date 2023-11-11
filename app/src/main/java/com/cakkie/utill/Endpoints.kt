package com.cakkie.utill


object Endpoints {

    /**
     * Base url
     **/
    //    private const val BASE_URL = "http://64.227.6.106/api/v1"  /*test base url*/
    private const val BASE_URL = "https://api.cakkie.com" /*val live base url*/

    /**
     * Authentication
     **/
    val CHECK_EMAIL = { email: String -> "$BASE_URL/auth/check-email/$email" } /* GET request */
    const val LOGIN = "$BASE_URL/auth/sign-in" /* POST request */
    const val VERIFY_OTP = "$BASE_URL/auth/verify-account" /* POST request */

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
    const val ACCOUNT = "$BASE_URL/account" /* Put request */
    const val UPDATE_PROFILE_IMAGE = "$BASE_URL/account/update-profile-image" /* Put request */
    const val VERIFY_USERNAME = "$BASE_URL/verify-username?username=" /* verify tag */


    /**
     * Notification
     */


}