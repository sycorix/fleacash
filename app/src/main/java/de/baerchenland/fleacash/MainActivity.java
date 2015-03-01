package de.baerchenland.fleacash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    final Context context = this;
    private ShareActionProvider mShareActionProvider;
    public String textAmount = "";
    public String textVendor = "";
    TextView textViewAmount;
    TextView textViewVendor;
    ListView listView;
    ArrayList<CashItem> itemList;
    CashItem cashItem;
    String itemTimeStamp;
    ListAdapter listAdapter;
    NumberFormat numberFormat;
    Boolean vendorNumKey;
    Boolean itemDone;
    Boolean freshItem;
    String itemListSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAmount = (TextView)findViewById(R.id.textViewAmount);
        textViewVendor = (TextView)findViewById(R.id.textViewVendor);

        numberFormat = NumberFormat.getInstance(Locale.GERMAN);

        // try to get the instance back from the last orientation
        itemList = (ArrayList)getLastNonConfigurationInstance();
        // Array of values to show in ListView
        if (itemList == null) {
            itemList = new ArrayList<CashItem>();
        }

        listAdapter = new de.baerchenland.fleacash.ListAdapter(this, itemList);
        listView = (ListView) findViewById(R.id.listViewAmount);
        listView.setAdapter(listAdapter);
        // listening to single list item on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // get selected item
                CashItem cashItem = (CashItem)parent.getItemAtPosition(position);
                deleteCashItemPopup(cashItem);
                // Toast.makeText(getBaseContext(), (CharSequence) cashItem.getInfo(), Toast.LENGTH_SHORT).show();
            }
        });

        calcItemSum();
        vendorNumKey = false;
        itemDone = false;
        freshItem = false;
    }

    /**
     *  The implementation of onRetainNonConfigurationInstance will return an
     *  instance of someExpensiveObject which we created earlier.  This instance
     *  will be available to the future instance of SomeActivity.
     */
    @Override
    @Deprecated
    public Object onRetainNonConfigurationInstance() {
        return itemList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        setShareIntent(makeShareIntent(""));

        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private Intent makeShareIntent(String textContent) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Beleg");
        sendIntent.putExtra(Intent.EXTRA_TEXT, textContent);
        sendIntent.setType("text/plain");
        return sendIntent;
    }

    public void calcItemSum() {
        double sum = 0;
        int index;
        CashItem item;
        String receipt = "";
        for(index = 0; index < itemList.size(); index++)
        {
            item = itemList.get(index);
            sum = sum + item.getAmount();
            // create receipt for share intent
            receipt = receipt + item.getVendorFormated() + ": " + String.format(Locale.GERMAN, "%6.2f", item.getAmount()) + "\n";
        }
        // set ui
        TextView textSum = (TextView) findViewById(R.id.textViewSum);
        itemListSum = String.format(Locale.GERMAN, "%6.2f", sum);
        textSum.setText(itemListSum);
        receipt = receipt + "Summe:  " + itemListSum;
        setShareIntent(makeShareIntent(receipt));
    }

    public String getCurrentTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return (sdf.format(date));
    }

    public void buttonVendorOnClick(View v) {
        Button button=(Button) v;
        Boolean okKey = false;
        String textVendorTmp = textVendor;

        if (itemDone) {
            itemDone = false;
            textVendorTmp = "";
        }
        switch(button.getId()) {
            case R.id.button00:
                textVendorTmp += "0";
                vendorNumKey = true;
                break;
            case R.id.button01:
                textVendorTmp += "1";
                vendorNumKey = true;
                break;
            case R.id.button02:
                textVendorTmp += "2";
                vendorNumKey = true;
                break;
            case R.id.button03:
                textVendorTmp += "3";
                vendorNumKey = true;
                break;
            case R.id.button04:
                textVendorTmp += "4";
                vendorNumKey = true;
                break;
            case R.id.button05:
                textVendorTmp += "5";
                vendorNumKey = true;
                break;
            case R.id.button06:
                textVendorTmp += "6";
                vendorNumKey = true;
                break;
            case R.id.button07:
                textVendorTmp += "7";
                vendorNumKey = true;
                break;
            case R.id.button08:
                textVendorTmp += "8";
                vendorNumKey = true;
                break;
            case R.id.button09:
                textVendorTmp += "9";
                vendorNumKey = true;
                break;
            case R.id.button0cl:
                textVendor = "";
                vendorNumKey = false;
                break;
            case R.id.button0o: // Ok
                if (textVendor.length() > 0) {
                    okKey = true;
                }
                vendorNumKey = false;
                break;
        }
        if (vendorNumKey) {
            textVendor = textVendorTmp;
        }
        if (okKey) {
            itemTimeStamp = getCurrentTimeStamp(); // temp. timestamp
            cashItem = new CashItem(itemTimeStamp, Integer.parseInt(textVendor),0);
            freshItem = true;
            if (textAmount.length() > 0) {
                cashItem.setAmount(convert(textAmount));
                itemList.add(cashItem);
                listAdapter.notifyDataSetChanged(); // update ui
                calcItemSum();
                itemDone = true;
                freshItem = false;
                textAmount = ""; // clear all input fields
                textVendor = "";
                textViewAmount.setText(textAmount);
            }
        }
        textViewVendor.setText(textVendor);
    }

    public void buttonAmountOnClick(View v) {
        Button button=(Button) v;

        switch(button.getId()) {
            case R.id.button10:
                textAmount += "0";
                break;
            case R.id.button11:
                textAmount += "1";
                break;
            case R.id.button12:
                textAmount += "2";
                break;
            case R.id.button13:
                textAmount += "3";
                break;
            case R.id.button14:
                textAmount += "4";
                break;
            case R.id.button15:
                textAmount += "5";
                break;
            case R.id.button16:
                textAmount += "6";
                break;
            case R.id.button17:
                textAmount += "7";
                break;
            case R.id.button18:
                textAmount += "8";
                break;
            case R.id.button19:
                textAmount += "9";
                break;
            case R.id.button1c:
                if (!textAmount.contains(",")) {
                    textAmount += ",";
                }
                break;
            case R.id.button1cl:
                textAmount = "";
                break;
            case R.id.button1o: // Ok
                if (textAmount.length() > 0 && textVendor.length() > 0) {
                    if (!freshItem) { // vendor method created item already
                        itemTimeStamp = getCurrentTimeStamp(); // temp. timestamp
                        cashItem = new CashItem(itemTimeStamp, Integer.parseInt(textVendor),0);
                    }
                    cashItem.setAmount(convert(textAmount));
                    itemList.add(cashItem);
                    listAdapter.notifyDataSetChanged(); // update ui
                    calcItemSum();
                    itemDone = true;
                    freshItem = false;
                    textAmount = ""; // clear all input fields
                    textVendor = "";
                    textViewVendor.setText(textVendor);
                }
                break;
        }

        textViewAmount.setText(textAmount);
    }

    public double convert(String textAmount) {
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

    public void buttonCancelOnClick(View v) {
        itemList.clear();
        listAdapter.notifyDataSetChanged(); // update ui
        calcItemSum();
    }

    public void buttonSaveOnClick(View v) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int index;
        CashItem item;
        String timeStamp;
        // save item list to db
        timeStamp = getCurrentTimeStamp();
        for(index = 0; index < itemList.size(); index++)
        {
            item = itemList.get(index);
            dbHandler.addCashItem(item, timeStamp);
        }
        // clear item list
        itemList.clear();
        listAdapter.notifyDataSetChanged(); // update ui
        calcItemSum();
    }

    private void deleteCashItemPopup(final CashItem cashItem) {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            // set title
            alertDialogBuilder.setTitle("Eintrag löschen?");
            // set dialog message
            alertDialogBuilder
                    .setMessage(cashItem.getInfo())
                    .setCancelable(false)
                    .setPositiveButton("Ja",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            itemList.remove(cashItem);
                            listAdapter.notifyDataSetChanged(); // update ui
                            calcItemSum();
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Nein",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calcShowSumsByVendor() {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList vendorItems;
        Integer index;
        CashItem item;
        double sumAll;

        // get list of calculated sums
        vendorItems = dbHandler.getVendorSums();

        // build popup dialog and show
        AlertDialog.Builder builderSingle;
        builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Summe pro Verkäufer");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
            MainActivity.this,
            android.R.layout.select_dialog_singlechoice);

        sumAll = 0.0;
        for(index = 0; index < vendorItems.size(); index++)
        {
            item = (CashItem)vendorItems.get(index);
            arrayAdapter.add(item.getShortInfo());
            sumAll += item.getAmount();
        }
        builderSingle.setTitle("Summe pro Verkäufer (Gesamt: " + String.format(Locale.GERMAN, "%6.2f)", sumAll));

        builderSingle.setNegativeButton("Ok",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                }
            });

        builderSingle.setAdapter(arrayAdapter,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    AlertDialog.Builder builderInner = new AlertDialog.Builder(
                        MainActivity.this);
                    // TODO:
                    // take a look at this http://stackoverflow.com/questions/9165322/using-arrayadapter-with-alertdialog-and-setadapter
                    // for a detailed list instead of a message here
                    builderInner.setMessage(strName);
                    builderInner.setTitle("Verkäufer");
                    builderInner.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                DialogInterface dialog,
                                int which) {
                                    dialog.dismiss();
                            }
                        });
                    builderInner.show();
                }
            });

        builderSingle.show();
    }

    private void exportDBToCsv() {
        //DBExport dbExport = new DBExport();
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList vendorItemsSum;
        ArrayList vendorItemsAll;

        // get list of calculated sums
        vendorItemsSum = dbHandler.getVendorSums();
        vendorItemsAll = dbHandler.getVendorAll();

        // Alternate storage locations and how to find them
        // getFilesDir() = internal directory for your app
        //      /data/data/de.baerchenland.fleacash/files/fleacash.csv
        // getExternalFilesDir(null) = absolute path to the directory on the primary external filesystem
        //      /storage/emulated/0/Android/data/de.baerchenland.fleacash/files/fleacash.csv
        // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //      /storage/emulated/0/Download/fleacash.csv
        File fileSum = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), DBExport.fileNameSum);
        File fileAll = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), DBExport.fileNameAll);

        if (DBExport.isExternalStorageWritable()) {
            try {
                DBExport.writeCsvFileSum(fileSum, vendorItemsSum);
                DBExport.writeCsvFileAll(fileAll, vendorItemsAll);
                Toast.makeText(MainActivity.this, "Export erfolgreich.", Toast.LENGTH_LONG).
                        show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Export gescheitert: " + e.getMessage(), Toast.LENGTH_LONG).
                        show();
            }
       }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_sums) {
            calcShowSumsByVendor();
            return true;
        }
        if (id == R.id.action_export) {
            exportDBToCsv();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}