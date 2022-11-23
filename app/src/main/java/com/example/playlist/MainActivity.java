package com.example.playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DBHelper dbHelper;
    ListView lv;
    TextView tv;
    SimpleCursorAdapter adapter;
    EditText fieldArtist, fieldTitle, fieldDuration, fieldRating;
    Cursor c;
    String sorting = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        fieldArtist = findViewById(R.id.fieldArtist);
        fieldTitle = findViewById(R.id.fieldTitle);
        fieldDuration = findViewById(R.id.fieldDuration);
        fieldRating = findViewById(R.id.fieldRating);
        lv = findViewById(R.id.listview);
        tv = findViewById(R.id.textview);

        refresh();
    }

    public void refresh(){

        c = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME + sorting, null);
        int[] view = new int[]{R.id.idtitle,R.id.title, R.id.artist, R.id.duration, R.id.rating};
        String[] fields = c.getColumnNames();

        adapter = new SimpleCursorAdapter(this, R.layout.layout, c, fields, view);
        lv.setAdapter(adapter);

        c = db.rawQuery("SELECT SUM(duration) FROM " + dbHelper.TABLE_NAME, null);
        c.moveToFirst();

        int duration = c.getInt(0);
        String line = "";
        int hour = duration/3600;
        int minute = (duration - hour*3600)/60;
        int second = duration - (hour*3600 + minute*60);

        if (hour > 1){
            line += hour + " ч. " + minute + " м. " + second + " с. ";
        }else if(minute > 1){
            line += minute + " м. " + second + " с. ";
        }else{
            line += second + " с. ";
        }
        tv.setText(line);
    }

    public void onClick(View view) {
        if (!(fieldArtist.getText().toString().equals("") || fieldTitle.getText().toString().equals("") ||
                fieldDuration.getText().toString().equals("") || fieldRating.getText().toString().equals(""))){

            c = db.rawQuery("SELECT _id FROM " + dbHelper.TABLE_NAME, null);
            c.moveToLast();
            db.execSQL("INSERT INTO " + dbHelper.TABLE_NAME + " VALUES (?,?,?,?,?)",
                    new Object[]
                            {c.getInt(0) + 1, fieldTitle.getText(), fieldArtist .getText(),
                                    fieldDuration.getText(), fieldRating.getText()});
            refresh();

        }else{
            Toast.makeText(this, "Пустые поля!", Toast.LENGTH_LONG).show();
        }
    }

    public void SortByArtist(View view) {
        sorting = " ORDER BY lower(artist);";
        refresh();
    }

    public void SortByTitle(View view) {
        sorting = " ORDER BY lower(title);";
        refresh();
    }

    public void SortByDuration(View view) {
        sorting = " ORDER BY duration DESC;";
        refresh();
    }

    public void SortByRating(View view) {
        sorting = " ORDER BY rating DESC;";
        refresh();
    }
}