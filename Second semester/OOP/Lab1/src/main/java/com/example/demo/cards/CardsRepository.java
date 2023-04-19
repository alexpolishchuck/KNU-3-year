package com.example.demo.cards;

import com.example.demo.security.DbConfig;
import com.example.demo.users.Person;
import com.example.demo.users.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardsRepository {

    @Autowired
    public CardsRepository(DbConfig dbConfig)
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
    public List<Card> find_users_cards(String username)
    {
        ArrayList<Card> cards = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(
                "SELECT id, number, is_blocked" +
                        " FROM cards WHERE owners_name = "
                        + "'" + username + "'");
        while(rs.next())
        {
            Card card = Card.builder()
                    .id(rs.getInt("id"))
                    .number(rs.getString("number"))
                    .is_blocked(rs.getBoolean("is_blocked"))
                    .owners_name(username)
                    .build();

            cards.add(card);
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cards;
    }
    public Card find_card_by_number(String number)
    {
        Card card = null;

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(
                    "SELECT id, owners_name, is_blocked" +
                            " FROM cards WHERE number = "
                            + "'" + number + "'");
            if(rs.next())
            {
                card = Card.builder()
                        .id(rs.getInt("id"))
                        .number(number)
                        .is_blocked(rs.getBoolean("is_blocked"))
                        .owners_name(rs.getString("owners_name"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return card;
    }
    public void save(Card card) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO cards (number, owners_name, is_blocked)" +
                            " VALUES ("
                            + "'" + card.getNumber() + "',"
                            + "'" + card.getOwners_name() + "',"
                            +  ((card.is_blocked())?1:0) + ")");
            connection.commit();
        } catch (Exception ex) {
            System.out.println(ex);

            connection.rollback();
        }
    }
    public void set_blocked_status(String number, Boolean status) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "UPDATE cards "
                            + "SET is_blocked = " + ((status)?1:0)
                            + " WHERE number = '" + number + "'");
            connection.commit();
        } catch (Exception ex) {
            System.out.println(ex);

            connection.rollback();
        }
    }

    public Card has_card(String number, String name)
    {
        Card card = null;

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(
                    "SELECT id, is_blocked" +
                            " FROM cards WHERE number = "
                            + "'" + number + "' AND owners_name = "
                            + "'" + name + "'");
            if(rs.next())
            {
                card = Card.builder()
                        .id(rs.getInt("id"))
                        .number(number)
                        .is_blocked(rs.getBoolean("is_blocked"))
                        .owners_name(name)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return card;
    }

    private Connection connection;

    private DbConfig dbConfig;
}
