package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;

public class MessageDAO {

    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // write preparedStatement's setString method here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int generatedUserId = (int) pkeyResultSet.getLong(1);
                return new Message(generatedUserId, message.getPosted_by(), message.getMessage_text(),
                        message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessage(Message user) {
        Connection connection = ConnectionUtil.getConnection();

        // try
        // {
        // String sql = "SELECT * FROM Message WHERE username = ? AND password = ?" ;
        // PreparedStatement preparedStatement = connection.prepareStatement(sql,
        // Statement.RETURN_GENERATED_KEYS);

        // //write preparedStatement's setString method here.
        // preparedStatement.setString(1, user.getUsername());
        // preparedStatement.setString(2, user.getPassword());

        // ResultSet rs = preparedStatement.executeQuery();

        // if(rs.next())
        // {
        // return new Message(rs.getInt("Message_id"), rs.getString("username"),
        // rs.getString("password"));
        // }
        // }
        // catch(SQLException e)
        // {
        // System.out.println(e.getMessage());
        // }
        return null;

    }

}