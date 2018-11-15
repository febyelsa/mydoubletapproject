package com.example.hp.doubletapapp.ui.bottomdialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.doubletapapp.R;
import com.example.hp.doubletapapp.database.WordEntity;
import com.example.hp.doubletapapp.databinding.FragmentAddNewWordBinding;
import com.example.hp.doubletapapp.utils.Constants;
import com.example.hp.doubletapapp.viewmodel.AddWordViewModel;

public class AddWordFragment extends BaseBottomSheetFragment<AddWordViewModel,FragmentAddNewWordBinding> {

    public static AddWordFragment newInstance(Bundle args) {
        AddWordFragment addWordFragment = new AddWordFragment();
        addWordFragment.setArguments(args);
        return addWordFragment;
    }

    @Override
    AddWordViewModel getViewModel() {
        return new AddWordViewModel(getActivity().getApplication());
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_add_new_word;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);
        binding.setAddWordViewModel(getViewModel());
        binding.getAddWordViewModel().setSelectedItem(getSelectedItemArgs());

        if(binding.getAddWordViewModel().getSelectedItem()!=null){
            binding.titleTextView.setText(getString(R.string.title_edit_word));
        }

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private WordEntity getSelectedItemArgs(){
        if(getArguments()!=null && getArguments().getParcelable(Constants.mEditItem)!=null){
            return getArguments().getParcelable(Constants.mEditItem);
        }

        return null;
    }
}
