package demo.dagger;

import demo.exception.NotFullPaidException;
import demo.exception.NotSufficientChangeException;
import demo.exception.SoldOutException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by TanVOD on Jan, 2022
 */
public class SimpleMachine implements Machine {
    private Inventory<Coin> cashInventory;
    private Inventory<Item> itemInventory;
    private Promotion promotion;

    private long totalSales;
    private Item currentItem;
    private long currentBalance;
    private List<Coin> listCoinsInserted = new ArrayList<Coin>();


    @Inject
    public SimpleMachine(Inventory<Coin> cashInventory, Inventory<Item> itemInventory, Promotion promotion) {
        this.cashInventory = cashInventory;
        this.itemInventory = itemInventory;
        this.promotion = promotion;
    }

    @Override
    public long selectItemAndGetPrice(Item item) {
        if (itemInventory.hasItem(item)) {
            currentItem = item;
            return currentItem.getPrice();
        }
        throw new SoldOutException("Sold Out, Please buy another item");
    }

    @Override
    public void insertCoin(Coin coin) {
        currentBalance = currentBalance + coin.getDenomination();
        listCoinsInserted.add(coin);
        cashInventory.add(coin);
    }

    @Override
    public Bucket<List<Item>, List<Coin>> collectItemAndChange() {
        List<Item> item = collectItem();
        totalSales = totalSales + currentItem.getPrice();

        List<Coin> change = collectChange();

        return new Bucket<List<Item>, List<Coin>>(item, change);
    }

    private List<Item> collectItem() throws NotSufficientChangeException,
            NotFullPaidException {
        if (isFullPaid()) {
            List<Item> list = new ArrayList<Item>();
            promotion.addItemAndCaculate(currentItem);
            itemInventory.deduct(currentItem);
            list.add(currentItem);

            if(promotion.getRemainBudget() >= currentItem.getPrice()
                    && promotion.getRateItem(currentItem)==100
                    && itemInventory.hasItem(currentItem)){

                int remainBudget = promotion.getRemainBudget() - currentItem.getPrice();
                promotion.setRemainBudget(remainBudget);
                itemInventory.deduct(currentItem);
                promotion.resetRateItem(currentItem);

                list.add(currentItem);
            }

            return list;
        }
        long remainingBalance = currentItem.getPrice() - currentBalance;
        throw new NotFullPaidException("Price not full paid, remaining : ",
                remainingBalance);
    }


    private List<Coin> collectChange() {
        long changeAmount = currentBalance - currentItem.getPrice();
        List<Coin> change = getChange(changeAmount);
        updateCashInventory(change);
        currentBalance = 0;
        currentItem = null;
        listCoinsInserted.clear();
        return change;
    }

    @Override
    public List<Coin> refund() {
        updateCashInventory(listCoinsInserted);
        currentBalance = 0;
        currentItem = null;
        return listCoinsInserted;
    }

    private boolean isFullPaid() {
        if (currentBalance >= currentItem.getPrice()) {
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        cashInventory.clear();
        itemInventory.clear();
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;

    }

    private void updateCashInventory(List<Coin> change) {
        for (Coin c : change) {
            cashInventory.deduct(c);
        }
    }

    public long getTotalSales() {
        return totalSales;
    }

    private List<Coin> getChange(long amount) throws NotSufficientChangeException {
        List<Coin> changes = new ArrayList<Coin>();
        Inventory<Coin> changeInventory = new Inventory<Coin>();
        if (amount > 0) {
            long remaining = amount;
            while (remaining > 0) {
                boolean isContinue = false;
                for (Coin c : Coin.values()) {
                    if (remaining >= c.getDenomination()
                            && cashInventory.hasItemWithQty(c, changeInventory.getQuantity(c) + 1)) {
                        remaining -= c.getDenomination();
                        changeInventory.add(c);
                        isContinue = true;
                        break;
                    }
                }
                if (!isContinue) {
                    break;
                }
            }
            if (remaining != 0) {
                throw new NotSufficientChangeException("Not sufficient change, please try another product!");
            }

            for (Coin c : changeInventory.getAll()) {
                for (int i = 0; i < changeInventory.getQuantity(c); i++) {
                    changes.add(c);
                }
            }
        }
        return changes;
    }


    public void nextDay() {
        promotion.nextDay();
    }

    public int getWinRate() {
        return promotion.getWinRate();
    }
    public int getRemainBudget() {
        return promotion.getRemainBudget();
    }

    public int getRateItem(Item item) {
        return promotion.getRateItem(item);
    }

    public List<Item> getHistItems() {
        return promotion.getHistItems();
    }


    public void setCashInventory(Inventory<Coin> cashInventory) {
        this.cashInventory = cashInventory;
    }

    public void setItemInventory(Inventory<Item> itemInventory) {
        this.itemInventory = itemInventory;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}

