package com.example.mybusinessbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mybusinessbook.Adapters.TransactionAdapter;
import com.example.mybusinessbook.R;
import com.example.mybusinessbook.Transaction;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ViewTransaction extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Transaction> transactions= new ArrayList<>();
    TransactionAdapter adapter;
    TextView txtExpense,txtIncome;
    Set<String> income;
    Set<String> expense;
    List<String> date = new ArrayList<>();
    List<String> amount = new ArrayList<>();
    List<String> details = new ArrayList<>();
    List<String>  type= new ArrayList<>();
    List<String>  categories= new ArrayList<>();
//    String[] categories={};
//    String[] amount = {};
//    String[] details = {};
//    String[] type = {};
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        txtExpense = findViewById(R.id.expenseRS);
        txtIncome = findViewById(R.id.incomeRS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        String FILE_NAME = "income.txt";
        int temp = 0;
        int temp1 = 0;
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ( (line = reader.readLine()) != null ) {
                String[] temp2 = line.split("@",0);
                System.out.println("temo" + temp2[0]);

                type.add(temp2[0]);
                amount.add(temp2[1]);
                if(type.get(i).equals("income")){
                    temp += Integer.parseInt(amount.get(i));
                }else{
                    temp1 += Integer.parseInt(amount.get(i));
                }

                details.add(temp2[2]);
                categories.add(temp2[3]);
                date.add(temp2[4]);
                i++;
                System.out.println("Read line: " + line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPref =getSharedPreferences("INCOME",MODE_PRIVATE);
        income = sharedPref.getStringSet("Income",null);
        expense = sharedPref.getStringSet("Expense",null);
        List<String> tempincome = new ArrayList<>(income);
        List<String> tempexpense = new ArrayList<>(expense);
        //categories = new String[]{tempincome.get(1),tempexpense.get(0), tempincome.get(3),tempexpense.get(1),tempexpense.get(2)};

//        for(int j = 0 ; j< 5 ;j ++)
//        {
//            if(income.contains(categories[j])){
//                temp += Integer.parseInt(amount[j]);
//            }else{
//                temp1 += Integer.parseInt(amount[j]);
//            }
//        }
        txtIncome.setText(String.valueOf(temp));
        txtExpense.setText(String.valueOf(temp1));
        getTransaction();
        adapter = new TransactionAdapter(ViewTransaction.this,transactions);
        recyclerView.setAdapter(adapter);
    }

    private void getTransaction() {
        for(int j= 0 ; j < i;j++)
        {
            Transaction t = new Transaction(categories.get(j),amount.get(j),date.get(j),details.get(j),type.get(j));
            transactions.add(t);
        }

    }
}