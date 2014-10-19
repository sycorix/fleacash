package de.baerchenland.fleacash;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by steffen on 19.10.14.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fleacashDB.db";
    private static final String TABLE_CASHITEMS = "cashitems";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VENDOR = "vendor";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public DBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CASHITEMS_TABLE = "CREATE TABLE " +
                TABLE_CASHITEMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_VENDOR
                + " INTEGER," + COLUMN_AMOUNT + " REAL," + COLUMN_TIMESTAMP + " TEXT)";
        db.execSQL(CREATE_CASHITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASHITEMS);
        onCreate(db);
    }

    public void addCashItem(CashItem cashItem) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_VENDOR, cashItem.getVendor());
        values.put(COLUMN_AMOUNT, cashItem.getAmount());
        values.put(COLUMN_TIMESTAMP, cashItem.getTimestamp());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CASHITEMS, null, values);
        db.close();
    }
}
