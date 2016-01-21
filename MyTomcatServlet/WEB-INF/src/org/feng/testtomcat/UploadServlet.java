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
		request.setCharacterEncoding("UTF-8");// ���������ļ���
		//String temp = request.getSession().getServletContext().getRealPath("/") + "temp"; // ��ʱĿ¼
		String temp = "D:/Android/resource/temp";
		// String loadpath =
		// request.getSession().getServletContext().getRealPath("/") + "Image";
		// // �ϴ��ļ����Ŀ¼
		String loadpath = "D:/Android/resource/file";
		DiskFileUpload fu = new DiskFileUpload();
		fu.setSizeMax(1024 * 1024 * 1024); // ���������û��ϴ��ļ���С,��λ:�ֽ�
		fu.setSizeThreshold(1024 * 1024); // �������ֻ�������ڴ��д洢������,��λ:�ֽ�
		fu.setRepositoryPath(temp); // ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼

		// ��ʼ��ȡ�ϴ���Ϣ
		int index = 0;
		List fileItems = null;
		System.out.println("doPost..." + request);

		try {
			fileItems = fu.parseRequest(request);
			System.out.println("fileItems=" + fileItems);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Iterator iter = fileItems.iterator(); // ���δ���ÿ���ϴ����ļ�
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();// �������������ļ�������б���Ϣ
			if (!item.isFormField()) {
				String name = item.getName();// ��ȡ�ϴ��ļ���,����·��
				System.out.println("file: name =" + name);
				name = name.substring(name.lastIndexOf("\\") + 1);// ��ȫ·������ȡ�ļ���
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

			} else// ȡ�������ļ�������б���Ϣ
			{
				String fieldvalue = item.getString();
				// �����������ӦдΪ��(תΪUTF-8����)
				// String fieldvalue = new
				// String(item.getString().getBytes(),"UTF-8");
			}
		}
		String text1 = "11";
		response.sendRedirect("result.jsp?text1=" + text1);

	}
}
