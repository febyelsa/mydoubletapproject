package com.example.hp.doubletapapp.ui;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.doubletapapp.events.BackgroundEvents;
import com.example.hp.doubletapapp.events.EventsMessages;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

abstract class BaseFragment<V extends AndroidViewModel, D extends ViewDataBinding> extends Fragment {

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
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEvent(BackgroundEvents events) {
        if (!events.getEvent().isEmpty() && EventsMessages.mEventInitiateListUpdate.equalsIgnoreCase(events.getEvent())) {
            performBackgroundTasks(events.getEvent());
        }
    }
    protected void performBackgroundTasks(String backgroundTask){
        // no implementation in base class
    }
}
