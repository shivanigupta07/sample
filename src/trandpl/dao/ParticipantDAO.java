/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trandpl.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import trandpl.dbutil.DBConnection;
import trandpl.pojo.ParticipantPojo;

/**
 *
 * @author shiva
 */
public class ParticipantDAO {
    public static int getNewParticipantId() throws SQLException{
      Connection conn = DBConnection.getConnection();
      Statement st= conn.createStatement();
      ResultSet rs=st.executeQuery("Select max(pid) from participants");
      int pId=101;
     rs.next();
          String strid=rs.getString(1);
        if(strid!=null){
          String id=strid.substring(3);
         pId=Integer.parseInt(id)+1;
      }
      return pId;
    }
    public static int addNewParticipant(ParticipantPojo Pt)throws SQLException,FileNotFoundException{
        Connection conn = DBConnection.getConnection();
      PreparedStatement ps=conn.prepareStatement("select userid from users where userid=?");
      ps.setString(1,Pt.getUserId().toUpperCase().trim());
       ps.setString(2,Pt.getId().trim());
      ResultSet rs=ps.executeQuery();
      if(rs.next()){
          return -1;
      }
      ps=conn.prepareStatement("insert into users values (?,?,?,?,?,?)");
       ps.setString(1,Pt.getUserId());
        ps.setString(2,Pt.getId());
         ps.setString(3,Pt.getName());
          ps.setString(4,Pt.getPassword());
           ps.setString(5,Pt.getType());
            ps.setString(6,"Y");
            int x=ps.executeUpdate();
            int y=0;
            if(x==1){
              ps=conn.prepareStatement("Insert into participants values(?,?,?,?,?)");
              ps.setString(1,Pt.getId());
              ps.setString(2,Pt.getPhone());
              ps.setString(3,Pt.getSkill());
              ps.setString(4,Pt.getQualifications());
              File f= Pt.getResume();
               InputStream fin=new FileInputStream(f.getPath());
              ps.setBlob(7,fin);
              y=ps.executeUpdate();  }     
            return y;
}
}