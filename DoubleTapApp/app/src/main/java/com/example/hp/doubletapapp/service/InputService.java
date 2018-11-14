package com.example.hp.doubletapapp.service;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.ImageView;

import com.example.hp.doubletapapp.R;
import com.example.hp.doubletapapp.repository.WordRepository;
import com.example.hp.doubletapapp.ui.adapter.SuggestionsAdapter;

public class InputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener,View.OnClickListener {

    private static final int mViewModeKeyBoard = 1;
    private static final int mViewModeSuggestions = 2;

    private View candidateView;
    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private RecyclerView suggestionList;
    private WordRepository wordRepository;
    private ImageView navigateToSuggestions;
    private ImageView navigateToKeyBoard;
    private SuggestionsAdapter suggestionsAdapter;
    private static int mViewMode = mViewModeKeyBoard;


    @Override
    public View onCreateInputView() {

        keyboard = new Keyboard(this, R.xml.qwerty);

        setUpKeyBoardView();
        wordRepository = new WordRepository(getApplication());

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
                    candidateView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            gestureDetector.onTouchEvent(event);
                            return true;
                        }
                    });
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
        suggestionsAdapter = new SuggestionsAdapter(wordRepository.getAllWords());
        suggestionList.setAdapter(suggestionsAdapter);
    }

    private void navigateToSuggestions() {
        setSuggestionsAdapter();
        keyboardView.setVisibility(View.GONE);
        navigateToSuggestions.setVisibility(View.GONE);
        navigateToKeyBoard.setVisibility(View.VISIBLE);
        suggestionList.setVisibility(View.VISIBLE);
        suggestionList.setMinimumHeight(keyboardView.getHeight());
        mViewMode = mViewModeSuggestions;
    }

    private void navigateToKeyBoard() {
        keyboardView.setVisibility(View.VISIBLE);
        navigateToSuggestions.setVisibility(View.VISIBLE);
        navigateToKeyBoard.setVisibility(View.GONE);
        suggestionList.setVisibility(View.GONE);
        mViewMode = mViewModeKeyBoard;
    }

    @Override
    public void onClick(View v) {
        if(R.id.navigateToSuggestions == v.getId()){
            navigateToSuggestions();
            return;
        }
        if(R.id.navigateToKeyBoard == v.getId()){
            navigateToKeyBoard();
        }
    }
    private GestureDetector gestureDetector = new GestureDetector(getApplication(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("TEST", "onDoubleTap");
            if(mViewMode == mViewModeKeyBoard){
                navigateToSuggestions();
            }else  if(mViewMode == mViewModeSuggestions){
                navigateToKeyBoard();
            }

            return super.onDoubleTap(e);
        }
    });

}
