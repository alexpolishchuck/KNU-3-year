package com.example.demo.users;

import com.example.demo.security.DbConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.*;
@Component
public class BankRepository {

    @Autowired
    public BankRepository(DbConfig dbConfig)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            this.dbConfig = dbConfig;
            connection = DriverManager.getConnection(dbConfig.db_url, dbConfig.username, dbConfig.password);
            connection.setAutoCommit(false);

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
            connection.commit();
        } catch (Exception ex) {
            System.out.println(ex);

            connection.rollback();
        }
    }
    public UserDetails findByUsername(String username)
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
    private DbConfig dbConfig;
}
