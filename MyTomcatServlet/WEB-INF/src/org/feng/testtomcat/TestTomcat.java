package org.feng.testtomcat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestTomcat extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int NAME_CODE_RIGHT = 0; //
	private static final int CODE_WRONG = 1; //
	private static final int NAME_WRONG = 2; //

	public TestTomcat() {

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet..." + req);

		if (req == null) {
			return;
		}
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		String name = req.getParameter("NAME");
		String code = req.getParameter("CODE");

		// ��������ʣ�û�����κβ�������HTML��ʽ���������������
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Tomcat Servlet����</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("Hello,��֪��������������ʵ�.");
		out.println("</body>");
		out.println("</html>");
		out.println("Hello,��һ��Tomcat!!!");
		out.close();

		// �ֻ��ͻ��˷���
		/*
		 * int ret = checkSubmit(name, code); out.print(ret); out.flush();
		 * out.close();
		 */

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request == null) {
			return;
		}
		

		/*
		 * resp.setContentType("text/html;charset=utf-8");
		 * req.setCharacterEncoding("utf-8");
		 * resp.setCharacterEncoding("utf-8");
		 * 
		 * PrintWriter out = resp.getWriter(); String name =
		 * req.getParameter("NAME"); String code = req.getParameter("CODE");
		 * 
		 * int ret = checkSubmit(name, code); out.print(ret); out.flush();
		 * out.close();
		 */
	}

	/**
	 * �жϵ�¼��������
	 * 
	 * @param name
	 * @param code
	 * @return
	 */
	private int checkSubmit(String name, String code) {
		int ret = -2;
		if (name == null || code == null) {
			return ret;
		}
		if (name.equals("admin")) {
			if (code.equals("123")) {
				ret = NAME_CODE_RIGHT;
			} else {
				ret = CODE_WRONG;
			}
		} else {
			ret = NAME_WRONG;
		}
		return ret;
	}

}
