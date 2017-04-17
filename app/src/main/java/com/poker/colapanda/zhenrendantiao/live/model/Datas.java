package com.poker.colapanda.zhenrendantiao.live.model;

/**
 * Created by zhangze on 2017/3/15 10:09
 */
public class Datas {
    private Game game;
    private AllBet all_bet;
    private UserBet user_bet;
    private History history;
    private User user;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public AllBet getAll_bet() {
        return all_bet;
    }

    public void setAll_bet(AllBet all_bet) {
        this.all_bet = all_bet;
    }

    public UserBet getUser_bet() {
        return user_bet;
    }

    public void setUser_bet(UserBet user_bet) {
        this.user_bet = user_bet;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
