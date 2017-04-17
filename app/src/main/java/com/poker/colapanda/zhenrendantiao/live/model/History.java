package com.poker.colapanda.zhenrendantiao.live.model;

import java.util.ArrayList;

/**
 * Created by zhangze on 2017/3/15 10:34
 */
public class History {
    private Count count;//各色开牌数
    private String[] card;//历史牌色
    private ArrayList<String[]> translate;//小历史

    public ArrayList<String[]> getTranslate() {
        return translate;
    }

    public void setTranslate(ArrayList<String[]> translate) {
        this.translate = translate;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public String[] getCard() {
        return card;
    }

    public void setCard(String[] card) {
        this.card = card;
    }
}
