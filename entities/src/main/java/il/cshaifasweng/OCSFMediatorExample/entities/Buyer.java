package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Buyers")
public class Buyer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String firstName;
    protected String lastName;
    protected int buyerId;
    protected long creditCardNum;
    protected String EMail;

    //one buyer can have multiple purchases associated with him
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    protected List<Purchase> purchases;

    public Buyer() {
         purchases = new ArrayList<Purchase>();
    }

    public Buyer(String firstName, String lastName, int buyerId, long creditCardNum, String EMail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.buyerId = buyerId;
        this.EMail = EMail;
        this.creditCardNum = creditCardNum;
        purchases = new ArrayList<Purchase>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public long getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(long creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(Purchase purchases) {
        this.purchases.add(purchases);
    }

}
