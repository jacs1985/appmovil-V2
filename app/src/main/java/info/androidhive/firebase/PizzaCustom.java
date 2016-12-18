package info.androidhive.firebase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;

import info.androidhive.firebase.dao.DataAccessObject;
import info.androidhive.firebase.model.Bread;
import info.androidhive.firebase.model.Dessert;
import info.androidhive.firebase.model.Drink;
import info.androidhive.firebase.model.Food;
import info.androidhive.firebase.model.Meat;
import info.androidhive.firebase.model.Menu;
import info.androidhive.firebase.model.Vegetal;
import info.androidhive.firebase.singletons.RandomComboGenerator;
import info.androidhive.firebase.strategys.EarlyMorningStrategy;
import info.androidhive.firebase.strategys.LunchStrategy;
import info.androidhive.firebase.strategys.MorningStrategy;


public class PizzaCustom extends Activity implements View.OnClickListener {

    private Button meat;
    private Button chicken;
    private Button tomato;
    private Button choricillo;
    private Button olive;
    private Button mushroom;
    private Button costButton;

    private Button menuDetails;
    private Button productsDetail;
    private Button botonPrueba;
    private Menu myMenu;

    private ArrayList<Vegetal> vegetalsList = new ArrayList<>();
    private ArrayList<Meat> meatsList = new ArrayList<>();
    private ArrayList<Bread> breadsList = new ArrayList<>();
    private ArrayList<Drink> drinks = new ArrayList<>();
    private ArrayList<Dessert> desserts = new ArrayList<>();

    private TextView pizzaDetails;
    private TextView totalCostText;

    private EarlyMorningStrategy earlyMorning = new EarlyMorningStrategy();
    private LunchStrategy lunch = new LunchStrategy();
    private MorningStrategy morning = new MorningStrategy();

    private ArrayList<Food> ingredients = new ArrayList<>();
    private ArrayList<Food> productsList = new ArrayList<>();
    private ArrayList<Food> products = new ArrayList<>();

