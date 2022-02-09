package project;

import database.PreviousActionsDB;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static void PrintProductHeader() {
        System.out.printf("\n%-6s%-10s%-8s%-15s%-11s%-8s%-14s%-10s%-10s\n", "SN", "Name", "Price", "Orignal_price", "Disscount", "Amount", "Expier data", "Min_Range", "State");
    }

    public static void PrintProduct(Product p) {

        System.out.printf("%-6d%-10s%-8.2f", p.getSN(), p.getName(), p.getPrice());
        System.out.printf("%-15.2f%-11.2f%-8d", p.getOrignalPrice(), p.getDiscount(), p.getAmount());
        System.out.printf("%-14s%-10d%-10s\n", p.getEPD(), p.getMinRange(), p.getpState());

    }

    public static void PrintActionHeader() {
        System.out.printf("\n%-60s%-13s\n", "Action_Name", "Date");
    }

    public static void PrintAction(Action ac) {
        System.out.printf("%-60s%-13s\n", ac.getActionName(), ac.getActionDate());
    }

    public static void registerAction(int eId, String actionName) {
    	DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        PreviousActionsDB.add_action(new Action(eId, actionName, format.format(new Date())));
    }
    
    public static void PrintOfferHeader() {
        System.out.printf("\n%-6s%-6s%-6s\n", "ID", "PSN" , "Discount");
    }

    public static void PrintOffer(Offer o) {
        System.out.printf("%-6d%-6d%-6.2f\n", o.getId() , o.getPSN() , o.getDiscount());
    }
}
