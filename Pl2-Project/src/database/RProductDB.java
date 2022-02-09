package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.Product;
import java.util.ArrayList;
import project.InventoryEmployee;

public class RProductDB {

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DriverManager.getConnection("jdbc:sqlite:system.db");
    }

    public static void add_RProduct(Product prod) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("insert into RProduct(name,orignal_price,discount,amount,EPD,minRange,state) values(?,?,?,?,?,?,?)");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setString(1, prod.getName());
            p.setDouble(2, prod.getOrignalPrice());
            p.setDouble(3, prod.getDiscount());
            p.setInt(4, prod.getAmount());
            p.setString(5, prod.getEPD());
            p.setInt(6, prod.getMinRange());
            p.setString(7, InventoryEmployee.updateProductState(prod.getEPD()));
            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static void delete_RProduct(int SN) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("delete from RProduct where SN = ?");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setInt(1, SN);

            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static void update_RProduct(int SN, String name, double orignalPrice, double discount, int amount, String EPD, int minRange) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("UPDATE RProduct SET name = ?, orignal_price = ?, discount = ?, amount = ?,EPD = ?,minRange = ? ,state = ? WHERE SN = ?");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setString(1, name);
            p.setDouble(2, orignalPrice);
            p.setDouble(3, discount);
            p.setInt(4, amount);
            p.setString(5, EPD);
            p.setInt(6, minRange);
            p.setString(7, InventoryEmployee.updateProductState(EPD));
            p.setInt(8, SN);

            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static void update_RProduct(int SN, double discount) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("UPDATE RProduct SET discount = ? WHERE SN = ?");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setDouble(1, discount);
            p.setInt(2, SN);

            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static void update_RProducts_states() {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("UPDATE RProduct SET state = ? WHERE SN = ?");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();

            ArrayList<Product> list = RProductDB.get_RProducts();

            for (int i = 0; i < list.size(); i++) {
                p.setString(1, InventoryEmployee.updateProductState(list.get(i).getEPD()));
                p.setInt(2, list.get(i).getSN());
                p.execute();
            }

        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static ArrayList<Product> get_RProducts() {
        ArrayList<Product> list = new ArrayList<>();
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from RProduct");) {
            {
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    list.add(new Product(r.getInt("SN"), r.getString("name"), r.getDouble("orignal_price"), r.getDouble("discount"), r.getInt("amount"), r.getString("EPD"), r.getInt("minRange"), r.getString("state")));
                }
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return list;
    }
    
    public static boolean isExist(int sn) {
        ArrayList<Product> list = RProductDB.get_RProducts();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSN() == sn) {
                return true;
            }
        }
        return false;
    }
    
    public static Product get_RProduct(int SN) {
        ArrayList<Product> list = new ArrayList<>();
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from RProduct where SN = ?");) {
            {
                p.setInt(1, SN);
                ResultSet r = p.executeQuery();

                while (r.next()) {
                    return new Product(r.getInt("SN"), r.getString("name"), r.getDouble("orignal_price"), r.getDouble("discount"), r.getInt("amount"), r.getString("EPD"), r.getInt("minRange"), r.getString("state"));
                }
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return new Product();
    }

    public static ArrayList<Product> get_ERProducts() {
        ArrayList<Product> list = new ArrayList<>();
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from RProduct where state = 'E'");) {
            {
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    list.add(new Product(r.getInt("SN"), r.getString("name"), r.getDouble("orignal_price"), r.getDouble("discount"), r.getInt("amount"), r.getString("EPD"), r.getInt("minRange"), r.getString("state")));
                }
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return list;
    }

    public static boolean isExsist(int sn) {
        ArrayList<Product> list = RProductDB.get_RProducts();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSN() == sn) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty() {
        ArrayList<Product> list = RProductDB.get_RProducts();

        if (list.isEmpty()) {
            return true;
        }
        return false;
    }
}
