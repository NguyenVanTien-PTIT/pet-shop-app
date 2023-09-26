package com.example.ecommerce_mobile_app.view;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.databinding.ActivityMainBinding;
import com.example.ecommerce_mobile_app.model.Customer;
import com.example.ecommerce_mobile_app.model.Product;
import com.example.ecommerce_mobile_app.util.PrefManager;
import com.example.ecommerce_mobile_app.view.fragment.CartFragment;
import com.example.ecommerce_mobile_app.view.fragment.HomeFragment;
import com.example.ecommerce_mobile_app.view.fragment.ProfileFragment;
import com.example.ecommerce_mobile_app.view.fragment.StoreFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    Fragment fragment=null;
    ChipNavigationBar chipNavigationBar;
    String change_to = "";

    List<Product> listProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        Customer customer = new PrefManager(this).getCustomer();
        if (customer != null){
            activityMainBinding.tvNameUser.setText(customer.getFullname());
            setImage(activityMainBinding.imgAvatar,customer.getPhotoImagePath());
        }

        chipNavigationBar = activityMainBinding.NavigationBar;
        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            change_to = Objects.requireNonNull(bundle.getSerializable("change_to")).toString();
            switch (change_to) {
                case "cart":
                    changeFragment(R.id.cart);
                    break;
                case "profile":
                    changeFragment(R.id.profile);
                    break;
                case "store":
                    changeFragment(R.id.store);
                    break;
            }
        }
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                changeFragment(i);
            }
        });
    }
    public void changeFragment(int i){
        switch (i)
        {
            case R.id.home:
                fragment = new HomeFragment();
                break;
            case R.id.store:
                fragment = new StoreFragment();
                break;
            case R.id.cart:
                fragment = new CartFragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            chipNavigationBar.setItemSelected(i,true);
        }
    }
    @BindingAdapter("setImage")
    public static void setImage(ImageView shapeableImageView, String imagePath){
        if (StringUtils.isBlank(imagePath)) {
            shapeableImageView.getContext().getDrawable(R.drawable.avatarhome1);
        } else {
            Glide.with(shapeableImageView.getContext()).load(imagePath).error(shapeableImageView.getContext().getDrawable(R.drawable.avatarhome1)).into(shapeableImageView);
        }
    }
}