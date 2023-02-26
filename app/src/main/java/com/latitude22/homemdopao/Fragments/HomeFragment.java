package com.latitude22.homemdopao.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.latitude22.homemdopao.AddCart;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.MyCart;
import com.latitude22.homemdopao.Pager;
import com.latitude22.homemdopao.R;

import java.util.ArrayList;

/**
 * Created by Anuved on 16-08-2016.
 */
public class HomeFragment extends Fragment implements TabLayout.OnTabSelectedListener{

    static  Context Mcontext ;
    static android.support.v4.app.FragmentManager  fm;
    private ViewPager viewPager;
    public static Fragment getInstance(Context Mcntx,FragmentManager FM){
       Mcontext = Mcntx;
        fm=FM;
        Fragment frag = new HomeFragment();
        return frag;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Destaques"));
        //Remove one tab of fixed plan
       // tabLayout.addTab(tabLayout.newTab().setText("Subscrição"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cart.setImageResource(R.drawable.icon_cart);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.header_text.setText("Inicio");
        MainActivity.icon_filter.setVisibility(View.GONE);
      //  MainActivity.mDrawerToggle.setVisibility(View.VISIBLE);
        MainActivity.main_cart_quantity.setVisibility(View.VISIBLE);
        //Initializing viewPager
        viewPager = (ViewPager) view.findViewById(R.id.pager);


        MainActivity.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), MyCart.class);
                // Intent intent1 = new Intent(MainActivity.this, MyCartActivity.class);
                startActivity(intent1);
                getActivity().finish();
            }
        });

        //Creating our pager adapter
        Pager adapter = new Pager(fm, tabLayout.getTabCount());
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        QuntityTotal();
        return view;
    }
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
    public double QuntityTotal(){
        int  total=0;
        ArrayList<AddCart>  addCarts= (ArrayList<AddCart>) AddCart.AddCart();
        for(int i=0;i<addCarts.size();i++){
            total =total+(addCarts.get(i).getProductquantity());
        }
        MainActivity.main_cart_quantity.setText("" + total);
        return  total;
    }
}

