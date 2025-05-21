package com.tutorial.personalizedlearningexperienceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LearningApp.db";
    private static final int DATABASE_VERSION = 1;

    // Quiz Results Table
    private static final String TABLE_QUIZ_RESULTS = "quiz_results";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TOPIC = "topic";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_TOTAL = "total";
    private static final String COLUMN_DATE = "date";

    public QuizDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_QUIZ_RESULTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TOPIC + " TEXT,"
                + COLUMN_SCORE + " INTEGER,"
                + COLUMN_TOTAL + " INTEGER,"
                + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_RESULTS);
        onCreate(db);
    }

    public long addQuizResult(String topic, int score, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOPIC, topic);
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_TOTAL, total);

        long id = db.insert(TABLE_QUIZ_RESULTS, null, values);
        db.close();
        return id;
    }

    public List<QuizResult> getAllQuizResults() {
        List<QuizResult> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUIZ_RESULTS,
                new String[]{COLUMN_ID, COLUMN_TOPIC, COLUMN_SCORE, COLUMN_TOTAL, COLUMN_DATE},
                null, null, null, null,
                COLUMN_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                QuizResult result = new QuizResult();
                result.setId(cursor.getInt(0));
                result.setTopic(cursor.getString(1));
                result.setScore(cursor.getInt(2));
                result.setTotal(cursor.getInt(3));
                result.setDate(cursor.getString(4));
                results.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return results;
    }

    public float getAverageScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(" + COLUMN_SCORE + "*100.0/" + COLUMN_TOTAL + ") FROM " + TABLE_QUIZ_RESULTS, null);
        if (cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }
        cursor.close();
        db.close();
        return 0;
    }

    public int getTotalQuizzes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_QUIZ_RESULTS, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return 0;
    }
}