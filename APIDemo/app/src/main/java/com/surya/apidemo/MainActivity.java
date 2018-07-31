package com.surya.apidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerID);
        progressBar=findViewById(R.id.pb);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        RetrofitApiInterface apiInterface = APIClient.getRetrofitApiClient()
                .create(RetrofitApiInterface.class);

        Call<Contacts> call = apiInterface.getAllContactsFromApi();
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    ContactAdaptar contactAdaptar= new ContactAdaptar(response.body().getContactList());
                    recyclerView.setAdapter(contactAdaptar);
                    //Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {

                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
