package com.redhat.bpms.examples.mortgage.model;

public class ShoppingCartItem  {

    public double price;
    public int quantity;
    public double promoSavings;
    public Product product;

    public String toString() {
        return ("productid: " + product.itemId + " quan: " + quantity + " price: " + price);
    }

}
