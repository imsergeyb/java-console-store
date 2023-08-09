package businessLogic;

import java.util.ArrayList;
import java.util.List;

import static facade.Facade.truncateValue;

public class Item {
    private final String itemID;
    private String itemName;
    private double unitPrice;
    private ArrayList<Review> reviews;

    public Item(String itemID, String itemName, double unitPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.reviews = new ArrayList<>();
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemId() {
        return itemID;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public List<Review> getReviews(){return reviews;}

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override

    public String toString() {
        return getItemId() + ": " + getItemName() + ". " + String.format("%.2f", truncateValue(getUnitPrice(), 2)) + " SEK";
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public int numOfReviews() {
        return reviews.size();
    }

    public Review getReview(int index) {
        return reviews.get(index);
    }

    @Override

    public boolean equals(Object anotherObject) {
        if (anotherObject == this) {
            return true;
        }
        if (anotherObject == null) {
            return false;
        }
        if (anotherObject instanceof Item) {
            return true;
        }
        if (anotherObject.getClass() == Item.class) {
            Item anotherItem = (Item) anotherObject;
            return getItemId().equals(anotherItem.getItemId());
        } else {
            return false;
        }
    }
}

