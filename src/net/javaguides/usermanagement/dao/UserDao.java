package net.javaguides.usermanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.usermanagement.model.User;
import net.javaguides.usermanagement.utils.JDBSUtils;


public class UserDao {
	

    // define sql statment

    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email) VALUES " +
        " (?, ?);";

    private static final String SELECT_USER_BY_ID = "select id,name,email from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ? where id = ?;";
    
    
    
    // insert record in database
     public void insertUser(User user) {
    	 try (Connection connection = JDBSUtils.getConnection();
    			 PreparedStatement preparedStatment = connection.prepareStatement(INSERT_USERS_SQL)){
			preparedStatment.setString(1, user.getName());
			preparedStatment.setString(2, user.getEmail());
			
			System.out.println(preparedStatment);
			preparedStatment.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
     }
     
     //select user from database
     
     public User selectUser(int id) {
    	 User user = null;
    	 try(Connection connection = JDBSUtils.getConnection();
    			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID) ){
    		 preparedStatement.setInt(1,id);
    		 ResultSet resultSet = preparedStatement.executeQuery();
    		 
    		 while(resultSet.next()) {
    			 String name = resultSet.getString("name");
    			 String email = resultSet.getString("email");
    			 
    			 user = new User(id,name,email); 
    		 }
    	 } catch (SQLException e) {
    		 e.printStackTrace();	 
    	 }
    	 return user; 
     }
     
     //Select all users
     
     public List < User > selectAllUsers() {

         // using try-with-resources to avoid closing resources (boiler plate code)
         List < User > users = new ArrayList < > ();
         // Step 1: Establishing a Connection
         try (Connection connection = JDBSUtils.getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
             System.out.println(preparedStatement);
             // Step 3: Execute the query or update query
             ResultSet rs = preparedStatement.executeQuery();

             // Step 4: Process the ResultSet object.
             while (rs.next()) {
            	 
                 int id = rs.getInt("id");
                 String name = rs.getString("name");
                 String email = rs.getString("email");
                 users.add(new User(id, name, email));
             }
         } catch (SQLException e) {
        	 e.printStackTrace();
         }
         return users;
     }
     
     // delete user from database
     
     public boolean deletUser(int id) {
         boolean rowDeleted = false;
         try (Connection connection = JDBSUtils.getConnection(); 
        		 PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
             statement.setInt(1, id);
             rowDeleted = statement.executeUpdate() > 0;
         }catch (SQLException e) {
        	 e.printStackTrace();
         }
         return rowDeleted;
     }
     
     //update user in database
     

     public boolean updateUser(User user) {
         boolean rowUpdated = false;
         try (Connection connection = JDBSUtils.getConnection(); 
        		 PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
             statement.setString(1, user.getName());
             statement.setString(2, user.getEmail());
             statement.setInt(3, user.getId());
            

             rowUpdated = statement.executeUpdate() > 0;
         }catch (SQLException e) {
        	 e.printStackTrace();
         }
         return rowUpdated;
     }
     
}
