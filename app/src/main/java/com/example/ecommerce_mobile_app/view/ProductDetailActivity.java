package com.example.ecommerce_mobile_app.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.adapter.DescriptionAdapter;
import com.example.ecommerce_mobile_app.adapter.ImageAdapter;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.ActivityItemDetailsBinding;
import com.example.ecommerce_mobile_app.model.response.BaseResponse;
import com.example.ecommerce_mobile_app.model.CartItem;
import com.example.ecommerce_mobile_app.model.Image;
import com.example.ecommerce_mobile_app.model.Product;
import com.example.ecommerce_mobile_app.model.WishlistItem;
import com.example.ecommerce_mobile_app.util.CustomToast;
import com.example.ecommerce_mobile_app.util.PrefManager;
import com.example.ecommerce_mobile_app.util.QuestionDialog;
import com.example.ecommerce_mobile_app.util.ReviewDialog;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    ActivityItemDetailsBinding activityItemDetailsBinding;
    private Product product;
    private Timer timer;
    private int totalItem;
    boolean hasEditted = false;

    private PrefManager prefManager = new PrefManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityItemDetailsBinding = ActivityItemDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityItemDetailsBinding.getRoot());

        Bundle bundle = getIntent().getExtras();
        product = (Product) bundle.getSerializable("product");
        activityItemDetailsBinding.setProduct(product);

        setImageSlide();
        setDescription();
        autoSlideImages();

        activityItemDetailsBinding.btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasEditted = !hasEditted;
                if (product.getIsFav()){
                    removeWishlistItem();
                }
                else
                    addWishlistItem();
            }
        });
        activityItemDetailsBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetailActivity.super.onBackPressed();
            }
        });

        activityItemDetailsBinding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this,MainActivity.class);
                bundle.clear();
                bundle.putSerializable("change_to","cart");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        activityItemDetailsBinding.btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCartItem(product.getId());
            }
        });

        activityItemDetailsBinding.tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionDialog questionDialog = new QuestionDialog(product.getId());
                questionDialog.show(getSupportFragmentManager(),"Questions");
            }
        });

        activityItemDetailsBinding.tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewDialog reviewDialog = new ReviewDialog(product.getId());
                reviewDialog.show(getSupportFragmentManager(),"Reviews");
            }
        });
    }

    private void autoSlideImages(){
        if(timer == null){
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = activityItemDetailsBinding.viewPagerItemDetails.getCurrentItem();
                        int total = totalItem;
                        if(currentItem < total){
                            currentItem++;
                            activityItemDetailsBinding.viewPagerItemDetails.setCurrentItem(currentItem);
                        }else {
                            activityItemDetailsBinding.viewPagerItemDetails.setCurrentItem(0);
                        }
                    }
                });
            }
        },3000,3000);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void addCartItem(int productId){
        RetrofitClient.getInstance().addToCart(prefManager.getCustomer().getId(), productId).enqueue(new Callback<BaseResponse<List<CartItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<CartItem>>> call, Response<BaseResponse<List<CartItem>>> response) {
                if (response.isSuccessful()){
                        CustomToast.showSuccessMessage(getApplicationContext(), "Add product to cart is successful!");
                }
                else {
                    CustomToast.showFailMessage(getApplicationContext(),"Add product to cart is unsuccessful!");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<CartItem>>> call, Throwable t) {

            }
        });
    }
    public void setImageSlide(){
        List<Image> images = new ArrayList<>();
        images.add(new Image(product.getImage()));
        images.add(new Image(product.getImage()));
        images.add(new Image(product.getImage()));

        totalItem = images.size() - 1; // lấy toltal để làm auto slide
        ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), images);
        activityItemDetailsBinding.viewPagerItemDetails.setAdapter(imageAdapter);
        activityItemDetailsBinding.circleIndicator.setViewPager(activityItemDetailsBinding.viewPagerItemDetails);
        imageAdapter.registerDataSetObserver(activityItemDetailsBinding.circleIndicator.getDataSetObserver());
    }
    public void setDescription(){
        DescriptionAdapter descriptionAdapter = new DescriptionAdapter();
        descriptionAdapter.setList(product.getDetails());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        activityItemDetailsBinding.rvDes.setLayoutManager(linearLayoutManager);
        activityItemDetailsBinding.rvDes.setAdapter(descriptionAdapter);
    }
    @BindingAdapter("setFav")
    public static void setFav(ImageView imageView, Boolean isFav){
        if (isFav.equals(true))
            Glide.with(imageView.getContext()).load(imageView.getContext().getDrawable(R.drawable.icon_love)).into(imageView);
        else
            Glide.with(imageView.getContext()).load(imageView.getContext().getDrawable(R.drawable.icon_love2)).into(imageView);
    }

    public void addWishlistItem(){
        RetrofitClient.getInstance().addWishlistItem(prefManager.getCustomer().getId(),product.getId()).enqueue(new Callback<BaseResponse<List<WishlistItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WishlistItem>>> call, Response<BaseResponse<List<WishlistItem>>> response) {
                if (response.isSuccessful())
                    if (response.body().getResponse_message().equals("Success")){
                        CustomToast.showSuccessMessage(getApplicationContext(),response.body().getResponse_description());
                        product.setIsFav(true);
                    }
                    else
                        CustomToast.showFailMessage(getApplicationContext(),response.body().getResponse_description());
                else
                    CustomToast.showFailMessage(getApplicationContext(),"Add to whish list is failure");
            }

            @Override
            public void onFailure(Call<BaseResponse<List<WishlistItem>>> call, Throwable t) {

            }
        });
    }
    public void removeWishlistItem(){
        RetrofitClient.getInstance().removeWishlistItem(prefManager.getCustomer().getId(),product.getId()).enqueue(new Callback<BaseResponse<List<WishlistItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WishlistItem>>> call, Response<BaseResponse<List<WishlistItem>>> response) {
                if (response.isSuccessful())
                    if (response.body().getResponse_message().equals("Success")){
                        CustomToast.showSuccessMessage(getApplicationContext(),response.body().getResponse_description());
                        product.setIsFav(false);
                    }
                    else
                        CustomToast.showFailMessage(getApplicationContext(),response.body().getResponse_description());
                else
                    CustomToast.showFailMessage(getApplicationContext(),"Remove from whish list is failure");
            }

            @Override
            public void onFailure(Call<BaseResponse<List<WishlistItem>>> call, Throwable t) {

            }
        });
    }

}