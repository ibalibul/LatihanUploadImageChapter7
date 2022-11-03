package com.iqbal.latihanuploadimagechapter7.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbal.latihanuploadimagechapter7.model.GetCarsRespons
import com.iqbal.latihanuploadimagechapter7.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewModelCar : ViewModel() {

    lateinit var addLiveDatauser : MutableLiveData<GetCarsRespons>

    init {
        addLiveDatauser = MutableLiveData()
    }
    fun postLiveUser() : MutableLiveData<GetCarsRespons>{
        return addLiveDatauser
    }

    fun postApiUser(address: RequestBody,city: RequestBody, email: RequestBody, fullname: RequestBody,  password: RequestBody,   phonenumber: RequestBody, image: MultipartBody.Part){
        RetrofitClient.instance.regiterUser(address,fullname,email,password,city,phonenumber,image)
            .enqueue(object : Callback<GetCarsRespons>{
                override fun onResponse(
                    call: Call<GetCarsRespons>,
                    response: Response<GetCarsRespons>
                ) {
                    if (response.isSuccessful){
                            addLiveDatauser.postValue(response.body())
                    }else{
                            addLiveDatauser.postValue(null)
                    }
                }

                override fun onFailure(call: Call<GetCarsRespons>, t: Throwable) {
                    addLiveDatauser.postValue(null)
                }

            })
    }
}