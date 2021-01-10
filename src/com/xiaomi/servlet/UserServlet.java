package com.xiaomi.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.xiaomi.dao.Util.DBUtil;
import com.xiaomi.dao.vo.Users;
import com.xiaomi.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String operate = request.getParameter("operate");
		//用户注册操作
		if("register".equals(operate)) {
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String phonenumber = request.getParameter("phonenumber"); 
			String address = request.getParameter("address");
			String hobby = request.getParameter("hobby");
			String sign = request.getParameter("sign");
			String Imgcode = request.getParameter("image");
			Users user = new Users(username,password,phonenumber,address,hobby,sign);
			
			UserService userService = new UserService();
			boolean imgCodeValidate = userService.ImgCodeValidate(Imgcode,request);
			if(imgCodeValidate == false) {
				request.setAttribute("rmsg", "验证码输入！");
				request.getRequestDispatcher("/register.jsp").forward(request, response);;
				return;
			}
			boolean inserflag = userService.register(user);
			
			if(inserflag == false) {
				request.setAttribute("rmsg", "注册失败！");
				request.getRequestDispatcher("/register.jsp").forward(request, response);;
				return;
			}
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
		//登录操作
		if("login".equals(operate)) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String Imgcode = request.getParameter("image");
			UserService userService = new UserService();
			boolean imgCodeValidate = userService.ImgCodeValidate(Imgcode,request);
			if(imgCodeValidate == false) {
				request.setAttribute("msg", "验证码输入！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);;
				return;
			}
			List<Users> userList = userService.login(username, password, request);
			if(userList == null) {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return ;
			}
			request.setAttribute("username", username);
			
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		//退出登录
		if("logout".equals(operate)) {
			request.getSession().invalidate();
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		//个人中心
		if("selfinfo".equals(operate)) {
			Users user = (Users) request.getSession().getAttribute("user");
			UserService userService = new UserService();
			List<Users> selfinfo = userService.selfinfo(user);
			for(Users user1 : selfinfo) {
				request.getSession().setAttribute("user", user1);
			}
			
			request.getRequestDispatcher("/self_info.jsp").forward(request, response);
		}
		
		//转入修改页面
		if("edit".equals(operate)) {
			Users user = (Users)request.getSession().getAttribute("user");
			request.setAttribute("user", user);
			request.getRequestDispatcher("edituser.jsp").forward(request, response);
		}
		
		//修改操作
		if("editupdate".equals(operate)) {
			String uid = request.getParameter("uid");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String phonenumber = request.getParameter("phonenumber");
			String address = request.getParameter("address");
			String hobby = request.getParameter("hobby");
			String sign = request.getParameter("sign");
			Users user = new Users(username,password,phonenumber,address,hobby,sign);
			UserService userService = new UserService();
			boolean editupdate = userService.editupdate(user);
			if(editupdate == true) {
				request.setAttribute("user", user);
				request.getRequestDispatcher("/self_info.jsp").forward(request, response);
			}
			request.getRequestDispatcher("/edituser.jsp").forward(request, response);	
		}
		
	}

}
