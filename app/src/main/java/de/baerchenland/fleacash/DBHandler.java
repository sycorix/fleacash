package de.baerchenland.fleacash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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

    public void addCashItem(CashItem cashItem, String timeStamp) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_VENDOR, cashItem.getVendor());
        values.put(COLUMN_AMOUNT, cashItem.getAmount());
        values.put(COLUMN_TIMESTAMP, timeStamp);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CASHITEMS, null, values);
        db.close();
    }

    public double convert(String textAmount) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMAN);
        Number number;
        double amount;
        try {
            number = numberFormat.parse(textAmount);
            amount = number.doubleValue();
        } catch (Exception e) {
            amount = 0;
        }
        return amount;
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
            vendorSum = new CashItem(index.toString(), Integer.parseInt(cursor.getString(0)),convert(cursor.getString(1)));
            vendorSumList.add(vendorSum);
        }
        db.close();
        return vendorSumList;
    }
}
