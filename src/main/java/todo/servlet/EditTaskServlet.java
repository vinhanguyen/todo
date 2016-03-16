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
        // validate request
        List<String> errors = validate(req);
        
        // if there were any errors
        if (!errors.isEmpty()) {
            // put Task back into request for user to modify in jsp
            Task task = new Task();
            task.setId(Integer.parseInt(req.getParameter("id")));
            task.setDescription(req.getParameter("description"));
            req.setAttribute("task", task);
            
            // also add error messages to display to user in jsp
            req.setAttribute("errors", errors);
            
            // forward to jsp
            req.getRequestDispatcher("/edit.jsp").forward(req, resp);
            
            // need to return to exit otherwise following code will execute
            return;
        }
        
        // if we reach this point, form submission is valid so update it
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
    
    private List<String> validate(HttpServletRequest req) {
        // make list of errors
        List<String> errors = new ArrayList<>();
        
        // get description
        String description = req.getParameter("description");
        
        // if description not provided or provided but not matches 1-100 characters (.)
        if (description == null || !description.matches("^.{1,100}$")) {
            errors.add("Description is required and cannot exceed 100 characters.");
        }
        
        // return errors
        return errors;
    }
}
