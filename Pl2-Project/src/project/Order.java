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
public class Order {

    private int id;
    private int PSN;
    private int amount;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order(int id, int PSN, int amount) {
        this.id = id;
        this.PSN = PSN;
        this.amount = amount;
    }

    public Order(int PSN, int amount) {
        this.PSN = PSN;
        this.amount = amount;
    }

    public int getPSN() {
        return PSN;
    }

    public void setPSN(int PSN) {
        this.PSN = PSN;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
