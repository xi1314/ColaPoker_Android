package com.poker.colapanda.zhenrendantiao.live.model;

/**
 * Created by zhangze on 2017/3/15 10:28
 */
public class Game {
    private String issue;           //期数
    private String status;         //牌局状态。0-未开始，1-已开始，2-已结束
    private String game_number;     //游戏局数
    private String status_content;  //投注状态
    private String countdown;       //倒计时
    private String live_url;        //主播视频
    private String open;            //开牌
    private String recharges_url;   //充值
    private String withdraws_url;   //提现
    private String live_url_md5;    //语音频道号
    private String wechat;          //客服
    private String live_time;       //直播时间

    public String getLive_time() {
        return live_time;
    }

    public void setLive_time(String live_time) {
        this.live_time = live_time;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGame_number() {
        return game_number;
    }

    public void setGame_number(String game_number) {
        this.game_number = game_number;
    }

    public String getStatus_content() {
        return status_content;
    }

    public void setStatus_content(String status_content) {
        this.status_content = status_content;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }

    public String getLive_url() {
        return live_url;
    }

    public void setLive_url(String live_url) {
        this.live_url = live_url;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getRecharges_url() {
        return recharges_url;
    }

    public void setRecharges_url(String recharges_url) {
        this.recharges_url = recharges_url;
    }

    public String getWithdraws_url() {
        return withdraws_url;
    }

    public void setWithdraws_url(String withdraws_url) {
        this.withdraws_url = withdraws_url;
    }

    public String getLive_url_md5() {
        return live_url_md5;
    }

    public void setLive_url_md5(String live_url_md5) {
        this.live_url_md5 = live_url_md5;
    }
}
