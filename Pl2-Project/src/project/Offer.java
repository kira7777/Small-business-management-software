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
public class Offer {

    private int id;
    private int PSN;
    private double discount;

    public Offer() {
    }

    public Offer(int PSN, double discount) {
        this.PSN = PSN;
        this.discount = discount;
    }

    public Offer(int id, int PSN, double discount) {
        this.id = id;
        this.PSN = PSN;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPSN() {
        return PSN;
    }

    public void setPSN(int PSN) {
        this.PSN = PSN;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
