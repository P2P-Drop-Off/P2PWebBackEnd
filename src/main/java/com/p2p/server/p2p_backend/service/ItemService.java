package com.p2p.server.p2p_backend.service;

import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import com.p2p.server.p2p_backend.model.Item;
import com.p2p.server.p2p_backend.model.StoreUser;
import com.p2p.server.p2p_backend.model.User;
import com.p2p.server.p2p_backend.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository){
        this.repository = repository;
    }

    public void createItem() {
        /*
            Assumption:
                1) The controller should verify C2C link with verifyMarketPlaceLink
         */
    }

    public Item getItem(String itemId) throws Exception{
        try {
            return repository.getItem(itemId);
        } catch (Exception e) {
            throw e;
        }
    }

    public String generateItemLink(StoreUser dropOffStore) {
        // Use:
        //  Called when user needs to send a transaction link to the buyer.
        //  Assumption:
        //      1) user already has been served getNearbyStores() and picks one
        //      2) user already has created the Item
        //  Future calls for Item Link should be done through other controller>service calls.
        return "";
    }

    public String generateDropOffCode(){
        return "";
    }

    public void changeItemLocation() {
        // Called when the Buyer requests Seller to change sale location (outside of app)
    }

    public void confirmIntentToBuy() {
        // Called when the Buyer requests user
    }
}
