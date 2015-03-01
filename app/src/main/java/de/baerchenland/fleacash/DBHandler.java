package de.baerchenland.fleacash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Database handler
 *
 * Created by steffen on 19.10.14.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fleacashDB.db";
    private static final String TABLE_CASHITEMS = "cashitems";

    public static final String COLUMN_ID = "_id";              // INTEGER
    public static final String COLUMN_VENDOR = "vendor";       // INTEGER   (Integer)
    public static final String COLUMN_AMOUNT = "amount";       // REAL      (double)
    public static final String COLUMN_TIMESTAMP = "timestamp"; // STRING    (String)

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

    public void addCashItem(CashItem cashItem, String timeStamp) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_VENDOR, cashItem.getVendor());
        values.put(COLUMN_AMOUNT, cashItem.getAmount());
        values.put(COLUMN_TIMESTAMP, timeStamp);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CASHITEMS, null, values);
        db.close();
    }

    public ArrayList getVendorSums() {
        ArrayList vendorSumList = new ArrayList();
        CashItem vendorSum;
        Integer index;
        String query = "SELECT " +  COLUMN_VENDOR + ", SUM(" + COLUMN_AMOUNT +") FROM " + TABLE_CASHITEMS + " GROUP BY " + COLUMN_VENDOR + " ORDER BY " +COLUMN_VENDOR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        index = 0;
        while(cursor.moveToNext()) {
            index++;
            vendorSum = new CashItem(index.toString(), cursor.getInt(0), cursor.getDouble(1));
            vendorSumList.add(vendorSum);
        }
        db.close();
        return vendorSumList;
    }


    public ArrayList getVendor100Sums() {
        ArrayList vendorSumList = new ArrayList();
        CashItem vendorSum;
        Integer index;
        String query = "SELECT " +  COLUMN_VENDOR + ", SUM(" + COLUMN_AMOUNT +") FROM " + TABLE_CASHITEMS + " WHERE " + COLUMN_VENDOR + " > 99 GROUP BY " + COLUMN_VENDOR + " ORDER BY " +COLUMN_VENDOR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        index = 0;
        while(cursor.moveToNext()) {
            index++;
            vendorSum = new CashItem(index.toString(), cursor.getInt(0), cursor.getDouble(1));
            vendorSumList.add(vendorSum);
        }
        db.close();
        return vendorSumList;
    }

    public ArrayList getVendorAll() {
        ArrayList vendorList = new ArrayList();
        CashItem vendor;
        Integer index;
        String query = "SELECT " + COLUMN_VENDOR + ", " + COLUMN_AMOUNT + ", " + COLUMN_TIMESTAMP + " FROM " + TABLE_CASHITEMS + " ORDER BY " + COLUMN_TIMESTAMP + ", " + COLUMN_VENDOR ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        index = 0;
        while(cursor.moveToNext()) {
            index++;
            vendor = new CashItem(cursor.getString(2), cursor.getInt(0), cursor.getDouble(1));
            vendorList.add(vendor);
        }
        db.close();
        return vendorList;
    }
}
