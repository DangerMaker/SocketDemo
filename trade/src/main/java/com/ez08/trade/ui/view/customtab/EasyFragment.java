package com.ez08.trade.ui.view.customtab;


import androidx.fragment.app.Fragment;

/**
 * Created by Administrator on 2016/11/27.
 * 为了向fragment中传入title
 */
public class EasyFragment {
    Fragment fragment;
    String name;

    public EasyFragment(Fragment fragment, String name) {
        this.fragment = fragment;
        this.name = name;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public String getName() {
        return this.name;
    }

}
