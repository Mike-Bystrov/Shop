package database;

public class Product {
    public static int id = 1;
    public String name;
    public int number;

    public Product(String aName) {
        id++;
        name = aName;
    }
}
