package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.Employee;
import java.util.ArrayList;
import project.AdminEmployee;
import project.InventoryEmployee;
import project.MarktingEmployee;
import project.SalesEmployee;

public class EmployeeDB {

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DriverManager.getConnection("jdbc:sqlite:system.db");
    }

    public static void add_employee(Employee emp) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("insert into employee(fname,lname,username,password,type) values(?,?,?,?,?)");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setString(1, emp.getfName());
            p.setString(2, emp.getlName());
            p.setString(3, emp.getUserName());
            p.setString(4, emp.getPassword());
            p.setString(5, emp.getEType());
            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static void delete_employee(int id) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("delete from employee where id = ? ");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setInt(1, id);

            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static void update_employee(int id, String fname, String lname, String username, String password, String type) {
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("UPDATE employee SET fname = ?, lname = ?, username = ?, password = ?,type = ? WHERE id = ?");
                PreparedStatement p1 = con.prepareStatement("PRAGMA foreign_keys = ON;");) {
            p1.execute();
            p.setString(1, fname);
            p.setString(2, lname);
            p.setString(3, username);
            p.setString(4, password);
            p.setString(5, type);
            p.setInt(6, id);

            p.execute();
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
    }

    public static ArrayList<Employee> get_employees() {
        ArrayList<Employee> list = new ArrayList<>();
        String c = "A";
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from employee");) {
            {
                ResultSet r = p.executeQuery();
                while (r.next()) {
                    if (r.getString("type") != null) {
                        c = r.getString("type");
                    }
                    if (c.equals("A")) {
                        list.add(new AdminEmployee(r.getInt("id"), r.getString("fname"), r.getString("lname"), r.getString("username"), r.getString("password")));
                    } else if (c.equals("M")) {
                        list.add(new MarktingEmployee(r.getInt("id"), r.getString("fname"), r.getString("lname"), r.getString("username"), r.getString("password")));
                    } else if (c.equals("I")) {
                        list.add(new InventoryEmployee(r.getInt("id"), r.getString("fname"), r.getString("lname"), r.getString("username"), r.getString("password")));
                    } else if (c.equals("S")) {
                        list.add(new SalesEmployee(r.getInt("id"), r.getString("fname"), r.getString("lname"), r.getString("username"), r.getString("password")));
                    }
                }
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return list;
    }

    public static Employee get_employee(int id) {
        ArrayList<Employee> list = new ArrayList<>();
        try (
                Connection con = connect();
                PreparedStatement p = con.prepareStatement("select * from employee where id = ?");) {
            {
                p.setInt(1, id);
                ResultSet r = p.executeQuery();
                String c;
                while (r.next()) {
                    c = r.getString("type");
                    if (c.equals("A")) {
                        return new AdminEmployee(r.getInt("id"), r.getString("fname"), r.getString("lname"), r.getString("username"), r.getString("password"));
                    } else if (c.equals("M")) {
                        return new MarktingEmployee(r.getInt("id"), r.getString("fname"), r.getString("lname"), r.getString("username"), r.getString("password"));
                    } else if (c.equals("I")) {
                        return new InventoryEmployee(r.getInt("id"), r.getString("fname"), r.getString("lname"), r.getString("username"), r.getString("password"));
                    } else if (c.equals("S")) {
                        return new SalesEmployee(r.getInt("id"), r.getString("fname"), r.getString("lname"), r.getString("username"), r.getString("password"));
                    }
                }
            }
        } catch (SQLException ee) {
            System.out.println(ee.getMessage());// we will put out custimize exption massages here
        }
        return new Employee();
    }

    public static boolean isExist(int id) {
        ArrayList<Employee> list = EmployeeDB.get_employees();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty() {
        ArrayList<Employee> list = EmployeeDB.get_employees();

        if (list.isEmpty()) {
            return true;
        }
        return false;
    }
}
