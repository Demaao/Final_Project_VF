package il.cshaifasweng.OCSFMediatorExample.entities;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_customer")
    protected Buyer customer;

    protected double cost;
    protected long creditCardNum;
    protected String transactionTime;


    public Purchase () {}

    public Purchase (Buyer customer, double cost, String transactionTime, long creditCardNum) {
        this.customer = customer;
        this.cost= cost;
        this.creditCardNum = creditCardNum;
        this.transactionTime = transactionTime;

    }

    public Buyer getCustomer() {
        return customer;
    }

    public void setCustomer(Buyer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(long creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

 }
