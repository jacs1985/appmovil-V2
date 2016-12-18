package info.androidhive.firebase.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import info.androidhive.firebase.interfaces.CostCalculation;


public class Combo  implements CostCalculation, Serializable {

    private int comboId;
    private Vegetal [] vegetales = new Vegetal[2];
    private Meat meat;
    private Bread bread;
    private int imageId;
    private Calendar comboDate;
    private int totalCost;


    public Combo(int id, Vegetal[] vegetales, Meat meat, Bread bread) {
        this.comboId = id;
        this.vegetales = vegetales;
        this.meat = meat;
        this.bread = bread;
    }

    public void setComboDate(Calendar date){
        this.comboDate = date;
    }

    public Calendar getComboDate(){ return comboDate;}

    public int getComboId(){
        return comboId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Vegetal[] getVegetales() {
        return vegetales;
    }

    public Meat getMeat() {
        return meat;
    }

    public Bread getBread() {
        return bread;
    }

    public String getComboComponents(){
        Calendar c = Calendar.getInstance();
        //int seconds = c.get(Calendar.SECOND);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        String components = "Combo #"+getComboId()+" Componentes: ";
        for (int i =0; i<getVegetales().length; i++){
            String separator = " + ";
            if(i==getVegetales().length-1){
                separator="";
            }
            components = components+getVegetales()[i].getName()+separator;
        }
        if(hour>=8 &&  hour<12){
            return components;
        }
        else if(hour>=12 &&  hour<18){
            return components+" + "+getMeat().getName();
        }
        else if(hour>=18 &&  hour<22){
            return components+" + "+getMeat().getName() +" + "+getBread().getName();
        }



        return components+" + "+getMeat().getName() +" + "+getBread().getName();
    }

    @Override
    public int calculateCost() {
        Calendar c = Calendar.getInstance();
        //int seconds = c.get(Calendar.SECOND);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Log.v("hora",String.valueOf(hour));

        //totalCost = getVegetales()[0].getCost() + getVegetales()[1].getCost() + getBread().getCost() + getMeat().getCost();
/*
        if(hour>=8 &&  hour<12) {

            return (int)(totalCost*0.001);
        }
        else if(hour>=12 &&  hour<18) {

            return (int)(totalCost*0.5);
        }
        else if(hour>=18 &&  hour<22) {

            return (int)(totalCost*5.5);
        }
        Log.v("calculateCost","last return");
        */
        if(hour>=8 &&  hour<12){
            totalCost = getVegetales()[0].getCost() + getVegetales()[1].getCost();
        }
        else if(hour>=12 &&  hour<18){
            totalCost = getVegetales()[0].getCost() + getVegetales()[1].getCost() + getMeat().getCost();
        }
        else if(hour>=18 &&  hour<24) {
            totalCost = getVegetales()[0].getCost() + getVegetales()[1].getCost() + getBread().getCost() + getMeat().getCost();
        }

        return totalCost;
    }

}
