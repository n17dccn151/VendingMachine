package demo.dagger;

/**
 * Created by TanVOD on Jan, 2022
 */
public enum Coin {
    VND200(200000), VND100(100000), VND50(50000),VND20(20000), VND10(10000) ;

    private int denomination;

    private Coin(int denomination){
        this.denomination = denomination;
    }

    public int getDenomination(){
        return denomination;
    }
}
