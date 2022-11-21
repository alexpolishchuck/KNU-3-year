package Lab7_2;

import Lab8.socket.request.vegetable;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;

public class DataBaseManager {

  public DataBaseManager() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(url,"root", "1234");
      fields = new ArrayList<String>(Arrays.asList("category","name"));

  }
  public void closeConnection() throws SQLException
  {
        connection.close();
  }
 public boolean doesElementExist(final String category,final String name) {
      try {
          Statement statement = connection.createStatement();
          ResultSet rs = statement.executeQuery("SELECT " + getField(parameter.CATEGORY) + " , " +
                  getField(parameter.NAME) + " FROM " + nameOfTable + " WHERE " + getField(parameter.CATEGORY) + " = \"" +
                  category + "\" AND " + getField(parameter.NAME) + " = \"" + name + "\"");

     if(rs.next())
         return true;
      } catch (SQLException e)
      {
          System.out.println(e);
      }
     return false;
 }


  public void addElement(final String category, final String name) throws SQLException {
        if(doesElementExist(category,name))
        {
            System.out.println("This element already exists");
            return;
        }
      Statement statement = connection.createStatement();
     statement.executeUpdate("INSERT INTO " + nameOfTable +" VALUES ( \"" + category + "\" ,\"" + name +"\" )");
  }
    public void deleteElement(final String category, final String name) throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM " + nameOfTable +" WHERE " + getField(parameter.CATEGORY) +" = \"" + category +
                "\" AND " + getField(parameter.NAME) +"= \"" + name +"\" ");
    }
    public void editElement(final String category, final String name, final String newString, parameter param) throws SQLException {
        if(!doesElementExist(category,name))
        {
            System.out.println("This element doesn't exist");
            return;
        }
        Statement statement = connection.createStatement();
        switch (param)
        {
            case CATEGORY:
                statement.executeUpdate("UPDATE " + nameOfTable + " SET " + getField(parameter.CATEGORY) + " = " +
                        "\""+ newString + "\"" + " WHERE " + getField(parameter.CATEGORY) + " = " +
                        "\""+category+"\" AND " + getField(parameter.NAME) + " = \"" + name +"\"");
                break;
            case NAME:
                statement.executeUpdate("UPDATE " + nameOfTable + " SET " + getField(parameter.NAME) + " = " +
                        "\""+ newString + "\"" + " WHERE " + getField(parameter.CATEGORY) + " = " +
                        "\""+category+"\" AND " + getField(parameter.NAME) + " = \"" + name +"\"");
                break;
        }
    }
    public ArrayList<vegetable> showTable() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM " + nameOfTable);
        Formatter formatter = new Formatter();
        formatter.format("%14s %14s\n",getField(parameter.CATEGORY),getField(parameter.NAME));
        ArrayList<vegetable> vegs = new ArrayList<>();
        while(rs.next())
        {
            vegs.add(new vegetable(rs.getString(getField(parameter.CATEGORY)),rs.getString(getField(parameter.NAME))));
            formatter.format("%14s %14s\n",rs.getString(getField(parameter.CATEGORY)),rs.getString(getField(parameter.NAME)));
        }
        System.out.println(formatter);


        return vegs;
    }
    private String getField(parameter param)
    {
        switch (param)
        {
            case NAME:
                return "name";
            case CATEGORY:
                return "category";
        }
        return "";
    }
    public enum parameter {CATEGORY,NAME};
  private final String url = "jdbc:mysql://localhost:3306/lab7_db";
  private final String nameOfTable = "shop";
  private Connection connection;
  private ArrayList<String>fields;


}
