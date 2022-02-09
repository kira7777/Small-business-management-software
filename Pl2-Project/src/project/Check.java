package project;

import java.util.InputMismatchException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Scanner;
import NewExceptions.*;
import database.EmployeeDB;
import database.ProductDB;
import database.OrderDB;
import database.RProductDB;
import database.SentOffersDB;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class Check {

    static String s;
    static boolean b;
    static Scanner input = new Scanner(System.in);

    public static String CheckFname() {

        do {
            try {
                s = input.next();
                CheckScannedName(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (WronghName e) {
                System.out.println("The name must contain letters only (a->z or A->Z)");
                System.out.print("Try again: ");
                b = true;
            }
        } while (b);
        return s;
    }

    public static String CheckLname() {

        do {
            try {
                s = input.next();
                CheckScannedName(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (WronghName e) {
                System.out.println("The name must be letters only (a->z or A->Z)");
                System.out.print("Try again: ");
                b = true;
            }
        } while (b);
        return s;
    }

    public static String CheckUsername() {

        do {
            try {
                s = input.next();
                CheckScannedUserName(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (WronghUserName e) {
                System.out.println("The Username must contain letters (a->z or A->Z) or positve numbers only");
                System.out.print("Try again: ");
                b = true;
            }
        } while (b);
        return s;
    }

    public static int CheckNumber() {

        do {
            try {
                s = input.nextInt() + "";
                IsNegative(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (InputMismatchException e) {
                System.out.println("The Input must be positive numbers only");
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            }
        } while (b);
        return Integer.parseInt(s);
    }

    public static double CheckDoubleNumber() {

        do {
            try {
                s = input.nextDouble() + "";
                IsNegative(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (InputMismatchException e) {
                System.out.println("The Input must be positive numbers only");
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            }
        } while (b);
        return Double.parseDouble(s);
    }

    public static int CheckID() {

        do {
            try {
                s = input.nextInt() + "";
                IsNegative(s);
                SearchID(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (InputMismatchException e) {
                System.out.println("The ID must be positive numbers only");
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            } catch (IDDoesNotExists e) {
                char c;
                System.out.println("The ID doesn't exists");
                do {
                    System.out.print("Try again (Y / N): ");
                    c = input.next().charAt(0);
                    if (c == 'N' || c == 'n') {
                        return -1;
                    }
                    if (c == 'Y' || c == 'y') {
                        break;
                    }
                } while (c != 'N' || c != 'n' || c != 'Y' || c != 'y');
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            }
        } while (b);
        return Integer.parseInt(s);
    }

    public static String CheckPassword() {

        do {
            try {
                s = input.next();
                IsEmptyInput(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            }
        } while (b);
        return s;
    }

    public static String CheckNewUsername() {

        do {
            try {
                s = input.next();
                CheckScannedNewUserName(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (WronghUserName e) {
                System.out.println("The Username must contain letters (a->z or A->Z) or positve numbers only");
                System.out.print("Try again: ");
                b = true;
            } catch (UserNameExists e) {
                System.out.println("The Username already exists");
                System.out.print("Enter new Username: ");
                b = true;
            }
        } while (b);
        return s;
    }

    public static String CheckExpierdDate() {

        do {
            try {
                s = input.next();
                CheckScannedExpierdDate(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (InputMismatchException e) {
                System.out.println("The Date must be positive numbers only");
                System.out.print("Try again: ");
                b = true;
            } catch (ParseException e) {
                System.out.println("The Date must contain numbers separated by (/) like this (dd/mm/yyyy) ");
                System.out.print("try again: ");
                b = true;
            }
        } while (b);
        return s;
    }

    public static int CheckSerialNumber() {

        do {
            try {
                s = input.nextInt() + "";
                IsNegative(s);
                SearchSN(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (InputMismatchException e) {
                System.out.println("The serial number must be positive numbers only");
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            } catch (IDDoesNotExists e) {
                char c;
                System.out.println("The serial number doesn't exists");
                do {
                    System.out.print("Try again (Y / N): ");
                    c = input.next().charAt(0);
                    if (c == 'N' || c == 'n') {
                        return -1;
                    }
                    if (c == 'Y' || c == 'y') {
                        break;
                    }
                } while (c != 'N' || c != 'n' || c != 'Y' || c != 'y');
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            }
        } while (b);
        return Integer.parseInt(s);
    }

    public static int Check_RP_SerialNumber() {

        do {
            try {
                s = input.nextInt() + "";
                IsNegative(s);
                SearchRPSN(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (InputMismatchException e) {
                System.out.println("The serial number must be positive numbers only");
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            } catch (IDDoesNotExists e) {
                char c;
                System.out.println("The serial number doesn't exists");
                do {
                    System.out.print("Try again (Y / N): ");
                    c = input.next().charAt(0);
                    if (c == 'N' || c == 'n') {
                        return -1;
                    }
                    if (c == 'Y' || c == 'y') {
                        break;
                    }
                } while (c != 'N' || c != 'n' || c != 'Y' || c != 'y');
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            }
        } while (b);
        return Integer.parseInt(s);
    }

    public static int CheckOrderID() {

        do {
            try {
                s = input.nextInt() + "";
                IsNegative(s);
                SearchOrderID(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (InputMismatchException e) {
                System.out.println("The ID must be positive numbers only");
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            } catch (IDDoesNotExists e) {
                char c;
                System.out.println("The ID doesn't exists");
                do {
                    System.out.print("Try again (Y / N): ");
                    c = input.next().charAt(0);
                    if (c == 'N' || c == 'n') {
                        return -1;
                    }
                    if (c == 'Y' || c == 'y') {
                        break;
                    }
                } while (c != 'N' || c != 'n' || c != 'Y' || c != 'y');
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            }
        } while (b);
        return Integer.parseInt(s);
    }

    public static int CheckOfferID() {
        do {
            try {
                s = input.nextInt() + "";
                IsNegative(s);
                SearchOfferID(s);
                b = false;
            } catch (NoInput e) {
                System.out.println("Their is no input");
                System.out.print("Try again: ");
                b = true;
            } catch (InputMismatchException e) {
                System.out.println("The ID must be positive numbers only");
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            } catch (IDDoesNotExists e) {
                char c;
                System.out.println("The ID doesn't exists");
                do {
                    System.out.print("Try again (Y / N): ");
                    c = input.next().charAt(0);
                    if (c == 'N' || c == 'n') {
                        return -1;
                    }
                    if (c == 'Y' || c == 'y') {
                        break;
                    }
                } while (c != 'N' || c != 'n' || c != 'Y' || c != 'y');
                System.out.print("Try again: ");
                input.nextLine();
                b = true;
            }
        } while (b);
        return Integer.parseInt(s);
    }

    private static void CheckScannedName(String s) throws NoInput, WronghName {
        IsEmptyInput(s);
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetter(s.charAt(i))) {
                throw new WronghName();
                //return true;// if it isn't correct
            }
        }
    }

    private static void CheckScannedUserName(String s) throws NoInput, WronghUserName {
        IsEmptyInput(s);
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                throw new WronghUserName();
                //return true;// if it isn't correct
            }
        }
    }

    private static void CheckScannedNewUserName(String s) throws NoInput, WronghUserName, UserNameExists {
        CheckScannedUserName(s);
        SearchUsername(s);
    }

    private static void SearchUsername(String s) throws NoInput, UserNameExists {
        IsEmptyInput(s);
        ArrayList<Employee> list = new ArrayList<>();
        list = EmployeeDB.get_employees();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUserName().equals(s)) {
                throw new UserNameExists();
            }
        }
    }

    private static void SearchID(String s) throws NoInput, IDDoesNotExists {
        IsEmptyInput(s);
        if (!EmployeeDB.isExist(Integer.parseInt(s))) {
            throw new IDDoesNotExists();
        }
    }

    private static void SearchSN(String s) throws NoInput, IDDoesNotExists {
        IsEmptyInput(s);
        if (!ProductDB.isExist(Integer.parseInt(s))) {
            throw new IDDoesNotExists();
        }
    }

    private static void SearchOrderID(String s) throws NoInput, IDDoesNotExists {
        IsEmptyInput(s);
        if (!OrderDB.isExist(Integer.parseInt(s))) {
            throw new IDDoesNotExists();
        }
    }

    private static void SearchOfferID(String s) throws NoInput, IDDoesNotExists {
        IsEmptyInput(s);
        if (!SentOffersDB.isExist(Integer.parseInt(s))) {
            throw new IDDoesNotExists();
        }
    }

    private static void SearchRPSN(String s) throws NoInput, IDDoesNotExists {
        IsEmptyInput(s);
        if (!RProductDB.isExist(Integer.parseInt(s))) {
            throw new IDDoesNotExists();
        }
    }

    private static void IsNegative(String s) throws NoInput, InputMismatchException {
        IsEmptyInput(s);
        if (s.contains("-")) {
            throw new InputMismatchException();
        }
    }

    private static void CheckScannedExpierdDate(String s) throws NoInput, InputMismatchException, ParseException {
        IsNegative(s);
        IsDate(s);
    }

    private static void IsDate(String s) throws NoInput, ParseException {
        IsEmptyInput(s);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        Date javaDate = dateFormat.parse(s);
//        int counter = 0;
//        for (int i = 0; i < s.length(); i++) {
//            if (!Character.isDigit(s.charAt(i)) || s.charAt(i) != '/') {
//                throw new InputMismatchException();
//                //return true;// if it isn't correct
//            }
//            if (s.charAt(i) == '/') {
//                counter++;
//            }
//        }
//        if (counter != 2) {
//            throw new NoInput();
//        }
    }

    private static void IsEmptyInput(String s) throws NoInput {
        if (s.trim().equals("")) {
            throw new NoInput();
        }
    }
}
