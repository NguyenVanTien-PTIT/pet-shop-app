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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.adapter.QuestionAdapter;
import com.example.ecommerce_mobile_app.api.RetrofitClient;
import com.example.ecommerce_mobile_app.model.request.PostQuestionRequest;
import com.example.ecommerce_mobile_app.model.response.BaseResponse;
import com.example.ecommerce_mobile_app.model.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDialog extends DialogFragment {
    View view;
    private RecyclerView rcv_question;
    private QuestionAdapter adapter = new QuestionAdapter();
    private List<Question> questions;

    private EditText ed_content;
    private Button btn_send;

    private int productId;

    public QuestionDialog(int productId) {
        this.productId = productId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_show_question_product,container,false);

        getQuestion();
        viewBinding();

        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_content.getText().toString().equals("") || ed_content.getText().toString().isEmpty())
                    CustomToast.showFailMessage(getContext(),"Please enter your question!");
                else
                    sendQuestion();
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

        rcv_question.getLayoutParams().height = (int) (size.y * 0.6);
        window.setLayout((int) (size.x * 0.90), (int) (size.y * 0.85));
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    public void viewBinding(){
        ed_content = view.findViewById(R.id.etQuestion);
        btn_send = view.findViewById(R.id.btnSendQuestion);
        rcv_question = view.findViewById(R.id.rcv_question);
        rcv_question.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setQuestions(questions);
        rcv_question.setAdapter(adapter);
    }

    public void disableView(){
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        rcv_question.setVisibility(View.GONE);
    }

    public void getQuestion(){
        RetrofitClient.getInstance().getQuestions(productId).enqueue(new Callback<BaseResponse<List<Question>>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<Question>>> call, @NonNull Response<BaseResponse<List<Question>>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse_message().equals("Success")){
                        questions = response.body().getData();
                        if(questions.size() == 0){
                            disableView();
                        }
                        adapter.setQuestions(questions);
                    }
                    else
                        CustomToast.showFailMessage(getContext(),response.body().getResponse_description());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<Question>>> call, @NonNull Throwable t) {

            }
        });
    }
    public void sendQuestion(){
        PostQuestionRequest postQuestionRequest = new PostQuestionRequest();
        postQuestionRequest.setQuestionContent(ed_content.getText().toString());
        RetrofitClient.getInstance().sendQuestion(new PrefManager(getContext()).getCustomer().getId(),productId,postQuestionRequest).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<String>> call, @NonNull Response<BaseResponse<String>> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().getResponse_message().equals("Success")){
                        CustomToast.showSuccessMessage(getContext(),response.body().getResponse_description());
                        ed_content.setText("");
                    }
                    else {
                        CustomToast.showFailMessage(getContext(),response.body().getResponse_description());
                    }
                }
                else {
                    CustomToast.showFailMessage(getContext(),"Sending question is failure!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<String>> call, @NonNull Throwable t) {

            }
        });
    }
}
