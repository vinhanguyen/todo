package todo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import todo.model.Task;

public class MysqlTaskDao implements TaskDao {
    
    private DataSource dataSource; 

    public MysqlTaskDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Task> getTasks(String user) throws DataAccessException {
        // sql query to select all rows from tasks table
        // where the user_name column matches the passed in user (String)
        String sql = "select * from tasks where user_name = ?";
        
        // create list to hold Task objects
        List<Task> list = new ArrayList<>();
        
        // get connection from datasource and create statement representing sql query to execute
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // replaced placeholder ? with passed in user
            preparedStatement.setString(1, user);
            
            // execute query to get resultset (rows)
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // while resultset has a "next" row
            while (resultSet.next()) {
                // create Task object
                Task task = new Task();
                // set its description
                task.setDescription(resultSet.getString("description"));
                
                // add Task to list
                list.add(task);
            }
        } catch (SQLException e) { // catch any database errors
            // throw our custom exception that wraps the database exception
            throw new DataAccessException("Error getting tasks", e);
        }
        // return list of Tasks
        return list;
    }

    @Override
    public void createTask(Task task, String user) throws DataAccessException {
        // sql to insert row into tasks table
        String sql = "insert into tasks (description, user_name) values (?, ?)";
        
        // get connection and wrap sql in statement
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // replace ? placeholder in sql with task's description (string)
            // and user
            preparedStatement.setString(1, task.getDescription());
            preparedStatement.setString(2, user);
            
            // execute sql
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // catch any database errors, wrap in custom exception, and throw it 
            throw new DataAccessException("Error creating task", e);
        }
    }
}
