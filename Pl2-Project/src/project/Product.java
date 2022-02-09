/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author ahmad
 */
public class Product {

    private int SN;
    private String name;
    private double orignalPrice;
    private double discount;
    private double price;
    private int amount;
    private String EPD;
    private int minRange;
    private String pState;

    public Product(String name, double orignalPrice, double discount, int amount, String EPD, int minRange) {
        this.name = name;
        this.orignalPrice = orignalPrice;
        this.discount = discount;
        this.price = this.orignalPrice - this.discount;
        this.amount = amount;
        this.EPD = EPD;
        this.minRange = minRange;
    }

    public Product(int SN, String name, double orignalPrice, double discount, int amount, String EPD, int minRange, String pState) {
        this.SN = SN;
        this.name = name;
        this.orignalPrice = orignalPrice;
        this.discount = discount;
        this.price = orignalPrice - discount;
        this.amount = amount;
        this.EPD = EPD;
        this.minRange = minRange;
        this.pState = pState;//R for in-return S for in-sale E for Expired
    }

    public Product() {
    }

    public int getSN() {
        return SN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOrignalPrice() {
        return orignalPrice;
    }

    public void setOrignalPrice(int orignalPrice) {
        this.orignalPrice = orignalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEPD() {
        return EPD;
    }

    public void setEPD(String EPD) {
        this.EPD = EPD;
    }

    public int getMinRange() {
        return minRange;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

}
