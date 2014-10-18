package de.baerchenland.fleacash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by steffen on 18.10.14.
 */
public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList data;
    private static LayoutInflater inflater = null;

    public ListAdapter(Context context, ArrayList data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.list_row, null);
        // get object at position
        CashItem cashItem = (CashItem) data.get(position);
        // fill list item
        TextView textNo = (TextView) vi.findViewById(R.id.textNo);
        textNo.setText(cashItem.getVendorFormated());
        TextView textItem = (TextView) vi.findViewById(R.id.textItem);
        textItem.setText(cashItem.getAmountFormated());
        return vi;
    }

}