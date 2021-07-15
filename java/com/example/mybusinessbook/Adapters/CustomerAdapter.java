package com.example.mybusinessbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mybusinessbook.Activities.CustomerTransaction;
import com.example.mybusinessbook.Customers_List;
import com.example.mybusinessbook.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerAdapter extends ArrayAdapter<Customers_List> {
    Context context;
    int id;
    Customers_List customer;
    ArrayList<Customers_List> list;


    public CustomerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Customers_List> objects) {
        super(context, resource, objects);
        this.context = context;
        this.id = resource;
        list = objects;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View singleview = convertView;
        customerHolder holder = null;
        if(singleview == null){
//            LayoutInflater inflater = ;
            singleview = LayoutInflater.from(context).inflate(id,parent,false);
            holder = new customerHolder();
            holder.name = singleview.findViewById(R.id.name);
            holder.Address = singleview.findViewById(R.id.Address);
            holder.mobileno = singleview.findViewById(R.id.Number);
            singleview.setTag(holder);
        }else{
            holder = (customerHolder) singleview.getTag();
        }
        customer = list.get(position);
        holder.name.setText(customer.getName());
        holder.Address.setText(customer.getAddress());
        holder.mobileno.setText(customer.getMobileno());
//        singleview.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "You clicked: "+list.get(position).getName(),Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(getContext(), CustomerTransaction.class);
//                i.putExtra("Name",list.get(position).getName());
//                context.startActivity(i);
//            }
//        });

//        singleview.setOnLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(),"POS : "+position, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        singleview.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                Toast.makeText(getContext(),"POS : "+list.get(position).getName(), Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        return singleview;
    }
    class customerHolder{
        TextView name,Address,mobileno;
    }
}
