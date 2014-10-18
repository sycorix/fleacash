package de.baerchenland.fleacash;

import java.util.Locale;

/**
 * Created by steffen on 18.10.14.
 */
public class CashItem {

    private String _id;
    private int _vendor;
    private double _amount;

    public CashItem() {

    }

    public CashItem(String id, int vendor, double amount) {
        this._id = id;
        this._vendor = vendor;
        this._amount = amount;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getId() {
        return this._id;
    }

    public void setVendor(int vendor) {
        this._vendor = vendor;
    }

    public int getVendor() {
        return this._vendor;
    }

    public String getVendorFormated() {
        return String.format("%2d", this._vendor);
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

}
