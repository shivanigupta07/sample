/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trandpl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import trandpl.dbutil.DBConnection;
import trandpl.pojo.ParticipantJobPojo;
import trandpl.pojo.ResultsPojo;

/**
 *
 * @author shiva
 */
public class ResultDAO {
    public static boolean applyForJob(ResultsPojo result) throws SQLException{
        Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement("select * from results where participantid=? and jobid=?");
   ps.setString(1, result.getpId());
   ps.setString(2, result.getJobId());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return false;
            }
            ps=conn.prepareStatement("insert into results values(?,?,?,?)");
            ps.setString(1,result.getpId());
              ps.setString(2,result.getJobId());
                ps.setDouble(3,result.getPercentage());
                  ps.setString(4,result.getSelectedByHr());
                  return 1==ps.executeUpdate();
    }
    public static List<ParticipantJobPojo>getAllAppliedJobs(String ParticipantId)throws SQLException{
     Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement
        ("select  jobs.jobid,jobs.jobtitle,companyname,tags from jobs,results,hr where jobs.jobid=results.jobid and jobs.hrid =hr.hrid and participantid=?and percentage =-1");      
   ps.setString(1, ParticipantId);
            List<ParticipantJobPojo>allAppliedJobsList=new ArrayList<>();
  
   ResultSet rs=ps.executeQuery();
   while(rs.next()){
  ParticipantJobPojo pj=new ParticipantJobPojo();  
pj.setJobId(rs.getString(1));
pj.setJobTitle(rs.getString(2));
pj.setCompanyName(rs.getString(3));
pj.setTags(rs.getString(4));
allAppliedJobsList.add(pj);
   }
   return allAppliedJobsList;
}
    public static boolean setResult(ResultsPojo rs) throws SQLException{
        PreparedStatement ps;
        ps=DBConnection.getConnection().prepareStatement("update results set percentage=? where participantid=? and jobid=?");
    ps.setDouble(1, rs.getPercentage());
    ps.setString(2, rs.getpId());
    ps.setString(3,rs.getJobId());
    return 1==ps.executeUpdate();
    }
}