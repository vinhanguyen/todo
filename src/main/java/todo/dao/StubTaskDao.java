package todo.dao;

import java.util.ArrayList;
import java.util.List;

import todo.model.Task;

public class StubTaskDao implements TaskDao {
    
    // create array of Strings using array literal syntax
    private String[] tasks = new String[]{"eat lunch", "read a book", "watch tv"};

    @Override
    public List<Task> geTasks() throws DataAccessException {
        // create a list that will hold Task objects
        List<Task> list = new ArrayList<>();
        
        // for each String in tasks array
        for (String description : tasks) {
            // create a new Task object
            Task task = new Task();
            task.setDescription(description);
            
            // add to list
            list.add(task);
        }
        // return list
        return list;
    }
    
    public static void main(String[] args) {
        // example usage
        
        // create instance of TaskDao
        TaskDao taskDao = new StubTaskDao();
        
        List<Task> list = null;
        
        // try to get tasks
        try {
            list = taskDao.geTasks();
        } catch (DataAccessException e) {
            // handle errors that occur during data access
        }
        
        // display tasks
        for (Task task : list) {
            System.out.println("task: " + task.getDescription());
        }
    }
}
