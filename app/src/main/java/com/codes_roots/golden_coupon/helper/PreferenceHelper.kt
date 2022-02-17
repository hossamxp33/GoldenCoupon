package com.codes_roots.golden_coupon.helper

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class PreferenceHelper(private val context: Context) {

        private var app_prefs: SharedPreferences? = null
        private  val app_ref = "userdetails"
        private  val IsActive = "IsActive"


    var CountryId: Int
        get() = app_prefs!!.getInt("CountryId", 0)
        set(CountryId) {
            var edit = app_prefs!!.edit()
            edit.putInt("CountryId", CountryId)
            edit.apply()
        }
          var CityId : Int
              get() = app_prefs!!.getInt("CityId", 0)
              set(CityId) {
                  var edit = app_prefs!!.edit()
                  edit.putInt("CityId", CityId)
                  edit.apply()
              }
    var userName : String?
        get() = app_prefs!!.getString("userName", "Alaa")
        set(userName) {
            val edit = app_prefs!!.edit()

            edit.putString("userName", userName)
            edit.apply()
        }
    var CityTileName : String?
        get() = app_prefs!!.getString("CityTileName", "قم بالضغط لتحديد المدينة")
        set(CityTileName) {
            val edit = app_prefs!!.edit()

            edit.putString("CityTileName", CityTileName)
            edit.apply()
        }
    var userAddress : String?
    get() = app_prefs!!.getString("userAddress","Address")
    set(userAddress) {
        val edit = app_prefs!!.edit()
        edit.putString("userAddress",userAddress)
        edit.apply()
    }
    var UserToken : String?
        get() = app_prefs!!.getString("token","0")
        set(token) {
            val edit = app_prefs!!.edit()
            edit.putString("token",token)
            edit.apply()
        }
    var userPhone : String?
        get() = app_prefs!!.getString("userPhone","userPhone")
        set(userPhone) {
            val edit = app_prefs!!.edit()
            edit.putString("userPhone",userPhone)
            edit.apply()
        }


    var UserId : Int
        get() = app_prefs!!.getInt("UserId", 0)
        set(UserId) {
            var edit = app_prefs!!.edit()
            edit.putInt("UserId", UserId)
            edit.apply()
        }

    var token : String?
        get() = app_prefs!!.getString("Token", "")
        set(token) {
            val edit = app_prefs!!.edit()
            edit.putString("Token", token)
            edit.apply()
        }
    var lang : String?
        get() = app_prefs!!.getString("lang", "")
        set(lang) {
            val edit = app_prefs!!.edit()
            edit.putString("lang", lang)
            edit.apply()
        }


    var photo : String?
        get() = app_prefs!!.getString("photo", "photo")
        set(photo) {
            val edit = app_prefs!!.edit()
            edit.putString("photo", photo)
            edit.apply()
        }




    init {
        try {
            app_prefs = context.getSharedPreferences(
                app_ref,
                Context.MODE_PRIVATE
            )
        } catch (e: NullPointerException) {
        }
    }
}