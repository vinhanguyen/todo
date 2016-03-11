package todo.servlet;

import java.io.IOException;
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

public class EditTaskServlet extends HttpServlet {
    
    private Logger logger = Logger.getLogger(EditTaskServlet.class.getName());
    
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
        try {
            // get id of task to edit
            int id = Integer.parseInt(req.getParameter("id"));
            
            // get user
            String user = req.getRemoteUser();
            
            // create dao
            TaskDao dao = new MysqlTaskDao(dataSource);
            
            // get task to edit
            Task task = dao.getTask(id, user);
            
            // add task to request
            req.setAttribute("task", task);
            
            // forward request to jsp form
            req.getRequestDispatcher("/edit.jsp").forward(req, resp);
        } catch (NumberFormatException|DataAccessException e) {
            // log exception
            logger.log(Level.WARNING, "Error getting task", e);
            
            // redirect to task list of any error occur
            resp.sendRedirect(req.getContextPath()+"/tasks");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // get posted form parameters
            String idParam = req.getParameter("id");
            String description = req.getParameter("description");
            
            // convert the id to int
            int id = Integer.parseInt(idParam);
            
            // create Task
            Task task = new Task();
            task.setDescription(description);
            task.setId(id);
            
            // get logged in user
            String user = req.getRemoteUser();
            
            // create instance of task dao
            TaskDao taskDao = new MysqlTaskDao(dataSource);
            
            // call update method
            taskDao.updateTask(task, user);
        } catch (Exception e) {
            // log the exception
            logger.log(Level.WARNING, "Error deleting task", e);
        }
        
        // redirect the user to the tasks list
        resp.sendRedirect(req.getContextPath()+"/tasks");
    }
}
