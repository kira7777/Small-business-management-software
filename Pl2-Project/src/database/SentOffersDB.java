/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import project.Offer;

/**
 *
 * @author ahmad
 */
public class SentOffersDB {

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DriverManager.getConnection("jdbc:sqlite:system.db");
    }

    public static void add_sent_offer(Offer o) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("insert into sent_offers(PSN,discount) values(?,?)");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setInt(1, o.getPSN());
            p.setDouble(2, o.getDiscount());
            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static void delete_sent_offer(int id) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("delete from sent_offers where id = ?");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setInt(1, id);
            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static ArrayList<Offer> get_sent_offers() {
        ArrayList<Offer> list = new ArrayList<>();
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from sent_offers");) {
            {
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    list.add(new Offer(r.getInt("id"), r.getInt("PSN"), r.getDouble("discount")));
                }
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return list;
    }

    public static Offer get_sent_offer(int id) {
        Offer o = new Offer();
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from sent_offers where id = ?");) {
            {
                p.setInt(1, id);
                ResultSet r = p.executeQuery();
                o = new Offer(r.getInt("id"), r.getInt("PSN"), r.getDouble("discount"));
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return o;
    }

    public static boolean isEmpty() {
        ArrayList<Offer> list = SentOffersDB.get_sent_offers();

        if (list.isEmpty()) {
            return true;
        }
        return false;
    }
    
    public static boolean isExist(int id) {
        ArrayList<Offer> list = SentOffersDB.get_sent_offers();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }
}
