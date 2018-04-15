package name.peterbukhal.example.news.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class NewsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
            chain.proceed(
                    chain.request()
                            .newBuilder()
                            .addHeader("X-Api-Key", "b59bc1f13f884301a259ebc4a7c68af2")
                            .build())

}