package com.example.haynervasquez.stuff;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ing. Erik Ahumada on 22/04/2016.
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {

    private ArrayList<Professor.Assigment> mData;

    public AssignmentAdapter(ArrayList<Professor.Assigment> data){
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assigment_list_row, parent, false);
        return new MyViewHolder(v);
        //return null;

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

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName, textViewStartDate, textViewFinishDate;

        public MyViewHolder(View itemView){
            super(itemView);
            this.textViewName       = (TextView)itemView.findViewById(R.id.assigment_name);
            this.textViewStartDate  = (TextView)itemView.findViewById(R.id.start_date);
            this.textViewFinishDate = (TextView)itemView.findViewById(R.id.finish_date);
        }
    }
}
