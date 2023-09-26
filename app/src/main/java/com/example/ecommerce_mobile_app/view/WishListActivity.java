package com.example.ecommerce_mobile_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.ecommerce_mobile_app.adapter.WishListAdapter;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.ActivityWishlistBinding;
import com.example.ecommerce_mobile_app.model.response.BaseResponse;
import com.example.ecommerce_mobile_app.model.CartItem;
import com.example.ecommerce_mobile_app.model.WishlistItem;
import com.example.ecommerce_mobile_app.util.CustomDialog;
import com.example.ecommerce_mobile_app.util.CustomToast;
import com.example.ecommerce_mobile_app.util.PrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListActivity extends AppCompatActivity {
    private ActivityWishlistBinding activityWishlistBinding;
    private WishListAdapter wishListAdapter = new WishListAdapter();
    private List<WishlistItem> wishlistItems;
    private PrefManager prefManager = new PrefManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWishlistBinding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(activityWishlistBinding.getRoot());
        getWishList();
        activityWishlistBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WishListActivity.super.onBackPressed();
            }
        });
        activityWishlistBinding.tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog();
                customDialog.setTitle("CLEAR FAVOURITE LIST");
                customDialog.setDes("Do you want to clear favourite list?");
                customDialog.setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                        clearFavList();
                    }
                });
                customDialog.setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });
                customDialog.show(getSupportFragmentManager(),"Clear fav list");
            }
        });
    }
    public void getWishList(){
        RetrofitClient.getInstance().getWishlist(prefManager.getCustomer().getId()).enqueue(new Callback<BaseResponse<List<WishlistItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WishlistItem>>> call, Response<BaseResponse<List<WishlistItem>>> response) {
               if (response.isSuccessful()){
                   if (response.body().getResponse_message().equals("Success")){
                       wishlistItems = response.body().getData();
                       wishListAdapter.setWishlistItems(wishlistItems);
                       wishListAdapter.setiOnClickWishItem(new WishListAdapter.IOnClickWishItem() {
                           @Override
                           public void addToCart(WishlistItem wishlistItem) {
                               addCartItem(wishlistItem);
                           }

                           @Override
                           public void removeWishItem(WishlistItem wishlistItem) {
                               CustomDialog dialog = new CustomDialog();
                               dialog.setPositiveButton(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       removeWishlistItem(wishlistItem);
                                       dialog.dismiss();
                                   }
                               });
                               dialog.setNegativeButton(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       dialog.dismiss();
                                   }
                               });
                               dialog.show(getSupportFragmentManager(),"Delete product");
                           }
                       });
                       activityWishlistBinding.rvWishlist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                       activityWishlistBinding.rvWishlist.setAdapter(wishListAdapter);
                   }
                   else {
                       CustomToast.showFailMessage(getApplicationContext(),response.body().getResponse_description());
                   }
               }
               else
                   CustomToast.showFailMessage(getApplicationContext(),"Loading favourite products is failure!");
            }

            @Override
            public void onFailure(Call<BaseResponse<List<WishlistItem>>> call, Throwable t) {

            }
        });
    }
    public void addCartItem(WishlistItem wishlistItem){
        RetrofitClient.getInstance().addCartItem(new PrefManager(this).getCustomer().getId(), wishlistItem.getProduct().getId(),1).enqueue(new Callback<BaseResponse<List<CartItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<CartItem>>> call, Response<BaseResponse<List<CartItem>>> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponse_message().equals("Success"))
                        CustomToast.showSuccessMessage(getApplicationContext(),response.body().getResponse_description());
                    else
                        CustomToast.showFailMessage(getApplicationContext(),response.body().getResponse_description());
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
    public void removeWishlistItem(WishlistItem wishlistItem){
        RetrofitClient.getInstance().removeWishlistItem(prefManager.getCustomer().getId(),wishlistItem.getProduct().getId()).enqueue(new Callback<BaseResponse<List<WishlistItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WishlistItem>>> call, Response<BaseResponse<List<WishlistItem>>> response) {
                if (response.isSuccessful())
                    if (response.body().getResponse_message().equals("Success")){
                        CustomToast.showSuccessMessage(getApplicationContext(),response.body().getResponse_description());
                        wishlistItems.remove(wishlistItem);
                        wishListAdapter.setWishlistItems(wishlistItems);
                        wishListAdapter.notifyDataSetChanged();
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
    public void clearFavList(){
        RetrofitClient.getInstance().deleteWishlist(prefManager.getCustomer().getId()).enqueue(new Callback<BaseResponse<List<WishlistItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WishlistItem>>> call, Response<BaseResponse<List<WishlistItem>>> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponse_message().equals("Success")){
                        CustomToast.showSuccessMessage(getApplicationContext(),response.body().getResponse_description());
                        wishlistItems.clear();
                        wishListAdapter.setWishlistItems(wishlistItems);
                    }
                    else
                        CustomToast.showFailMessage(getApplicationContext(),response.body().getResponse_description());
                }
                else
                    CustomToast.showFailMessage(getApplicationContext(),"Clear favourite list is failure!");
            }

            @Override
            public void onFailure(Call<BaseResponse<List<WishlistItem>>> call, Throwable t) {

            }
        });
    }
}