package blesh;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by SKYLIFE on 24/06/2017.
 */

public interface RetrofitAPI {

    @POST("demoChallenge")
    Call<Object> doTransactionOperation(@Body RequestInformation requestInformation);

}