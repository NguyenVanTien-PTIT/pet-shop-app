package com.example.ecommerce_mobile_app.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.adapter.BoxProductAdapter;
import com.example.ecommerce_mobile_app.api.CONSTANT;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.FragmentHomeBinding;
import com.example.ecommerce_mobile_app.model.Product;
import com.example.ecommerce_mobile_app.model.WishlistItem;
import com.example.ecommerce_mobile_app.model.response.BaseResponse;
import com.example.ecommerce_mobile_app.model.response.LoadHomePageResponse;
import com.example.ecommerce_mobile_app.util.PrefManager;
import com.example.ecommerce_mobile_app.view.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;
    RecyclerView rcvNew, rcvPopular;
    BoxProductAdapter adapterNew = new BoxProductAdapter(), adapterPopular = new BoxProductAdapter();
    boolean checkNew, checkPop = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false);

        setListProducts();
        getListFavProduct();

        fragmentHomeBinding.tvNewArrivals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkNew) {
                    fragmentHomeBinding.rvNewArrivalsHome.setVisibility(View.GONE);
                    checkNew = false;
                }
                else{
                    fragmentHomeBinding.rvNewArrivalsHome.setVisibility(View.VISIBLE);
                    checkNew = true;
                }
            }
        });

        fragmentHomeBinding.tvPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPop == true) {
                    fragmentHomeBinding.rvPopularHome.setVisibility(View.GONE);
                    checkPop = false;
                }
                else{
                    fragmentHomeBinding.rvPopularHome.setVisibility(View.VISIBLE);
                    checkPop = true;
                }
            }
        });

        fragmentHomeBinding.tvViewAllNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).changeFragment(R.id.store);
            }
        });

        fragmentHomeBinding.tvViewAllPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).changeFragment(R.id.store);
            }
        });
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setListProducts(){
        rcvNew = fragmentHomeBinding.rvNewArrivalsHome;
        rcvPopular = fragmentHomeBinding.rvPopularHome;

        RetrofitClient.getInstance().loadHomePage().enqueue(new Callback<LoadHomePageResponse>() {
            @Override
            public void onResponse(Call<LoadHomePageResponse> call, Response<LoadHomePageResponse> response) {

                if (response.isSuccessful()){
                    assert response.body() != null;
                    List<Product> bestSellerProds = response.body().getListBestSeller();
                    List<Product> newProds = response.body().getListNewProduct();

                    bestSellerProds.forEach(p -> p.setImage(CONSTANT.PATH_IMAGE_PREFIX + p.getImage()));
                    newProds.forEach(p -> p.setImage(CONSTANT.PATH_IMAGE_PREFIX + p.getImage()));


                    adapterPopular.setmListProducts(bestSellerProds);
                    adapterPopular.setContext(getContext());
                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(),2);
                    rcvPopular.setLayoutManager(gridLayoutManager1);
                    rcvPopular.setAdapter(adapterPopular);

                    adapterNew.setmListProducts(newProds);
                    adapterNew.setContext(getContext());
                    GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(),2);
                    rcvNew.setLayoutManager(gridLayoutManager2);
                    rcvNew.setAdapter(adapterNew);
                }

            }

            @Override
            public void onFailure(Call<LoadHomePageResponse> call, Throwable t) {

            }
        });
    }
    public void getListFavProduct(){
        RetrofitClient.getInstance().getWishlist(new PrefManager(getContext()).getCustomer().getId()).enqueue(new Callback<BaseResponse<List<WishlistItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WishlistItem>>> call, Response<BaseResponse<List<WishlistItem>>> response) {
                if (response.isSuccessful() && response.body().getResponse_message().equals("Success")){
                    adapterNew.setmListFavProducts(response.body().getData());
                    adapterPopular.setmListFavProducts(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<List<WishlistItem>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getListFavProduct();
    }
}