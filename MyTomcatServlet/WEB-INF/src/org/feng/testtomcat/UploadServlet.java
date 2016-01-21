package org.feng.testtomcat;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8827948776932036486L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request == null) {
			return;
		}
		request.setCharacterEncoding("UTF-8");// 解析中文文件名
		//String temp = request.getSession().getServletContext().getRealPath("/") + "temp"; // 临时目录
		String temp = "D:/Android/resource/temp";
		// String loadpath =
		// request.getSession().getServletContext().getRealPath("/") + "Image";
		// // 上传文件存放目录
		String loadpath = "D:/Android/resource/file";
		DiskFileUpload fu = new DiskFileUpload();
		fu.setSizeMax(1024 * 1024 * 1024); // 设置允许用户上传文件大小,单位:字节
		fu.setSizeThreshold(1024 * 1024); // 设置最多只允许在内存中存储的数据,单位:字节
		fu.setRepositoryPath(temp); // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录

		// 开始读取上传信息
		int index = 0;
		List fileItems = null;
		System.out.println("doPost..." + request);

		try {
			fileItems = fu.parseRequest(request);
			System.out.println("fileItems=" + fileItems);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Iterator iter = fileItems.iterator(); // 依次处理每个上传的文件
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();// 忽略其他不是文件域的所有表单信息
			if (!item.isFormField()) {
				String name = item.getName();// 获取上传文件名,包括路径
				System.out.println("file: name =" + name);
				name = name.substring(name.lastIndexOf("\\") + 1);// 从全路径中提取文件名
				long size = item.getSize();
				if ((name == null || name.equals("")) && size == 0)
					continue;
				int point = name.indexOf(".");
				if (point > 0) {
					String subName = name.substring(0, point);
					System.out.println("subName =" + subName);
					name = subName + (new Date()).getTime() + "_" + index 
							+ name.substring(point, name.length());
				} 				
				index++;
				System.out.println("72 loadpath=" + loadpath);
				System.out.println("73 name=" + name);
				File fNew = new File(loadpath, name);
				try {
					item.write(fNew);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else// 取出不是文件域的所有表单信息
			{
				String fieldvalue = item.getString();
				// 如果包含中文应写为：(转为UTF-8编码)
				// String fieldvalue = new
				// String(item.getString().getBytes(),"UTF-8");
			}
		}
		String text1 = "11";
		response.sendRedirect("result.jsp?text1=" + text1);

	}
}
