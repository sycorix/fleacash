package de.baerchenland.fleacash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    final Context context = this;
    public String textAmount = "";
    public String textVendor = "";
    TextView textViewAmount;
    TextView textViewVendor;
    ListView listView;
    ArrayList itemList;
    CashItem cashItem;
    String itemTimeStamp;
    ListAdapter listAdapter;
    PopupWindow removeItemPopup;
    Button btnClosePopup;
    NumberFormat numberFormat;
    Boolean vendorNumKey;
    Boolean itemDone;
    Boolean freshItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAmount = (TextView)findViewById(R.id.textViewAmount);
        textViewVendor = (TextView)findViewById(R.id.textViewVendor);

        numberFormat = NumberFormat.getInstance(Locale.GERMAN);

        // Array of values to show in ListView
        itemList = new ArrayList();

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

        vendorNumKey = false;
        itemDone = false;
        freshItem = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void calcItemSum() {
        double sum = 0;
        int index;
        CashItem item;
        for(index = 0; index < itemList.size(); index++)
        {
            item = (CashItem)itemList.get(index);
            sum = sum + item.getAmount();
        }
        // set ui
        TextView textSum = (TextView) findViewById(R.id.textViewSum);
        textSum.setText(String.format(Locale.GERMAN, "%6.2f", sum));
    }

    public String getCurrentTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
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
        Boolean numberEntry = false;

        switch(button.getId()) {
            case R.id.button10:
                textAmount += "0";
                numberEntry = true;
                break;
            case R.id.button11:
                textAmount += "1";
                numberEntry = true;
                break;
            case R.id.button12:
                textAmount += "2";
                numberEntry = true;
                break;
            case R.id.button13:
                textAmount += "3";
                numberEntry = true;
                break;
            case R.id.button14:
                textAmount += "4";
                numberEntry = true;
                break;
            case R.id.button15:
                textAmount += "5";
                numberEntry = true;
                break;
            case R.id.button16:
                textAmount += "6";
                numberEntry = true;
                break;
            case R.id.button17:
                textAmount += "7";
                numberEntry = true;
                break;
            case R.id.button18:
                textAmount += "8";
                numberEntry = true;
                break;
            case R.id.button19:
                textAmount += "9";
                numberEntry = true;
                break;
            case R.id.button1c:
                if (!textAmount.contains(",")) {
                    textAmount += ",";
                    numberEntry = true;
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
            item = (CashItem)itemList.get(index);
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

        for(index = 0; index < vendorItems.size(); index++)
        {
            item = (CashItem)vendorItems.get(index);
            arrayAdapter.add(item.getShortInfo());
        }

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
        DBExport dbExport = new DBExport();
        if (dbExport.isExternalStorageWritable()) {
            try {
                // make new file
                File file = new File(dbExport.fileName);
                file.createNewFile();
                /*
                FileOutputStream outputStream;
                outputStream = openFileOutput(dbExport.fileName, Context.MODE_WORLD_READABLE);
                outputStream.write(null);
                outputStream.close();
                */
            } catch (Exception e) {
                e.printStackTrace();
            }
            dbExport.writeCsvFile();
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