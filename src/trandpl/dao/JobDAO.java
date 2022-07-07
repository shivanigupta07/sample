
package trandpl.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import trandpl.dbutil.DBConnection;
import trandpl.pojo.JobPojo;
import trandpl.pojo.ParticipantJobPojo;

/**
 *
 * @author shiva
 */
public class JobDAO {
    public static int getNewJobId() throws SQLException{
          Connection conn = DBConnection.getConnection();
      Statement st= conn.createStatement();
      ResultSet rs=st.executeQuery("Select max(jobId) from jobs");
      rs.next();
      String strid=rs.getString(1);
       int jobId=101;
        if(strid!=null){
          String id=strid.substring(4);
         jobId=Integer.parseInt(id)+1;
      }
        return jobId;
    }
    public static boolean addNewHr(JobPojo job)throws SQLException{
        Connection conn = DBConnection.getConnection();
      PreparedStatement ps=conn.prepareStatement("insert into Jobs values(?,?,?,?,?)");
      ps.setString(1,job.getJobId());
       ps.setString(2,job.getJobTitle());
         ps.setString(3,job.getHrID());
          ps.setString(4,job.getTags());
           ps.setInt(5,job.getStatus());
           return 1==ps.executeUpdate();
}
    public static List<JobPojo> getAllActiveJobsByCurrentHr(String hrId)throws SQLException{
         Connection conn = DBConnection.getConnection();
   PreparedStatement ps=conn.prepareStatement("select jobid,jobtitle,tags,status from jobs where hrid=? and status!=-1");
          ps.setString(1,hrId);
           ResultSet rs=ps.executeQuery();
             List<JobPojo> allJobsList = new ArrayList<>();
             while(rs.next()){
            JobPojo obj=new JobPojo();
     obj.setJobId(rs.getString(1));
      obj.setJobTitle(rs.getString(2));
      obj.setTags(rs.getString(3));
      obj.setStatus(rs.getInt(4));
     allJobsList.add(obj);
 }
 return allJobsList;
    }
    public static boolean removeJobByJobId(String jobId)throws SQLException{
         Connection conn = DBConnection.getConnection();
         PreparedStatement ps=conn.prepareStatement("update jobs set status=-1 where jobid=?");
         ps.setString(1, jobId);
         return ps.executeUpdate()==1;
    }
        public static List<JobPojo> getAllEditableJobsByCurrentHr(String hrId)throws SQLException{
   Connection conn=DBConnection.getConnection();
  PreparedStatement ps=conn.prepareStatement
        ("select jobid,jobtitle,tags,status from jobs where hrid=? and status=0");
 ps.setString(1,hrId);
 ResultSet rs=ps.executeQuery();
     List<JobPojo> allJobsList =new ArrayList<>();
 while(rs.next()){
     JobPojo obj=new JobPojo();
     obj.setJobId(rs.getString(1));
   
      obj.setJobTitle(rs.getString(2));
      obj.setTags(rs.getString(3));
      obj.setStatus(rs.getInt(4));
     allJobsList.add(obj);
 }
 return allJobsList;  
   }
  public static boolean editJobByJobId(JobPojo job)throws SQLException{
  Connection conn=DBConnection.getConnection();
  PreparedStatement ps=conn.prepareStatement("update Jobs set Jobtitle=?,tags=? where jobid=? ");
            ps.setString(1,job.getJobTitle());
             ps.setString(2,job.getTags());
              ps.setString(3,job.getJobId());
              return 1==ps.executeUpdate();
        }

        public static void setJobStatus(String jobId)throws SQLException{
            Connection conn=DBConnection.getConnection();
              PreparedStatement ps=conn.prepareStatement("update Jobs set status = 1 where jobId=? ");
              ps.setString(1,jobId);
              ps.executeUpdate();
        }
          public static List<JobPojo> getAllOpenJobsByCurrentHr(String hrId)throws SQLException{
            Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement("select jobid,jobtitle,tags,status from jobs where hrid=? and status=0 order by  jobid");
            ps.setString(1,hrId);
            ResultSet rs=ps.executeQuery();
     List<JobPojo> allJobsList =new ArrayList<>();
 while(rs.next()){
     JobPojo obj=new JobPojo();
     obj.setJobId(rs.getString(1));
      obj.setJobTitle(rs.getString(2));
      obj.setTags(rs.getString(3));
      obj.setStatus(rs.getInt(4));
     allJobsList.add(obj);
 }
 return allJobsList;  
   }
          public static List<ParticipantJobPojo>getAllAvailableJobs() throws SQLException{
                 Connection conn = DBConnection.getConnection();
      Statement st= conn.createStatement();
      ResultSet rs=st.executeQuery("Select jobid,jobtitle,companyname,tags,jobs.hrid from jobs,hr where jobs.hrid=hr.hrid and status=1 order by jobid");
         List<ParticipantJobPojo> allOpenJobsList=new ArrayList<>();
         while(rs.next()){
             ParticipantJobPojo pt=new ParticipantJobPojo();
             pt.setJobId(rs.getString(1));
             pt.setJobTitle(rs.getString(2));
             pt.setCompanyName(rs.getString(3));
             pt.setTags(rs.getString(4));
               pt.setHrID(rs.getString(5));
               allOpenJobsList.add(pt);
         }
         return allOpenJobsList;
          }
}