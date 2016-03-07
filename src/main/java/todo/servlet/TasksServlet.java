package todo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import todo.dao.DataAccessException;
import todo.dao.MysqlTaskDao;
import todo.dao.StubTaskDao;
import todo.dao.TaskDao;
import todo.model.Task;

public class TasksServlet extends HttpServlet {
    
    private Logger logger = Logger.getLogger(TasksServlet.class.getName());
    
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource)context.lookup("java:comp/env/jdbc/todoDB");
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // create instance of TaskDao
        TaskDao taskDao = new MysqlTaskDao(dataSource);

        // try to get list of tasks from dao
        List<Task> tasks = null;
        try {
            tasks = taskDao.geTasks();
        } catch (DataAccessException e) {
            logger.log(Level.WARNING, "Error getting tasks", e);
            // set list to empty list if dao throws exception
            tasks = new ArrayList<>();
        }
        // add the list of tasks as an attribute of request object
        req.setAttribute("tasks", tasks);
        
        // forward the request to the tasks.jsp page using the request's RequestDispatcher object
        req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
    }
}
