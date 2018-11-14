package com.example.hp.doubletapapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.doubletapapp.R;
import com.example.hp.doubletapapp.database.WordEntity;
import com.example.hp.doubletapapp.databinding.FragmentListWordsBinding;
import com.example.hp.doubletapapp.events.EventsMessages;
import com.example.hp.doubletapapp.ui.adapter.RecyclerViewAdapter;
import com.example.hp.doubletapapp.viewmodel.ListFragmentViewModel;

import java.util.List;

public class ListWordsFragment extends BaseFragment<ListFragmentViewModel, FragmentListWordsBinding> {

    public static ListWordsFragment newInstance(Bundle args) {
        ListWordsFragment wordsListFragment = new ListWordsFragment();
        wordsListFragment.setArguments(args);
        return wordsListFragment;
    }

    @Override
    ListFragmentViewModel getViewModel() {
        return new ListFragmentViewModel(getActivity().getApplication());
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_list_words;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding.setWordViewModel(getViewModel());
        viewModel = ViewModelProviders.of(this).get(ListFragmentViewModel.class);
        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        binding.getWordViewModel().getMutableWordsList().observe(getActivity(), new Observer<List<WordEntity>>() {
            @Override
            public void onChanged(@Nullable List<WordEntity> wordEntities) {
                if (wordEntities != null && !wordEntities.isEmpty()) {
                    showWordsList(wordEntities);
                    return;
                }
                viewModel.setIsListEmpty(true);
            }

        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void performBackgroundTasks(String backgroundTask) {
       if(EventsMessages.mEventInitiateListUpdate.equalsIgnoreCase(backgroundTask)){
           binding.getWordViewModel().getMutableWordsList();
           showWordsList(binding.getWordViewModel().getAllWords());
       }
    }

    private void showWordsList(List<WordEntity> wordEntities){
        setRecyclerView(wordEntities);
        binding.textViewEmptyMsg.setVisibility(View.GONE);
    }

    private void setRecyclerView(List<WordEntity> wordEntities){
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(wordEntities);
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.recyclerView.setAdapter(recyclerViewAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

    }
}
