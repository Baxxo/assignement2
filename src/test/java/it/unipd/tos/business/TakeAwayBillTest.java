////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import org.junit.Test;

import it.unipd.tos.business.exception.RestaurantBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.ItemType;

public class TakeAwayBillTest {

    private User u1 = new User("matteo", "basso", "matteo@basso.com", LocalDate.of(1990, 1, 10));

    @Test
    public void getOrderNull() throws RestaurantBillException {
        List<MenuItem> l = null;

        try {
            TakeAwayBill.getOrderPrice(l, u1);
        } catch (RestaurantBillException e) {
            assertEquals("ORDINE_NULL", e.getException());
        }
    }

    @Test
    public void getOrderEmpty() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();

        try {
            TakeAwayBill.getOrderPrice(l, u1);
        } catch (RestaurantBillException e) {
            assertEquals("ORDINE_VUOTO", e.getException());
        }
    }

    @Test
    public void getOrderAccept() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", 4, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Bevande, "coppa ciao", 2, 2, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Budini, "coppa boby", 20, 1, LocalTime.of(18, 18, 21)));

        assertEquals(TakeAwayBill.getOrderPrice(l, u1), 38, 0.001);
    }

    @Test
    public void getItemNull() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(null, "coppa nafta", 4, 5, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Bevande, "coppa ciao", 2, 3, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa billy", 14, 10, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Budini, "coppa boby", 24, 40, LocalTime.of(18, 18, 21)));

        try {
            TakeAwayBill.getOrderPrice(l, u1);
        } catch (RestaurantBillException e) {
            assertEquals("ITEM_NULL", e.getException());
        }
    }

    @Test
    public void getPriceNegativeQuantityPositive() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", -4, 5, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Bevande, "coppa ciao", 2, 3, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa billy", -14, 10, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Budini, "coppa boby", 5, 40, LocalTime.of(18, 18, 21)));

        try {
            TakeAwayBill.getOrderPrice(l, u1);
        } catch (RestaurantBillException e) {
            assertEquals("PREZZO_NEGATIVO", e.getException());
        }
    }

    @Test
    public void getPriceNegativeQuantityNegative() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", -4, -5, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Bevande, "coppa ciao", 2, 3, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa billy", -14, 10, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Budini, "coppa boby", 5, 40, LocalTime.of(18, 18, 21)));

        try {
            TakeAwayBill.getOrderPrice(l, u1);
        } catch (RestaurantBillException e) {
            assertEquals("PREZZO_NEGATIVO", e.getException());
        }
    }

    @Test
    public void getQuantityNegativePricePosity() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", 4, 5, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Bevande, "coppa ciao", 2, 3, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa billy", 14, -10, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Budini, "coppa boby", 5, 40, LocalTime.of(18, 18, 21)));

        try {
            TakeAwayBill.getOrderPrice(l, u1);
        } catch (RestaurantBillException e) {
            assertEquals("QUANTITA_NEGATIVA", e.getException());
        }
    }

    @Test
    public void getMoreThanFiveIcecream() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Gelati, "banana split", 5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", 5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Bevande, "coppa ciao", 5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa billy", 5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa boby", 4, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa licky", 5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa caramello", 5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa cioccolato", 5, 1, LocalTime.of(18, 18, 21)));

        assertEquals(TakeAwayBill.getOrderPrice(l, u1), 37, 0.001);
    }
}
