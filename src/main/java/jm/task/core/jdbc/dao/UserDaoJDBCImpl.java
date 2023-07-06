package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        String sql = "create table if not exists Users(id INT, name VARCHAR(20), lastname VARCHAR(20), age INT)";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "drop table if exists Users";
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into Users (id, name, lastname, age) values(id, ?, ?, ?)";
        try (PreparedStatement prestatement = connection.prepareStatement(sql);) {
            prestatement.setString(1, name);
            prestatement.setString(2, lastName);
            prestatement.setInt(3, (byte)age);

            prestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from Users where id = ?";
        try (PreparedStatement prestatement = connection.prepareStatement(sql);) {
            prestatement.setInt(1, (int) id);
            prestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "select *from Users";
        try (PreparedStatement preStatement = connection.prepareStatement(sql);) {
            ResultSet resultSet = preStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User(name, lastName, age);
                    users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "truncate Users";
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
