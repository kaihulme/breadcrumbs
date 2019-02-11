package bristol.ac.uk.breadcrumbsspe.api;

import bristol.ac.uk.breadcrumbsspe.entity.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    @GET("users")
    Call<User> getUserbyCode(@Query(value = "code",encoded = true) String code);
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://129.213.113.83/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
