/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trandpl.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author shiva
 */
public class DBConnection {
     private static Connection conn;
  
      static{
          try{
              Class.forName("oracle.jdbc.OracleDriver");
              conn=DriverManager.getConnection("jdbc:oracle:thin:@//LAPTOP-P6VPQP8K:1521/xe","tnp","project");
              JOptionPane.showMessageDialog(null,"Connect sucessfully to the DB");
          }
          catch(ClassNotFoundException ex){
              JOptionPane.showMessageDialog(null,"error in driver loading"+ex.getMessage());
              ex.printStackTrace();
          }
           catch(SQLException ex){
              JOptionPane.showMessageDialog(null,"error in opening the connection to DB"+ex.getMessage());
              ex.printStackTrace();
          }
      }
  public static Connection getConnection(){
      return conn;
  }
  public static Connection closeConnection(){
      try{
          conn.close();
          JOptionPane.showMessageDialog(null,"DisConnected sucessfully  from the DB");
      }
       catch(SQLException ex){
              JOptionPane.showMessageDialog(null,"error in opening the connection to DB"+ex.getMessage());
              ex.printStackTrace();
          }
     return null;
  }
}
