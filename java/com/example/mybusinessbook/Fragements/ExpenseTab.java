package com.example.mybusinessbook.Fragements;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.mybusinessbook.Activities.AddCategory;
import com.example.mybusinessbook.Activities.UpdateCategory;
import com.example.mybusinessbook.R;
import com.example.mybusinessbook.helper.SQLiteHandler;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenseTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenseTab extends Fragment {
    ListView list;
    List<String> expense;
    ArrayAdapter<String> adapter;
    Set<String> set = new HashSet<>();
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
                DeleteExpense(expense.get(info.position));
                return true;
            case R.id.Update:
                UpdateExpense(expense.get(info.position));
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void UpdateExpense(String s) {
        Intent i = new Intent(getActivity(), UpdateCategory.class);
        i.putExtra("CAT",s);
        startActivity(i);
    }

    private void DeleteExpense(String s) {
        helper.deleteCategory(s);
        adapter.notifyDataSetChanged();
    }

    public ExpenseTab() {
        // Required empty public constructor
    }

    public static ExpenseTab newInstance(){
        return new ExpenseTab();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            String new_cat = data.getStringExtra("CAT_NAME");
            helper.addcategory(new_cat,"Expense");

            expense.add(new_cat);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_expense_tab, container, false);
        setHasOptionsMenu(true);
        list = v.findViewById(R.id.listExpense);
        expense = new ArrayList<String>();
        helper = new SQLiteHandler(getActivity());
//        SharedPreferences prefs = getActivity().getSharedPreferences("INCOME", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
        fab = v.findViewById(R.id.fab);
//        set.add("Home Rent");
//        set.add("Office Rent");
//        set.add("Purchase");
//        editor.putStringSet("Expense",set);
//        editor.commit();
//        for(String x:set)
//            expense.add(x);
        expense = helper.getAllExpenseCategory();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddCategory.class);
                startActivityForResult(i,1);
            }
        });
        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,expense);
        list.setAdapter(adapter);
        registerForContextMenu(list);
        return v;
    }

}