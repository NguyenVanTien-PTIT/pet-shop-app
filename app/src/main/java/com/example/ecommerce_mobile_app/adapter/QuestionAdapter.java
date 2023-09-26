package com.example.ecommerce_mobile_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce_mobile_app.R;
import com.example.ecommerce_mobile_app.databinding.ListItemQuestionBinding;
import com.example.ecommerce_mobile_app.model.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>{
    private List<Question> questions;

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        if (this.questions != null)
            for (int i = 0; i < this.questions.size(); i++)
                if (this.questions.get(i).getAnswer() == null){
                    this.questions.remove(i);
                    i--;
                }
        notifyDataSetChanged();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemQuestionBinding listItemQuestionBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_question,parent,false);
        return new QuestionViewHolder(listItemQuestionBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.listItemQuestionBinding.setQuestion(question);
    }

    @Override
    public int getItemCount() {
        return questions != null ? questions.size() : 0;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{
        ListItemQuestionBinding listItemQuestionBinding;
        public QuestionViewHolder(@NonNull ListItemQuestionBinding listItemQuestionBinding) {
            super(listItemQuestionBinding.getRoot());
            this.listItemQuestionBinding = listItemQuestionBinding;
        }
    }
}
