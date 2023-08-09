package businessLogic;
import static facade.Facade.truncateValue;

public class Transaction {

    private final String itemID;
    private final int amount;
    private final double purchasePrice;

    public Transaction(String itemID, int amount, double purchasePrice) {
        this.itemID = itemID;
        this.amount = amount;
        this.purchasePrice = purchasePrice ;
    }

    public String getItemID(){return this.itemID;}
    public int getAmount() {
        return this.amount;
    }
    public double getPurchasePrice() {
        return this.purchasePrice;
    }

    @Override
    public String toString() {
        return getItemID() + ": " + getAmount() + " item(s). " + String.format("%.2f", truncateValue(getPurchasePrice(), 2)) + " SEK";
    }
}
