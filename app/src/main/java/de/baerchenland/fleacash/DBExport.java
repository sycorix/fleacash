package de.baerchenland.fleacash;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * Export to CSV file
 *
 * Created by steffen on 25.10.14.
 */

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVWriteProc;
import au.com.bytecode.opencsv.CSVWriter;

import static android.os.Environment.*;

public class DBExport {

    static String fileNameSum = "fleacashsum.csv";
    static String fileNameAll = "fleacash.csv";

    private static final CSV csv = CSV
            .separator(',')  // delimiter of fields
            .quote('"')      // quote character
            .skipLines(1)
            .create();       // new instance is immutable

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static void writeCsvFileSum(File file, ArrayList vendorItems) {
        final ArrayList localItems;
        localItems = vendorItems;
        try {
            csv.write(file, new CSVWriteProc() {
                public void process(CSVWriter out) {
                    Integer index;
                    CashItem item;
                    out.writeNext("Vendor", "Amount");
                    for(index = 0; index < localItems.size(); index++)
                    {
                        item = (CashItem)localItems.get(index);
                        out.writeNext(item.getVendorCsv(), item.getAmountCsv());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeCsvFileAll(File file, ArrayList vendorItems) {
        final ArrayList localItems;
        localItems = vendorItems;
        try {
            csv.write(file, new CSVWriteProc() {
                public void process(CSVWriter out) {
                    Integer index;
                    CashItem item;
                    out.writeNext("Vendor", "Amount", "Timestamp");
                    for(index = 0; index < localItems.size(); index++)
                    {
                        item = (CashItem)localItems.get(index);
                        out.writeNext(item.getVendorCsv(), item.getAmountCsv(), item.getTimestamp());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
