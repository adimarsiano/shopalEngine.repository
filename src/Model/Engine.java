// TODO: marked as //**

package Model;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.Iterator;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

/**
 * Created by user on 12/04/2018.
 */

//////////////////////////////////////////////// TODO GOOGLE CONNECTION
//    public boolean signIn(String emailAddress) {
//        boolean res = true; //// to integrate with google
//        //TODO CHECK RETURN THE RESPOND FROM GOOGLE API
//        if(res){
//            updateUserInfoInDb();
//        }
//        return true;
//    }
//
//    //TODO
//    private void updateUserInfoInDb() {
//    }
//    //TODO CHECK HOW TO DO SO
//    public void stayLogIn(){
//
//    }
//    public void CreateNewUser() {
//        //adi marsiano
//        //s;ss
//    }
//////////////////////////////////////////////////



public class Engine {
    MongoDatabase database;

    public Engine(){
        String a ="a";
        // Connection to mongoDB
        MongoClient mongoClient = new MongoClient( "Localhost" , 27017 );
        // Access to shopalDB
        database = mongoClient.getDatabase("shopal");

        //** not here just for experience
        productScan("1", "1");
    }


    private void productScan(String barcode, String stockId) {
        String productId = "1";
        // ** Str productId = getProductId_Barcode(barcode); // working with barcode API

        MongoCollection<Document> stocks = database.getCollection("stocks");

        try{
            updateStock(productId, stockId, stocks);
            updateShopList(productId, stockId, stocks);
        }
        catch(Exception e){
            System.out.println(e.getMessage()); //** massage to client
        }
    }


    private void updateStock(String productId, String stockId, MongoCollection<Document> stocks) throws Exception {
        int available;
        String availableStr;

        // querying the document with stockId
        Document doc = stocks.find(eq("stockId", stockId)).first();

        if(doc == null){
            throw new Exception("Invalid stock"); // stock not exist in DB
        }

        // getting the available quantity of products
        available = getAvailableQuantityOfProduct(productId, stockId, stocks);
        if(available == 0){
            throw new Exception("Product not in stock"); // product not exist in stock
        }

        // update stock : available = available -1
        availableStr = Integer.toString(--available);
        stocks.updateOne(and(eq("stockId", stockId),eq("list.productId", productId) )
                , new Document("$set", new Document("list.$.available", availableStr)));
    }

    private int getAvailableQuantityOfProduct(String productId, String stockId, MongoCollection<Document> stocks){
        String quantityStr;
        int quantity;

        //  find the value of the available quantity (return: "list" of document type)
        FindIterable<Document> res = stocks.find(eq("stockId", stockId))
                .projection(and(elemMatch("list", Document.parse("{ productId: " + "\"" + productId + "\"" + "}")),
                        fields(include("list.available"), excludeId())  ));

        //  using iterator to extract the document
        Iterator iterator = res.iterator();
        Document doc = (Document) iterator.next();
        quantityStr = doc.toString();

        // extract digits from the string -> eventually the available quantity
        quantityStr = quantityStr.replaceAll("[^0-9]", "");
        quantity = Integer.parseInt(quantityStr);

        return quantity;
    }


    private void updateShopList(String productId, String stockId, MongoCollection<Document> stocks){
        MongoCollection<Document> shopList = database.getCollection("shopList");
        //** TODO

    }


//////////////////////////////////////////////// TODO SCAN ACTIVITY CONNECTION
//    public void scanProductWithUpdate() {
//        int serialNumber = Barcode.scanBarcode(new Object());
//        int bar;
//        Product product = findProductDetails(serialNumber);
//        printProductDetailsFromBarcode(product); //TODO TALK ABOUT THE OPTIONS
//        //TODO RETURN JSON TO THE UI
//    }
//
//    public void continuityScan() {
//        int serialNumber = Barcode.scanBarcode(new Object());
//        Product product = findProductDetails(serialNumber);
//        //TODO CREATE JSON (OR UPDATE AN EXISTING)
//    }

//    private Product findProductDetails(int serialNumber) {
//        //implement date and name....
//        Date date = new Date();
//        String name = "Milk";
//        Product product = new Product();
//        product.setSerialNumber(serialNumber);
//       // product.setDate(11-00-1992);
//        product.setProductName(name);
//        return product;
//    }
//
//    public void printProductDetailsFromBarcode(Product product) {
//        System.out.println("Serial Number: " + product.getSerialNumber());
//        System.out.println("Product Name: " + product.getProductName());
//        System.out.println("Product Date: " + product.getDate());
//    }
//    public void updateUi() {
//        //TODO RETURN LIST OF JSONS TO UI
//    }
//    public boolean checkIsItOutOfDate(Date date) throws ParseException {
//        Date todayDate;
//        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//        todayDate = dateFormatter.parse(dateFormatter.format(new Date() ));
//        return dateFormatter.format(todayDate).equals(dateFormatter.format(date));
//    }
//////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////////////
//    public void increaseAmount() {
//
//    }
//
//    public void deleteList(ShoppingList shoppingList) {
//
//    }
//
//    public void updateList(ShoppingList shoppingList) {
//
//    }
//
//    public void logOut() {
//
//    }
/////////////////////////////////////////////////////////////////////////////////////


}
