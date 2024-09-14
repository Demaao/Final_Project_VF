package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity

@Table(name = "purchases")
public class Purchase implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productType;
    protected String purchaseDate;
    private String paymentMethod;
    protected double pricePaid;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    protected Customer customer;


    public Purchase () {}

    public Purchase(String productType, String purchaseDate, String paymentMethod, double pricePaid, Customer customer) {
        this.productType = productType;
        this.purchaseDate = purchaseDate;
        this.paymentMethod = paymentMethod;
        this.pricePaid = pricePaid;
        this.customer = customer;
    }

    // Getters and setters
    public String getProductType() {
        return productType;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
