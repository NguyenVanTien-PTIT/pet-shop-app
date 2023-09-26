package com.example.ecommerce_mobile_app.util;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.adapter.ReviewAdapter;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.model.response.BaseResponse;
import com.example.ecommerce_mobile_app.model.Review;
import com.example.ecommerce_mobile_app.model.request.SendReviewRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewDialog extends DialogFragment {
    private View view;
    private RecyclerView rcv_review;
    private ReviewAdapter reviewAdapter = new ReviewAdapter();
    private EditText ed_headline, ed_comment;
    private RatingBar ratingBar;
    private ConstraintLayout layoutSendReview;
    private Button btn_send;
    private boolean canReview;
    private int productId;

    public ReviewDialog(int productId) {
        this.productId = productId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_show_review_product,container);
        viewBinding();
        checkCanWriteReview();
        setListReview();

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBar.getNumStars() == 0f || ed_headline.getText().toString().equals("") || ed_headline.getText().toString().isEmpty() || ed_comment.getText().toString().equals("") || ed_comment.getText().toString().isEmpty())
                    CustomToast.showFailMessage(getContext(),"Please  fill full value for each attribute");
                else
                    sendReview();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        rcv_review.getLayoutParams().height = (int) (size.y * 0.5);
        window.setLayout((int) (size.x * 0.90), (int) (size.y * 0.85));
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    public void viewBinding(){
        rcv_review = view.findViewById(R.id.rcv_review);
        ratingBar = view.findViewById(R.id.ratingBar);
        ed_headline = view.findViewById(R.id.etHeadLine);
        ed_comment = view.findViewById(R.id.etReview);
        btn_send = view.findViewById(R.id.btnSendReview);

        layoutSendReview = view.findViewById(R.id.layoutSendReview);

    }
    public void disableView(){
//        ratingBar.setVisibility(View.GONE);
//        ed_headline.setVisibility(View.GONE);
//        ed_comment.setVisibility(View.GONE);
//        btn_send.setVisibility(View.GONE);

        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        rcv_review.getLayoutParams().height = (int) (size.y * 0.75);
        layoutSendReview.setVisibility(View.GONE);
    }
    public void checkCanWriteReview(){
        int customerId = new PrefManager(getContext()).getCustomer().getId();
        RetrofitClient.getInstance().canReview(customerId, productId).enqueue(new Callback<BaseResponse<Boolean>>() {
            @Override
            public void onResponse(Call<BaseResponse<Boolean>> call, Response<BaseResponse<Boolean>> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponse_message().equals("Success")){
                        if (response.body().getData())
                            checkHadReview(productId);
                        else
                            disableView();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Boolean>> call, Throwable t) {

            }
        });
    }
    public void checkHadReview(int customerId){
        RetrofitClient.getInstance().hadReview(customerId,productId).enqueue(new Callback<BaseResponse<Boolean>>() {
            @Override
            public void onResponse(Call<BaseResponse<Boolean>> call, Response<BaseResponse<Boolean>> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponse_message().equals("Success")){
                        if (!response.body().getData())
                            disableView();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Boolean>> call, Throwable t) {

            }
        });
    }
    public void setListReview(){
        RetrofitClient.getInstance().getReviews(productId).enqueue(new Callback<BaseResponse<List<Review>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Review>>> call, Response<BaseResponse<List<Review>>> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponse_message().equals("Success")){
                        reviewAdapter.setReviews(response.body().getData());
                        rcv_review.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv_review.setAdapter(reviewAdapter);
                    }
                    else {
                        CustomToast.showFailMessage(getContext(),response.body().getResponse_description());
                    }
                }
                else {
                    CustomToast.showFailMessage(getContext(),"Looding reviews is failure!");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Review>>> call, Throwable t) {

            }
        });
    }
    public void sendReview(){
        SendReviewRequest sendReviewRequest = new SendReviewRequest();
        sendReviewRequest.setHeadline(ed_headline.getText().toString());
        sendReviewRequest.setComment(ed_comment.getText().toString());
        sendReviewRequest.setRating(ratingBar.getNumStars());
        RetrofitClient.getInstance().writeReview(new PrefManager(getContext()).getCustomer().getId(), productId, sendReviewRequest).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (response.isSuccessful()){
                    if (response.body().getResponse_message().equals("Success")){
                        CustomToast.showSuccessMessage(getContext(),response.body().getResponse_description());
                        ed_headline.setText("");
                        ed_comment.setText("");
                        ratingBar.setRating(0f);
                    }
                    else {
                        CustomToast.showFailMessage(getContext(),response.body().getResponse_description());
                    }
                }
                else {
                    CustomToast.showFailMessage(getContext(),"Sending review is failure!");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

            }
        });
    }
}
