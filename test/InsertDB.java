package test;

import java.sql.*;
import java.util.Scanner;

public class InsertDB {
   Connection con;
   public InsertDB(String user, String passwd) {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         String url = "jdbc:mysql://localhost:3306/test";
         con = DriverManager.getConnection(url, user, passwd);
      } catch (Exception e) {
         System.out.println("연결 실패");
         e.printStackTrace();
      }
   }
   public void addUser(int score, String name) throws SQLException {
      String sql = "insert into user(score, uname) values(?, ?)";
      PreparedStatement st = con.prepareStatement(sql);
      st.setInt(1, score);
      st.setString(2, name);
      st.executeUpdate();
   }
   
   public void close() throws SQLException {
      con.close();
   }
   
   public static void main(String[] args) throws Exception
   {  
	   TetrisCanvas tetrisCanvas = new TetrisCanvas();
       
	   String name="";
	   int score=0;
	   score = tetrisCanvas.getScore();
      Scanner sc = new Scanner(System.in);
      sc.close();
      
      score = tetrisCanvas.getScore();
      InsertDB db = new InsertDB("root", "rkarbfgid819");
      db.addUser(score, name);
      db.close();
      

   }

}