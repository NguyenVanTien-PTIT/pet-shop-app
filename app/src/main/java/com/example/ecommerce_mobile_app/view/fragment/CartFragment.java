package com.example.ecommerce_mobile_app.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.adapter.CartItemAdapter;
import com.example.ecommerce_mobile_app.api.CONSTANT;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.FragmentCartBinding;
import com.example.ecommerce_mobile_app.model.CartItem;
import com.example.ecommerce_mobile_app.model.InfoCart;
import com.example.ecommerce_mobile_app.model.response.LoadOrderResponse;
import com.example.ecommerce_mobile_app.model.response.OrdersDTO;
import com.example.ecommerce_mobile_app.util.CustomDialog;
import com.example.ecommerce_mobile_app.util.CustomToast;
import com.example.ecommerce_mobile_app.util.PrefManager;
import com.example.ecommerce_mobile_app.view.MainActivity;
import com.example.ecommerce_mobile_app.view.PlaceOrderActivity;
import com.example.ecommerce_mobile_app.view.ProductDetailActivity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {

    FragmentCartBinding fragmentCartBinding;

    List<CartItem> mListCartItems;
    OrdersDTO order;
    RecyclerView recyclerView;
    CartItemAdapter cartItemAdapter = new CartItemAdapter();

    private final InfoCart infoCart = new InfoCart();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
        recyclerView = fragmentCartBinding.rvListCart;
        setCart();

        fragmentCartBinding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItemAdapter.getmListCartItems().size() == 0) {
                    CustomDialog customDialog = new CustomDialog();
                    customDialog.setTitle("EMPTY CART");
                    customDialog.setDes("Your cart is empty. Please add something!");
                    customDialog.setTextPositive("Go store");
                    customDialog.setPositiveButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                            ((MainActivity) getActivity()).changeFragment(R.id.store);
                        }
                    });
                    customDialog.setNegativeButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });
                    customDialog.show(getActivity().getSupportFragmentManager(), "Empty cart");
                } else {
                    Intent intent = new Intent(getContext(), PlaceOrderActivity.class);
                    intent.putExtra("listCartItems", (Serializable) cartItemAdapter.getmListCartItems());
                    intent.putExtra("infoCart", infoCart);
                    intent.putExtra("order", order);
                    startActivity(intent);
                }
            }
        });

        fragmentCartBinding.tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog();
                customDialog.setTitle("CLEAR CART");
                customDialog.setDes("Do you want to clear your cart?");
                customDialog.setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                        clearCart();
                    }
                });
                customDialog.setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });
                customDialog.show(getActivity().getSupportFragmentManager(), "Clear cart");
            }
        });
        return fragmentCartBinding.getRoot();
    }

    public void setCart() {
        RetrofitClient.getInstance().loadOrderByCustomer(new PrefManager(getContext()).getToken()).enqueue(new Callback<LoadOrderResponse>() {
            @Override
            public void onResponse(Call<LoadOrderResponse> call, Response<LoadOrderResponse> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    order = response.body().getOrdersDTO();

                    response.body().getOrderItems().forEach(item -> {
                        item.setImage(CONSTANT.PATH_IMAGE_PREFIX + item.getImage());
                        item.getProduct().setImage(CONSTANT.PATH_IMAGE_PREFIX + item.getProduct().getImage());
                    });
                    mListCartItems = response.body().getOrderItems();
                    cartItemAdapter.setiClickOnCartItem(new CartItemAdapter.IClickOnCartItem() {
                        @Override
                        public void clickMinus(CartItem cartItem) {
                            if (cartItem.getQuantity() == 1) {
                                clickDelete(cartItemAdapter.getmListCartItems(), cartItem);
                            } else {
                                updateCartItem(cartItem, "minus");
                            }
                        }

                        @Override
                        public void clickPlus(CartItem cartItem) {
                            updateCartItem(cartItem, "plus");
                        }

                        @Override
                        public void clickDelete(List<CartItem> mListCartItems, CartItem cartItem) {
                            CustomDialog customDialog = new CustomDialog();
                            customDialog.setPositiveButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    deleteCartItem(mListCartItems, cartItem);
                                }
                            });
                            customDialog.setNegativeButton(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });
                            customDialog.show(getActivity().getSupportFragmentManager(), "Delete cart item");
                        }

                        @Override
                        public void clickProduct(CartItem cartItem) {
                            // get product

                            Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("product", cartItem.getProduct());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    cartItemAdapter.setmListCartItems(mListCartItems);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(cartItemAdapter);
                    infoCart.setTotalItem(mListCartItems.size());
                    infoCart.setTotalPrice(cartItemAdapter.calTotal());
                    fragmentCartBinding.setInfoCart(infoCart);
                }
            }

            @Override
            public void onFailure(Call<LoadOrderResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updateCartItem(CartItem cartItem, String action) {
        RetrofitClient.getInstance().updateCartItem(cartItem.getOrderProductId(), action, new PrefManager(getContext()).getToken()).enqueue(new Callback<LoadOrderResponse>() {
            @Override
            public void onResponse(Call<LoadOrderResponse> call, Response<LoadOrderResponse> response) {
                if (response.isSuccessful()) {
                    if (action.equals("plus")) {
                        infoCart.setTotalPrice(infoCart.getTotalPrice() + cartItem.getSubtotal() / cartItem.getQuantity());
                        cartItem.setSubtotal(cartItem.getSubtotal() + cartItem.getSubtotal() / cartItem.getQuantity());
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                    }
                    if (action.equals("minus")) {
                        infoCart.setTotalPrice(infoCart.getTotalPrice() - cartItem.getSubtotal() / cartItem.getQuantity());
                        cartItem.setSubtotal(cartItem.getSubtotal() - cartItem.getSubtotal() / cartItem.getQuantity());
                        cartItem.setQuantity(cartItem.getQuantity() - 1);
                    }
                } else {
                    CustomToast.showFailMessage(getContext(), "Save cart is unsucessful!");
                }
            }

            @Override
            public void onFailure(Call<LoadOrderResponse> call, Throwable t) {

            }
        });
    }

    public void deleteCartItem(List<CartItem> mListCartItems, CartItem cartItem) {
        RetrofitClient.getInstance().removeCartItem(cartItem.getOrderProductId(), new PrefManager(getContext()).getToken()).enqueue(new Callback<LoadOrderResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<LoadOrderResponse> call, Response<LoadOrderResponse> response) {
                if (response.isSuccessful()) {
                    infoCart.setTotalPrice(infoCart.getTotalPrice() - cartItem.getSubtotal());
                    infoCart.setTotalItem(infoCart.getTotalItem() - 1);
                    mListCartItems.remove(cartItem);
                    cartItemAdapter.notifyDataSetChanged();
                    CustomToast.showSuccessMessage(getContext(), "Delete product is successful");
                } else {
                    CustomToast.showFailMessage(getContext(), "Delete product is unsuccessful!");
                }
            }

            @Override
            public void onFailure(Call<LoadOrderResponse> call, Throwable t) {

            }
        });
    }

    @BindingAdapter("setTotalPrice")
    public static void setTotalPrice(TextView textView, float price) {
        String format = String.format("%.2f", price) + " $";
        textView.setText(format);
    }

    @BindingAdapter("setTotalItem")
    public static void setTotalItem(TextView textView, int item) {
        String format = String.format("Total: (%d items)", item);
        textView.setText(format);
    }

    public void clearCart() {
        if (Objects.isNull(order) || Objects.isNull(order.getId())) {
            CustomToast.showSuccessMessage(getContext(), "Clear cart is successful!");
            return;
        }

        RetrofitClient.getInstance().clearCartItems(order.getId(), new PrefManager(getContext()).getToken()).enqueue(new Callback<LoadOrderResponse>() {
            @Override
            public void onResponse(Call<LoadOrderResponse> call, Response<LoadOrderResponse> response) {
                if (response.isSuccessful()) {
                    resetCart();
                } else
                    CustomToast.showFailMessage(getContext(), "Clear cart is failure!");
            }

            @Override
            public void onFailure(Call<LoadOrderResponse> call, Throwable t) {

            }
        });
    }

    private void resetCart() {
        CustomToast.showSuccessMessage(getContext(), "Clear cart is successful!");
        mListCartItems.clear();
        cartItemAdapter.setmListCartItems(mListCartItems);
        infoCart.setTotalItem(0);
        infoCart.setTotalPrice(0.0f);
    }
}