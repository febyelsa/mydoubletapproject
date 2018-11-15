package com.example.hp.doubletapapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.ImageView;

import com.example.hp.doubletapapp.R;
import com.example.hp.doubletapapp.repository.WordRepository;
import com.example.hp.doubletapapp.ui.adapter.SuggestionsAdapter;

public class InputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener,View.OnClickListener {

    private View candidateView;
    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private RecyclerView suggestionList;
    private WordRepository wordRepository;
    private ImageView navigateToSuggestions;
    private ImageView navigateToKeyBoard;
    private SuggestionsAdapter suggestionsAdapter;


    @Override
    public View onCreateInputView() {

        keyboard = new Keyboard(this, R.xml.qwerty);

        setUpKeyBoardView();
        wordRepository = new WordRepository(getApplication());
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        return keyboardView;

    }

    @Override
    public void onComputeInsets(InputMethodService.Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }
    }

    @Override
    public View onCreateCandidatesView() {

        candidateView = getLayoutInflater().inflate(R.layout.candidate_view, null);
        setCandidatesView(candidateView);
        setCandidatesViewShown(false);
        setUpCandidateView();
        setUpSuggestionsList();
        return candidateView;

    }


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        InputConnection inputConnection = getCurrentInputConnection();

        if (inputConnection != null) {

            switch (primaryCode) {

                case Keyboard.KEYCODE_DELETE:
                    CharSequence selectedText = inputConnection.getSelectedText(0);
                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                    break;

                default:
                    char code = (char) primaryCode;
                    inputConnection.commitText(String.valueOf(code), 1);
                    setCandidatesViewShown(true);
                    setSuggestionsAdapter();
            }

        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private void setUpCandidateView() {
        navigateToSuggestions = candidateView.findViewById(R.id.navigateToSuggestions);
        navigateToKeyBoard = candidateView.findViewById(R.id.navigateToKeyBoard);
        suggestionList = candidateView.findViewById(R.id.suggestionsView);

        navigateToSuggestions.setOnClickListener(this);
        navigateToKeyBoard.setOnClickListener(this);
    }

    private void setUpKeyBoardView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
    }

    private void setUpSuggestionsList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        suggestionList.setLayoutManager(layoutManager);
        suggestionList.setItemAnimator(new DefaultItemAnimator());
        suggestionList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private void setSuggestionsAdapter() {
        suggestionsAdapter = new SuggestionsAdapter(getApplication(),wordRepository.getAllWords());
        suggestionList.setAdapter(suggestionsAdapter);
    }

    private void navigateToSuggestions() {
        keyboardView.setVisibility(View.GONE);
        navigateToSuggestions.setVisibility(View.GONE);
        navigateToKeyBoard.setVisibility(View.VISIBLE);
        suggestionList.setVisibility(View.VISIBLE);
        suggestionList.setMinimumHeight(keyboardView.getHeight());
    }

    private void setNavigateToKeyBoard() {
        keyboardView.setVisibility(View.VISIBLE);
        navigateToSuggestions.setVisibility(View.VISIBLE);
        navigateToKeyBoard.setVisibility(View.GONE);
        suggestionList.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if(R.id.navigateToSuggestions == v.getId()){
            navigateToSuggestions();
            return;
        }
        if(R.id.navigateToKeyBoard == v.getId()){
            setNavigateToKeyBoard();
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            getCurrentInputConnection().commitText(message,message.length()+2);
            Log.d("receiver", "Got message: " + message);
        }
    };
}
