package Model;

import java.util.Date;

/**
 * Created by user on 12/04/2018.
 */
public class Product {
    private int serialNumber;
    private String name;
    private Date date;
    private int count = 1;

    public int getCount() {
        return count;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setName(String productName) {
        this.name = productName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
