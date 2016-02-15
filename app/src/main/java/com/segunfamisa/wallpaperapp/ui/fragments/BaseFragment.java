package com.segunfamisa.wallpaperapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.segunfamisa.wallpaperapp.App;
import com.segunfamisa.wallpaperapp.utils.AppUtils;

/**
 * Base Fragment to be used by all other fragments
 *
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inject

    }

    public App getApplication() {
        if(getActivity() != null) {
            return (App)getActivity().getApplication();
        } else {
            return null;
        }
    }

    public void closeFragment(){
        if(getActivity() != null){
            if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0){
                hideKeyboard();
                getActivity().getSupportFragmentManager().popBackStack();
            }
            else{
                hideKeyboard();
                getActivity().finish();
            }
        }
    }

    protected void hideKeyboard() {
        AppUtils.hideKeyboard(getActivity());
    }
}
