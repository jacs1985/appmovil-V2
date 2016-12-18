package info.androidhive.firebase;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ImageChooserListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import info.androidhive.firebase.dao.DataAccessObject;
import info.androidhive.firebase.managers.GalleryManager;
import info.androidhive.firebase.managers.TimerManager;
import info.androidhive.firebase.model.Bread;
import info.androidhive.firebase.model.Buttons;
import info.androidhive.firebase.model.Dessert;
import info.androidhive.firebase.model.Drink;
import info.androidhive.firebase.model.Food;
import info.androidhive.firebase.model.Meat;
import info.androidhive.firebase.model.Menu;
import info.androidhive.firebase.model.Vegetal;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;

public class MenuAppActivity extends AppCompatActivity implements View.OnClickListener, ImageChooserListener {

    //private Button botonPrueba;
    //private Button menuDetails;
    //private Button productsDetail;
    private Button galleryButton;
    private Button loadLocalDataButton;
    private Button menuButton;
    private Button configuracion;
    private Button dailyInfoButton;

    private RelativeLayout menuBackGround;
    private Menu myMenu;


    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    private ArrayList<Vegetal> vegetalsList = new ArrayList<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    private ArrayList<Meat> meatsList = new ArrayList<>();
    private ArrayList<Bread> breadsList = new ArrayList<>();
    private ArrayList<Drink> drinks = new ArrayList<>();
    private ArrayList<Dessert> desserts = new ArrayList<>();


    private ArrayList<Food> products = new ArrayList<>();
    private ArrayList<Buttons> help = new ArrayList<>();

    IntentFilter s_intentFilter = new IntentFilter();
   // private int connectionCounter = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
    private GoogleApiClient client;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_app);
        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        myRef.setValue("Hello, World!");
       // refvegetallist = new FirebaseDatabase("https://apprenescoffe.firebaseio.com/vegetals/vegetals");
        //botonPrueba = (Button)findViewById(R.id.random_combos_button);
        //botonPrueba.setOnClickListener(this);

        //menuDetails = (Button)findViewById(R.id.menu_details);
        //menuDetails.setOnClickListener(this);

        //productsDetail = (Button)findViewById(R.id.products_detail);
        //productsDetail.setOnClickListener(this);

        galleryButton = (Button) findViewById(R.id.gallery_button);
        galleryButton.setOnClickListener(this);

        loadLocalDataButton = (Button) findViewById(R.id.load_localdata_button);
        loadLocalDataButton.setOnClickListener(this);

        menuButton = (Button) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(this);

        configuracion = (Button) findViewById(R.id.configuracion);
        configuracion.setOnClickListener(this);

        dailyInfoButton = (Button) findViewById(R.id.daily_info_button);
        dailyInfoButton.setOnClickListener(this);


        defaultsObjects();
        defaultMenu();

        String packageName = getApplicationContext().getPackageName() + ".ACTION";

        (TimerManager.getInstance()).launchClock(getApplicationContext(), packageName, 1);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            try {
                if (data.getData() != null) {
                    Uri uriPicture = data.getData();
                    menuBackGround.setBackground(uriPictureToBitmap(uriPicture));
                }
            } catch (NullPointerException nPE) {
                nPE.getMessage();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.random_combos_button:
                RandomComboGenerator.getInstance().fillMenu(vegetalsList, meatsList, breadsList, drinks, desserts, myMenu);
                Toast.makeText(getBaseContext(),"Combos generados",Toast.LENGTH_LONG).show();
                break;*/

            /*case R.id.menu_details:
                Intent menusList = new Intent(MenuAppActivity.this, MenuList.class);
                menusList.putExtra("obj", myMenu);
                startActivity(menusList);
                break;*/

            /*case R.id.products_detail:
                Intent productsListIntent = new Intent(MenuAppActivity.this, ProductsList.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("products", products);
                productsListIntent.putExtras(bundleObject);
                startActivity(productsListIntent);
                break;*/

            case R.id.gallery_button:
                GalleryManager.getInstance(this, this).selectImageFromGallery();
                break;

            case R.id.menu_button:
                Intent pizzaIntent = new Intent(MenuAppActivity.this, PizzaCustom.class);
                Bundle bundleProducts = new Bundle();
                bundleProducts.putSerializable("products", products);
                pizzaIntent.putExtras(bundleProducts);
                startActivity(pizzaIntent);
                break;

            case R.id.configuracion:
                Intent configuracionIntent = new Intent(MenuAppActivity.this, MainActivity.class);
              /*  Bundle bundleconection = new Bundle();
                bundleconection.putSerializable("conection",connectionButton);
                bundleconection.putAll(bundleconection);*/
                /*connectionCounter++;
                if (connectionCounter % 2 == 0) {
                    connectionIntent.putExtra("connectionBoolean", true);
                } else {
                    connectionIntent.putExtra("connectionBoolean", false);
                }*/
                startActivity(configuracionIntent);
                break;

            case R.id.daily_info_button:

                Intent dailyInfo  = new Intent(MenuAppActivity.this, Help.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("help", help);
                dailyInfo .putExtras(bundleObject);
                startActivity(dailyInfo);
                break;
                /*Intent productsListIntent = new Intent(MenuAppActivity.this, ProductsList.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable("products", products);
                productsListIntent.putExtras(bundleObject);
                startActivity(productsListIntent);
                break;*/

        }
    }


    private void defaultsObjects() {

        DataAccessObject jsons = new DataAccessObject();

        vegetalsList    = jsons.leerJson(getApplicationContext(),"vegetals.json");
        meatsList       = jsons.leerJson(getApplicationContext(),"meats.json");
        breadsList      = jsons.leerJson(getApplicationContext(),"breads.json");
        drinks          = jsons.leerJson(getApplicationContext(),"drinks.json");
        desserts        = jsons.leerJson(getApplicationContext(),"desserts.json");
        products        = jsons.leerJson(getApplicationContext(),"products.json");
        help            = jsons.leerJson(getApplicationContext(),"help.json");
       //help            = jsons.leerHelp(getApplicationContext(),"help.json");


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
        for (int i=0;i<help.size();i++){
           products.add(help.get(i));
        }
    }

    private void defaultMenu() {

        myMenu = new Menu(1, "Default Menu", "Lorem ipsum Menu");
    }

    private Drawable uriPictureToBitmap(Uri selectedImage) {
        Drawable resultDrawable = null;
        try {
            InputStream imageStream = MenuAppActivity.this.getContentResolver().openInputStream(selectedImage);
            Bitmap returnedImageBitmap = BitmapFactory.decodeStream(imageStream);
            resultDrawable = new BitmapDrawable(getResources(), returnedImageBitmap);
            if (returnedImageBitmap.getHeight() > 2000) {
                int width = Math.round(returnedImageBitmap.getWidth() / 4);
                int height = Math.round(returnedImageBitmap.getHeight() / 4);
                resultDrawable = new BitmapDrawable(Bitmap.createScaledBitmap(returnedImageBitmap, width, height, false));
            }
        } catch (FileNotFoundException | OutOfMemoryError | NullPointerException e) {
            e.printStackTrace();
        }
        return resultDrawable;
    }

    @Override
    public void onImageChosen(ChosenImage chosenImage) {
    }

    @Override
    public void onError(String s) {
    }

    @Override
    public void onImagesChosen(ChosenImages chosenImages) {
    }

//    /**
//     * ATTENTION: This was auto-generated to implement the App Indexing API.
//     * See https://g.co/AppIndexing/AndroidStudio for more information.
//     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("MenuApp Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
}
