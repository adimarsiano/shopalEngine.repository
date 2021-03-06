package Model;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

public class Stock{
    private  MongoCollection<Document> stocks;

    /** Public: **/
    // connection to DB collection : stocks
    public Stock(MongoDatabase database) {
        stocks = database.getCollection("stocks");
    }

    // Add a new product to user's "stock" | UI: no need to update shopList (limit = 0)
    public void addProduct(String stockId, String productId) throws Exception {

        if (!is_stockId_existInDB(stockId)) {
            throw new Exception("Invalid stock"); // stock not exist in DB
        }

        //check if the productId already exist in user stock
        if (is_productId_existInDB(productId, stockId)) {
            throw new Exception("product already exist in stock"); // productId exist in DB
        }

        // update stock with new productId -> default: available = 1, limit = 0
        stocks.updateOne(eq("stockId", stockId)
                , new Document("$push", new Document("list", new Document("productId", productId)
                        .append("available", "1").append("limit", "0"))));
    }

    // update exist product after we scan it (throwing to the can) -> available = available -1
    public void updateProductAfterScan(String stockId, String productId) throws Exception {
        int available;
        String availableStr;

        // check validation of stockId, productId:
        if (!is_stockId_existInDB(stockId)) {
            throw new Exception("Invalid stock"); // stock not exist in DB
        }

        if (!is_productId_existInDB(productId, stockId)) {
            throw new Exception("product not exist in stock"); // productId not exist in DB
        }

        // getting the available quantity of products
        available = getValueOfProductFromStock(productId, stockId, "available");
        if (available == 0) {
            throw new Exception("Product not in stock"); // product not exist in stock
        }

        // update product : available = available - 1
        if(--available < 0){
            available = 0;
        }
        availableStr = Integer.toString(available);
        stocks.updateOne(and(eq("stockId", stockId), eq("list.productId", productId))
                , new Document("$set", new Document("list.$.available", availableStr)));
    }

    // update (new or exist) product's quantities -> available, limit
    public void updateProduct(String stockId, String productId, String available, String limit) throws Exception {
        if (!is_stockId_existInDB(stockId)) {
            throw new Exception("Invalid stock"); // error: stock not exist in DB
        }

        if (!is_productId_existInDB(productId, stockId)) {
            addProduct(stockId, productId);     // product not in stock -> add it
        }

        // update product's quantities
        stocks.updateOne(and(eq("stockId", stockId), eq("list.productId", productId))
                , new Document("$set", new Document("list.$.available", available).append("list.$.limit", limit)));
    }


    /** Private: **/
    private boolean is_stockId_existInDB(String stockId) {
        boolean res = true;
        Document doc = stocks.find(eq("stockId", stockId)).first();

        if (doc == null) {
            res = false;
        }

        return res;
    }

    private boolean is_productId_existInDB(String stockId, String productId) throws Exception {
        boolean res = true;
        Document doc = stocks.find(and(eq("stockId", stockId), eq("list.productId", productId))).first();

        if (doc == null) {
            res = false;
        }

        return res;
    }

    private int getValueOfProductFromStock(String stockId, String productId, String fieldName) {
        String quantityStr;
        int quantity;

        // find the document with the desire value (return: "list" of document type)
        FindIterable<Document> queryResDoc = stocks.find(eq("stockId", stockId))
                .projection(and(elemMatch("list", Document.parse("{ productId: " + "\"" + productId + "\"" + "}")),
                        fields(include("list." + fieldName), excludeId())));

        // extract the value from specific document
        quantityStr = documentToStringValue(queryResDoc);
        quantity = Integer.parseInt(quantityStr);

        return quantity;
    }

    private String documentToStringValue(FindIterable<Document> queryResDoc) {
        String quantity;

        //  using iterator to extract the document
        Iterator iterator = queryResDoc.iterator();
        Document doc = (Document) iterator.next();
        quantity = doc.toString();

        // extract digits from the string -> eventually String of the value
        quantity = quantity.replaceAll("[^0-9]", "");

        return quantity;
    }


}
