////////////////////////////////////////////////////////////////////
// Matteo Basso 1227134
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.time.LocalDate;

import it.unipd.tos.business.exception.RestaurantBillException;

public class User {

    private String name;
    private String surname;
    private String mail;
    private LocalDate date;

    public User(String name, String surname, String mail, LocalDate date) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.date = date;
    }

    public LocalDate getDate() throws RestaurantBillException {
        if(date == null) {
            throw new RestaurantBillException("DATE_NULL");
        }
        return date;
    }

    public String getMail() throws RestaurantBillException {
        if(mail == null) {
            throw new RestaurantBillException("MAIL_NULL");
        }
        return mail;
    }
}
