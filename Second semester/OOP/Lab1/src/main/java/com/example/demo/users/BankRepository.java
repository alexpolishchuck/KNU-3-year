package com.example.demo.users;

import com.example.demo.security.DbConfig;
import java.sql.*;
public class BankRepository {
    public BankRepository()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DbConfig.getDb_url(),
                    DbConfig.getUsername(),
                    DbConfig.getPassword());

        } catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public void save(Person person) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO bank_users (name, password, role)" +
                            " VALUES ("
                            + "'" + person.getName() + "',"
                            + "'" + person.getPassword() + "',"
                            + "'" + person.getRole().name() + "')");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public Person findByUsername(String username)
    {
        Person person = null;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT id, password, role FROM bank_users WHERE name = "
                            + "'" + username + "'");
            if(rs.next())
            {
                person = Person.builder()
                        .role(Roles.valueOf(rs.getString("role")))
                        .password(rs.getString("password"))
                        .name(username)
                        .build();
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return person;
    }

    private Connection connection;
}
