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
        
        // get logged in user's username
        String user = req.getRemoteUser();

        // try to get list of tasks from dao
        List<Task> tasks = null;
        try {
            // get tasks for logged in user
            tasks = taskDao.getTasks(user);
        } catch (DataAccessException e) {
            logger.log(Level.WARNING, "Error getting tasks", e);
            // set list to empty list if dao throws exception
            tasks = new ArrayList<>();
        }
        // add the list of tasks as an attribute of request object
        req.setAttribute("tasks", tasks);
        
        // also add username user "user" key to display in the jsp's heading
        req.setAttribute("user", user);
        
        // forward the request to the tasks.jsp page using the request's RequestDispatcher object
        req.getRequestDispatcher("/tasks.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get description parameter from request object
        String description = req.getParameter("description");
        
        // create instance of TaskDao
        TaskDao dao = new MysqlTaskDao(dataSource);
        
        // create Task using description
        Task task = new Task();
        task.setDescription(description);
        
        // get logged in user's username
        String user = req.getRemoteUser();
        
        // call create method on dao to insert Task
        try {
            dao.createTask(task, user);
        } catch (DataAccessException e) {
            // if error occurs while inserting task, add exception's message to request attribute to named error
            req.setAttribute("errors", e.getMessage());
        }
        // redirect to /tasks so that doGet is invoked
        resp.sendRedirect(req.getContextPath() + "/tasks");
    }
}
