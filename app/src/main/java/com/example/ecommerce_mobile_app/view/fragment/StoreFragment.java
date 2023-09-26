package com.example.ecommerce_mobile_app.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce_mobile_app.adapter.CategoryAdapter;
import com.example.ecommerce_mobile_app.adapter.BoxProductAdapter;
import com.example.ecommerce_mobile_app.api.CONSTANT;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.databinding.FragmentStoreBinding;
import com.example.ecommerce_mobile_app.model.response.BaseResponse;
import com.example.ecommerce_mobile_app.model.Category;
import com.example.ecommerce_mobile_app.model.Product;
import com.example.ecommerce_mobile_app.model.WishlistItem;
import com.example.ecommerce_mobile_app.model.response.LoadProductPageResponse;
import com.example.ecommerce_mobile_app.util.PrefManager;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreFragment extends Fragment {
    FragmentStoreBinding fragmentStoreBinding;
    private List<Category> mListCategories;
    private List<Product> mListProducts;
    private List<WishlistItem> mListFavProducts;
    private CategoryAdapter categoryAdapter;
    private BoxProductAdapter boxProductAdapter = new BoxProductAdapter();
    private RecyclerView rcvCategory, rcvProduct;
    private String keySearch = "";
    private int categoryIdFilter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentStoreBinding = FragmentStoreBinding.inflate(inflater,container,false);
        rcvCategory = fragmentStoreBinding.rvCategoryStore;
        rcvProduct = fragmentStoreBinding.rvListItem;

        load();

        getListFavProduct();
        fragmentStoreBinding.etSearchHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                keySearch = charSequence.toString();
                boxProductAdapter.doFilter(categoryIdFilter,keySearch);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return fragmentStoreBinding.getRoot();
    }

    public void load() {
        RetrofitClient.getInstance().loadProductPage().enqueue(new Callback<LoadProductPageResponse>() {
            @Override
            public void onResponse(Call<LoadProductPageResponse> call, Response<LoadProductPageResponse> response) {
                if (response.isSuccessful()){
                    mListCategories = response.body().getCategoryDTOList();
                    Category category = new Category(0,"All");
                    mListCategories.add(0,category);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                    rcvCategory.setLayoutManager(linearLayoutManager);
                    categoryAdapter = new CategoryAdapter(mListCategories, getContext(), new CategoryAdapter.IOnItemClickListener() {
                        @Override
                        public void onItemClickListener(Category category) {
                            categoryIdFilter = category.getId();
                            boxProductAdapter.doFilter(categoryIdFilter,keySearch);
                        }
                    });
                    rcvCategory.setAdapter(categoryAdapter);


                    response.body()
                            .getProductDTOList()
                            .forEach(product -> product.setImage(CONSTANT.PATH_IMAGE_PREFIX + product.getImage()));

                    mListProducts = response.body().getProductDTOList();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                    boxProductAdapter.setmListProducts(mListProducts);
                    boxProductAdapter.setContext(getContext());
                    rcvProduct.setLayoutManager(gridLayoutManager);
                    rcvProduct.setAdapter(boxProductAdapter);
                }
            }

            @Override
            public void onFailure(Call<LoadProductPageResponse> call, Throwable t) {

            }
        });
    }
    public void getListFavProduct(){
        RetrofitClient.getInstance().getWishlist(new PrefManager(getContext()).getCustomer().getId()).enqueue(new Callback<BaseResponse<List<WishlistItem>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<WishlistItem>>> call, Response<BaseResponse<List<WishlistItem>>> response) {
                if (response.isSuccessful() && response.body().getResponse_message().equals("Success")){
                    mListFavProducts = response.body().getData();
                    boxProductAdapter.setmListFavProducts(mListFavProducts);
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