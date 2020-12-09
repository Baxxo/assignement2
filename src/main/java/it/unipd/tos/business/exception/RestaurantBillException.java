////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business.exception;

public class RestaurantBillException extends Exception {

    private String type;

    public RestaurantBillException(String i) {
        if (i.equals("ORDINE_NULL")) {
            type = "ORDINE_NULL";
        }

        if (i.equals("ORDINE_VUOTO")) {
            type = "ORDINE_VUOTO";
        }

        if (i.equals("PREZZO_NEGATIVO")) {
            type = "PREZZO_NEGATIVO";
        }

        if (i.equals("QUANTITA_NEGATIVA")) {
            type = "QUANTITA_NEGATIVA";
        }

        if (i.equals("ITEM_NULL")) {
            type = "ITEM_NULL";
        }

        if (i.equals("MASSIMO_NUMERO_ELEMENTI")) {
            type = "MASSIMO_NUMERO_ELEMENTI";
        }

        if (i.equals("NOME_PROD_NULL")) {
            type = "NOME_PROD_NULL";
        }

        if (i.equals("HOUR_NULL")) {
            type = "HOUR_NULL";
        }

        if (i.equals("DATE_NULL")) {
            type = "DATE_NULL";
        }

        if (i.equals("MAIL_NULL")) {
            type = "MAIL_NULL";
        }
    }

    public String getException() {
        return type;
    }

}
