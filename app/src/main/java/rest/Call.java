package rest;

import android.content.Context;
import android.util.Log;

import custom.ProgressLoading;
import model.ItemResponse;
import retrofit2.Callback;
import retrofit2.Response;


public class Call {

    private ProgressLoading loading;
    private retrofit2.Call<ItemResponse> call;
    private Delegate delegate;


    public Call(Context context, Delegate delegate) {
        this.delegate = delegate;
        this.loading = new ProgressLoading(context);
    }

    public void execute() {
        loading.onShow();
        Service itemCallInterface = Client.getClient().create(Service.class);
        call = itemCallInterface.getRows();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ItemResponse> call, Response<ItemResponse> response) {

                Log.v("Response", response.body().toString());
                Log.v("Title", response.body().getTitle());
                Log.v("Title", response.body().getRows().get(1).getTitle());
                Log.v("Title", "" + response.body().getRows().get(1).getDescription());
                int statusCode = response.code();
                if (statusCode == Client.RESP_OK) {
                    delegate.onSuccess(response.body());
                } else {
                    delegate.onFailure("On Failure - " + statusCode);
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(retrofit2.Call<ItemResponse> call, Throwable t) {
                delegate.onFailure(t.getMessage());
                loading.dismiss();
            }
        });
    }

    public interface Delegate {
        void onSuccess(ItemResponse itemResponse);

        void onFailure(Object t);
    }

}
