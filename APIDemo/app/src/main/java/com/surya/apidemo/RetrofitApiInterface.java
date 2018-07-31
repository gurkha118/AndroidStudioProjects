package com.surya.apidemo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiInterface {

    @GET("contacts")
    Call<Contacts> getAllContactsFromApi();


}
