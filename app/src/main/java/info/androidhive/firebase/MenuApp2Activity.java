package info.androidhive.firebase;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import info.androidhive.firebase.dao.DataAccessObject;
import info.androidhive.firebase.managers.TimerManager;
import info.androidhive.firebase.model.Buttons;
import info.androidhive.firebase.model.Food;

public class MenuApp2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button loadLocalDataButton;
    private Button configuracion;
    private Button dailyInfoButton;
    private ArrayList<Food> products = new ArrayList<>();
    private ArrayList<Buttons> help = new ArrayList<>();
    private int connectionCounter = 0;
    IntentFilter s_intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_app2);
        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);

        loadLocalDataButton = (Button) findViewById(R.id.connection_info_container);
        loadLocalDataButton.setOnClickListener(this);

        configuracion = (Button) findViewById(R.id.configuracion);
        configuracion.setOnClickListener(this);

        dailyInfoButton = (Button) findViewById(R.id.daily_info_button);
        dailyInfoButton.setOnClickListener(this);

        defaultsObjects();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        (TimerManager.getInstance()).stopClock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        (TimerManager.getInstance()).stopClock();
    }

    @Override
    protected void onStop() {
        super.onStop();
        (TimerManager.getInstance()).stopClock();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.connection_info_container:
                Intent load_ventas = new Intent(MenuApp2Activity.this, UserConnection.class);
                //connectionCounter++;
               /* if (connectionCounter % 2 == 0) {
                    load_ventas.putExtra("connectionBoolean", false);
                }*/
                startActivity(load_ventas);
                break;

            case R.id.configuracion:
                Intent configuracionIntent = new Intent(MenuApp2Activity.this, MainActivity.class);
                /*connectionCounter++;
                if (connectionCounter % 2 == 0) {
                    connectionIntent.putExtra("connectionBoolean", true);
                } else {
                    connectionIntent.putExtra("connectionBoolean", false);
                }*/
                startActivity(configuracionIntent);
                break;

            case R.id.daily_info_button:

                Intent dailyInfo  = new Intent(MenuApp2Activity.this, Help.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("help", help);
                dailyInfo .putExtras(bundleObject);
                startActivity(dailyInfo);
                break;


        }
    }

    private void defaultsObjects() {

        DataAccessObject jsons = new DataAccessObject();

        help            = jsons.leerJson(getApplicationContext(),"help.json");
        //help            = jsons.leerHelp(getApplicationContext(),"help.json");

        for (int i=0;i<help.size();i++){
            products.add(help.get(i));
        }
    }

}
