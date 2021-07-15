package com.example.mybusinessbook.Fragements;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybusinessbook.Activities.AddCategory;
import com.example.mybusinessbook.Activities.UpdateCategory;
import com.example.mybusinessbook.Customers_List;
import com.example.mybusinessbook.R;
import com.example.mybusinessbook.helper.SQLiteHandler;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncomeTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeTab extends Fragment {
    ListView list;
    private List<String> income;
    //Set<String> set = new HashSet<>();
    Map<String,String> cat = new HashMap<>();
    ArrayAdapter<String> adapter;
    ExtendedFloatingActionButton fab;
    SQLiteHandler helper;

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.delete_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.Delete:
                DeleteIncome(income.get(info.position));
                return true;
            case R.id.Update:
                UpdateIncome(income.get(info.position));
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void UpdateIncome(String s) {
        Intent i = new Intent(getActivity(), UpdateCategory.class);
        i.putExtra("CAT",s);
        startActivity(i);
    }

    private void DeleteIncome(String s) {
        helper.deleteCategory(s);
        adapter.notifyDataSetChanged();
    }

    public IncomeTab() {
        // Required empty public constructor
    }
    public static IncomeTab newInstance() {
        return new IncomeTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            String new_cat = data.getStringExtra("CAT_NAME");
            helper.addcategory(new_cat,"Income");
            //SharedPreferences prefs = getActivity().getSharedPreferences("INCOME", MODE_PRIVATE);
            //SharedPreferences.Editor editor = prefs.edit();
            //Set<String> temp = prefs.getStringSet("Income",null);
            //editor.clear();
            //editor.commit();
            //temp.add(new_cat);
//            for(String a:temp)
//                System.out.println("Hello "+a);
            //editor.putStringSet("Income",set);
            //editor.commit();
            income.add(new_cat);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_income_tab, container, false);
        setHasOptionsMenu(true);
        income = new ArrayList<>();
        helper = new SQLiteHandler(getActivity());
        list = v.findViewById(R.id.listincome);
        fab = v.findViewById(R.id.fab);
        //SharedPreferences prefs = getActivity().getSharedPreferences("INCOME", MODE_PRIVATE);
        //SharedPreferences.Editor editor = prefs.edit();
        //editor.clear();
        //set.add("Salary");
        //set.add("Share");
        //set.add("Sale");
        //set.add("Brokrage");
        //editor.putStringSet("Income",set);
        //editor.commit();
        //for(String x:set)
        //    income.add(x);
        //cat = helper.getAllIncomeCategory();
//        if(cat!=null){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                cat.values().forEach(tab->income.add(tab));
//            }
//        }
        income = helper.getAllIncomeCategory();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddCategory.class);
            startActivityForResult(i,1);
            }
        });

        adapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_list_item_1,income);
        list.setAdapter(adapter);
        registerForContextMenu(list);
        return v;
    }

}