package com.example.mangmacs.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.R;
import com.example.mangmacs.adapter.SoupAdapter;
import com.example.mangmacs.model.SoupListModel;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoupActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SoupListModel> soupList;
    private ApiInterface apiInterface;
    private SoupAdapter soupAdapter;
    private TextView btnArrowBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soup);
        btnArrowBack = findViewById(R.id.arrow_back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<SoupListModel>> call= apiInterface.getSoup();
        call.enqueue(new Callback<List<SoupListModel>>() {
            @Override
            public void onResponse(Call<List<SoupListModel>> call, Response<List<SoupListModel>> response) {
                soupList = response.body();
                soupAdapter = new SoupAdapter(SoupActivity.this,soupList);
                recyclerView.setAdapter(soupAdapter);
            }

            @Override
            public void onFailure(Call<List<SoupListModel>> call, Throwable t) {

            }
        });
        //back arrow button
        btnArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SoupActivity.this,home_activity.class));
            }
        });
    }
}