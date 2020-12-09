////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.unipd.tos.business.exception.RestaurantBillException;
import it.unipd.tos.model.MenuItem.ItemType;

public class MenuItemTest {

    @Test
    public void getHourNull() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", 4, 1, null));

        try {
            l.get(0).getHour_order();
        } catch (RestaurantBillException e) {
            assertEquals("HOUR_NULL", e.getException());
        }
    }
    
    @Test
    public void getHour() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", 4, 1, LocalTime.of(18, 18, 21)));

        try {
            assertEquals(LocalTime.of(18, 18, 21), l.get(0).getHour_order());
        } catch (RestaurantBillException e) {         
        }
    }

    @Test
    public void getItemNull() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(null, "coppa nafta", 4, 1, LocalTime.of(18, 18, 21)));

        try {
            l.get(0).getItem();
        } catch (RestaurantBillException e) {
            assertEquals("ITEM_NULL", e.getException());
        }
    }


    @Test
    public void getItem() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Gelati, "coppa nafta", 4, 1, LocalTime.of(18, 18, 21)));

        try {
            assertEquals(ItemType.Gelati, l.get(0).getItem());
        } catch (RestaurantBillException e) {
        }
    }

    @Test
    public void getProduct() {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", 4, 1, LocalTime.of(18, 18, 21)));

        try {
            assertEquals("coppa nafta", l.get(0).getName());
        } catch (RestaurantBillException e) {
        }
    }

    @Test
    public void getNullName() {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, null, 4, 1, LocalTime.of(18, 18, 21)));

        try {
            l.get(0).getName();
        } catch (RestaurantBillException e) {
            assertEquals("NOME_PROD_NULL", e.getException());
        }
    }
}
