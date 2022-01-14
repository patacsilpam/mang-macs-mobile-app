package com.example.mangmacs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mangmacs.activities.DineInActivity;
import com.example.mangmacs.activities.OrderActivity;
import com.example.mangmacs.activities.OrderModeActivity;
import com.example.mangmacs.activities.PickUpActivity;
import com.example.mangmacs.adapter.OrderListsAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CartModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickUpPayment extends AppCompatActivity {
    private TextView arrowBack,total;
    private RecyclerView recyclerViewOrder;
    private List<CartModel> orderModelLists;
    private OrderListsAdapter orderListsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_payment);
        arrowBack = findViewById(R.id.arrow_back);
        total = findViewById(R.id.total);
        recyclerViewOrder = findViewById(R.id.recyclerviewPayment);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));
        showOrders();
        Back();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("TotalOrderPrice"));
    }
    private void showOrders() {
        String email = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CartModel>> getUserCart = apiInterface.getCart(email);
        getUserCart.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                orderModelLists = response.body();
                orderListsAdapter = new OrderListsAdapter(PickUpPayment.this,orderModelLists);
                recyclerViewOrder.setAdapter(orderListsAdapter);
                //   countCart = recyclerView.getAdapter().getItemCount();
                // cart.setText("("+String.valueOf(countCart)+")");
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {

            }
        });
    }

    private void Back() {
        //arrow back button
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickUpPayment.this, PickUpActivity.class));
            }
        });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String totalPrice = intent.getStringExtra("totalorderprice");
            total.setText(totalPrice);
        }
    };
}