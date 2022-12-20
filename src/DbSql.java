import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DbSql {
    private Connection connection;
    private Statement stmt;

    DbSql() {
        connection = null;
        stmt = null;
        try {
            String url = "jdbc:sqlite:C://Users/weste/Desktop/skoleting/Programmering/Semester_Projekt_1/Database1.db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void opretBruger(User b) {
        String sql = "INSERT INTO brugere (brugernavn,brugerkode)" +
                "VALUES('"+b.getUserName() + "','" + b.getUserPass()+"')";
        try {
            Statement stmt=connection.createStatement();
            stmt.execute(sql);
            System.out.println("Connection to SQLite has been established. Your account has been saved.");
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addAWish(User u, Gave g) {
        String sql = "INSERT INTO gaveliste (gave,gavemodtager,gavegiver,gavepris,bought)" +
                "VALUES('"+g.getGave() + "','" + g.getGaveModtager() + "','" + g.getGaveGiver() + "','" + g.getGavePris() + "','" + g.getBought() + "')";
        try {
            Statement stmt=connection.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("addAWish fejl");
        }
    }

    public Gave getGavelisteIDDB(User u, Gave g) {
        String sql = "SELECT * from gaveliste where gave='"+g.getGave()+"'";
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                g.setGavelisteID(rs.getInt(1));
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i getGavelisteIDDB");
        }
        return g;
    }

    public void connectUserAndGave(User b, Gave g) {
        String sql = "INSERT INTO brugeronske (brugerid,gavelisteid)" +
                "VALUES("+b.getUserID() + "," + g.getGavelisteID()+")";
        try {
            connection.setAutoCommit(true);
            Statement stmt=connection.createStatement();
            stmt.execute(sql);
            System.out.println("Connection to SQLite has been established. Your gift has been saved.");
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i connectUserAndGave");
        }
    }

    public User getUser(String userName, String userPass) {
        User b = new User();
        String sql = "SELECT * from brugere where brugernavn='"+userName+"' AND brugerkode='"+userPass+"'";
        try {
            Statement stmt=connection.createStatement();
            connection.setAutoCommit(true);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                b.setUserID(rs.getInt(1));
                b.setUserName(rs.getString(2));
                b.setUserPass(rs.getString(3));
            } else {
                System.out.println();
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i getUser");
        }
    return b;
    }

    public User getUserFromID(int userID) {
        User b = new User();
        String sql = "SELECT * from brugere where brugerid="+String.valueOf(userID);
        try {
            connection.setAutoCommit(true);
            Statement stmt=connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                b.setUserID(rs.getInt(1));
                b.setUserName(rs.getString(2));
                b.setUserPass(rs.getString(3));
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i getUserFromID");
        }
        return b;
    }

    public Gave getGave(String g) {
        Gave g1 = new Gave();
        String sql = "SELECT * from gaveliste where gave='"+g+"'";
        try {
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                g1.setGavelisteID(rs.getInt(1));
                g1.setGave(rs.getString(2));
                g1.setGaveModtager(rs.getString(3));
                g1.setGaveGiver(rs.getString(4));
                g1.setGavePris(rs.getString(5));
                g1.setBought(rs.getString(6));
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i getGave");
        }
        return g1;
    }

    // Del ønske
    public ArrayList<Gave> shareAWish(User u) {
        String sql = "SELECT * from gaveliste where gavegiver='"+u.getUserName()+"'";
        ArrayList<Gave> liste = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Gave g = new Gave();
                g.setGavelisteID(rs.getInt(1));
                g.setGave(rs.getString(2));
                g.setGaveModtager(rs.getString(3));
                g.setGaveGiver(rs.getString(4));
                g.setGavePris(rs.getString(5));
                g.setBought(rs.getString(6));
                liste.add(g);
            }
            return liste;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i shareAWish");
        }
        return null;
    }

    // Fjern ønske

    public void removeAWish(String gave, int gaveID) {
        String sql = "DELETE FROM gaveliste where gave='"+gave+"' AND gavelisteid="+gaveID+"";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            System.out.println("Ønsket er nu fjernet..");
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i removeAWish");
        }
    }

    public void removeConnectionUserWish(User u, Gave g) {
        String sql = "DELETE FROM brugeronske where brugerid='"+u.getUserID()+"' AND gavelisteid='"+g.getGavelisteID()+"'";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            System.out.println("Connection between User and Gift has been removed.");
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i removeConnectionUserWish");
        }
    }

    // Hent andens ønskeliste

    public ArrayList<Gave> getAWishlist(String user) {
        String sql = "SELECT * from gaveliste where gavegiver='"+user+"'";
        ArrayList<Gave> liste1 = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Gave g = new Gave();
                g.setGavelisteID(rs.getInt(1));
                g.setGave(rs.getString(2));
                g.setGaveModtager(rs.getString(3));
                g.setGaveGiver(rs.getString(4));
                g.setGavePris(rs.getString(5));
                g.setBought(rs.getString(6));
                liste1.add(g);
            }
            return liste1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i getAWishlist");
        }
        return null;
    }

    // Marker ønske som købt

    public void markWish(Gave g, User u) {
        String bought = "Ja";
        String sql = "UPDATE gaveliste SET bought='"+bought+"' WHERE gavelisteid="+g.getGavelisteID()+"";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            System.out.println("Gaven " + g.getGave() + " er blevet markeret som købt.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i markWish");
        }
    }

    public User getUserFromName(String username) {
        String sql = "SELECT * from brugere where brugernavn='"+username+"'";
        User u3 = new User();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                u3.setUserID(rs.getInt(1));
                u3.setUserName(rs.getString(2));
                u3.setUserPass(rs.getString(3));
            }
            return u3;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Fejl i getUserFromName");
        }
        return null;
    }
}