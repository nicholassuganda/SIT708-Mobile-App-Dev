package com.tutorial.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuizApp.db";
    private static final int DATABASE_VERSION = 1;

    // Questions table
    private static final String TABLE_NAME = "questions";
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_OPTION1 = "option1";
    private static final String KEY_OPTION2 = "option2";
    private static final String KEY_OPTION3 = "option3";
    private static final String KEY_ANSWER = "answer";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION + " TEXT,"
                + KEY_OPTION1 + " TEXT,"
                + KEY_OPTION2 + " TEXT,"
                + KEY_OPTION3 + " TEXT,"
                + KEY_ANSWER + " INTEGER)";
        db.execSQL(CREATE_QUESTIONS_TABLE);

        // Insert questions
        addQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void addQuestions(SQLiteDatabase db) {
        List<Question> questions = new ArrayList<>();

        // Add your 5 questions here
        questions.add(new Question(
                "What is the base class for activities in Android?",
                "Activity",
                "AppCompatActivity",
                "FragmentActivity",
                1));

        questions.add(new Question(
                "Which method is called when an Activity first starts?",
                "onStart()",
                "onCreate()",
                "onResume()",
                2));

        questions.add(new Question(
                "What is used to declare UI elements in Android?",
                "Java code",
                "XML",
                "JSON",
                2));

        questions.add(new Question(
                "Which component is NOT part of Android architecture?",
                "Content Provider",
                "Activity Manager",
                "View Controller",
                3));

        questions.add(new Question(
                "What is the current latest version of Android?",
                "Android 10",
                "Android 11",
                "Android 12",
                3));

        for (Question question : questions) {
            ContentValues values = new ContentValues();
            values.put(KEY_QUESTION, question.getQuestion());
            values.put(KEY_OPTION1, question.getOption1());
            values.put(KEY_OPTION2, question.getOption2());
            values.put(KEY_OPTION3, question.getOption3());
            values.put(KEY_ANSWER, question.getAnswer());

            db.insert(TABLE_NAME, null, values);
        }
    }

    public List<Question> getAllQuestions() {
        List<Question> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setOption1(cursor.getString(2));
                question.setOption2(cursor.getString(3));
                question.setOption3(cursor.getString(4));
                question.setAnswer(cursor.getInt(5));

                questionsList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionsList;
    }

}
