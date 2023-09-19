package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    public Account insertAccount(Account user)
    {
        Connection connection = ConnectionUtil.getConnection();
        
        try 
        {                
            String sql = "INSERT INTO account (username, password) VALUES (?,?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            //write preparedStatement's setString method here.
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
        
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if(pkeyResultSet.next())
            {
                int generatedUserId = (int) pkeyResultSet.getLong(1);
                return new Account(generatedUserId, user.getUsername(), user.getPassword());
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccount(Account user)
    {
        Connection connection = ConnectionUtil.getConnection();

        try 
        {                
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            //write preparedStatement's setString method here.
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
        
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next())
            {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;

    }
    
}
