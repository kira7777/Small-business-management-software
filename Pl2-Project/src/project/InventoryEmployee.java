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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryEmployee extends Employee {

    final static int numOfDaysToGetCloseToExpire = 3; //to use it later in notifications

    public InventoryEmployee(int id, String fName, String lName, String userName, String password) {
        super(id, fName, lName, userName, password, "I");
    }

    public InventoryEmployee(String fName, String lName, String userName, String password) {
        super(fName, lName, userName, password, "I");
    }

    @Override
    public String getTitle() {
        return "IE." + super.getTitle();
    }

    public int openList() {
        int c;
        System.out.println("\nHello ," + this.getfName() + "!\n");
        do {
            numOfNotifications();
            System.out.printf("\nInventory Menu:"
                    + "\nAdd a new product.                          (Enter 1)"
                    + "\nAdd a product to the Sales Return List.     (Enter 2)"
                    + "\nDelete a product.                           (Enter 3)"
                    + "\nUpdate a product information                (Enter 4)"
                    + "\nList all products.                          (Enter 5)"
                    + "\nSearch for a product.                       (Enter 6)"
                    + "\nManage the Damages items.                   (Enter 7)"
                    + "\nManage the Sales Return.                    (Enter 8)"
                    + "\nShow All Sent Offers by Markting Dep.       (Enter 9)"
                    + "\nAlter your information.                     (Enter 10)"
                    + "\nAlter your User-Name and Password.          (Enter 11)"
                    + "\nDisplay all your previous actions.          (Enter 12)"
                    + "\nLogOut.                                     (Enter 13)\n");
            System.out.printf("?: ");
            ProductDB.update_products_states();
            RProductDB.update_RProducts_states();
            c = Check.CheckNumber();
            if (c != 0 && c != 1 && c != 2 && c != 3 && c != 4 && c != 5 && c != 6 && c != 7 && c != 8 && c != 9 && c != 10 && c != 11 && c != 12 && c != 13) {
                System.out.println("Invaild Input!");
                continue;
            }
            if (c == 13) {
                break;
            }
            switch (c) {
                case 0:
                    displayNotifications();
                    break;
                case 1:
                    newProduct();
                    break;
                case 2:
                    newRProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    listProduct();
                    break;
                case 6:
                    searchProduct();
                    break;
                case 7:
                    manageDamagesItems();
                    break;
                case 8:
                    manageSalesReturn();
                    break;
                case 9:
                    manageSentOffers();
                    break;
                case 10:
                    alterInformation();
                    break;
                case 11:
                    alterUserNameAndPassword();
                    break;
                case 12:
                    displayPreviousActions();
                    break;
            }

        } while (c != 13);
        System.out.printf("bey bey ,%s!\n", this.getfName());
        return 0;
    }

    public void newProduct() {
        System.out.printf("Enter Product Name: ");
        String name = Check.CheckFname();
        System.out.printf("Enter Original Price: ");
        double OP = Check.CheckDoubleNumber();
        System.out.printf("Enter Discount: ");
        double diss = Check.CheckDoubleNumber();
        System.out.printf("Enter Amount: ");
        int amount = Check.CheckNumber();
        System.out.printf("Enter Expierd Date: ");
        String EPD = Check.CheckExpierdDate();
        System.out.printf("Enter the Minmum Range: ");
        int minRange = Check.CheckNumber();
        Product d = new Product(name, OP, diss, amount, EPD, minRange);
        ProductDB.add_product(d);
        System.out.println("\nAdded!\n");
        Util.registerAction(this.getId(), "Add-New_Product:(" + name + ").");
    }

    public void newRProduct() {
        System.out.printf("Enter Product Name: ");
        String name = Check.CheckFname();
        System.out.printf("Enter Original Price: ");
        double OP = Check.CheckDoubleNumber();
        System.out.printf("Enter Discount: ");
        double diss = Check.CheckDoubleNumber();
        System.out.printf("Enter Amount: ");
        int amount = Check.CheckNumber();
        System.out.printf("Enter Expierd Date: ");
        String EPD = Check.CheckExpierdDate(); // Need to ask
        System.out.printf("Enter the Minmum Range: ");
        int minRange = Check.CheckNumber(); // Need to ask
        Product d = new Product(name, OP, diss, amount, EPD, minRange);
        RProductDB.add_RProduct(d);
        System.out.println("\nAdded!\n");
        Util.registerAction(this.getId(), "Add-New_Product To Sales Return List:(" + name + ").");
    }

    public void deleteProduct() {
        int sn;
        System.out.printf("Enter Product Serial Number: ");
        sn = Check.CheckSerialNumber(); // Need to ask
        if (sn == -1) {
            System.out.println("No deletion happened");
        } else {
            ProductDB.delete_product(sn);
            System.out.println("\nDeleted!\n");
            Util.registerAction(this.getId(), "Delete-Product SN :(" + sn + ").");
        }
    }

    public void deleteEProduct() {
        int sn;
        System.out.printf("Enter Product Serial Number: ");
        sn = Check.CheckSerialNumber(); // Need to ask
        if (sn == -1) {
            System.out.println("No deletion happened");
        } else {
            ProductDB.delete_product(sn);
            System.out.println("\nDeleted!\n");
            Util.registerAction(this.getId(), "Delete-Expired Product SN :(" + sn + ").");
        }
    }

    public void deleteAllEProduct() {
        ArrayList<Product> list = ProductDB.get_Eproducts();
        for (int i = 0; i < list.size(); i++) {
            int sn = list.get(i).getSN();
            ProductDB.delete_product(sn);
            Util.registerAction(this.getId(), "Delete-Expired Product SN :(" + sn + ").");
        }
        System.out.println("\nAll Deleted!\n");
    }

    public void updateProduct() {
        System.out.printf("For update the discount only.        (Enter 1)\n"
                        + "For update all product info.         (Enter 2)\n"
                        + "?: ");
        int choice = Check.CheckNumber();
        System.out.printf("Enter Product Serial Number: ");
        int sn = Check.CheckSerialNumber();
        if (sn == -1) {
            System.out.println("No update happened");
        } else {
            if (choice == 1) {
                System.out.printf("Enter  Discount: ");
                double diss = Check.CheckDoubleNumber();
                ProductDB.update_product(sn, diss);
                System.out.println("\nUpdated!\n");
                Util.registerAction(this.getId(), "Update-Product SN :(" + sn + ").");
            } else if (choice == 2) {
                System.out.printf("Enter new Name: ");
                String name = Check.CheckFname();
                System.out.printf("Enter Original Price: ");
                double OP = Check.CheckDoubleNumber();
                System.out.printf("Enter Discount: ");
                double diss = Check.CheckDoubleNumber();
                System.out.printf("Enter Amount: ");
                int amount = Check.CheckNumber();
                //input.nextLine();
                System.out.printf("Enter Expier Date: ");
                String EPD = Check.CheckExpierdDate();
                System.out.printf("Enter the Minmum Range: ");
                int minRange = Check.CheckNumber();
                ProductDB.update_product(sn, name, OP, diss, amount, EPD, minRange);
                System.out.println("\nUpdated!\n");
                Util.registerAction(this.getId(), "Update-Product SN :(" + sn + ").");
            }
        }
    }

    public static void add_offer(int sn, int discount) {        //what is this method made for ??????
        ProductDB.update_product(sn, discount);
    }

    public void listProduct() {
        ArrayList<Product> list = ProductDB.get_products();
        Util.PrintProductHeader();
        for (int i = 0; i < list.size(); i++) {
            Util.PrintProduct(list.get(i));
        }
    }

    public void listRProduct() {
        ArrayList<Product> list = RProductDB.get_RProducts();

        Util.PrintProductHeader();
        for (int i = 0; i < list.size(); i++) {
            Util.PrintProduct(list.get(i));

        }
    }

    public void listEProduct() {
        ArrayList<Product> list = new ArrayList<>();
        list = ProductDB.get_Eproducts();

        Util.PrintProductHeader();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getpState().equals("E")) {
                Util.PrintProduct(list.get(i));

            }
        }
    }

    public void searchProduct() {
        System.out.print("Enter the serial number : ");
        int sn = Check.CheckSerialNumber();
        if (sn == -1) {
            System.out.println("No search happened");
        } else {
            Util.PrintProductHeader();
            Util.PrintProduct(ProductDB.get_Product(sn));
        }
    }

    public static String updateProductState(String EPDate) {

        SimpleDateFormat EPFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date productDate;
        long Pmillis = 0;//assign to 0 only to avoid not initialized var excption

        try {
            productDate = EPFormat.parse(EPDate);
            Pmillis = productDate.getTime();

        } catch (ParseException ex) {
            Logger.getLogger(InventoryEmployee.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (System.currentTimeMillis() > Pmillis) {
            return "E";
        } else {
            return "S";
        }
    }

    public static boolean compareEPD(String EPDate1, String EPDate2) {

        SimpleDateFormat EPFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date productDate1;
        Date productDate2;
        long Pmillis1 = 0;//assign to 0 only to avoid not initialized var excption
        long Pmillis2 = 0;//assign to 0 only to avoid not initialized var excption

        try {
            productDate1 = EPFormat.parse(EPDate1);
            Pmillis1 = productDate1.getTime();

            productDate2 = EPFormat.parse(EPDate2);
            Pmillis2 = productDate2.getTime();

        } catch (ParseException ex) {
            Logger.getLogger(InventoryEmployee.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (Pmillis1 > Pmillis2) {
            return true;
        }
        return false;
    }

    public static ArrayList<Product> closeToExpProducts() {
        ArrayList<Product> allProducts = ProductDB.get_products();
        ArrayList<Product> closeToExpProducts = new ArrayList<Product>();

        SimpleDateFormat EPFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date productDate;
        long Pmillis = 0;//assign to 0 only to avoid not initialized var excption

        try {
            for (int i = 0; i < allProducts.size(); i++) {
                Product p = allProducts.get(i);
                productDate = EPFormat.parse(p.getEPD());
                Pmillis = productDate.getTime();

                if (Pmillis <= TimeUnit.DAYS.toMillis(InventoryEmployee.numOfDaysToGetCloseToExpire) + System.currentTimeMillis() && !(System.currentTimeMillis() > Pmillis)) {
                    closeToExpProducts.add(p);
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(InventoryEmployee.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return closeToExpProducts;
    }

    public static ArrayList<Product> ReducedProducts() {
        ArrayList<Product> allProducts = ProductDB.get_products();
        ArrayList<Product> ReducedProducts = new ArrayList<Product>();

        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            if (p.getAmount() <= p.getMinRange()) {
                ReducedProducts.add(p);
            }
        }
        return ReducedProducts;
    }

    public static boolean comparePrice(double price1, double price2) {
        if (price1 > price2) {
            return true;
        }
        return false;
    }

    public void manageDamagesItems() {
        System.out.println("\nExpired Items :");
        listEProduct();
        ArrayList<Product> list = ProductDB.get_Eproducts();
        if (!list.isEmpty()) {
            int c;
            do {
                System.out.printf("\nExpired Items Operations:"
                        + "\nRemove all from the inventory.       (Enter 1)"
                        + "\nRemove selected item from inventory. (Enter 2)"
                        + "\nExit                                 (Enter 3)\n"
                );
                System.out.printf("?: ");
                c = Check.CheckNumber();

                if (c != 1 && c != 2 && c != 3) {
                    System.out.println("\nInvaild Input!\n");
                    continue;
                }

                switch (c) {
                    case 1:
                        deleteAllEProduct();
                        break;
                    case 2:
                        deleteEProduct();
                        break;
                }
            } while (c != 3 && !list.isEmpty());
        }
    }

    public void ReturnAllToInventory() {
        ArrayList<Product> list = RProductDB.get_RProducts();
        for (int i = 0; i < list.size(); i++) {
            int sn = list.get(i).getSN();
            ProductDB.add_product(RProductDB.get_RProduct(sn));
            RProductDB.delete_RProduct(sn);
            System.out.println("\nAll Returend!\n");
            Util.registerAction(this.getId(), "Return-Product_to_Inventory SN :(" + sn + ").");
        }
    }

    public void ReturnSlectedItemToInventory() {
        System.out.printf("Enter the serial number: ");
        int sn = Check.Check_RP_SerialNumber();
        if (sn == -1) {
            System.out.println("Nothing returned");
        } else {
            ProductDB.add_product(RProductDB.get_RProduct(sn));
            RProductDB.delete_RProduct(sn);
            System.out.println("\nReturned!\n");
            Util.registerAction(this.getId(), "Return-Product_to_Inventory SN :(" + sn + ").");
        }
    }

    public void manageSalesReturn() {
        System.out.println("\nSales Return :");
        listRProduct();
        if (!RProductDB.isEmpty()) {
            int c;
            do {
                System.out.printf("\nReturned Items Operations:"
                        + "\nReturn all to the inventory.           (Enter 1)"
                        + "\nReturn selected item to the inventory. (Enter 2)"
                        + "\nExit                                   (Enter 3)\n"
                );
                System.out.printf("?: ");
                c = Check.CheckNumber();

                if (c != 1 && c != 2 && c != 3) {
                    System.out.println("\nInvaild Input!\n");
                    continue;
                }

                switch (c) {
                    case 1:
                        ReturnAllToInventory();
                        break;
                    case 2:
                        ReturnSlectedItemToInventory();
                        break;
                }
            } while (c != 3 && !RProductDB.isEmpty());
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

    private void numOfNotifications() {
        int numOfNotifications = 0;
        ArrayList closeToExpProducts = closeToExpProducts();
        ArrayList ReducedProducts = ReducedProducts();

        numOfNotifications += closeToExpProducts.size();
        numOfNotifications += ReducedProducts.size();

        if (numOfNotifications > 0) {
            System.out.printf("\nYou Have (%d) Notifications!    (Enter (0) To Display)\n", numOfNotifications);
        }
        if (numOfNotifications == 0) {
            System.out.printf("\nYou Don`t Have Any Notifications!.\n", numOfNotifications);
        }
    }

    private void displayNotifications() {
        ArrayList<Product> closeToExpProducts = closeToExpProducts();
        ArrayList<Product> ReducedProducts = ReducedProducts();

        if (closeToExpProducts.size() + ReducedProducts.size() == 0) {
            return;
        }

        if (!closeToExpProducts.isEmpty()) {
            System.out.printf("\nGet close to Expire Products (Will Expire in %d Day/s!) : \n", InventoryEmployee.numOfDaysToGetCloseToExpire);
            Util.PrintProductHeader();
            for (int i = 0; i < closeToExpProducts.size(); i++) {
                Product P = closeToExpProducts.get(i);
                Util.PrintProduct(P);
            }
        }

        if (!ReducedProducts.isEmpty()) {
            System.out.println("\nReduced To Minmim Range Products : ");
            Util.PrintProductHeader();
            for (int i = 0; i < ReducedProducts.size(); i++) {
                Product P = ReducedProducts.get(i);
                Util.PrintProduct(P);
            }
        }

    }

    private void displayPreviousActions() {
        ArrayList<Action> list = PreviousActionsDB.get_actions(this.getId());
        Util.PrintActionHeader();
        for (int i = 0; i < list.size(); i++) {
            Util.PrintAction(list.get(i));
        }
    }

    private void manageSentOffers() {
        System.out.println("\nSent Offers :");
        displayAllSentOffers();
        if (!SentOffersDB.isEmpty()) {
            int c;
            do {
                System.out.printf("\nSent Offers Operations:"
                        + "\nAccept All.                            (Enter 1)"
                        + "\nReject All.                            (Enter 2)"
                        + "\nAccept Selected One.                   (Enter 3)"
                        + "\nReject Selected One.                   (Enter 4)"
                        + "\nExit                                   (Enter 5)\n"
                );
                System.out.printf("?: ");
                c = Check.CheckNumber();
                if (c != 1 && c != 2 && c != 3 && c != 4 && c != 5) {
                    System.out.println("\nInvaild Input!\n");
                }

                switch (c) {
                    case 1:
                        AcceptAllSentOffers();
                        break;
                    case 2:
                        RejectAllSentOffers();
                        break;
                    case 3:
                        AcceptSelectedSentOffer();
                        break;
                    case 4:
                        RejectSelectedSentOffer();
                        break;
                }
            } while (c != 5 && !SentOffersDB.isEmpty());
        }
    }

    private void displayAllSentOffers() {
        ArrayList<Offer> list = SentOffersDB.get_sent_offers();
        Util.PrintOfferHeader();
        for (int i = 0; i < list.size(); i++) {
            Util.PrintOffer(list.get(i));
        }
    }

    private void AcceptAllSentOffers() {
        ArrayList<Offer> list = SentOffersDB.get_sent_offers();
        for (int i = 0; i < list.size(); i++) {
            Offer o = list.get(i);
            Product p = ProductDB.get_Product(o.getPSN());
            ProductDB.update_product(o.getPSN(), p.getDiscount() + o.getDiscount());
            SentOffersDB.delete_sent_offer(o.getId());
            Util.registerAction(this.getId(), "Accept-Offer SN :(" + o.getPSN() + ") Discount :(" + o.getDiscount() + ").");
        }
        System.out.println("\nAll Accepted!\n");
    }

    private void RejectAllSentOffers() {
        ArrayList<Offer> list = SentOffersDB.get_sent_offers();
        for (int i = 0; i < list.size(); i++) {
            Offer o = list.get(i);
            SentOffersDB.delete_sent_offer(o.getId());
            Util.registerAction(this.getId(), "Reject-Offer SN :(" + o.getPSN() + ") Discount :(" + o.getDiscount() + ").");
        }
        System.out.println("\nAll Rejected!\n");
    }

    private void AcceptSelectedSentOffer() {
        System.out.printf("Enter The Offer ID: ");
        int id = Check.CheckOfferID();
        if (id == -1) {
            System.out.println("No Offer Accepted!");
        } else {
            Offer o = SentOffersDB.get_sent_offer(id);
            Product p = ProductDB.get_Product(o.getPSN());
            ProductDB.update_product(o.getPSN(), p.getDiscount() + o.getDiscount());
            SentOffersDB.delete_sent_offer(o.getId());
            System.out.println("\nAccepted!\n");
            Util.registerAction(this.getId(), "Accept-Offer SN :(" + o.getPSN() + ") Discount :(" + o.getDiscount() + ").");
        }
    }

    private void RejectSelectedSentOffer() {
        System.out.printf("Enter The Offer ID: ");
        int id = Check.CheckOfferID();
        if (id == -1) {
            System.out.println("No Offer Rejected!");
        } else {
            Offer o = SentOffersDB.get_sent_offer(id);
            SentOffersDB.delete_sent_offer(o.getId());
            System.out.println("\nRejected!\n");
            Util.registerAction(this.getId(), "Reject-Offer SN :(" + o.getPSN() + ") Discount :(" + o.getDiscount() + ").");
        }
    }

}
