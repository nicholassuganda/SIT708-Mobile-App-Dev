package com.tutorial.itube;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "itube.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PLAYLIST = "playlist";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VIDEO_URL = "video_url";

    public PlaylistDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + TABLE_PLAYLIST +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_VIDEO_URL + " TEXT)";
        db.execSQL(CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        onCreate(db);
    }

    public void addVideo(String videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VIDEO_URL, videoUrl);
        db.insert(TABLE_PLAYLIST, null, values);
        db.close();
    }

    public List<String> getAllVideos() {
        List<String> videoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PLAYLIST,
                new String[]{COLUMN_VIDEO_URL},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                videoList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return videoList;
    }
}