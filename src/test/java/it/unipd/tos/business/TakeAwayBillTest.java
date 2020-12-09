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

        assertEquals(37, TakeAwayBill.getOrderPrice(l, u1), 0.001);
    }

    @Test
    public void tenPercentDiscountOverFiftyEuroOrder() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", 15, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Bevande, "coppa ciao", 15, 2, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Bevande, "coppa boby", 8, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Budini, "coppa licky", 15, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Bevande, "coppa caramello", 15, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "coppa cioccolato", 10, 1, LocalTime.of(18, 18, 21)));

        assertEquals(111.81, TakeAwayBill.getOrderPrice(l, u1), 0.001);
    }

    @Test
    public void tenPercentAndFiftyPercentDiscountOverFiftyEuroOrder() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 1, LocalTime.of(18, 18, 21)));// 20
        l.add(new MenuItem(ItemType.Budini, "coppa nafta", 15, 1, LocalTime.of(18, 18, 21)));// 15
        l.add(new MenuItem(ItemType.Bevande, "coppa ciao", 15, 2, LocalTime.of(18, 18, 21)));// 30
        l.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 18, 21)));// 10
        l.add(new MenuItem(ItemType.Bevande, "coppa boby", 8, 1, LocalTime.of(18, 18, 21)));// 8
        l.add(new MenuItem(ItemType.Budini, "coppa licky", 15, 1, LocalTime.of(18, 18, 21)));// 15
        l.add(new MenuItem(ItemType.Bevande, "coppa caramello", 15, 1, LocalTime.of(18, 18, 21)));// 15
        l.add(new MenuItem(ItemType.Gelati, "coppa cioccolato", 10, 1, LocalTime.of(18, 18, 21)));// 10
        l.add(new MenuItem(ItemType.Gelati, "coppa cioccolato", 5, 1, LocalTime.of(18, 18, 21)));// 5
        l.add(new MenuItem(ItemType.Gelati, "coppa cioccolato", 5, 1, LocalTime.of(18, 18, 21)));// 5
        l.add(new MenuItem(ItemType.Gelati, "coppa cioccolato", 2, 1, LocalTime.of(18, 18, 21)));// 1

        assertEquals(121.81, TakeAwayBill.getOrderPrice(l, u1), 0.001);
    }

    @Test
    public void orderMoreThan30Elements() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        for (int i = 0; i < 40; i++) {
            l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 1, LocalTime.of(18, 18, 21)));
        }

        try {
            TakeAwayBill.getOrderPrice(l, u1);
        } catch (RestaurantBillException e) {
            assertEquals("MASSIMO_NUMERO_ELEMENTI", e.getException());
        }
    }

    @Test
    public void orderMoreThan30ElementsOfOneType() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 40, LocalTime.of(18, 18, 21)));

        try {
            TakeAwayBill.getOrderPrice(l, u1);
        } catch (RestaurantBillException e) {
            assertEquals("MASSIMO_NUMERO_ELEMENTI", e.getException());
        }
    }

    @Test
    public void orderMoreThan30ElementsWith2ElemtsOfOneItem() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        for (int i = 0; i < 30; i++) {
            l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 1, LocalTime.of(18, 18, 21)));
        }
        l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 2, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 2, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 2, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 2, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 2, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 20, 2, LocalTime.of(18, 18, 21)));

        try {
            TakeAwayBill.getOrderPrice(l, u1);
        } catch (RestaurantBillException e) {
            assertEquals("MASSIMO_NUMERO_ELEMENTI", e.getException());
        }
    }

    @Test
    public void orderLessThan10Item() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Gelati, "banana split", 2, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 2, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 2, 1, LocalTime.of(18, 18, 21)));

        assertEquals(6.5, TakeAwayBill.getOrderPrice(l, u1), 0.001);
    }

    @Test
    public void orderLessThan10ItemDiscount50Percent() throws RestaurantBillException {
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Gelati, "banana split", 1.5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 1, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 1.5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 1.5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 1.5, 1, LocalTime.of(18, 18, 21)));
        l.add(new MenuItem(ItemType.Gelati, "banana split", 1.5, 1, LocalTime.of(18, 18, 21)));

        assertEquals(8.5, TakeAwayBill.getOrderPrice(l, u1), 0.001);
    }

    @Test
    public void notGiveAway() throws RestaurantBillException {

        GiveAwayFilter.setup(new String[10]);

        List<MenuItem> l1 = new ArrayList<MenuItem>();
        l1.add(new MenuItem(ItemType.Budini, "coppa boby", 20, 1, LocalTime.of(18, 35, 21)));
        assertEquals(20, TakeAwayBill.getOrderPrice(l1, u1), 0.001);

    }

    @Test
    public void giveAway10Order() throws RestaurantBillException {
        
        GiveAwayFilter.setup(new String[10]);

        User u2 = new User("luca", "martini", "luca@martini.it", LocalDate.of(2010, 5, 4));
        User u3 = new User("gianni", "rodari", "gianni@rodari.it", LocalDate.of(2015, 7, 2));
        User u4 = new User("franco", "pasati", "franco@pasati.it", LocalDate.of(2016, 7, 2));
        User u5 = new User("luigi", "righetti", "luigi@righetti.it", LocalDate.of(2017, 7, 2));
        User u6 = new User("marco", "girondi", "marco@girondi.it", LocalDate.of(2002, 12, 31));
        User u7 = new User("francesco", "girondi", "francesco@girondi.it", LocalDate.of(2010, 12, 31));
        User u8 = new User("paolo", "girondi", "paolo@girondi.it", LocalDate.of(2003, 12, 31));
        User u9 = new User("pino", "girondi", "pino@girondi.it", LocalDate.of(2004, 12, 31));
        User u10 = new User("silvano", "girondi", "silvano@girondi.it", LocalDate.of(2005, 12, 31));
        User u11 = new User("teresa", "girondi", "teresa@girondi.it", LocalDate.of(2006, 12, 31));
        User u12 = new User("paola", "girondi", "paola@girondi.it", LocalDate.of(2007, 12, 31));

        List<MenuItem> l1 = new ArrayList<MenuItem>();
        l1.add(new MenuItem(ItemType.Budini, "coppa boby", 20, 1, LocalTime.of(18, 35, 21)));
        assertEquals(20, TakeAwayBill.getOrderPrice(l1, u1), 0.001);

        List<MenuItem> l2 = new ArrayList<MenuItem>();
        l2.add(new MenuItem(ItemType.Bevande, "coppa ciao", 2, 2, LocalTime.of(18, 46, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l2, u2), 0.001);

        List<MenuItem> l3 = new ArrayList<MenuItem>();
        l3.add(new MenuItem(ItemType.Bevande, "coppa ciao", 2, 2, LocalTime.of(18, 46, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l3, u3), 0.001);

        List<MenuItem> l4 = new ArrayList<MenuItem>();
        l4.add(new MenuItem(ItemType.Budini, "coppa nafta", 4, 1, LocalTime.of(18, 58, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l4, u4), 0.001);

        List<MenuItem> l5 = new ArrayList<MenuItem>();
        l5.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 00, 10)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l5, u5), 0.001);

        List<MenuItem> l6 = new ArrayList<MenuItem>();
        l6.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 16, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l6, u6), 0.001);

        List<MenuItem> l7 = new ArrayList<MenuItem>();
        l7.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 25, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l7, u7), 0.001);

        List<MenuItem> l8 = new ArrayList<MenuItem>();
        l8.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 25, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l8, u8), 0.001);

        List<MenuItem> l9 = new ArrayList<MenuItem>();
        l9.add(new MenuItem(ItemType.Budini, "coppa boby", 20, 1, LocalTime.of(18, 35, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l9, u9), 0.001);

        List<MenuItem> l10 = new ArrayList<MenuItem>();
        l10.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 34, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l10, u10), 0.001);

        List<MenuItem> l11 = new ArrayList<MenuItem>();
        l11.add(new MenuItem(ItemType.Budini, "coppa boby", 20, 1, LocalTime.of(18, 50, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l11, u11), 0.001);

        List<MenuItem> l12 = new ArrayList<MenuItem>();
        l12.add(new MenuItem(ItemType.Budini, "coppa boby", 20, 1, LocalTime.of(18, 50, 21)));
        assertEquals(20, TakeAwayBill.getOrderPrice(l12, u12), 0.001);

        List<MenuItem> l13 = new ArrayList<MenuItem>();
        l13.add(new MenuItem(ItemType.Budini, "coppa boby", 20, 1, LocalTime.of(18, 50, 21)));
        assertEquals(20, TakeAwayBill.getOrderPrice(l13, u12), 0.001);

    }

    @Test
    public void giveAway10OrderOf10Order() throws RestaurantBillException {

        GiveAwayFilter.setup(new String[10]);

        User u2 = new User("luca", "martini", "luca@martini.it", LocalDate.of(2010, 5, 4));
        User u3 = new User("gianni", "rodari", "gianni@rodari.it", LocalDate.of(2015, 7, 2));
        User u4 = new User("franco", "pasati", "franco@pasati.it", LocalDate.of(2016, 7, 2));
        User u5 = new User("luigi", "righetti", "luigi@righetti.it", LocalDate.of(2017, 7, 2));
        User u6 = new User("marco", "girondi", "marco@girondi.it", LocalDate.of(2002, 12, 31));
        User u7 = new User("francesco", "girondi", "francesco@girondi.it", LocalDate.of(2010, 12, 31));
        User u8 = new User("paolo", "girondi", "paolo@girondi.it", LocalDate.of(2003, 12, 31));
        User u9 = new User("pino", "girondi", "pino@girondi.it", LocalDate.of(2004, 12, 31));
        User u10 = new User("silvano", "girondi", "silvano@girondi.it", LocalDate.of(2005, 12, 31));
        User u11 = new User("teresa", "girondi", "teresa@girondi.it", LocalDate.of(2006, 12, 31));

        List<MenuItem> l2 = new ArrayList<MenuItem>();
        l2.add(new MenuItem(ItemType.Bevande, "coppa ciao", 2, 2, LocalTime.of(18, 46, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l2, u2), 0.001);

        List<MenuItem> l3 = new ArrayList<MenuItem>();
        l3.add(new MenuItem(ItemType.Bevande, "coppa ciao", 2, 2, LocalTime.of(18, 46, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l3, u3), 0.001);

        List<MenuItem> l4 = new ArrayList<MenuItem>();
        l4.add(new MenuItem(ItemType.Budini, "coppa nafta", 4, 1, LocalTime.of(18, 58, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l4, u4), 0.001);

        List<MenuItem> l5 = new ArrayList<MenuItem>();
        l5.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 00, 10)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l5, u5), 0.001);

        List<MenuItem> l6 = new ArrayList<MenuItem>();
        l6.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 16, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l6, u6), 0.001);

        List<MenuItem> l7 = new ArrayList<MenuItem>();
        l7.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 25, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l7, u7), 0.001);

        List<MenuItem> l8 = new ArrayList<MenuItem>();
        l8.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 25, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l8, u8), 0.001);

        List<MenuItem> l9 = new ArrayList<MenuItem>();
        l9.add(new MenuItem(ItemType.Budini, "coppa boby", 20, 1, LocalTime.of(18, 35, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l9, u9), 0.001);

        List<MenuItem> l10 = new ArrayList<MenuItem>();
        l10.add(new MenuItem(ItemType.Gelati, "coppa billy", 10, 1, LocalTime.of(18, 34, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l10, u10), 0.001);

        List<MenuItem> l11 = new ArrayList<MenuItem>();
        l11.add(new MenuItem(ItemType.Budini, "coppa boby", 20, 1, LocalTime.of(18, 50, 21)));
        assertEquals(0, TakeAwayBill.getOrderPrice(l11, u11), 0.001);

    }
}
