package bristol.ac.uk.breadcrumbsspe.api;

import android.content.Intent;

import com.squareup.picasso.Picasso;

import bristol.ac.uk.breadcrumbsspe.HintActivity;
import bristol.ac.uk.breadcrumbsspe.R;
import bristol.ac.uk.breadcrumbsspe.entity.Hint;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QRCodeHintHandler implements Callback<Hint> {
    private String base_URL = "http://129.213.113.83/api/hints";
    private HintActivity hintActivity;
    private Hint hint;
    private void setURL(String url){
        base_URL = url;
        base_URL += "/";
    }

    public void start(HintActivity hintActivity, String url){
        setURL(url);
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.hintActivity = hintActivity;
        QRCodeHintService qrCodeHintService = retrofit.create(QRCodeHintService.class);
        Call<Hint> hintCall = qrCodeHintService.getHint();
        hintCall.enqueue(this);
    }

    public void setHintActivity(HintActivity hintActivity){
        this.hintActivity = hintActivity;
    }

    @Override
    public void onResponse(Call<Hint> call, Response<Hint> response) {
        if(response.isSuccessful() && response.body() != null){
            hint = response.body();
            hintActivity.hintTextView.setText(hint.getHintText());
            Picasso.get().load(hint.getHintImageUrl()).into(hintActivity.hintImage);
        } else {

            System.out.println(response.errorBody());
            Intent nextQ = new Intent(hintActivity, QRCodeCaptureActivity.class);
            nextQ.putExtra("DIALOG", true);
            hintActivity.startActivity(nextQ);
            hintActivity.overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        }
    }

    @Override
    public void onFailure(Call<Hint> call, Throwable t) {
        t.printStackTrace();
        Intent nextQ = new Intent(hintActivity, QRCodeCaptureActivity.class);
        nextQ.putExtra("DIALOG", true);
        hintActivity.startActivity(nextQ);
        hintActivity.overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }
}
