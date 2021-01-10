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
import com.xiaomi.dao.vo.Cart;
import com.xiaomi.dao.vo.Good;
import com.xiaomi.dao.vo.Users;
import com.xiaomi.service.CartService;
import com.xiaomi.service.GoodService;

/**
 * Servlet implementation class GoodServlet
 */
@WebServlet("/GoodServlet")
public class GoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodServlet() {
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
		
		
		if("search".equals(operate)) {
			String good_name = request.getParameter("good_name");
			GoodService goodService = new GoodService();
			List<Good> getGoods = goodService.getGoodsByname(good_name);
			request.getSession().setAttribute("searchgoods", getGoods);
			request.getRequestDispatcher("/searchlist.jsp").forward(request, response);
		}
		
		if("xiaomi".equals(operate)) {
			GoodService goodService = new GoodService();
			List<Good> allGoods = goodService.getAllGoods();
			request.getSession().setAttribute("searchgoods", allGoods);
			request.getRequestDispatcher("/searchlist.jsp").forward(request, response);
		}
		
		if("detail".equals(operate)) {
			String good_name = request.getParameter("good_name");
			GoodService goodService = new GoodService();
			List<Good> detail = goodService.detail(good_name);
			request.setAttribute("goodsList", detail);
			request.setAttribute("goodcList", detail);
			request.setAttribute("goodtList", detail);
			request.getRequestDispatcher("/goods_details.jsp").forward(request, response);
		}
		
		if("buy".equals(operate)) {
			CartService cartService = new CartService();
			int uid = ((Users)request.getSession().getAttribute("user")).getUid();
			String color = request.getParameter("color");
			String type = request.getParameter("type");
			String good_name = request.getParameter("good_name");
			GoodService goodService = new GoodService();
			List<Good> goodByCondition = goodService.getGoodByCondition(type, good_name, color);
			Good goodTarget = goodByCondition.get(0);
			
			Cart cartInDB = cartService.findCartByUidGid(uid, goodTarget.getGoodId());
			Cart cart = new Cart();
			if(cartInDB == null) {
				cart.setGoodId(goodTarget.getGoodId());
				cart.setPrice(goodTarget.getGoodPrice());
				cart.setGoodNum(1);
				cart.setStatus(0);
				cart.setUid(uid);
				cartService.addCart(cart);
			}else {
				cart.setPreId(cartInDB.getPreId());
				cart.setGoodId(goodTarget.getGoodId());
				cart.setPrice(cartInDB.getGoodNum() * goodTarget.getGoodPrice());
				cart.setGoodNum(cartInDB.getGoodNum()+1);
				cart.setStatus(0);
				cart.setUid(uid);
				cartService.updateCart(cart);
			}
			request.getRequestDispatcher("/success_add_cart.jsp").forward(request, response);
		}
		
		
		
		
	}

}
