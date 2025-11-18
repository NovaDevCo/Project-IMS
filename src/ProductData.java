import java.util.ArrayList;
import java.util.List;

public class ProductData {

    private ProductData() {
        // Utility class
    }

    public static List<Product> sampleProducts() {
        List<Product> samples = new ArrayList<>();

        samples.add(new Product("001", "Electric Guitars", "HILLS The Next HN4 S/SS", 32999.00, 3));
        samples.add(new Product("002", "Electric Guitars", "HILLS The Next HZ7 S/GB", 46199.00, 8));
        samples.add(new Product("003", "Electric Guitars", "HILLS The Next HN6 G/UV", 50999.00, 6));
        samples.add(new Product("004", "Electric Guitars", "HILLS The Next HN5 S/MEG", 35999.00, 10));
        samples.add(new Product("005", "Electric Guitars", "HILLS The Next HN5 S/BK", 35999.00, 11));
        samples.add(new Product("006", "Electric Guitars", "Luxars S-G62", 13999.00, 7));
        samples.add(new Product("007", "Electric Guitars", "Sawtooth ES", 8999.00, 8));
        samples.add(new Product("008", "Electric Guitars", "Smiger L-G1", 6499.00, 14));
        samples.add(new Product("009", "Electric Guitars", "Enya Nova Go SP1", 16999.00, 15));
        samples.add(new Product("010", "Electric Guitars", "Enya - NEXTG2 Basic", 44399.00, 9));

        return samples;
    }
}
