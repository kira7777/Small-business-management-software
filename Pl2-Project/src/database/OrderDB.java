package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.Order;
import java.util.ArrayList;

public class OrderDB {

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DriverManager.getConnection("jdbc:sqlite:system.db");
    }

    public static void add_order(Order ord) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("insert into orders(PSN,amount) values(?,?)");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {

            p1.execute();
            p.setInt(1, ord.getPSN());
            p.setInt(2, ord.getAmount());

            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static void delete_order(int id) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("delete from orders where id = ?");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();

            p.setInt(1, id);

            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static void update_order(int PSN, int amount) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("UPDATE orders SET amount = ? WHERE PSN = ?");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setInt(1, amount);
            p.setInt(2, PSN);

            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static ArrayList<Order> get_orders() {
        ArrayList<Order> list = new ArrayList<>();
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from orders");) {
            {
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    list.add(new Order(r.getInt("id"), r.getInt("PSN"), r.getInt("amount")));
                }
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return list;
    }

    public static Order get_order(int id) {
        ArrayList<Order> list = new ArrayList<>();
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from orders where id = ?");) {
            {
                p.setInt(1, id);
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    return new Order(r.getInt("id"), r.getInt("PSN"), r.getInt("amount"));
                }
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return new Order();
    }

    public static boolean isExist(int id) {
        ArrayList<Order> list = OrderDB.get_orders();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty() {
        ArrayList<Order> list = OrderDB.get_orders();

        if (list.isEmpty()) {
            return true;
        }
        return false;
    }
}
