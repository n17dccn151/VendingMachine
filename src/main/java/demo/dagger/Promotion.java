package demo.dagger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TanVOD on Jan, 2022
 */
public class Promotion {

    private Map<Item, Integer> rateItems = new HashMap<Item, Integer>();
    private List<Item> histItems = new ArrayList<Item>();

    private int remainBudget;
    private int budget;
    private int winRate;

    public Promotion(){
    }

    public int getRemainBudget() {
        return remainBudget;
    }

    public void setRemainBudget(int remainBudget) {
        this.remainBudget = remainBudget;
    }

    public int getWinRate() {
        return winRate;
    }

    public void setWinRate(int winRate) {
        this.winRate = winRate;
    }

    public int getRateItem(Item item) {
        return rateItems.get(item) == null ? 0 : rateItems.get(item);
    }

    public void setRateItems(Map<Item, Integer> rateItems) {
        this.rateItems = rateItems;
    }

    public void nextDay(){
        if(this.getRemainBudget() > 0){
            int rate = this.getWinRate() + this.getWinRate()/2;
            this.setWinRate(rate>100?100:rate);
        }else{
            this.setWinRate(10);
        }
        this.setRemainBudget(this.budget);
        this.histItems.clear();
        this.rateItems.clear();
    }

    public void addItemAndCaculate(Item item) {

        if(this.getRemainBudget() > 0){
            this.histItems.add(item);
            if(histItems.size()>=3){
                int t = 0;
                Item last = histItems.get(histItems.size()-1);
                for (int i = histItems.size()-1; i>=0 ; i--) {
                    if(last.getName().equals(histItems.get(i).getName())){
                        t++;
                    }
                }

                if(t==3){
                    int rate = rateItems.get(item) == null ? 0: rateItems.get(item) ;
                    this.rateItems.put(item, rate + this.getWinRate()>100 ? 100 : rate + this.getWinRate());
                    this.histItems.clear();
                }
            }
        }
    }


    public void resetRateItem(Item item) {
        rateItems.put(item, 0);
    }

    public List<Item> getHistItems() {
        return histItems;
    }

    public void setBudget(int budget) {
        this.budget = budget;
        this.remainBudget = budget;
    }
}
