package todo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.dao.DataAccessException;
import todo.dao.StubTaskDao;
import todo.dao.TaskDao;
import todo.model.Task;

public class TasksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // create instance of TaskDao
        TaskDao taskDao = new StubTaskDao();

        // try to get list of tasks from dao
        List<Task> tasks = null;
        try {
            tasks = taskDao.geTasks();
        } catch (DataAccessException e) {
            // set list to empty list if dao throws exception
            tasks = new ArrayList<>();
        }
        // add the list of tasks as an attribute of request object
        req.setAttribute("tasks", tasks);
        
        // forward the request to the tasks.jsp page using the request's RequestDispatcher object
        req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
    }
}
