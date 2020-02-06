package net.javaguides.usermanagement.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.javaguides.usermanagement.dao.UserDao;
import net.javaguides.usermanagement.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name = "UserServlet", urlPatterns = "/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDAO;
	
	public void init() {
		userDAO = new UserDao();
	}
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getServletPath();
		
		switch (action) {
		case "/new":
			
			showNewForm(request,response);
			
			break;
		case "/insert":
			
			insertUser(request, response); 

			break;
		case "/update":
			updateUser(request,response);

			break;
		case "/delete":
			deleteUser(request, response);

			break;
		case "/edit":
			showEditForm(request, response);
			break;

		default:
			listUser(request,response);
			break;
		}
		
	}

	//return list of users to view layer
	
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		List<User> listUser = userDAO.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user-list.jsp");
		dispatcher.forward(request, response);
	}
	
	//display user-form
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	//handle create user request
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		User newUser = new User(name, email);
		userDAO.insertUser(newUser);
		response.sendRedirect("list");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
		    throws  ServletException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        User existingUser = userDAO.selectUser(id);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/user-form.jsp");
		        request.setAttribute("user", existingUser);
		        dispatcher.forward(request, response);

		    }
	
	//handle update user request
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		userDAO.updateUser(new User(id,name, email));
		response.sendRedirect("list");
	}
	
	// handle delete request
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		userDAO.deletUser(id);
		response.sendRedirect("list");
		
	}
	
	
	


}
