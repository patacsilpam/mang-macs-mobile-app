package com.example.mangmacs.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangmacs.R;
import com.example.mangmacs.SharedPreference;
import com.example.mangmacs.adapter.NewOrdersDetailAdapter;
import com.example.mangmacs.api.ApiInterface;
import com.example.mangmacs.api.OrderStatusListener;
import com.example.mangmacs.api.OrdersListener;
import com.example.mangmacs.api.RetrofitInstance;
import com.example.mangmacs.model.CurrentOrdersModel;
import com.example.mangmacs.model.SettingsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrderDetailsActivity extends AppCompatActivity implements OrdersListener{
    private TextView orderNumber,orderType,totalAmount,arrowBack,estTime,changeableStatus;
    private TextView pickUpName,pickUpEmail,deliveryName,deliveryPhoneNum,devAddress,devLabelAddress,paymentMethod,deliveryFee;
    private CardView deliveryDetails,pickUpDetails,paymentMethodDetails,deliveryFeeDetails;
    private String newAccountName,newEmail,newRecipientName,newPhoneNumber,newLabelAddress,newAddress,newOrderType,newOrderStatus,newOrderNumber,newDeliveryTime,newPaymentMethod,newDeliveryFee,newRequiredTme,newRequiredDate,newWaitingTime;
    private RecyclerView newOrderDetailLists;
    private ImageView pendingIcon,receiveIcon,processingIcon,forDeliveryIcon;
    private View line1,line2,line3,line4;
    private List<CurrentOrdersModel> currentOrdersModels;
    private NewOrdersDetailAdapter newOrdersDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order_details);
        arrowBack = findViewById(R.id.arrow_back);
        changeableStatus = findViewById(R.id.changeableStatus);
        estTime = findViewById(R.id.estTime);
        orderNumber = findViewById(R.id.orderNumber);
        orderType = findViewById(R.id.orderType);
        totalAmount = findViewById(R.id.totalAmount);
        pendingIcon = findViewById(R.id.pendingIcon);
        receiveIcon = findViewById(R.id.receivedIcon);
        processingIcon = findViewById(R.id.processingIcon);
        forDeliveryIcon = findViewById(R.id.forDeliveryIcon);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        pickUpDetails = findViewById(R.id.pickUpDetails);
        pickUpName = findViewById(R.id.pickUpName);
        pickUpEmail = findViewById(R.id.pickUpEmail);
        deliveryName = findViewById(R.id.deliveryName);
        deliveryPhoneNum = findViewById(R.id.deliveryPhoneNum);
        devAddress = findViewById(R.id.devAddress);
        devLabelAddress = findViewById(R.id.labelAddress);
        deliveryDetails = findViewById(R.id.deliveryDetails);
        paymentMethodDetails = findViewById(R.id.paymentMethodDetails);
        paymentMethod = findViewById(R.id.paymentMethod);
        deliveryFee = findViewById(R.id.deliveryFee);
        deliveryFeeDetails = findViewById(R.id.deliveryFeeDetails);
        deliveryFeeDetails.setVisibility(View.VISIBLE);
        deliveryDetails.setVisibility(View.VISIBLE);
        pickUpDetails.setVisibility(View.VISIBLE);
        paymentMethodDetails.setVisibility(View.GONE);
        newOrderDetailLists = findViewById(R.id.newOrderDetailLists);
        newOrderDetailLists.setHasFixedSize(true);
        newOrderDetailLists.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        dismissOrder();
        showOrders();
        Back();
    }
    @SuppressLint("ResourceAsColor")
    private void dismissOrder() {
        Intent intent = getIntent();
        newDeliveryTime = intent.getStringExtra("deliveryTime");
        newAccountName = intent.getStringExtra("customerName");
        newEmail = intent.getStringExtra("email");
        newRecipientName = intent.getStringExtra("recipientName");
        newPhoneNumber = intent.getStringExtra("phoneNumber");
        newLabelAddress = intent.getStringExtra("labelAddress");
        newAddress = intent.getStringExtra("address");
        newOrderType = intent.getStringExtra("orderType");
        newOrderStatus = intent.getStringExtra("orderStatus");
        newOrderNumber = intent.getStringExtra("orderNumber");
        newPaymentMethod = intent.getStringExtra("paymentMethod");
        newDeliveryFee = intent.getStringExtra("deliveryFee");
        newRequiredTme = intent.getStringExtra("requiredTime");
        newRequiredDate = intent.getStringExtra("requiredDate");
        newWaitingTime = intent.getStringExtra("waitingTime");
        orderType.setText(newOrderType);
        orderNumber.setText("#".concat(newOrderNumber));
        paymentMethod.setText(newPaymentMethod);
        deliveryFee.setText(newDeliveryFee);
        String OrderType = orderType.getText().toString();
        //show order status
        if (newOrderStatus.equals("Pending")){
            pendingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            line1.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
        }
        if(newOrderStatus.equals("Order Received")){
            pendingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            receiveIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            line1.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
            line2.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
        }
        if(newOrderStatus.equals("Order Processing")){
            pendingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            receiveIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            processingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            line1.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
            line2.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
            line3.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
        }
        if(newOrderStatus.equals("Out for Delivery") || newOrderStatus.equals("Ready for Pick Up")){
            pendingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            receiveIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            processingIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            forDeliveryIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
            line1.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
            line2.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
            line3.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
            line4.setBackgroundColor(ContextCompat.getColor(this, R.color.pressed));
        }
        //show customer details
        if (OrderType.equals("Pick Up")){
            pickUpName.setText(newAccountName);
            pickUpEmail.setText(newEmail);
            deliveryDetails.setVisibility(View.GONE);
            pickUpDetails.setVisibility(View.VISIBLE);
            paymentMethodDetails.setVisibility(View.VISIBLE);
            deliveryFeeDetails.setVisibility(View.GONE);
            changeableStatus.setText("Ready\nfor\nPick up");
        }
        else if (OrderType.equals("Deliver")){
            deliveryName.setText(newRecipientName);
            deliveryPhoneNum.setText(newPhoneNumber);
            devAddress.setText(newAddress);
            devLabelAddress.setText(newLabelAddress);
            deliveryDetails.setVisibility(View.VISIBLE);
            pickUpDetails.setVisibility(View.GONE);
            paymentMethodDetails.setVisibility(View.VISIBLE);
            deliveryFeeDetails.setVisibility(View.VISIBLE);
        }
        else{
            pickUpDetails.setVisibility(View.GONE);
            deliveryDetails.setVisibility(View.GONE);
            deliveryFeeDetails.setVisibility(View.GONE);
        }
        //show estimated delivery time
        if(newRequiredTme.contains("now")){

            estTime.setText(newWaitingTime);
        }
        else{
            estTime.setText(newRequiredDate.concat(" ").concat(newRequiredTme));
        }
    }
    //show customer orders
    private void showOrders(){
        String email = SharedPreference.getSharedPreference(this).setEmail();
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<List<CurrentOrdersModel>> call = apiInterface.getNewOrderDetails(email,newOrderNumber);
        call.enqueue(new Callback<List<CurrentOrdersModel>>() {
            @Override
            public void onResponse(Call<List<CurrentOrdersModel>> call, Response<List<CurrentOrdersModel>> response) {
                currentOrdersModels = response.body();
                newOrdersDetailAdapter = new NewOrdersDetailAdapter(CurrentOrderDetailsActivity.this,currentOrdersModels,CurrentOrderDetailsActivity.this);
                newOrderDetailLists.setAdapter(newOrdersDetailAdapter);
                newOrdersDetailAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<CurrentOrdersModel>> call, Throwable t) {

            }
        });
    }
    private void Back() {
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyOrdersActivity.class));
            }
        });
    }

    @Override
    public void onProductsChange(ArrayList<String> products) {

    }

    @Override
    public void onProductCategoryChange(ArrayList<String> category) {

    }

    @Override
    public void onProductCodeChange(ArrayList<String> productCode) {

    }

    @Override
    public void onVariationChange(ArrayList<String> variations) {

    }

    @Override
    public void onQuantityChange(ArrayList<Integer> quantity) {

    }

    @Override
    public void onAddOnsChange(ArrayList<String> addOns) {

    }

    @Override
    public void onSubTotalChange(ArrayList<String> subTotal) {

    }

    @Override
    public void onPriceChange(ArrayList<String> price) {

    }

    @Override
    public void onImgProductChange(ArrayList<String> imgProduct) {

    }

    @Override
    public void onCustomerIdChange(String customerId) {

    }

    @Override
    public void onTotalAmountChange(String amount) {
        int totalOrder = Integer.parseInt(amount);
        int totalPayment = totalOrder + Integer.parseInt(newDeliveryFee);
        totalAmount.setText("??? ".concat(String.valueOf(totalPayment)).concat(".00"));
    }


}