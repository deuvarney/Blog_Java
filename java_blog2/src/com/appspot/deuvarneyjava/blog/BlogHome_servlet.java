package com.appspot.deuvarneyjava.blog;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.deuvarneyjava.services.CommonService;
import com.appspot.deuvarneyjava.services.MyDatastoreService;
import com.google.appengine.api.datastore.Entity;

public class BlogHome_servlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);

		boolean returnJson = false;
		boolean returnXml = false;

		String exportMethod = req.getParameter("export");
		if (exportMethod != null) {
			if (exportMethod.equals("xml") || exportMethod.equals("json")) {
				Iterable<Entity> all_posts = new MyDatastoreService()
						.queryAllPosts();

				if (exportMethod.equals("json")) {
					String jsonResp = CommonService.returnJson(all_posts);
					resp.getWriter().println(jsonResp);
				}
				// else{
				// String xmlResp = CommonService.re
				// }
				return;
			}
		}
		System.out.println("Export method: " + req.getParameter("export"));

		RequestDispatcher dispatcher = req.getRequestDispatcher("/blog_page");
		// new MyDatastoreService().queryAllPosts();
		dispatcher.forward(req, resp);

	}

}
