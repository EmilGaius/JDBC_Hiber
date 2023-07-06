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
        String sql = "create table if not exists users(id INT, name VARCHAR(20) PRIMARY KEY, last_name VARCHAR(20), age TINYINT)";
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "drop table if exists users";
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (id, name, last_name, age) values(id, ?, ?, ?)";
        try (PreparedStatement prestatement = connection.prepareStatement(sql);) {
            prestatement.setString(1, name);
            prestatement.setString(2, lastName);
            prestatement.setByte(3, age);

            prestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id = ?";
        try (PreparedStatement prestatement = connection.prepareStatement(sql);) {
            prestatement.setLong(1, id);
            prestatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "select *from users";
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
        String sql = "truncate users";
        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
