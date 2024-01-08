package com.example.a4darbas;

import android.app.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AddNoteActivity extends Activity {

    EditText edNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.edNote = findViewById(R.id.edNote);
    }

    public void onBtnSaveAndCloseClick(View view) {
        String noteToAdd = edNote.getText().toString();
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>());
        savedSet.add(noteToAdd);

        editor.putString(Constants.NOTE_KEY, noteToAdd);
        editor.putString(Constants.NOTE_KEY_DATE, formattedDate);
        editor.putStringSet(Constants.NOTES_ARRAY_KEY, savedSet);
        editor.apply();

        finish();
    }
}