    private Food pizza = new Food("Pizza", 2000, "pizza");
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_custom);

        Bundle bundleObject = getIntent().getExtras();
        productsList = (ArrayList<Food>) bundleObject.getSerializable("products");

        meat = (Button) findViewById(R.id.meat_button);
        meat.setOnClickListener(this);

        chicken = (Button) findViewById(R.id.chicken_button);
        chicken.setOnClickListener(this);

        tomato = (Button) findViewById(R.id.tomato_button);
        tomato.setOnClickListener(this);

        mushroom = (Button) findViewById(R.id.mushroom_button);
        mushroom.setOnClickListener(this);

        olive = (Button) findViewById(R.id.olive_button);
        olive.setOnClickListener(this);

        choricillo = (Button) findViewById(R.id.choricillo_button);
        choricillo.setOnClickListener(this);

        costButton = (Button) findViewById(R.id.cost_button);
        costButton.setOnClickListener(this);

        menuDetails = (Button)findViewById(R.id.menu_details);
        menuDetails.setOnClickListener(this);

        productsDetail = (Button)findViewById(R.id.products_detail);
        productsDetail.setOnClickListener(this);

        botonPrueba = (Button)findViewById(R.id.random_combos_button);
        botonPrueba.setOnClickListener(this);

        pizzaDetails = (TextView) findViewById(R.id.pizza_details);

        totalCostText = (TextView) findViewById(R.id.total_cost);

        defaultsObjects();
        defaultMenu();

        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getName().equalsIgnoreCase("Queso")
                    || productsList.get(i).getName().equalsIgnoreCase("Jamón")) {
                ingredients.add(productsList.get(i));
            }
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void addToDetails(String newIngredient) {
        if (pizzaDetails.getText().toString().contains(newIngredient)) {
            String stringToReplace = ", " + newIngredient;
            pizzaDetails.setText(pizzaDetails.getText().toString().replace(stringToReplace, ""));
            for (int i = 0; i < ingredients.size(); i++) {
                if (ingredients.get(i).getName().equalsIgnoreCase(newIngredient)) {
                    ingredients.remove(i);
                }
            }
        } else {
            pizzaDetails.setText(pizzaDetails.getText().toString() + ", " + newIngredient);
            for (int i = 0; i < productsList.size(); i++) {
                if (productsList.get(i).getName().equalsIgnoreCase(newIngredient)) {
                    ingredients.add(productsList.get(i));
                }
            }
        }
    }

    private void calculateTotal(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if(hour>=20 ||  hour<=9){
            int costo = earlyMorning.totalCost(ingredients) +  pizza.getCost() ;
            totalCostText.setText("$"+costo);
            //totalCostText.setText("$"+earlyMorning.totalCost(ingredients)+pizza.getCost());
        }else if(hour>11 && hour<16){
            int costo = lunch.totalCost(ingredients) +  pizza.getCost() ;
            totalCostText.setText("$"+costo);
            //totalCostText.setText("$"+lunch.totalCost(ingredients)+pizza.getCost());
        }else{
            int costo = morning.totalCost(ingredients) +  pizza.getCost() ;
            totalCostText.setText("$"+costo);
            //totalCostText.setText("$"+morning.totalCost(ingredients)+pizza.getCost());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meat_button:
                addToDetails("Lomo");
                break;

            case R.id.mushroom_button:
                addToDetails("Champiñones");
                break;

            case R.id.chicken_button:
                addToDetails("Pollo");
                break;

            case R.id.tomato_button:
                addToDetails("Tomate");
                break;

            case R.id.olive_button:
                addToDetails("Aceitunas");
                break;

            case R.id.choricillo_button:
                addToDetails("Choricillo");
                break;

            case R.id.cost_button:
                calculateTotal();
                break;

            case R.id.menu_details:
                for(int i=0;i<10;i++){
                    RandomComboGenerator.getInstance().fillMenu(vegetalsList, meatsList, breadsList, drinks, desserts, myMenu);
                }
                
                Intent menusList = new Intent(PizzaCustom.this, MenuList.class);
                menusList.putExtra("obj", myMenu);
                startActivity(menusList);
                break;

            case R.id.products_detail:
                Intent productsListIntent = new Intent(PizzaCustom.this, ProductsList.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("products", products);
                productsListIntent.putExtras(bundleObject);
                startActivity(productsListIntent);
                break;

            case R.id.random_combos_button:
                RandomComboGenerator.getInstance().fillMenu(vegetalsList, meatsList, breadsList, drinks, desserts, myMenu);
                Toast.makeText(getBaseContext(),"Combos generados",Toast.LENGTH_LONG).show();
                break;
        }

    }

    private void defaultsObjects(){
        DataAccessObject jsons = new DataAccessObject();

        vegetalsList    = jsons.leerJson(getApplicationContext(),"vegetals.json");
        meatsList       = jsons.leerJson(getApplicationContext(),"meats.json");
        breadsList      = jsons.leerJson(getApplicationContext(),"breads.json");
        drinks          = jsons.leerJson(getApplicationContext(),"drinks.json");
        desserts        = jsons.leerJson(getApplicationContext(),"desserts.json");
        products        = jsons.leerJson(getApplicationContext(),"products.json");

        for (int i=0; i<vegetalsList.size(); i++){
            products.add(vegetalsList.get(i));
        }

        for (int i=0; i<meatsList.size(); i++){
            products.add(meatsList.get(i));
        }

        for (int i=0; i<breadsList.size(); i++){
            products.add(breadsList.get(i));
        }

        for (int i=0; i<drinks.size(); i++){
            products.add(drinks.get(i));
        }

        for (int i=0; i<desserts.size(); i++){
            products.add(desserts.get(i));
        }
    }

    private void defaultMenu(){
        myMenu = new Menu(1, "Default Menu", "Lorem ipsum Menu");
    }

    /*
    private void defaultsObjects() {
        DataAccessObject jsons = new DataAccessObject();

        products = jsons.leerJson(getApplicationContext(), "products.json");
    }
    */

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
  /*  public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PizzaCustom Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
*/
  /*  @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }*/
}
