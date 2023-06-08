package test;
import java.sql.*;

public class SearchDB {
   Connection con;
   public String[] result= new String[9];
   
   public SearchDB(String user, String passwd) {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         String url = "jdbc:mysql://localhost:3306/test";
         con = DriverManager.getConnection(url, user, passwd);
      } catch(Exception e) {
         System.out.println("데이터베이스 연결 실패:");
         e.printStackTrace();
      }
   }
   // 테이블 모든 데이터 읽어오기
   public void getAll() throws SQLException {
	  int count =0;
	  String sql = "select * from user ORDER BY score DESC ";
      PreparedStatement st = con.prepareStatement(sql);
      ResultSet rs = st.executeQuery();
      while(rs.next()) {
         int score = rs.getInt(1);
         String uname = rs.getString(2);
         System.out.printf("플레이어 : %s 점수 : %d\n", uname, score);
         try
         {
        	 result[count] = Integer.toString(count+1) + "등 \t"+"플레이어 : "+uname+"  점수 : "+Integer.toString(score)+"\n";
         }
        
         catch(Exception e)
         {
        	e.printStackTrace();
         }
         count = count +1;
      }
   }
   
   public void close() throws SQLException {
      con.close();
   }
   public String[] getResult()
   {
	   return result;
   }
   
   public static void main(String args[]) throws SQLException {
      SearchDB db = new SearchDB("root", "rkarbfgid819");
      System.out.println("---------------");
      db.getAll();
      db.close();
   }
}