package com.example.mybusinessbook.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybusinessbook.R;
import com.example.mybusinessbook.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyHolder>{
    Context context;
    List<Transaction> list;
    Transaction transaction;
    //List<String> tranList = new ArrayList<>();

    public TransactionAdapter(Context context, List<Transaction> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_transaction_sample,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        transaction = list.get(position);
        holder.txtCategory.setText(transaction.getCategoty());
        holder.txtAmount.setText(transaction.getAmount());
        holder.txtDate.setText(transaction.getDate());
        holder.txtDetails.setText(transaction.getDetials());
        if(transaction.getType().equals("income")){
            holder.txtAmount.setTextColor(Color.parseColor("#0E8A00"));
        }else{
            holder.txtAmount.setTextColor(Color.parseColor("#F44336"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txtCategory,txtDate,txtAmount,txtDetails;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtAmount = itemView.findViewById(R.id.Amount);
            txtCategory = itemView.findViewById(R.id.Category);

            txtDate = itemView.findViewById(R.id.Date);
            txtDetails = itemView.findViewById(R.id.Details);
        }
    }
}
