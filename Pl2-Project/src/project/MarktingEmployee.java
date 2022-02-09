/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import database.EmployeeDB;
import database.PreviousActionsDB;
import database.ProductDB;
import database.RProductDB;
import database.SentOffersDB;
import java.util.ArrayList;

public class MarktingEmployee extends Employee {

    public MarktingEmployee(int id, String fName, String lName, String userName, String password) {
        super(id, fName, lName, userName, password, "M");
    }

    public MarktingEmployee(String fName, String lName, String userName, String password) {
        super(fName, lName, userName, password, "M");
    }

    @Override
    public String getTitle() {
        return "ME." + super.getTitle();
    }

    public int openList() {
        int c;
        System.out.println("\nHello ," + this.getfName() + "!\n");
        do {

            System.out.printf("\nMarkting Menu:"
                    + "\nMake products reports.                       (Enter 1)"
                    + "\nMake offer and send it to the Inventory Dep. (Enter 2)"
                    + "\nAlter your information.                      (Enter 3)"
                    + "\nAlter your User-Name and Password.           (Enter 4)"
                    + "\nDisplay all your previous actions.           (Enter 5)"
                    + "\nLogOut.                                      (Enter 6)\n");
            System.out.printf("?: ");
            ProductDB.update_products_states();
            RProductDB.update_RProducts_states();
            c = Check.CheckNumber();
            if (c != 1 && c != 2 && c != 3 && c != 4 && c != 5 && c != 6) {
                System.out.println("Invaild Input!");
                continue;
            }
            if (c == 6) {
                break;
            }

            switch (c) {
                case 1:
                    makeReports();
                    break;
                case 2:
                    Make_offer_and_send_it();
                    break;
                case 3:
                    alterInformation();
                    break;
                case 4:
                    alterUserNameAndPassword();
                    break;
                case 5:
                    displayPreviousActions();
                    break;
            }

        } while (c != 6);
        System.out.println("bey bey ," + this.getfName() + "!\n");
        return 0;
    }

    public void makeReports() {
        int c;
        do {
            System.out.printf("\nMaking Report about products by:"
                    + "\nOfferd products.                     (Enter 1)"
                    + "\nHas price greater than.              (Enter 2)"
                    + "\nHas price less than.                 (Enter 3)"
                    + "\nNumber of products.                  (Enter 4)"
                    + "\nList products ordered by the EPD.    (Enter 5)"
                    + "\nList products ordered by the Price.  (Enter 6)"
                    + "\nExit.                                (Enter 7)\n");
            System.out.printf("?: ");
            c = Check.CheckNumber();

            if (c != 1 && c != 2 && c != 3 && c != 4 && c != 5 && c != 6 && c != 7) {
                System.out.println("Invaild Input!");
                continue;
            }

            switch (c) {
                case 1:
                    makeReportByOffers();
                    break;
                case 2:
                    makeReportByHasPriceGreaterThan();
                    break;
                case 3:
                    makeReportByHasPriceLessThan();
                    break;
                case 4:
                    makeReportByNumberOfProducts();
                    break;
                case 5:
                    makeReportOrderedByEPD();
                    break;
                case 6:
                    makeReportOrderedByPrice();
                    break;
            }

        } while (c != 7);
    }

    private void makeReportByOffers() {
        ArrayList<Product> list = ProductDB.get_products();

        Util.PrintProductHeader();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDiscount() > 0) {
                Util.PrintProduct(list.get(i));

            }
        }

    }

    private void makeReportByHasPriceGreaterThan() {
        System.out.printf("Enter the price : ");
        int price = Check.CheckNumber();
        ArrayList<Product> list = ProductDB.get_products();

        Util.PrintProductHeader();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPrice() > price) {
                Util.PrintProduct(list.get(i));

            }
        }

    }

    private void makeReportByHasPriceLessThan() {
        System.out.printf("Enter the price : ");
        int price = Check.CheckNumber();
        ArrayList<Product> list = ProductDB.get_products();
        Util.PrintProductHeader();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPrice() < price) {
                Util.PrintProduct(list.get(i));

            }
        }

    }

    private void makeReportByNumberOfProducts() {
        ArrayList<Product> list = ProductDB.get_products();
        System.out.println("\nNumber of products = " + list.size());
    }

    private void Make_offer_and_send_it() {
        System.out.printf("Enter the SN of the product : ");
        int psn = Check.CheckSerialNumber();
        if (psn == -1) {
            System.out.println("No offers made");
        } else {
            System.out.printf("Enter the discount : ");
            double discount = Check.CheckDoubleNumber();
            System.out.println("\nOffer Sent!");
            SentOffersDB.add_sent_offer(new Offer(psn, discount));
            Util.registerAction(this.getId(), "Sent-Offer SN :(" + psn + ") Discount :(" + discount + ").");
        }
    }

    private void sortByPrice(ArrayList<Product> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (InventoryEmployee.comparePrice(list.get(i).getPrice(), list.get(j).getPrice())) {
                    Product temp = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, temp);
                }
            }
        }
    }

    private void sortByEPD(ArrayList<Product> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (InventoryEmployee.compareEPD(list.get(i).getEPD(), list.get(j).getEPD())) {
                    Product temp = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, temp);
                }
            }
        }
    }

    private void makeReportOrderedByEPD() {
        ArrayList<Product> list = ProductDB.get_products();
        this.sortByEPD(list);
        Util.PrintProductHeader();

        for (int i = 0; i < list.size(); i++) {
            Util.PrintProduct(list.get(i));

        }
    }

    private void makeReportOrderedByPrice() {
        ArrayList<Product> list = ProductDB.get_products();
        this.sortByPrice(list);
        Util.PrintProductHeader();

        for (int i = 0; i < list.size(); i++) {
            Util.PrintProduct(list.get(i));

        }
    }

    private void alterInformation() {
        System.out.printf("Enter first name : ");
        String fname = Check.CheckFname();
        System.out.printf("Enter last name : ");
        String lname = Check.CheckLname();
        EmployeeDB.update_employee(this.getId(), fname, lname, this.getUserName(), this.getPassword(), this.getEType());
        System.out.println("\nUpdated!\n");
        Util.registerAction(this.getId(), "Update-Your First-Name & Last-Name.");
    }

    void alterUserNameAndPassword() {
        System.out.printf("Enter user name : ");
        String username = Check.CheckNewUsername();
        System.out.printf("Enter password : ");
        String password = Check.CheckPassword();
        EmployeeDB.update_employee(this.getId(), this.getfName(), this.getlName(), username, password, this.getEType());
        System.out.println("\nUpdated!\n");
        Util.registerAction(this.getId(), "Update-Your User-Name & Password.");
    }

    private void displayPreviousActions() {
        ArrayList<Action> list = PreviousActionsDB.get_actions(this.getId());
        Util.PrintActionHeader();
        for (int i = 0; i < list.size(); i++) {
            Util.PrintAction(list.get(i));
        }
    }

}
