package info.androidhive.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import info.androidhive.firebase.adapters.FoodListAdapter;
import info.androidhive.firebase.adapters.HelpListAdapter;
import info.androidhive.firebase.interfaces.OrderBy;
import info.androidhive.firebase.model.Buttons;
import info.androidhive.firebase.model.Food;

/**
 * Created by Victoria on 10-12-2016.
 */

public class Help extends AppCompatActivity implements AdapterView.OnItemClickListener{

private ListView buttonList;
private ArrayList<Buttons> helps = new ArrayList<>();

@Override
protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundleObject=getIntent().getExtras();

        helps =(ArrayList<Buttons>)bundleObject.getSerializable("help");

        setContentView(R.layout.products_list);

        buttonList=(ListView)findViewById(R.id.menus_list);
        buttonList.setAdapter(new HelpListAdapter(getBaseContext(),helps));
        buttonList.setOnItemClickListener(this);
        }

@Override
public void onItemClick(AdapterView<?>parent,View view,int position,long id){

        }

}

