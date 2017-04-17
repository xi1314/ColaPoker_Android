package com.poker.colapanda.zhenrendantiao.live.model;

/**
 * Created by zhangze on 2017/3/15 11:00
 */
public class Count {
    private int spades;   //黑桃数
    private int hearts;   //红桃数
    private int clubs;   //梅花数
    private int diamonds; //方片数
    private int jokers;  //王数

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
