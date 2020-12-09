////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import it.unipd.tos.business.exception.RestaurantBillException;

public class UserTest {

    @Test
    public void getDate() {
        User u2 = new User("matteo", "basso", "matteo@basso.com", LocalDate.of(1990, 1, 10));

        try {
            assertEquals(LocalDate.of(1990, 1, 10), u2.getDate());
        } catch (RestaurantBillException e) {
        }
    }

    @Test
    public void getMail() {
        User u2 = new User("matteo", "basso", "matteo@basso.com", LocalDate.of(1990, 1, 10));

        try {
            assertEquals("matteo@basso.com", u2.getMail());
        } catch (RestaurantBillException e) {
        }
    }

    @Test
    public void getNullDate() {
        User u2 = new User("matteo", "basso", "matteo@basso.com", null);

        try {
            u2.getDate();
        } catch (RestaurantBillException e) {
            assertEquals("DATE_NULL", e.getException());
        }
    }

    @Test
    public void getNullMail() {
        User u2 = new User("matteo", "basso", null, LocalDate.of(1990, 1, 10));

        try {
            u2.getMail();
        } catch (RestaurantBillException e) {
            assertEquals("MAIL_NULL", e.getException());
        }
    }
}
