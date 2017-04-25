package com.poker.colapanda.zhenrendantiao.live.model;

/**
 * Created by zhangze on 2017/3/15 10:33
 */
public class AllBet {

    private int spades;   //黑桃倍数
    private int hearts;   //红桃倍数
    private int clubs;   //梅花倍数
    private int diamonds; //方片倍数
    private int jokers;  //王倍数
    private int big;  //大
    private int small;  //小
    private int single;  //单
    private int pair;  //双
    private int peace;  //和

    public int getBig() {
        return big;
    }

    public void setBig(int big) {
        this.big = big;
    }

    public int getSmall() {
        return small;
    }

    public void setSmall(int small) {
        this.small = small;
    }

    public int getSingle() {
        return single;
    }

    public void setSingle(int single) {
        this.single = single;
    }

    public int getPair() {
        return pair;
    }

    public void setPair(int pair) {
        this.pair = pair;
    }

    public int getPeace() {
        return peace;
    }

    public void setPeace(int peace) {
        this.peace = peace;
    }

    public int getSpades() {
        return spades;
    }

    public void setSpades(int spades) {
        this.spades = spades;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public int getClubs() {
        return clubs;
    }

    public void setClubs(int clubs) {
        this.clubs = clubs;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public int getJokers() {
        return jokers;
    }

    public void setJokers(int jokers) {
        this.jokers = jokers;
    }
}
