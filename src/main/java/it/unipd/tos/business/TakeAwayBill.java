////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.util.List;

import it.unipd.tos.business.exception.RestaurantBillException;
import it.unipd.tos.model.MenuItem;

public interface TakeAwayBill {

    static double getOrderPrice(List<MenuItem> itemsOrdered, User user) throws RestaurantBillException {

        if (itemsOrdered == null) {
            throw new RestaurantBillException("ORDINE_NULL");
        }
        if (itemsOrdered.isEmpty()) {
            throw new RestaurantBillException("ORDINE_VUOTO");
        }

        double tot = 0;

        for (MenuItem menuItem : itemsOrdered) {
            if (menuItem.getPrice() < 0) {
                throw new RestaurantBillException("PREZZO_NEGATIVO");
            }
            if (menuItem.getQuantity() < 0) {
                throw new RestaurantBillException("QUANTITA_NEGATIVA");
            }
        }

        return tot;
    }
}
