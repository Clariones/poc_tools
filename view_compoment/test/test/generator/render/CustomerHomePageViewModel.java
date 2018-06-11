package test.generator.render;

import com.terapico.shuxiang.customer.Customer;
import com.terapico.shuxiang.store.Store;

public class CustomerHomePageViewModel {
    protected Store store;
    protected Customer user;
    protected boolean hasPersonalization;
    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
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
