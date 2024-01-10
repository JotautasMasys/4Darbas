package com.example.a4darbas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AddNoteActivity extends Activity {

    EditText edNoteName;
    EditText edNoteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edNoteName = findViewById(R.id.edNoteName);
        edNoteContent = findViewById(R.id.edNote);
    }

    public void onBtnSaveAndCloseClick(View view) {
        String noteName = edNoteName.getText().toString();
        String noteContent = edNoteContent.getText().toString();

        if (!noteName.isEmpty() && !noteContent.isEmpty()) {
            Date currentDate = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(currentDate);

            SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>());
            savedSet.add(noteName);

            editor.putString(Constants.NOTE_KEY, noteContent);
            editor.putString(Constants.NOTE_KEY_DATE, formattedDate);
            editor.putStringSet(Constants.NOTES_ARRAY_KEY, savedSet);
            editor.apply();

            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Note name and content cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}