package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.HashMap;

public class SocialMediaDAO {

    public HashMap<String, Account> getAllUsernames()
    {
        Connection connection = ConnectionUtil.getConnection();
        HashMap<String, Account> accounts = new HashMap<>();

        try
        {
            String sql = "SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                Account account = new Account(rs.getString("username"), rs.getString("password"));
                accounts.put(account.username, account);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

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
    
}
