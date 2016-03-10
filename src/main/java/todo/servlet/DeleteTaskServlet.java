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

import todo.dao.MysqlTaskDao;
import todo.dao.TaskDao;

public class DeleteTaskServlet extends HttpServlet {
    
    private Logger logger = Logger.getLogger(DeleteTaskServlet.class.getName());
    
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // get id of task to delete
            String idParam = req.getParameter("id");
            
            // convert the id string to an int so you can pass into deleteTask method
            // this method will throw a NumberFormatException if its parameter cannot be converted into an int
            int id = Integer.parseInt(idParam);
            
            // create instance of task dao
            TaskDao taskDao = new MysqlTaskDao(dataSource);
            
            // call deleteTask method passing in the id (int)
            // this method throws a DataAccessException if the delete fails
            taskDao.deleteTask(id);
        } catch (Exception e) {
            // catch any exceptions that are subclasses of the Exception class
            // this will catch either NumberFormatException or DataAccessException 
            // because both are subclasses of Exception
            
            // log the exception
            logger.log(Level.WARNING, "Error deleting task", e);
        }
        
        // redirect the user to the tasks list
        resp.sendRedirect(req.getContextPath()+"/tasks");
    }
}
