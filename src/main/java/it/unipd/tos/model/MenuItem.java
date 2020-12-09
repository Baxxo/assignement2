////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import java.time.LocalTime;

import it.unipd.tos.business.exception.RestaurantBillException;

public class MenuItem {

    public enum ItemType {
        Gelati, Budini, Bevande;
    }

    private ItemType item;
    private String name;

    private double price;
    private int quantity;
    private LocalTime hour_order;

    public MenuItem(ItemType type, String name, double price, int qt, LocalTime d) {
        item = type;
        this.name = name;
        this.price = price;
        quantity = qt;
        hour_order = d;
    }

    public LocalTime getHour_order() throws RestaurantBillException {
        if (hour_order == null) {
            throw new RestaurantBillException("HOUR_NULL");
        }
        return hour_order;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemType getItem() throws RestaurantBillException {
        if (item == null) {
            throw new RestaurantBillException("ITEM_NULL");
        }
        return item;
    }

    public double getPrice() {
        return price;
    }

    public String getName() throws RestaurantBillException {
        if (name == null) {
            throw new RestaurantBillException("NOME_PROD_NULL");
        }
        return name;
    }
}
