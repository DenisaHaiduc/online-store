package domain;


public class ProductBuilder implements I_EntityBuilder<Product> {
    @Override
    public Product createEntity(String line) {
        String[] tokens = line.split(",");
        if (tokens.length != 4) {
            throw new IllegalArgumentException("Linia nu are formatul corect pentru un produs: " + line);
        }
        int id = Integer.parseInt(tokens[0].trim());
        String name = tokens[1].trim();
        String category = tokens[2].trim();
        int price = Integer.parseInt(tokens[3].trim());

        return new Product(id, name, category, price);
    }
}
