package info.androidhive.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.vlonjatg.android.apptourlibrary.AppTour;
import com.vlonjatg.android.apptourlibrary.MaterialSlide;

/**
 * @author Vlonjat Gashi (vlonjatg)
 */
public class FotoAyuda extends AppTour {

    @Override
    public void init(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Sacar el action bar
        getSupportActionBar().hide();
        int secondColor = Color.parseColor("#c0c0c0");
        int thirdColor = Color.parseColor("#c0c0c0");
        int forthColor = Color.parseColor("#c0c0c0");
        int customSlideColor = Color.parseColor("#c0c0c0");
    //009866
        //Create pre-created fragments
        String bienvenido = getResources().getString(R.string.bienvenido);
        String bienvenido_sub = getResources().getString(R.string.bienvenido_sub);
        String mapa_ayuda = getResources().getString(R.string.mapa_ayuda);
        String mapa_ayuda_sub = getResources().getString(R.string.mapa_ayuda_sub);
        String listo = getResources().getString(R.string.listo);
        String listo_sub = getResources().getString(R.string.listo_sub);

        //Create pre-created fragments
        Fragment secondSlide = MaterialSlide.newInstance(R.drawable.cafe, bienvenido,bienvenido_sub, Color.BLACK, Color.BLACK);
        Fragment thirdSlide = MaterialSlide.newInstance(R.drawable.cafe,mapa_ayuda,mapa_ayuda_sub, Color.BLACK, Color.BLACK);
       // Fragment forthSlide = MaterialSlide.newInstance(R.drawable.cafe, camara_ayuda, camara_ayuda_sub, Color.BLACK, Color.BLACK);
        Fragment fiveSlide = MaterialSlide.newInstance(R.drawable.cafe, listo, listo_sub, Color.BLACK, Color.BLACK);

        //Add slides
        //addSlide(firstSlide, firstColor);
        addSlide(secondSlide, secondColor);
        addSlide(thirdSlide, thirdColor);
        //addSlide(forthSlide, forthColor);
        addSlide(fiveSlide, customSlideColor);

        //Custom slide
        //addSlide(new CustomSlide(), customSlideColor);

        //Customize tour
        String aceptar = getResources().getString(R.string.aceptar);
        String finalizar = getResources().getString(R.string.finalizar);

        setSkipButtonTextColor(Color.BLACK);
        setSkipText(finalizar);
        setNextButtonColorToWhite();
        setDoneButtonTextColor(Color.BLACK);
        setDoneText(aceptar);
    }

    @Override
    public void onSkipPressed() {
        //Toast.makeText(this, "Skip", Toast.LENGTH_SHORT).show();
        //Do something after clicking Skip button.
        //E.x: Go to the sign up slide.
        setCurrentSlide(3);
    }

    @Override
    public void onDonePressed() {
        //Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        String estado = "true";
        SharedPreferences sp2 = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp2.edit();
        edit.putString("estado", estado);
        edit.commit();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        return;
    }


}
