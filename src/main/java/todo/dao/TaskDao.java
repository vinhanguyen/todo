package todo.dao;

import java.util.List;

import todo.model.Task;

public interface TaskDao {

    void createTask(Task task) throws DataAccessException;
    List<Task> getTasks() throws DataAccessException;
}
