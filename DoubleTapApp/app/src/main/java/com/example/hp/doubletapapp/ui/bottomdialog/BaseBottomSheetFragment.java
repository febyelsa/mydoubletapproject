package com.example.hp.doubletapapp.ui.bottomdialog;

import android.arch.lifecycle.AndroidViewModel;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract class BaseBottomSheetFragment<V extends AndroidViewModel, D extends ViewDataBinding> extends BottomSheetDialogFragment {

    protected V viewModel;
    protected D binding;

    abstract V getViewModel();

    @LayoutRes
    abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, getLayoutRes(), container, false);
        View view = binding.getRoot();
        return view;

    }
}
