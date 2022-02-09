/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import database.EmployeeDB;
import database.ProductDB;
import database.OrderDB;
import database.PreviousActionsDB;
import database.RProductDB;
import java.util.ArrayList;

public class SalesEmployee extends Employee {

    public SalesEmployee(int id, String fName, String lName, String userName, String password) {
        super(id, fName, lName, userName, password, "S");
    }

    public SalesEmployee(String fName, String lName, String userName, String password) {
        super(fName, lName, userName, password, "S");
    }

    @Override
    public String getTitle() {
        return "SE." + super.getTitle();
    }

    public int openList() {
        int c;
        System.out.println("\nHello ," + this.getfName() + "!\n");
        do {

            System.out.printf("\nSales Menu:"
                    + "\nSearch for a Product.               (Enter 1)"
                    + "\nList all Products.                  (Enter 2)"
                    + "\nList all orders.                    (Enter 3)"
                    + "\nMake an order.                      (Enter 4)"
                    + "\nDelete an order.                    (Enter 5)"
                    + "\nAlter your information.             (Enter 6)"
                    + "\nAlter your User-Name and Password.  (Enter 7)"
                    + "\nDisplay all your previous actions.  (Enter 8)"
                    + "\nLogOut.                             (Enter 9)\n");
            System.out.printf("?: ");
            ProductDB.update_products_states();
            RProductDB.update_RProducts_states();
            c = Check.CheckNumber();
            if (c != 1 && c != 2 && c != 3 && c != 4 && c != 5 && c != 6 && c != 7 && c != 8 && c != 9) {
                System.out.println("Invaild Input!");
                continue;
            }

            if (c == 9) {
                break;
            }

            switch (c) {
                case 1:
                    search();
                    break;
                case 2:
                    print_list_Product();
                    break;
                case 3:
                    print_list_Order();
                    break;
                case 4:
                    make_an_order();
                    break;
                case 5:
                    delete_an_order();
                    break;
                case 6:
                    alterInformation();
                    break;
                case 7:
                    alterUserNameAndPassword();
                    break;
                case 8:
                    displayPreviousActions();
                    break;
            }

        } while (c != 9);
        System.out.println("bey bey ," + this.getfName() + "!\n");
        return 0;
    }

    private void search() {
        System.out.print("Enter the serial number : ");
        int sn = Check.CheckSerialNumber();
        if (sn == -1) {
            System.out.println("No search happened");
        } else {
            Util.PrintProductHeader();
            Util.PrintProduct(ProductDB.get_Product(sn));
        }
    }

    private void print_list_Product() {
        ArrayList<Product> list = ProductDB.get_products();
        Util.PrintProductHeader();
        for (int i = 0; i < list.size(); i++) {
            Util.PrintProduct(list.get(i));
        }
    }

    private void print_list_Order() {
        ArrayList<Order> list = OrderDB.get_orders();
        System.out.printf("\n%-5s %-5s %-15s\n", "ID", "PSN", "AMOUNT");
        Order o;
        for (int i = 0; i < list.size(); i++) {
            o = list.get(i);
            System.out.printf("%-5d %-5d %-15d\n", o.getId(), o.getPSN(), o.getAmount());
            System.out.println();
        }
    }

    private void make_an_order() {
        System.out.print("Enter the amount: ");
        int amount = Check.CheckNumber();
        System.out.print("Enter the product serial number: ");
        int psn = Check.CheckSerialNumber();
        if (psn == -1) {
            System.out.println("No order happened");
        } else {
            Product p = ProductDB.get_Product(psn);
            if (amount > p.getAmount()) {
                System.out.println("\nOnly (" + p.getAmount() + ") piece is available.\n");
                return;
            }
            OrderDB.add_order(new Order(psn, amount));
            System.out.println("\nAdded!\n");
            Util.registerAction(this.getId(), "Add-Order SN:(" + psn + ") Amount:(" + amount + ").");
        }
    }

    private void delete_an_order() {
        System.out.printf("Enter the order id: ");
        int id = Check.CheckOrderID();
        if (id == -1) {
            System.out.println("No deletion happened");
        } else {
            OrderDB.delete_order(id);
            System.out.println("\nDeleted!");
            Util.registerAction(this.getId(), "Delete-Order SN:(" + id + ").");
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
