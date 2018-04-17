package Model;

import MyScanner.Barcode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 12/04/2018.
 */
public class Engine {

////////////////////////////////////////////// TODO GOOGLE CONNECTION
    public boolean signIn(String emailAddress) {
        boolean res = true; //// to integrate with google
        //TODO CHECK RETURN THE RESPOND FROM GOOGLE API
        if(res){
            updateUserInfoInDb();
        }
        return true;
    }

    //TODO
    private void updateUserInfoInDb() {
    }
    //TODO CHECK HOW TO DO SO
    public void stayLogIn(){

    }
    public void CreateNewUser() {
        //adi marsiano
    }







    //adimarsianoooonn
    ////////////////////////////////////////////// TODO SCAN ACTIVITY CONNECTION
    public void scanProductWithUpdate() {
        int serialNumber = Barcode.scanBarcode(new Object());
        int bar;
        Product product = findProductDetails(serialNumber);
        printProductDetailsFromBarcode(product); //TODO TALK ABOUT THE OPTIONS
        //TODO RETURN JSON TO THE UI
    }

    public void continuityScan() {
        int serialNumber = Barcode.scanBarcode(new Object());
        Product product = findProductDetails(serialNumber);
        //TODO CREATE JSON (OR UPDATE AN EXISTING)
    }
    private Product findProductDetails(int serialNumber) {
        //implement date and name....
        Date date = new Date();
        String name = "Milk";
        Product product = new Product();
        product.setSerialNumber(serialNumber);
       // product.setDate(11-00-1992);
        product.setProductName(name);
        return product;
    }

    public void printProductDetailsFromBarcode(Product product) {
        System.out.println("Serial Number: " + product.getSerialNumber());
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Product Date: " + product.getDate());
    }
    public void updateUi() {
        //TODO RETURN LIST OF JSONS TO UI
    }
    public boolean checkIsItOutOfDate(Date date) throws ParseException {
        Date todayDate;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        todayDate = dateFormatter.parse(dateFormatter.format(new Date() ));
        return dateFormatter.format(todayDate).equals(dateFormatter.format(date));
    }
////////////////////////////////////////////////////////////////////////////////////////////
    public void increaseAmount() {

    }

    public void deleteList(ShoppingList shoppingList) {

    }

    public void updateList(ShoppingList shoppingList) {

    }

    public void logOut() {

    }



}
