package com.example.hp.doubletapapp.utils;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.hp.doubletapapp.ui.bottomdialog.AddWordFragment;

/**
 * Singleton handling fragment functions
 */
public class FragmentUtils {

    private void FragmentUtils(){
        // no need for public constructor
    }

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int resourceId) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(resourceId,fragment);
        ft.commit();

    }

    public static BottomSheetDialogFragment showBottomDialogFragment(FragmentManager fragmentManager, String tag, Bundle args){
        AddWordFragment bottomSheetDialog = AddWordFragment.newInstance(args);
        bottomSheetDialog.show(fragmentManager, tag);
        return bottomSheetDialog;
    }
}
