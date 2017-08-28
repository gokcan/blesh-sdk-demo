package blesh;

/**
 * @author Skylifee7 on 24/06/2017.
 */

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestClient {

    private static Retrofit retrofit;

    private static final String DEV_URL = "https://test.bleshsdk.com/bleshweb/";

    static {
        setupRestClient();
    }

    private RestClient() {
    }

    public static Retrofit get() {
        return retrofit;
    }

    private static void setupRestClient() {

        String restApiURL = DEV_URL;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(restApiURL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
