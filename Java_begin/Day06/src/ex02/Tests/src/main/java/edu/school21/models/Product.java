package edu.school21.numbers;

import java.util.Objects;

public class Product {
    private Long    identifier;
    private String  name;
    private int     price;

    public Product(Long identifier, String name, int price) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;

        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }

        Product data = (Product) object;

        return (identifier.equals(data.identifier) && name.equals(data.name)
                && price == data.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, price);
    }

    @Override
    public String toString() {
        return "Product: [identifier=" + identifier
                + ", name=" + name
                + ", price=" + price
                + "]";
    }
}
