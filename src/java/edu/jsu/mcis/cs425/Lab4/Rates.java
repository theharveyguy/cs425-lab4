package edu.jsu.mcis.cs425.Lab4;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Rates {
    
    public static final String RATE_FILENAME = "rates.csv";
    
    public static List<String[]> getRates(String path) {
        
        StringBuilder s = new StringBuilder();
        List<String[]> data = null;
        String line;
        
        try {
            
            /* Open Rates File; Attach BufferedReader */
            
            BufferedReader reader = new BufferedReader(new FileReader(path));
            
            /* Get File Data */
            
            while((line = reader.readLine()) != null) {
                s.append(line).append('\n');
            }
            
            reader.close();
            
            /* Attach CSVReader; Parse File Data to List */
            
            CSVReader csvreader = new CSVReader(new StringReader(s.toString()));
            data = csvreader.readAll();
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        /* Return List */
        
        return data;
        
    }
    
    public static String getRatesAsTable(List<String[]> csv) {
        
        StringBuilder s = new StringBuilder();
        String[] row;
        
        try {
            
            /* Create Iterator */
            
            Iterator<String[]> iterator = csv.iterator();
            
            /* Create HTML Table */
            
            s.append("<table>");
            
            while (iterator.hasNext()) {
                
                /* Create Row */
                
                row = iterator.next();
                s.append("<tr>");
                
                for (int i = 0; i < row.length; ++i) {
                    s.append("<td>").append(row[i]).append("</td>");
                }
                
                /* Close Row */
                
                s.append("</tr>");
            
            }
            
            /* Close Table */
            
            s.append("</table>");
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        /* Return Table */
        
        return (s.toString());
        
    }
    
    public static String getRatesAsJson(List<String[]> csv) {
        
        String results = "";
        String[] row;
        
        try {
            
            /* Create Iterator */
            
            Iterator<String[]> iterator = csv.iterator();
            
            /* Create JSON Containers */
            
            JSONObject json = new JSONObject();
            JSONObject rates = new JSONObject();            
            
            /* 
             * Add rate data to "rates" container and add "date" and "base"
             * values to "json" container.  See the "getRatesAsTable()" method
             * for an example of how to get the CSV data from the list, and
             * don't forget to skip the header row!
             *
             * *** INSERT YOUR CODE HERE ***
             */
            row = iterator.next();
            
            while (iterator.hasNext()){
                row = iterator.next();
                Double rate = Double.parseDouble(row[2]); // converts the string rate data to double
                rates.put(row[1], rate); // loads string code data and double rate data to rates JSON object
            }
            
            // put rates JSON object into json JSON object and add Date and Base attributes
            
            json.put("Rates", rates);
            json.put("Date", "2019-9-20");
            json.put("Base", "USD");

            /* Parse top-level container to a JSON string */
            
            results = JSONValue.toJSONString(json);
            
            System.err.println(results);
            
        } //Lab4A's work is in this method -MH
        
        catch (Exception e) { System.err.println( e.toString() ); }
        
        /* Return JSON string */
        
        return (results.trim());
        
    }
    public static String getRatesAsJson(String code){
        //db pool variables
        Database db = null;
        Connection connection;
        //SQL variables
        String query;
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        boolean hasResult;
        //JSON variables
        String results = "";
        JSONObject json = new JSONObject();
        JSONObject rates = new JSONObject();

        try{
            db = new Database();
            connection = db.getConnection();
            
            query = "SELECT rate FROM rates WHERE code = "+code;
            
            pstatement = connection.prepareStatement(query);
            hasResult = pstatement.execute();
            
            if (hasResult){
                resultset = pstatement.getResultSet(); // only result should be rate data
                rates.put(code, resultset);
            }//collect data and package it in JSON, bow optional
            json.put("Rates", rates);
            json.put("Date", "2019-9-20");
            json.put("Base", "USD");
            
            results = JSONValue.toJSONString(json);
        }
        
        catch (Exception e) { System.err.println( e.toString() ); }
        
        finally {
                        
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
            if (db != null) { db.closeConnection(); }
            
        }// Close db pool connection and clear db variables
        
        return(results.trim());
    } //Lab4B's work is in here -MH
}

class Database{
    Context envContext = null, initContext = null;
    DataSource ds = null;
    Connection conn = null;

    public Database() throws NamingException {
        try {
            envContext = new InitialContext();
            initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();   
        }
        catch (SQLException e) {}
    } // Constructor
    
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {}   
        }
    } // End closeConnection()
    
    public Connection getConnection() { return conn; }
} // Database pool class, repurposed from Lab3B - MH