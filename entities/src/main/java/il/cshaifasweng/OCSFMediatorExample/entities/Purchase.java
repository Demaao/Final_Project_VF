package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productType;
    private String purchaseDate;
    private String paymentMethod;
    private double pricePaid;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public Purchase() {}

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
