package rest;


import model.ItemResponse;
import retrofit2.Call;
import retrofit2.http.GET;
//call the service using this interface
public interface  Service {
@GET("/s/2iodh4vg0eortkl/facts.json")
Call<ItemResponse> getRows();
}
