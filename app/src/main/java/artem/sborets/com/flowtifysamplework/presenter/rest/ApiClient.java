package artem.sborets.com.flowtifysamplework.presenter.rest;

import android.util.Base64;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiClient {

    private static final int CONNECTION_TIMEOUT = 7000;
    private static final String API_BASE_URL = "http://flowtify-dev.westeurope.cloudapp.azure.com/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

//    public static <S> S createService(Class<S> serviceClass) {
//        return createService(serviceClass, null, null);
//    }

    static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder().header("Authorization", basic).header("Accept", "application/json").method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        OkHttpClient client = httpClient.readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS).build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}