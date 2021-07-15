package com.example.mybusinessbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import com.example.mybusinessbook.Activities.AddCategory;
import com.example.mybusinessbook.Adapters.TabAdapter;
import com.example.mybusinessbook.Fragements.IncomeTab;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class Expense extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter tabAdapter;
    //ExtendedFloatingActionButton fab;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        //fab = findViewById(R.id.fab);
        tabAdapter = new TabAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        fab.setOnClickListener(v->{
//            Intent i = new Intent(this, AddCategory.class);
//            startActivityForResult(i,1);
//        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1){
//            String new_cat = data.getStringExtra("CAT_NAME");
//            String choice = data.getStringExtra("CATEGORY");
////            System.out.println(new_cat);
////            System.out.println(choice);
//
//
//        }
    //}
}