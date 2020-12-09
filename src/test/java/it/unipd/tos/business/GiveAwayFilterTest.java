////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.unipd.tos.business.exception.RestaurantBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.ItemType;

public class GiveAwayFilterTest {

    @Test
    public void testUserAlreadyExist() throws RestaurantBillException {
        
        GiveAwayFilter give = new GiveAwayFilter(new String[5]);

        give.checkUser("user1");

        assertEquals(true, give.checkUser("user1"));

    }

    @Test
    public void testUserNotExist() throws RestaurantBillException {

        GiveAwayFilter give = new GiveAwayFilter(new String[5]);

        assertEquals(false, give.checkUser("user1"));

    }

    @Test
    public void testUserAlreadyExist2Users() throws RestaurantBillException {

        GiveAwayFilter give = new GiveAwayFilter(new String[5]);

        give.checkUser("user1");
        give.checkUser("user2");

        assertEquals(true, give.checkUser("user1"));

    }

    @Test
    public void testHourBefore18() throws RestaurantBillException {

        GiveAwayFilter give = new GiveAwayFilter(new String[5]);
        
        User u1 = new User("matteo", "basso", "matteo@basso.com", LocalDate.of(1990, 1, 10));
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "coppa billy", 4, 1, LocalTime.of(15, 1, 10)));

        assertEquals(0, give.checkIfGiftOrder(l, u1), 0);

    }

    @Test
    public void testHourAfter19() throws RestaurantBillException {

        GiveAwayFilter give = new GiveAwayFilter(new String[5]);
        
        User u1 = new User("matteo", "basso", "matteo@basso.com", LocalDate.of(1990, 1, 10));
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "Banana Split", 4, 1, LocalTime.of(20, 1, 10)));

        assertEquals(0, give.checkIfGiftOrder(l, u1), 0);

    }

    @Test
    public void testisOrder() throws RestaurantBillException {
        
        GiveAwayFilter give = new GiveAwayFilter(new String[5]);
        
        User u1 = new User("matteo", "basso", "matteo@basso.com", LocalDate.of(2010, 1, 10));
        List<MenuItem> l = new ArrayList<MenuItem>();
        l.add(new MenuItem(ItemType.Budini, "Banana Split", 4, 1, LocalTime.of(18, 1, 10)));

        assertEquals(true, give.isGiftOrder(l, u1));

    }
}
