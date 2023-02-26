package com.latitude22.homemdopao;

/**
 * Created by Anuved on 06-08-2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.latitude22.homemdopao.Fragments.Featured;
import com.latitude22.homemdopao.Fragments.Fixedplan;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        Fragment frag= new Featured();
        switch (position) {
            case 0:
                frag = new Featured();
                break;
            case 1:
                frag = new Fixedplan();
                break;

        }
        return frag;
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}