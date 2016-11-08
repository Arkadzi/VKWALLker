package me.humennyi.arkadii.vkwallker.data.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.humennyi.arkadii.vkwallker.data.entities.PostEntity;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "VKWALLkerDb";
    private static int DB_VERSION = 1;
    private static final String TABLE_NAME = "Posts";
    private static final String ID = "_id";
    private static final String POST_JSON = "post_json";
    private static final String POST_ID = "post_id";
    private final Gson gson = new Gson();


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
                "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                POST_ID + " TEXT NOT NULL," +
                POST_JSON + " TEXT NOT NULL," +
                " UNIQUE (" + POST_ID + ")" +
                ")" +
                ";";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DB_VERSION < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public void insert(PostEntity postEntity) {
        ContentValues values = new ContentValues();
        values.put(POST_ID, postEntity.getId());
        values.put(POST_JSON, gson.toJson(postEntity));
        getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public List<PostEntity> getWithOffset(int offset, int count) {
        String sql = String.format("SELECT %s from %s LIMIT ?, ?", POST_JSON, TABLE_NAME);
        String[] selectionArgs = {String.valueOf(offset), String.valueOf(count)};
        Cursor cursor = getWritableDatabase().rawQuery(sql, selectionArgs);
        List<PostEntity> entities = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    PostEntity postEntity = gson.fromJson(cursor.getString(0), PostEntity.class);
                    entities.add(postEntity);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        Log.e("DBHelper", "read " + entities.size() + " entities");
        return entities;
    }

    public void deletePosts() {
        getWritableDatabase().delete(TABLE_NAME, null, null);
    }
}
