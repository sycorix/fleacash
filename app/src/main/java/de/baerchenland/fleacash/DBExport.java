package de.baerchenland.fleacash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * Created by steffen on 25.10.14.
 */

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVWriteProc;
import au.com.bytecode.opencsv.CSVWriter;

public class DBExport {

    static String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    static String fileName = sdDir + File.separator + "fleacash.csv";
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

    public static void writeCsvFile() {
        // CSVWriter will be closed after end of processing
        try {
            csv.write(fileName, new CSVWriteProc() {
                public void process(CSVWriter out) {
                    out.writeNext("Header1", "Header2");
                    out.writeNext("v11", "v12");
                    out.writeNext("v21", "v22");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    Environment.getExternalStorageDirectory().getPath()
//    File exportDir = new File(Environment.getExternalStorageDirectory(), "");
//    if (!exportDir.exists())
//    {
//        exportDir.mkdirs();
//    }

//    File file = new File(exportDir, "csvname.csv");

    /*
    java.sql.ResultSet myResultSet = ....
    writer.writeAll(myResultSet, includeHeaders);
     */
}
