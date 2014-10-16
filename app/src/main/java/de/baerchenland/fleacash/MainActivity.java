package de.baerchenland.fleacash;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.GridView;

public class MainActivity extends Activity {

    public String textAmount = "";
    public String textVendor = "";
    TextView textViewAmount;
    TextView textViewVendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAmount = (TextView)findViewById(R.id.textViewAmount);
        textViewVendor = (TextView)findViewById(R.id.textViewVendor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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