package com.example.a4darbas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> listNoteItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView lvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    private void initializeViews() {
        lvNotes = findViewById(R.id.lvNotes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNoteItems);
        lvNotes.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_options_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
        String lastSavedNote = sharedPref.getString(Constants.NOTE_KEY, "NA");
        String lastSavedNoteDate = sharedPref.getString(Constants.NOTE_KEY_DATE, "1900-01-01");
        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);

        if (savedSet != null) {
            this.listNoteItems.clear();
            this.listNoteItems.addAll(savedSet);
            this.adapter.notifyDataSetChanged();
        }

        Snackbar.make(lvNotes, String.format("%s: %s", getString(R.string.msg_last_saved_note), lastSavedNote), Snackbar.LENGTH_LONG).show();
        Toast.makeText(this, lastSavedNoteDate, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note:
                Intent i = new Intent(this, AddNoteActivity.class);
                startActivity(i);
                startAddNoteActivity();
                return true;
            case R.id.remove_note:
                removeNote();
                return true;
            case R.id.update_note:
                showToast(getString(R.string.msg_updated_clicked));
                return true;
            case R.id.dunno:
                showToast(getString(R.string.msg_dunno_clicked));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void startAddNoteActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, Integer.parseInt(String.valueOf(Constants.ADD_NOTE_REQUEST_CODE)));
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void removeNote() {
        int positionToRemove = 0;

        if (positionToRemove >= 0 && positionToRemove < listNoteItems.size()) {
            String removedNote = listNoteItems.remove(positionToRemove);
            adapter.notifyDataSetChanged();

            Snackbar.make(lvNotes, String.format("Note removed: %s", removedNote), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(lvNotes, "Invalid position for removal", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
        }
    }
}