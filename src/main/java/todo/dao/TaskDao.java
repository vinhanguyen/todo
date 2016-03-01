package todo.dao;

import java.util.List;

import todo.model.Task;

public interface TaskDao {

    List<Task> geTasks() throws DataAccessException;
}
