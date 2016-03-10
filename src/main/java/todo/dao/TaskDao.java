package todo.dao;

import java.util.List;

import todo.model.Task;

public interface TaskDao {

    void createTask(Task task, String user) throws DataAccessException;
    List<Task> getTasks(String user) throws DataAccessException;
    void deleteTask(int id) throws DataAccessException;
}
