package com.weather.app.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.doAsync
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestService{

    enum class API(val url : String){
        BASE_URL("https://api.openweathermap.org/data/2.5/")
    }


    fun <T>create(service: Class<T>, api : API): T{
        val gsonBuilder =  GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls()
        gsonBuilder.setExclusionStrategies(object : ExclusionStrategy{
            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                return false
            }

            override fun shouldSkipField(f: FieldAttributes?): Boolean {
                return f!!.name.contains("ex_")
            }
        })
        val gson = gsonBuilder.create()
        val retrofit  = Retrofit.Builder()
            .baseUrl(api.url)
                
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(this.client())
            .build()
        return retrofit.create(service)
    }
    fun <T>create(service: Class<T>, api : API,cache: Boolean,context: Context): T{
        val gsonBuilder =  GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls()
        gsonBuilder.setExclusionStrategies(object : ExclusionStrategy{
            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                return false
            }

            override fun shouldSkipField(f: FieldAttributes?): Boolean {
                return f!!.name.contains("ex_")
            }
        })
        val gson = gsonBuilder.create()
        val retrofit  = Retrofit.Builder()
                .baseUrl(api.url)

                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(this.client(cache,context))
                .build()
        return retrofit.create(service)
    }
    private fun client(cache: Boolean,context: Context): OkHttpClient{
        if (cache){
            val cacheSize = (5 * 1024 * 1024).toLong()
            val myCache = Cache(context.cacheDir, cacheSize)
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                    .cache(myCache)
                    .hostnameVerifier { _, _ ->
                        true
                    }
                    .addInterceptor { chain ->

                        var request = chain.request()
                        request = if (hasNetwork(context)!!)
                            request.newBuilder().header("Cache-Control", "public, max-age=" + 10).build()
                        else
                            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                        chain.proceed(request)
                    }
                    //.addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
        }else{
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                    .hostnameVerifier { _, _ ->
                        true
                    }
                    //.addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
        }

    }
    private fun client() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            //.addInterceptor(interceptor)
            .hostnameVerifier { _, _ ->
                true
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}