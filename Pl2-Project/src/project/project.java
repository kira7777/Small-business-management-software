/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import database.EmployeeDB;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ahmad
 */
public class project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.printf("\nHyper Market Management System\n");
        String username, password;
        int c;
        do {
            System.out.printf("\nMain Menu:\nLogin (Enter 1)\nExit  (Enter 0)\n?:");
            c = Check.CheckNumber();
            if (c != 0 && c != 1) {
                System.out.println("Invaild Input!");
                continue;
            }
            if (c == 0) {
                break;
            }
            System.out.printf("\nEnter User-Name: ");
            username = Check.CheckUsername();
            System.out.printf("Enter Password : ");
            password = input.nextLine();
            if (!userExsist(username, password)) {
                System.out.println("User Not Found!");
                continue;
            }

            ArrayList<Employee> list = EmployeeDB.get_employees();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUserName().equals(username) && list.get(i).getPassword().equals(password)) {
                    if (list.get(i).getEType().equals("A")) {
                        AdminEmployee admin = new AdminEmployee(list.get(i).getId(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getUserName(), list.get(i).getPassword());
                        admin.openList();
                    }
                    if (list.get(i).getEType().equals("M")) {
                        MarktingEmployee marktingEmp = new MarktingEmployee(list.get(i).getId(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getUserName(), list.get(i).getPassword());
                        marktingEmp.openList();
                    }
                    if (list.get(i).getEType().equals("I")) {
                        InventoryEmployee inventoryEmp = new InventoryEmployee(list.get(i).getId(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getUserName(), list.get(i).getPassword());
                        inventoryEmp.openList();
                    }
                    if (list.get(i).getEType().equals("S")) {
                        SalesEmployee salesEmp = new SalesEmployee(list.get(i).getId(), list.get(i).getfName(), list.get(i).getlName(), list.get(i).getUserName(), list.get(i).getPassword());
                        salesEmp.openList();
                    }
                }
            }
        } while (c != 0);
        System.out.println("Exit!");
    }

    public static boolean userExsist(String username, String password) {
        ArrayList<Employee> list = new ArrayList<>();
        list = EmployeeDB.get_employees();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserName().equals(username) && list.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
