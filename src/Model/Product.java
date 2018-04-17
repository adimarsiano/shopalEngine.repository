package Model;

import java.util.Date;

/**
 * Created by user on 12/04/2018.
 */
public class Product {
    private int serialNumber;
    private String productName;
    private Date date;
    private int count = 1;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getProductName() {
        return productName;
    }

    public Date getDate() {
        return date;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
