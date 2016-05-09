package com.example.haynervasquez.stuff;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by erik on 9/05/16.
 */
public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.MyViewHolder> {

    private ArrayList<Professor.Assigment> mData;
    private RecyclerClickListener mRecyclerClickListener;

    public interface RecyclerClickListener{
        void itemClick(Professor.Assigment assigment);
    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener){
        mRecyclerClickListener=recyclerClickListener;
    }

    public StudentViewAdapter(ArrayList<Professor.Assigment> data){
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_assigment_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewName.setText(holder.textViewName.getText()+" : "+mData.get(position).getName());
        holder.textViewStartDate.setText(holder.textViewStartDate.getText()+" : "+mData.get(position).getStartDate());
        holder.textViewFinishDate.setText(holder.textViewFinishDate.getText()+" : "+mData.get(position).getFinishDate());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName, textViewStartDate, textViewFinishDate,textBy;
        public ImageButton buttonJoin;

        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            this.textViewName       = (TextView)itemView.findViewById(R.id.name_student_view);
            this.textViewStartDate  = (TextView)itemView.findViewById(R.id.start_date_student_view);
            this.textViewFinishDate = (TextView)itemView.findViewById(R.id.finish_date_student_view);
            this.buttonJoin         = (ImageButton)itemView.findViewById(R.id.join_btn_student_view);
            this.textBy             = (TextView)itemView.findViewById(R.id.professor_name_student_view);
        }

        @Override
        public void onClick(View v) {
            Log.i("onClickMyViewHolder","onClick in Student");
            if (mRecyclerClickListener != null) {
                Log.i("onClickMyViewHolder",""+getPosition());
                mRecyclerClickListener.itemClick(mData.get(getPosition()));
            }
        }
    }
}
