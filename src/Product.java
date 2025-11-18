import java.text.NumberFormat;
import java.util.Locale;

public class Product {
    private String id;
    private String category;
    private String model;
    private double price;
    private int stock;       // current stock (after sales)
    private int qtySold;
    private double salesValue;
    private String status;

    public Product(String id, String category, String model, double price, int stock) {
        this.id = id;
        this.category = category;
        this.model = model;
        this.price = price;
        this.stock = stock;
        this.qtySold = 0;
        this.salesValue = 0.0;
        updateStatus();
    }

    // Getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        updateSalesValue();
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        updateStatus();
    }

    public int getQtySold() {
        return qtySold;
    }

    public void setQtySold(int qtySold) {
        this.qtySold = qtySold;
        updateSalesValue();
    }

    public double getSalesValue() {
        return salesValue;
    }

    public String getStatus() {
        return status;
    }

    // Increase sold quantity by delta (positive = new sales)
    public boolean sell(int delta) {
        if (delta < 0) return false;
        if (delta > stock) return false;
        stock -= delta;
        qtySold += delta;
        updateSalesValue();
        updateStatus();
        return true;
    }

    // Helper updates
    private void updateSalesValue() {
        this.salesValue = this.qtySold * this.price;
    }

    private void updateStatus() {
        this.status = (this.stock <= 0) ? "No stock available" : "Active";
    }

    private String formatCurrency(double value) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        return nf.format(value).replace("PHP", "₱").replace("php", "₱");
    }

    @Override
    public String toString() {
        return  "ID:           " + id + "\n" +
                "Category:     " + category + "\n" +
                "Model:        " + model + "\n" +
                "Price:        " + formatCurrency(price) + "\n" +
                "Stock:        " + stock + "\n" +
                "Qty sold:     " + qtySold + "\n" +
                "Sales value:  " + formatCurrency(salesValue) + "\n" +
                "Status:       " + status + "\n";
    }
}
