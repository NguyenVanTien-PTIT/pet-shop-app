package com.example.ecommerce_mobile_app.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ecommerce_mobile_app.R;

public class CustomDialog extends DialogFragment {
    private View.OnClickListener btnYesListener = null, btnCancelListener = null;
    private TextView tvTitle, tvDes;
    private Button btnYes, btnCancel;
    private String title = "DELETE PRODUCT", des = "Are you sure that you want to delete this product?", textPos = "Yes", textNega = "Cancel";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_delete_item_cart,container,false);
        tvTitle = view.findViewById(R.id.tv_tittle);
        tvDes = view.findViewById(R.id.tv_desc);
        btnYes = view.findViewById(R.id.btnYes);
        btnCancel = view.findViewById(R.id.btnCancel);
        tvTitle.setText(title);
        tvDes.setText(des);
        btnYes.setText(textPos);
        btnYes.setOnClickListener(btnYesListener);
        btnCancel.setText(textNega);
        btnCancel.setOnClickListener(btnCancelListener);

        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return view;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDes(String des){
        this.des = des;
    }
    public void setTextPositive(String text){
        this.textPos = text;
    }
    public void setTextNegative(String text){
        this.textNega = text;
    }
    public void setPositiveButton(View.OnClickListener onClickListener){
        this.btnYesListener = onClickListener;
    }
    public void setNegativeButton(View.OnClickListener onClickListener){
        this.btnCancelListener = onClickListener;
    }

}
