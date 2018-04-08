package vichitpov.com.fbs.sqlite;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.model.NotificationModel;

public class NotificationHelper extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    private static final String DATABASE_NAME = "notification.db";
    private static final int VERSION = 1;

    private static final String TABLE_NOTIFICATION = "tbl_notification";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String NOTIFICATION_TYPE = "notification_type";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NOTIFICATION + "(" +
            "" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "" + TITLE + " TEXT," +
            "" + NOTIFICATION_TYPE + " TEXT);";


    public NotificationHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + TABLE_NOTIFICATION);
    }

    public void insertNotification(String id, String title, String notificationType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(TITLE, title);
        contentValues.put(NOTIFICATION_TYPE, notificationType);

        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NOTIFICATION, null, contentValues);
        sqLiteDatabase.close();
    }

    public List<NotificationModel> getNotificationList() {
        sqLiteDatabase = this.getWritableDatabase();
        List<NotificationModel> list = new ArrayList<>();
        String SQL = "SELECT * FROM " + TABLE_NOTIFICATION + " ORDER BY " + ID + " DESC ";
        Cursor cursor = sqLiteDatabase.rawQuery(SQL, null);
        if (cursor.moveToFirst()) {
            do {
                NotificationModel fm = new NotificationModel();
                fm.setId(String.valueOf(cursor.getInt(0)));
                fm.setTitle(cursor.getString(1));
                fm.setNotificationType(cursor.getString(2));

                list.add(fm);
            } while (cursor.moveToNext());
        }

        return list;
    }
}
