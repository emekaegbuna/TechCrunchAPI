package com.example.techcrunchapi.data.remote


import com.example.techcrunchapi.data.model.authorModel.AuthorModel
import com.example.techcrunchapi.utils.Constant
import com.example.techcrunchapi.data.model.postModel.TechCrunchModel
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


interface WebServices {
    companion object {
        val instance: WebServices by lazy {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(WebServices::class.java)
        }
    }

    @GET(Constant.postUrl)
    fun fetchTechCrunchModel(): Single<List<TechCrunchModel>>

    @GET(Constant.userUrl)
    fun fetchAuthors(@Path("userID") userID: Int): Single<AuthorModel>

}