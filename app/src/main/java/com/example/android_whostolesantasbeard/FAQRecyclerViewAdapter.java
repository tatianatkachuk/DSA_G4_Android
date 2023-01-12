package com.example.android_whostolesantasbeard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.Question;

import java.util.ArrayList;
import java.util.List;

public class FAQRecyclerViewAdapter extends RecyclerView.Adapter<FAQRecyclerViewAdapter.ViewHolder> {

    private List<Question> questionsList;
   
   
   
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView id;
        public TextView answer;
        public TextView question;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            id = (TextView) v.findViewById(R.id.qid);
            question = (TextView) v.findViewById(R.id.qid2);
            answer = (TextView) v.findViewById(R.id.qid3);
        }
    }

    public void setData(List<Question> myDataset) {
        questionsList = myDataset;
        notifyDataSetChanged();
    }
    public FAQRecyclerViewAdapter() {
        questionsList = new ArrayList<>();
    }
    public FAQRecyclerViewAdapter(List<Question> myDataset) {
        questionsList = myDataset;
    }


    public void remove(int position) {
        questionsList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public FAQRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.recyclerview_faq_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Question q = questionsList.get(position);
        holder.id.setText("Number" +q.getId());
        holder.question.setText("Question: " + q.getQuestion());
        holder.answer.setText("Answer:" +q.getAnswer());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return questionsList.size();
    }

}
