package test.generator.render;

import com.terapico.shuxiang.customer.Customer;
import com.terapico.shuxiang.store.Store;

public class CustomerHomePageViewModel {
    protected Store shop;
    protected Customer user;
    protected boolean hasPersonalization;
    
    public Store getShop() {
        return shop;
    }
    public void setShop(Store shop) {
        this.shop = shop;
    }
    public Customer getUser() {
        return user;
    }
    public void setUser(Customer user) {
        this.user = user;
    }
    public boolean isHasPersonalization() {
        return hasPersonalization;
    }
    public void setHasPersonalization(boolean hasPersonalization) {
        this.hasPersonalization = hasPersonalization;
    }
    
    
}
