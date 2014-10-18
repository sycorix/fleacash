package de.baerchenland.fleacash;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    public String textAmount = "";
    public String textVendor = "";
    TextView textViewAmount;
    TextView textViewVendor;
    ListView listView;
    ArrayList itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAmount = (TextView)findViewById(R.id.textViewAmount);
        textViewVendor = (TextView)findViewById(R.id.textViewVendor);

        // Array of values to show in ListView
        itemList = new ArrayList();
        // some dummy data
        String timeStamp = getCurrentTimeStamp();
        CashItem cashItem = new CashItem(timeStamp,22,2.30);
        itemList.add(cashItem);
        itemList.add(new CashItem(timeStamp,22,4.00));
        itemList.add(new CashItem(timeStamp,22,7.50));
        itemList.add(new CashItem(timeStamp,2,1.50));
        itemList.add(new CashItem(timeStamp,99,0.50));
        listView = (ListView) findViewById(R.id.listViewAmount);
        listView.setAdapter(new de.baerchenland.fleacash.ListAdapter(this, itemList));
        // listening to single list item on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // get selected item
                CashItem cashItem = (CashItem)parent.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), (CharSequence) cashItem.getInfo(), Toast.LENGTH_SHORT).show();
            }
        });

        calcItemSum();
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
                textAmount += ",";
                break;
            case R.id.button1cl:
                textAmount = "";
                break;
        }

        textViewAmount.setText(textAmount);
    }

    public void buttonVendorOnClick(View v) {
        Button button=(Button) v;

        switch(button.getId()) {
            case R.id.button00:
                textVendor += "0";
                break;
            case R.id.button01:
                textVendor += "1";
                break;
            case R.id.button02:
                textVendor += "2";
                break;
            case R.id.button03:
                textVendor += "3";
                break;
            case R.id.button04:
                textVendor += "4";
                break;
            case R.id.button05:
                textVendor += "5";
                break;
            case R.id.button06:
                textVendor += "6";
                break;
            case R.id.button07:
                textVendor += "7";
                break;
            case R.id.button08:
                textVendor += "8";
                break;
            case R.id.button09:
                textVendor += "9";
                break;
            case R.id.button0cl:
                textVendor = "";
                break;
        }

        textViewVendor.setText(textVendor);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}