/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.Action;
import java.sql.ResultSet;

/**
 *
 * @author ahmad
 */
public class PreviousActionsDB {

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DriverManager.getConnection("jdbc:sqlite:system.db");
    }

    public static void add_action(Action ac) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("insert into pre_actions values(?,?,?)");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setInt(1, ac.geteID());
            p.setString(2, ac.getActionName());
            p.setString(3, ac.getActionDate());

            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static ArrayList<Action> get_actions(int e_id) {
        ArrayList<Action> list = new ArrayList<>();
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from pre_actions where e_id = ?");) {
            {
                p.setInt(1, e_id);
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    list.add(new Action(r.getInt("e_id"), r.getString("action_name"), r.getString("action_date")));
                }
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return list;
    }

}
