package com.example.hp.doubletapapp;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hp.doubletapapp.events.BackgroundEvents;
import com.example.hp.doubletapapp.events.ClickEvents;
import com.example.hp.doubletapapp.events.EventsMessages;
import com.example.hp.doubletapapp.ui.ListWordsFragment;
import com.example.hp.doubletapapp.utils.Constants;
import com.example.hp.doubletapapp.utils.FragmentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTopToolbar;
    private BottomSheetDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        FragmentUtils.addFragment(getSupportFragmentManager(), ListWordsFragment.newInstance(null), R.id.container);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_new_word) {
            dialogFragment = FragmentUtils.showBottomDialogFragment(getSupportFragmentManager(), "Add word", null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(ClickEvents events) {
        if (!events.getMessage().isEmpty() && EventsMessages.mEventNewWordSaveClicked.equalsIgnoreCase(events.getMessage())) {
            dialogFragment.dismiss();
            EventBus.getDefault().post(new BackgroundEvents(EventsMessages.mEventInitiateListUpdate));
            return;
        }
        if (!events.getMessage().isEmpty() && EventsMessages.mEventItemSelectedForEditing.equalsIgnoreCase(events.getMessage())) {

            Bundle args = new Bundle();
            args.putParcelable(Constants.mEditItem, events.getItem());
            dialogFragment = FragmentUtils.showBottomDialogFragment(getSupportFragmentManager(), "Add word", args);

        }
    }
}
