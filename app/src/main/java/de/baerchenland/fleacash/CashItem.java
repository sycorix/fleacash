package de.baerchenland.fleacash;

import java.util.Locale;

/**
 * Created by steffen on 18.10.14.
 */
public class CashItem {

    private String _timestamp;
    private int _vendor;
    private double _amount;

    public CashItem() {

    }

    public CashItem(String timestamp, int vendor, double amount) {
        this._timestamp = timestamp;
        this._vendor = vendor;
        this._amount = amount;
    }

    public void setTimestamp(String timestamp) {
        this._timestamp = timestamp;
    }

    public String getTimestamp() {
        return this._timestamp;
    }

    public void setVendor(int vendor) {
        this._vendor = vendor;
    }

    public int getVendor() {
        return this._vendor;
    }

    public String getVendorFormated() {
        return String.format("%3d", this._vendor);
    }

    public void setAmount(double amount) {
        this._amount = amount;
    }

    public double getAmount() {
        return this._amount;
    }

    public String getAmountFormated() {
        return String.format(Locale.GERMAN, "%6.2f", this._amount);
    }

    public String getInfo() {
        return this.getVendorFormated() + ":   " + this.getAmountFormated() + "   (" + this._timestamp + " )" ;
    }

}
