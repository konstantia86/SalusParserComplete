

package salusdatabaseparser;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * @author Konstantia
 */
public class DBConnect {
  
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public DBConnect(){
    
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/salus_forensics", "root", "12345");
            System.out.println("I'm in the DB bitches!");
            
            
            
        } catch (Exception e){
            System.out.println("To mpoulo:"+e);
        }
           
       
    }
}
