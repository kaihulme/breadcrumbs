package bristol.ac.uk.breadcrumbsspe.api;

import bristol.ac.uk.breadcrumbsspe.entity.Hint;
import retrofit2.Call;
import retrofit2.http.GET;

public interface QRCodeHintService {
    @GET(".")
    Call<Hint> getHint();
}
