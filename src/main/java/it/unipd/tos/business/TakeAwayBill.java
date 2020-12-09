////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.util.List;

import it.unipd.tos.business.exception.RestaurantBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.MenuItem.ItemType;

public interface TakeAwayBill {

    static GiveAwayFilter give = new GiveAwayFilter(new String[10]);

    static double getOrderPrice(List<MenuItem> itemsOrdered, User user) throws RestaurantBillException {

        if (itemsOrdered == null) {
            throw new RestaurantBillException("ORDINE_NULL");
        }

        if (itemsOrdered.isEmpty()) {
            throw new RestaurantBillException("ORDINE_VUOTO");
        }

        if (give.isGiftOrder(itemsOrdered, user)) {
            return 0;
        }

        double tot = 0;
        int count = 0;
        int ice_count = 0;

        for (MenuItem menuItem : itemsOrdered) {
            if (menuItem.getPrice() < 0) {
                throw new RestaurantBillException("PREZZO_NEGATIVO");
            }
            if (menuItem.getQuantity() < 0) {
                throw new RestaurantBillException("QUANTITA_NEGATIVA");
            }
            tot += (menuItem.getPrice() * menuItem.getQuantity());
            if (menuItem.getItem().equals(ItemType.Gelati)) {
                ice_count++;
            }
            count += menuItem.getQuantity();
        }

        if (count > 30) {
            throw new RestaurantBillException("MASSIMO_NUMERO_ELEMENTI");
        }

        if (ice_count > 5) {
            tot -= returnMinPrice(itemsOrdered) / 2;
        }

        if (tot >= 50) {
            tot /= 1.1;
            tot = Math.floor(tot * 100) / 100;
        }

        if (tot <= 10) {
            tot += 0.5;
        }

        return tot;
    }

    static double returnMinPrice(List<MenuItem> itemsOrdered) {

        double min = itemsOrdered.get(0).getPrice();
        for (MenuItem menuItem : itemsOrdered) {
            if (menuItem.getPrice() < min) {
                min = menuItem.getPrice();
            }
        }
        return min;
    }
}
