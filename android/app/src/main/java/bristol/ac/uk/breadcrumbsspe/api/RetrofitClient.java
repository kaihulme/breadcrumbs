package bristol.ac.uk.breadcrumbsspe.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new BasicAuthInterceptor("jackSmith@hotmail.co.uk", "aurora44"))
            .build();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://129.213.113.83/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
}
