package demo.dagger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TanVOD on Jan, 2022
 */
public class Inventory<T> {
    private Map<T, Integer> inventory = new HashMap<T, Integer>();

    public Inventory(){
    }

    public int getQuantity(T item){
        Integer value = inventory.get(item);
        return value == null? 0 : value ;
    }

    public void add(T item){
        int count = inventory.get(item) == null ? 0: inventory.get(item);
        inventory.put(item, count+1);
    }

    public void deduct(T item) {
        if (hasItem(item)) {
            int count = inventory.get(item);
            inventory.put(item, count - 1);
        }
    }

    public boolean hasItem(T item){
        return getQuantity(item) > 0;
    }

    public void clear(){
        inventory.clear();
    }

    public void put(T item, int quantity) {
        inventory.put(item, quantity);
    }


    public List<T> getAll() {
        return new ArrayList(inventory.keySet());
    }

    public boolean hasItemWithQty(T item, int quantity) {
        return getQuantity(item) >= quantity;
    }
}
