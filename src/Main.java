public class Main {

    // Create ONE ProductManager so Main can use it
    private final ProductManager manager = new ProductManager();

    public static void main(String[] args) {
        Main program = new Main();
        program.start();
    }

    // Start program
    public void start() {

        // Login first
        if (!manager.entry()) {   // entry() was moved inside ProductManager
            return;
        }

        // Main loop
        while (true) {

            // Show menu
            manager.showMenu();

            // Get user choice
            String choice = manager.scanner.nextLine();

            // Switch (clean version you requested)
            switch (choice) {

                case "1":
                    manager.addProduct();
                    break;

                case "2":
                    manager.editProduct();
                    break;

                case "3":
                    manager.viewProducts();
                    break;

                case "4":
                    manager.searchProduct();
                    break;

                case "5":
                    manager.deleteProduct();
                    break;

                case "6":
                    manager.viewLogs();
                    break;

                case "7":
                    manager.deletelogs();
                    break;

                case "8":
                    System.out.println("Exiting program. Thank you.");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
