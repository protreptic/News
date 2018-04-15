package name.peterbukhal.example.news.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import name.peterbukhal.example.news.data.remote.model.NewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface News {

    companion object {

        val gson: Gson =
                GsonBuilder()
                        .create()

        val instance: News by lazy {
            Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .client(OkHttpClient.Builder()
                                .addInterceptor(NewsInterceptor())
                                .addInterceptor(HttpLoggingInterceptor().apply {
                                    level = BODY
                                })
                    .build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(News::class.java)
        }

    }

    @GET("everything")
    fun everything(@Query("q") query: String): Observable<NewsResponse>

}