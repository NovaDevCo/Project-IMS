import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductManager {

    // Public scanner so Main can use the same scanner (as in your Main examples)
    public final Scanner scanner = new Scanner(System.in);
    private final List<Product> products = new ArrayList<>();
    private final List<String> logs = new ArrayList<>();

    public ProductManager() {
        Product_Data();
    }

    // ---------- Authorization & Menu ----------
    public boolean entry() {
        final String PASSWORD = "Garcia";
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.print("Enter passcode to continue: ");
            String userPass = scanner.nextLine();
            if (PASSWORD.equals(userPass)) {
                System.out.println("\n-------------------------");
                System.out.println("Welcome to the system!");
                System.out.println("-------------------------");
                return true;
            } else {
                attempts++;
                if (attempts == maxAttempts) {
                    System.out.println("Too many attempts! Try again later.");
                    return false;
                } else {
                    System.out.println("Incorrect password. Please try again.");
                    System.out.println("Attempts left: " + (maxAttempts - attempts));
                }
            }
        }
        return false;
    }

    public void showMenu() {
        System.out.println("\n=== INVENTORY MENU ===");
        System.out.println("1. Add Product");
        System.out.println("2. Edit Product");
        System.out.println("3. View Products");
        System.out.println("4. Search Product");
        System.out.println("5. Delete Product");
        System.out.println("6. View Logs");
        System.out.println("7. Delete Logs");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    // ---------- CRUD & Search ----------
    public void addProduct() {
        System.out.println("\n--- Add Product ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Category: ");
        String category = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        double price;
        while (true) {
            try {
                System.out.print("Price: ");
                price = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Try again.");
            }
        }

        int stock;
        while (true) {
            try {
                System.out.print("Stock: ");
                stock = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock. Try again.");
            }
        }

        Product p = new Product(id, category, model, price, stock);
        products.add(p);
        logs.add(timeStamp() + " Product Added: ID=" + id + ", Model=" + model);
        System.out.println("Product added successfully.");
    }

    public void editProduct() {
        System.out.print("\nEnter product ID to edit: ");
        String id = scanner.nextLine();
        Product p = findById(id);
        if (p == null) {
            System.out.println("Product ID not found!.");
            return;
        }

        System.out.println("\nEditing product:");
        System.out.println(p);

        boolean done = false;
        while (!done) {
            System.out.println("Choose field to edit:");
            System.out.println("1. ID  2. Category  3. Model  4. Price  5. Stock  6. Qty sold  7. Cancel");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("New ID: ");
                    String newId = scanner.nextLine();
                    String oldId = p.getId();
                    p.setId(newId);
                    logs.add(timeStamp() + " ID Changed: " + oldId + " -> " + newId);
                    break;
                case "2":
                    System.out.print("New Category: ");
                    String newCategory = scanner.nextLine();
                    String oldCat = p.getCategory();
                    p.setCategory(newCategory);
                    logs.add(timeStamp() + " Category Changed: " + oldCat + " -> " + newCategory);
                    break;
                case "3":
                    System.out.print("New Model: ");
                    String newModel = scanner.nextLine();
                    String oldModel = p.getModel();
                    p.setModel(newModel);
                    logs.add(timeStamp() + " Model Changed: " + oldModel + " -> " + newModel);
                    break;
                case "4":
                    try {
                        System.out.print("New Price: ");
                        double newPrice = Double.parseDouble(scanner.nextLine());
                        double oldPrice = p.getPrice();
                        p.setPrice(newPrice);
                        logs.add(timeStamp() + " Price Changed: " + oldPrice + " -> " + newPrice);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price input.");
                    }
                    break;
                case "5":
                    try {
                        System.out.print("New Stock: ");
                        int newStock = Integer.parseInt(scanner.nextLine());
                        int oldStock = p.getStock();
                        p.setStock(newStock);
                        logs.add(timeStamp() + " Stock Changed: " + oldStock + " -> " + newStock);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid stock input.");
                    }
                    break;
                case "6":
                    try {
                        System.out.print("New Qty sold (total sold): ");
                        int newQty = Integer.parseInt(scanner.nextLine());
                        if (newQty < 0) {
                            System.out.println("Qty sold cannot be negative.");
                            break;
                        }
                        int currentQty = p.getQtySold();
                        int diff = newQty - currentQty;
                        // If increasing qty sold, ensure enough stock
                        if (diff > 0) {
                            if (p.getStock() < diff) {
                                System.out.println("Not enough stock to increase qty sold. Operation cancelled.");
                                break;
                            } else {
                                p.sell(diff); // adjusts stock and qtySold
                                logs.add(timeStamp() + " Qty Sold Increased: +" + diff + " for ID=" + p.getId());
                            }
                        } else if (diff < 0) {
                            // Decreasing qty sold (restock difference)
                            int restore = -diff;
                            p.setQtySold(newQty);
                            p.setStock(p.getStock() + restore);
                            logs.add(timeStamp() + " Qty Sold Decreased: " + diff + " for ID=" + p.getId());
                        } else {
                            System.out.println("No change to qty sold.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity input.");
                    }
                    break;
                case "7":
                    System.out.println("Edit cancelled.");
                    logs.add(timeStamp() + " Edit cancelled for ID=" + p.getId());
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (!choiceEquals(choice, "7")) {
                System.out.print("Edit another field? (y/n): ");
                String yn = scanner.nextLine().trim().toLowerCase();
                if (!yn.equals("y")) done = true;
            }
        }

        System.out.println("Product updated.");
    }

    public void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("\nNo products available.");
            return;
        }
        System.out.println("\n--- Product Inventory ---");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    // ProductManager.java - Updated searchProduct method
    // ProductManager.java - Updated searchProduct method
    public void searchProduct() {
        System.out.println("\nSearch by: 1. ID  2. Product Name/Category"); // Updated prompt
        System.out.print("Choice: ");
        String option = scanner.nextLine();

        if ("1".equals(option)) {
            System.out.print("Enter ID: ");
            String id = scanner.nextLine();
            Product p = findById(id);
            if (p != null) {
                System.out.println("\n--- Product Found ---");
                System.out.println(p);
            } else {
                System.out.println("No product found with that ID.");
            }
        } else if ("2".equals(option)) {
            System.out.print("Enter product name/category (or fragment): ");
            String name = scanner.nextLine().toLowerCase();
            List<Product> matches = new ArrayList<>();

            for (Product p : products) {
                // FIX: Search in either the Model OR the Category
                boolean modelMatch = p.getModel().toLowerCase().contains(name);
                boolean categoryMatch = p.getCategory().toLowerCase().contains(name);

                if (modelMatch || categoryMatch) {
                    matches.add(p);
                }
            }

            if (matches.isEmpty()) {
                System.out.println("No products found matching that name/category.");
            } else {
                System.out.println("\n--- Matches ---");
                for (Product m : matches) System.out.println(m);
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public void deleteProduct() {
        System.out.print("\nEnter the ID of the product to delete: ");
        String id = scanner.nextLine();
        Product p = findById(id);
        if (p == null) {
            System.out.println("No product found with that ID.");
            return;
        }

        System.out.println("\n--- Product to delete ---");
        System.out.println(p);
        System.out.print("Are you sure you want to delete this product? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("y")) {
            products.remove(p);
            logs.add(timeStamp() + " Product Deleted: ID=" + id + ", Model=" + p.getModel());
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // ---------- Logs ----------
    public void viewLogs() {
        if (logs.isEmpty()) {
            System.out.println("\nNo logs found.");
            return;
        }
        System.out.println("\n--- Activity Logs ---");
        for (String l : logs) {
            System.out.println(l);
        }
    }

    public void deletelogs() {
        if (logs.isEmpty()) {
            System.out.println("No logs to delete.");
            return;
        }
        System.out.print("Are you sure you want to delete all logs? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("y")) {
            logs.clear();
            System.out.println("Logs deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // ---------- Helpers ----------
    private Product findById(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    private String timeStamp() {
        return "[" + java.time.LocalDateTime.now().toString() + "]";
    }

    private boolean choiceEquals(String a, String b) {
        return a != null && a.equals(b);
    }

    // ---------- Sample data (hardcoded) ----------
    private void Product_Data() {
        List<Product> sampleProducts = ProductData.sampleProducts();
        products.addAll(sampleProducts);
        logs.add(timeStamp() + " Sample data loaded (" + sampleProducts.size() + " items).");
    }


}
