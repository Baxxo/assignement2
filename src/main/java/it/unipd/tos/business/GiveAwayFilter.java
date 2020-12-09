////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

import it.unipd.tos.business.exception.RestaurantBillException;
import it.unipd.tos.model.MenuItem;

public class GiveAwayFilter {

    static int totGift = 0;

    static String[] userGift = new String[10];

    static int totUser = 0;

    GiveAwayFilter(String[] userGift) {
        GiveAwayFilter.userGift = userGift;
        totUser = 0;
        totGift = 0;
    }

    static void setup(String[] userGift) {
        GiveAwayFilter.userGift = userGift;
        totUser = 0;
        totGift = 0;
    }

    boolean isGiftOrder(List<MenuItem> itemsOrdered, User user) throws RestaurantBillException {

        if (totGift >= 10) {
            return false;
        }

        if (calculateAge(user.getDate(), LocalDate.now()) < 19) {
            if (checkIfGiftOrder(itemsOrdered, user) == 0) {
                if (!checkUser(user.getMail())) {
                    totGift++;
                    return true;
                }
            }
        }

        return false;
    }

    boolean checkUser(String user) {

        for (int i = 0; i < totUser; i++) {
            if (userGift[i] != null) {
                if (userGift[i].equals(user)) {
                    return true;
                }
            }
        }

        if (totUser < 10) {
            userGift[totUser++] = user;
        }

        return false;
    }

    double checkIfGiftOrder(List<MenuItem> order, User user) throws RestaurantBillException {
        for (MenuItem menuItem : order) {
            if (menuItem.getHour_order().isBefore(LocalTime.of(18, 0, 0))
                    || menuItem.getHour_order().isAfter(LocalTime.of(19, 0, 0))) {
                return 0;
            }
        }
        return 0;
    }

    int calculateAge(LocalDate birthDate, LocalDate currentDate) {

        return Period.between(birthDate, currentDate).getYears();
    }

}
