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
    public List<Task> geTasks() throws DataAccessException {
        // sql query to select all rows from tasks table
        String sql = "select * from tasks";
        
        // create list to hold Task objects
        List<Task> list = new ArrayList<>();
        
        // get connection from datasource and create statement representing sql query to execute
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
}
