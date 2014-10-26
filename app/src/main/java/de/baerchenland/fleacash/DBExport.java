package de.baerchenland.fleacash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by steffen on 25.10.14.
 */

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVWriteProc;
import au.com.bytecode.opencsv.CSVWriter;

public class DBExport {

    static String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    // static String fileName = sdDir + File.separator + "fleacash.csv";
    static String fileNameSum = "fleacashsum.csv";
    // Not sure if the / is on the path or not
    // static File outfile = new File(sdDir + File.separator + fileName);

    private static final CSV csv = CSV
            .separator(';')  // delimiter of fields
            .quote('"')      // quote character
            .skipLines(1)
            .create();       // new instance is immutable

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static void writeCsvFile(File file, ArrayList vendorItems) {
        final ArrayList localItems;
        localItems = vendorItems;
        // CSVWriter will be closed after end of processing
        try {
            csv.write(file, new CSVWriteProc() {
                public void process(CSVWriter out) {
                    Integer index;
                    CashItem item;

                    out.writeNext("Vendor", "Amount");
                    for(index = 0; index < localItems.size(); index++)
                    {
                        item = (CashItem)localItems.get(index);
                        out.writeNext(item.getVendorFormated(), item.getAmountFormated());

                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
