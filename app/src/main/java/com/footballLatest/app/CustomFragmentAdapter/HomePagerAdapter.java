package com.footballLatest.app.CustomFragmentAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.footballLatest.app.AccountsListFragment;
import com.footballLatest.app.CategoryListFragment;

public class HomePagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 2;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);

    }

    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int arg0) {
        Bundle data = new Bundle();
        switch(arg0){

            /** Android tab is selected */
            case 0:
                CategoryListFragment myCategory = new CategoryListFragment();
                data.putInt("current_page", arg0+1);
                myCategory.setArguments(data);
                return myCategory;
            case 1:
                AccountsListFragment accountsList = new AccountsListFragment();
                data.putInt("current_page", arg0+1);
                accountsList.setArguments(data);
                return accountsList;




        }

        return null;
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}