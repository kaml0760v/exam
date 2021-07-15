package com.example.mybusinessbook.Fragements;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mybusinessbook.Activities.CustomerTransaction;
import com.example.mybusinessbook.Adapters.CustomerAdapter;
import com.example.mybusinessbook.Customers_List;
import com.example.mybusinessbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerList extends Fragment {
    String[] names = {"kamlesh","Dev","Gaurang","Hemangini"};
    String[] mobileno = {"9293622830","9823839623","9293622830","9293622830"};
    String[] Address = {"Surat","Valsad","Vasda","Vyara"};
    ArrayList<Customers_List> customer;
    CustomerAdapter customerAdapter;
    ListView list;
    public CustomerList() {
        // Required empty public constructor
    }
    public static CustomerList newInstance(){return new CustomerList();}


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("choose");
        getActivity().getMenuInflater().inflate(R.menu.pop_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.SMS:
//                Toast.makeText(getActivity(),"CALL",Toast.LENGTH_SHORT).show();
                sendSMS(mobileno[info.position]);
                return true;
            case R.id.CALL:
//                Toast.makeText(getActivity(),"SMS",Toast.LENGTH_SHORT).show();
                callTo(mobileno[info.position]);
                return true;
            case R.id.LandReceive:
                Intent i = new Intent(getContext(), CustomerTransaction.class);
                i.putExtra("Name",names[info.position]);
                i.putExtra("Number",mobileno[info.position]);
                startActivity(i);
                return true;
        }

        return super.onContextItemSelected(item);

    }

    private void callTo(String s) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + s));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void sendSMS(String mobile) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
        intent.putExtra("address",mobile);
        intent.putExtra("sms_body", "Please pay Due amounts");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_customer_list, container, false);
        setHasOptionsMenu(true);
        list = v.findViewById(R.id.listCustomer);
        customer = new ArrayList<>();
        for(int i = 0 ; i < names.length;i++){
            customer.add(new Customers_List(names[i],Address[i],mobileno[i]));
        }
        customerAdapter = new CustomerAdapter(getActivity(),R.layout.customer_list_sample,customer);
        list.setAdapter(customerAdapter);
        list.setLongClickable(true);
        registerForContextMenu(list);

//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),"pos :"+position,Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        return v;
    }
}