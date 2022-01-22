package com.dagger;

import demo.dagger.*;
import demo.exception.NotFullPaidException;
import demo.exception.NotSufficientChangeException;
import demo.exception.SoldOutException;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TanVOD on Jan, 2022
 */
public class DaggerUnitTest {


    private SimpleMachine sm;
    private Inventory<Coin> cashInventory = new Inventory<Coin>();
    private Inventory<Item> itemInventory = new Inventory<Item>();
    private  Promotion promotion = new Promotion();

    
    @Before
    public void setUp(){
        MachinesComponent component = DaggerMachinesComponent.create();
        sm = component.buildMachine();

        for (Coin c : Coin.values()) {
            cashInventory.put(c, 10);
        }

        for (Item i : Item.values()) {
            itemInventory.put(i, 10);
        }

        promotion.setBudget(50000);
        promotion.setWinRate(10);

        sm.setCashInventory(cashInventory);
        sm.setItemInventory(itemInventory);
        sm.setPromotion(promotion);

        itemInventory.put(Item.PEPSI, 11);
        itemInventory.put(Item.SODA, 20);


    }

    @After
    public void tearDown(){
        sm = null;
        cashInventory = null;
        itemInventory = null;
        promotion = null;
    }


    @Test
    public void testBuyItemWithExactPrice() {
        long price = sm.selectItemAndGetPrice(Item.COKE);
        Assert.assertEquals(Item.COKE.getPrice(), price);
        sm.insertCoin(Coin.VND10);

        Bucket<List<Item>, List<Coin>> bucket = sm.collectItemAndChange();
        Item item = bucket.getFirst().get(0);
        List<Coin> change = bucket.getSecond();

        Assert.assertEquals(Item.COKE, item);
        Assert.assertTrue(change.isEmpty());
        Assert.assertEquals(0, getTotal(change));
    }

    @Test
    public void testBuyItemWithMorePrice(){
        long price = sm.selectItemAndGetPrice(Item.SODA);
        Assert.assertEquals(Item.SODA.getPrice(), price);

        sm.insertCoin(Coin.VND200);
        sm.insertCoin(Coin.VND200);

        Bucket<List<Item>, List<Coin>> bucket = sm.collectItemAndChange();
        Item item = bucket.getFirst().get(0);
        List<Coin> change = bucket.getSecond();

        Assert.assertEquals(Item.SODA, item);
        Assert.assertTrue(!change.isEmpty());
        Assert.assertEquals(400000 - Item.SODA.getPrice(), getTotal(change));
    }

    @Test
    public void testRefundWhenCancle(){
        List<Coin> coinRefund = new ArrayList<Coin>();
        long price = sm.selectItemAndGetPrice(Item.PEPSI);
        Assert.assertEquals(Item.PEPSI.getPrice(), price);

        sm.insertCoin(Coin.VND50);
        sm.insertCoin(Coin.VND50);

        coinRefund = sm.refund();

        Assert.assertEquals(100000, getTotal(coinRefund));
        Assert.assertEquals(50000, coinRefund.get(0).getDenomination());
        Assert.assertEquals(50000, coinRefund.get(1).getDenomination());
    }

    @Test(expected= SoldOutException.class)
    public void testSoldOut(){
        for (int i = 0; i < 11; i++) {
            sm.selectItemAndGetPrice(Item.COKE);
            sm.insertCoin(Coin.VND20);
            sm.collectItemAndChange();
        }
    }

    @Test(expected= NotSufficientChangeException.class)
    public void testNotSufficientChange(){
        for (int i = 0; i < 10; i++) {
            sm.selectItemAndGetPrice(Item.PEPSI);
            sm.insertCoin(Coin.VND20);
            sm.collectItemAndChange();
        }
        sm.selectItemAndGetPrice(Item.PEPSI);
        sm.insertCoin(Coin.VND20);
        sm.collectItemAndChange();
    }

    @Test(expected= NotFullPaidException.class)
    public void testNotFullPaid(){
        sm.selectItemAndGetPrice(Item.SODA);
        sm.insertCoin(Coin.VND10);
        sm.collectItemAndChange();
    }

    @Test
    public void testTotalSale(){
        long price = sm.selectItemAndGetPrice(Item.PEPSI);
        Assert.assertEquals(Item.PEPSI.getPrice(), price);

        sm.insertCoin(Coin.VND50);
        sm.collectItemAndChange();

        Assert.assertEquals(10000, sm.getTotalSales());
    }

