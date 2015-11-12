package ru.callsanalyzer.objects;


import java.util.Date;

/**
 * Created by msv on 06.11.2015.
 */
public class Call {
    public static final int NUM_FIELDS = 7;
    private Date date;
    private int id;
    private String account;
    private String numberFrom;
    private String numberTo;
    private double sum; // сумма по тарифу
    private int duration; // длительность в секугдах

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account.replaceAll("[()+ -]","");
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberFrom() {
        return numberFrom;
    }

    public void setNumberFrom(String numberFrom) {
        this.numberFrom = numberFrom.replace(" ","");;
    }

    public String getNumberTo() {
        return numberTo;
    }

    public void setNumberTo(String numberTo) {
        this.numberTo = numberTo.replace(" ","");
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return  getDate() + " " +
                account + "" +
                getNumberFrom() + " " +
                getNumberTo() + " " +
                getSum() + " " +
                getDuration();
    }
}
