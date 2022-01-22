package demo.dagger;

import java.util.List;

/**
 * Created by TanVOD on Jan, 2022
 */
public interface Machine {
    public long selectItemAndGetPrice(Item item);
    public void insertCoin(Coin coin);
    public List<Coin> refund();
    public Bucket<List<Item>, List<Coin>> collectItemAndChange();
    public void reset();
}
