package com.ubaya.ubayakuliner160420067.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.ubayakuliner160420067.model.Orders
import com.ubaya.ubayakuliner160420067.model.Promo

class ListPromosViewModel(application: Application): AndroidViewModel(application)  {
    val promoLD = MutableLiveData<ArrayList<Promo>>()
    val promoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://raw.githubusercontent.com/veliciamp/B_160420067_UbayaKuliner/main/promos.json"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<List<Promo>>() { }.type
                val result = Gson().fromJson<ArrayList<Promo>>(it, sType)
                promoLD.value = result
                loadingLD.value = false

                Log.d("showvoley", result.toString())

//                loadingLD.value = false
//                Log.d("showvoley", it)
            },
            {
                Log.d("showvoley", it.toString())
                promoLoadErrorLD.value = true
                loadingLD.value = false
            })
//        tag itu kek idnya
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}