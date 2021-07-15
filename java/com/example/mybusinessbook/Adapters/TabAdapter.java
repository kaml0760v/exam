package com.example.mybusinessbook.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mybusinessbook.Fragements.ExpenseTab;
import com.example.mybusinessbook.Fragements.IncomeTab;

public class TabAdapter extends FragmentStatePagerAdapter {
    int no_of_tabs;

    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        no_of_tabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0){
            fragment = new IncomeTab();
        }else if(position == 1){
            fragment  = new ExpenseTab();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return no_of_tabs;
    }
}