    @Test
    public void testWinRateOfItem_WhenPurchasesSameItem3Time(){
        for (int i = 0; i < 2; i++) {
            sm.selectItemAndGetPrice(Item.PEPSI);
            sm.insertCoin(Coin.VND20);
            sm.collectItemAndChange();
        }

        for (int i = 0; i < 3; i++) {
            sm.selectItemAndGetPrice(Item.COKE);
            sm.insertCoin(Coin.VND20);
            sm.collectItemAndChange();
        }

        for (int i = 0; i < 2; i++) {
            sm.selectItemAndGetPrice(Item.PEPSI);
            sm.insertCoin(Coin.VND10);
            sm.collectItemAndChange();
        }


        for (int i = 0; i < 3; i++) {
            sm.selectItemAndGetPrice(Item.SODA);
            sm.insertCoin(Coin.VND20);
            sm.collectItemAndChange();
        }

        for (int i = 0; i < 3; i++) {
            sm.selectItemAndGetPrice(Item.COKE);
            sm.insertCoin(Coin.VND10);
            sm.collectItemAndChange();
        }

        Assert.assertEquals(0, sm.getRateItem(Item.PEPSI));
        Assert.assertEquals(20, sm.getRateItem(Item.COKE));
        Assert.assertEquals(10, sm.getRateItem(Item.SODA));

    }

    @Test
    public void testWinRateIncrease_WhenDayByDayAndLimitCannotBeReached(){
        sm.nextDay();
        Assert.assertEquals(10+10/2, sm.getWinRate());

        sm.nextDay();
        Assert.assertEquals((10+10/2)+(10+10/2)/2, sm.getWinRate());
    }

    @Test
    public void testGetFreeItem_WhenOverManyDays(){
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();

        for (int i = 0; i < 5; i++) {
            sm.selectItemAndGetPrice(Item.COKE);
            sm.insertCoin(Coin.VND10);
            sm.collectItemAndChange();
        }

        sm.selectItemAndGetPrice(Item.COKE);
        sm.insertCoin(Coin.VND10);
        Bucket<List<Item>, List<Coin>> bucket = sm.collectItemAndChange();

        Item purchasedItem = bucket.getFirst().get(0);
        Item freeItem = bucket.getFirst().get(1);

        Assert.assertEquals(Item.COKE, purchasedItem);
        Assert.assertEquals(Item.COKE, freeItem);

    }

    @Test
    public void testRemainBudget_WhenGetFreeItem(){
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();

        for (int i = 0; i < 6; i++) {
            sm.selectItemAndGetPrice(Item.COKE);
            sm.insertCoin(Coin.VND10);
            sm.collectItemAndChange();
        }

        Assert.assertEquals(40000, sm.getRemainBudget());
    }

    @Test
    public void testLimitBudgetADay(){
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();

        for (int i = 0; i < 6; i++) {
            sm.selectItemAndGetPrice(Item.COKE);
            sm.insertCoin(Coin.VND10);
            sm.collectItemAndChange();
        }

        Assert.assertEquals(40000, sm.getRemainBudget());

        for (int i = 0; i < 6; i++) {
            sm.selectItemAndGetPrice(Item.SODA);
            sm.insertCoin(Coin.VND20);
            sm.collectItemAndChange();
        }

        Assert.assertEquals(20000, sm.getRemainBudget());

        for (int i = 0; i < 6; i++) {
            sm.selectItemAndGetPrice(Item.SODA);
            sm.insertCoin(Coin.VND20);
            sm.collectItemAndChange();
        }

        Assert.assertEquals(0, sm.getRemainBudget());
    }

    @Test
    public void testWinRate_WhenLimitBudgetLastDay(){
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();
        sm.nextDay();

        for (int i = 0; i < 6; i++) {
            sm.selectItemAndGetPrice(Item.COKE);
            sm.insertCoin(Coin.VND10);
            sm.collectItemAndChange();
        }

        for (int i = 0; i < 6; i++) {
            sm.selectItemAndGetPrice(Item.SODA);
            sm.insertCoin(Coin.VND20);
            sm.collectItemAndChange();
        }

        for (int i = 0; i < 6; i++) {
            sm.selectItemAndGetPrice(Item.SODA);
            sm.insertCoin(Coin.VND20);
            sm.collectItemAndChange();
        }

        sm.nextDay();
        Assert.assertEquals(10, sm.getWinRate());

    }

    private long getTotal(List<Coin> change){
        long total = 0;
        for(Coin c : change){
            total = total + c.getDenomination();
        }
        return total;
    }


}
