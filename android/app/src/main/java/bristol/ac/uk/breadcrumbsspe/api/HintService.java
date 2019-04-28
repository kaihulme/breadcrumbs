package bristol.ac.uk.breadcrumbsspe.api;

import bristol.ac.uk.breadcrumbsspe.entity.Hint;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HintService {
    @GET("hints")
    Call<Hint> getHint(@Query("code") String code);
}
