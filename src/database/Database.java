/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import abitazione.Abitazione;

/**
 *
 * @author David
 */
public class Database {
    private static String cmdStart = "net START MySQL";
    private static String cmdStop = "net STOP MySQL";
    private static Connection conn;
    private static Statement st;
    public static Abitazione ab;
    
    private static void startServer() {
        try {
            System.out.println("Avviamento server MySQL...");
            Process p = Runtime.getRuntime().exec(cmdStart);
            System.out.println("Server MySQL in funzione.");
        } 
        catch (IOException e) {
            String msg = "ERRORE! \n" + "Impossibile avviare il server MySQL: \n\n" + 
                            e.toString() + "\n" + stackTrace2String(e.getStackTrace());
            JOptionPane.showMessageDialog(null, msg, "InfoBox: " + "attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private static void stopServer() {
        try {
            System.out.println("Chiusura server MySQL...");
            Process p = Runtime.getRuntime().exec(cmdStop);
            System.out.println("Server MySQL non in funzione.");
        } 
        catch (IOException e) {
            String msg = "ERRORE! \n" + "Impossibile chiudere il server MySQL: \n\n" + 
                            e.toString() + "\n" + stackTrace2String(e.getStackTrace());
            JOptionPane.showMessageDialog(null, msg, "InfoBox: " + "attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    
    private static String stackTrace2String(StackTraceElement[] st) {
        String tmp = "";
        for(int i = 0; i < st.length; i++) {
            tmp += "\t" + st[i].toString() + ";\n";
        }
        return tmp;
    }
    
    
    public static void creaConnessione() {
        startServer();
        try {
            Class.forName("com.mysql.jdbc.Driver");  
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ourhouse", "root", "root");  
        }
        catch(ClassNotFoundException e) {
            String msg = "ERRORE!\n" + "Driver non trovati: \n\n\t" + 
                            e.toString() + "\n" + stackTrace2String(e.getStackTrace());
            JOptionPane.showMessageDialog(null, msg, "InfoBox: " + "attenzione", JOptionPane.WARNING_MESSAGE);
        }
        catch(SQLException e) {
            String msg = "ERRORE! \n" + "Impossibile stabilire una connessione al DB: \n\n" + 
                            e.toString() + "\n" + stackTrace2String(e.getStackTrace());
            JOptionPane.showMessageDialog(null, msg, "InfoBox: " + "attenzione", JOptionPane.WARNING_MESSAGE);
        }
        catch(Exception e) {
            String msg = "ERRORE! \n" + "Errore generico: \n\n" + 
                            e.toString() + "\n" + stackTrace2String(e.getStackTrace());
            JOptionPane.showMessageDialog(null, msg, "InfoBox: " + "attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static boolean authenticate(String cod_abitazione,String password)
    {
        boolean ok=true;
        String where="cod_abitazione='"+cod_abitazione+"' and password ='"+password+"';";
        ArrayList<String> r;
        try {
                r=selectQuery("cod_abitazione,nome_abitazione", "abitazioni",where);
                if(!r.isEmpty())
                {
                    JOptionPane.showMessageDialog(null ,"Login eseguito con successo" , "Info: " + "Connessione riuscita!",JOptionPane.NO_OPTION);
                    ab=new Abitazione();
                    ab.set(r.get(0), r.get(1));
                }
                else
                {
                    JOptionPane.showMessageDialog(null ,"Abitazione non trovata" , "Info: " + "Errore!",JOptionPane.NO_OPTION);
                    ok=false;
                }
            }
            catch(Exception e) {
                String msg = "ERRORE! \n"  + 
                                e.toString() + "\n" + stackTrace2String(e.getStackTrace());
                JOptionPane.showMessageDialog(null, msg, "InfoBox: " + "attenzione", JOptionPane.WARNING_MESSAGE);
                ok = false;
            }
        
        
        
        
        return ok;
       
    }
    
    public static ArrayList<String> selectQuery(String attributes, String from) {
        ResultSet rs = null;
        ArrayList<String> s = new ArrayList<String>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT " + attributes + " FROM " + from);
            ResultSetMetaData rsData = rs.getMetaData();
            int col = rsData.getColumnCount();
            while(rs.next()) {
                for(int i = 1; i <= col; i++) {
                    s.add(rs.getString(i));
                }
            }
            System.out.print(s);
            st.close();
        }
        catch(SQLException e) {
            String msg = "ERRORE! \n" + "Impossibile interrogare il DB: \n\n" + 
                            e.toString() + "\n" + stackTrace2String(e.getStackTrace());
            JOptionPane.showMessageDialog(null, msg, "InfoBox: " + "attenzione", JOptionPane.WARNING_MESSAGE);
        }
        finally {
            return s;
        }
    }
    public static ArrayList<String> selectQuery(String attributes, String from, String where) {
        ResultSet rs = null;
        ArrayList<String> s = new ArrayList<String>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT " + attributes + " FROM " + from + " WHERE " + where);
            ResultSetMetaData rsData = rs.getMetaData();
            int col = rsData.getColumnCount();
            while(rs.next()) {
                for(int i = 1; i <= col; i++) {
                    s.add(rs.getString(i));
                }
            }
            System.out.print(s);
            st.close();
        }
        catch(SQLException e) {
            String msg = "ERRORE! \n" + "Impossibile interrogare il DB: \n\n" + 
                            e.toString() + "\n" + stackTrace2String(e.getStackTrace());
            JOptionPane.showMessageDialog(null, msg, "InfoBox: " + "attenzione", JOptionPane.WARNING_MESSAGE);
        }
        finally {
            return s;
        }
    }
    
}
