package info.androidhive.firebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.firebase.R;
import info.androidhive.firebase.model.Buttons;
import info.androidhive.firebase.model.Food;

/**
 * Created by Victoria on 10-12-2016.
 */

public class HelpListAdapter extends ArrayAdapter<Buttons> implements ListAdapter {

    private Context context;
    private ArrayList<Buttons> helps;

    public HelpListAdapter(Context context, ArrayList<Buttons> helps) {
        super(context, 0, helps);
        this.context = context;
        this.helps = helps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_products_list, parent, false);
        Buttons helps = getItem(position);
        TextView name = (TextView) row.findViewById(R.id.combo_text);
        name.setText(helps.getName()+":"+helps.getType());
        return row;
    }

}
