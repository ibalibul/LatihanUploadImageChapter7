package com.iqbal.latihanuploadimagechapter7.network

import com.iqbal.latihanuploadimagechapter7.model.GetCarsRespons
import okhttp3.Address
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIService {

    @POST("auth/register")
    @Multipart
    fun regiterUser(
        @Part("address") address: RequestBody,
        @Part("city") city: RequestBody,
        @Part("email") category: RequestBody,
        @Part("fullname") name: RequestBody,
        @Part("password") price: RequestBody,
        @Part("phonenumber") status: RequestBody,
        @Part fileImage: MultipartBody.Part
    ): Call<GetCarsRespons>
}