package com.example.ecommerce_mobile_app.util;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.view.MainActivity;
import com.example.ecommerce_mobile_app.view.ProductDetailActivity;

public class OrderSuccessDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_successful_order,container,false);
        view.findViewById(R.id.btnContinueShop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return view;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        goStore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        goStore();
    }

    public void goStore(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("change_to","store");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
