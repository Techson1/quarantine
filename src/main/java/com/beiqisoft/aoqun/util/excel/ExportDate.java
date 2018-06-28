package com.beiqisoft.aoqun.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportDate {
	
	/**
	 * 将实体List写入Excel
	 * 
	 * @param entitys
	 * @param fileds
	 *            写入Excel字段
	 * @param fileName
	 *            Excel文件名称
	 * @param titles
	 *            写入Excel字段标题
	 */
	public static void writeExcel(List<?> entitys, String[] fileds,
			String fileName, String[] titles) {
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (wwb != null) {
			WritableSheet ws = wwb.createSheet("sheet1", 0);
			for (int i = 0; i < titles.length; ++i) {
				Label lable = new Label(i, 0, titles[i]);
				try {
					ws.addCell(lable);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			int lenth = entitys.size();
			for (int i = 0; i < lenth; ++i) {
				String[] values = readEntity(entitys.get(i), fileds);
				for (int j = 0; j < values.length; ++j) {
					Label lable = new Label(j, i + 1, values[j]);
					try {
						ws.addCell(lable);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			try {
				wwb.write();
				wwb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
		}
	}
	
	/**
	 * 将实体List写入Excel
	 * 
	 * @param entitys
	 * @param fileds
	 *            写入Excel字段
	 * @param fileName
	 *            Excel文件名称
	 * @param titles
	 *            写入Excel字段标题
	 * @throws IOException 
	 */
	public static void writeExcel(String tableName,HttpServletResponse response,List<?> entitys, String[] fileds,
			String fileName, String[] titles) throws IOException {
		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流       
		response.setHeader("Content-disposition", "attachment; filename="+tableName+".xls");// 设定输出文件头       
		response.setContentType("application/msexcel");// 定义输出类型  
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(os);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (wwb != null) {
			WritableSheet ws = wwb.createSheet("sheet1", 0);
			for (int i = 0; i < titles.length; ++i) {
				Label lable = new Label(i, 0, titles[i]);
				try {
					ws.addCell(lable);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			int lenth = entitys.size();
			for (int i = 0; i < lenth; ++i) {
				String[] values = readEntity(entitys.get(i), fileds);
				for (int j = 0; j < values.length; ++j) {
					Label lable = new Label(j, i + 1, values[j]);
					try {
						ws.addCell(lable);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			try {
				wwb.write();
				wwb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
		}
	}
	
	/**
	 * 从网络获取Excel文件 
	 * @param path 地址
	 * @param version 版本号
	 * @param row 第几行开始
	 * @return
	 * @throws Exception 
	 */
	public static List<String[]> getUrl(String path,String version,int row) throws Exception  {
		URL url = new URL(path);
		File file=new File("tump.xls");
		inputstreamtofile(url.openStream(),file);
		List<String[]> list = new LinkedList<String[]>();
		Workbook rwb = Workbook.getWorkbook(file);
		Sheet rs = rwb.getSheet(0);
		int clos = rs.getColumns();// 得到所有的列
		int rows = rs.getRows();   // 得到所有的行
		for (int i = 0; i < rows; i++) {
			String[] s = new String[clos];
			for (int j = 0; j < clos; j++){
				if (i==0 && j==0){
					if (version.equals(rs.getCell(j, i).getContents().split(":")[1])){
						continue;
					}
					else{
						return list;
					}
				}
				s[j] = rs.getCell(j, i).getContents();
			}
			String lens="";
			for (String len:s){
				lens+=len;
			}
			if (i>row && lens.length()>=clos){
					list.add(s);
			}
		}
		return list;
	}
	
	/**
	 *InputStream转换file
	 **/
	public static void inputstreamtofile(InputStream ins,File file) throws Exception{
		   OutputStream os = new FileOutputStream(file);
		   int bytesRead = 0;
		   byte[] buffer = new byte[8192];
		   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
		      os.write(buffer, 0, bytesRead);
		   }
		   os.close();
		   ins.close();
	}
	
	/**
	 * 实体的内容转换为字符串数组
	 * 
	 * @author wd
	 * @author czx 修改
	 * @param obj
	 *            实体
	 * @param fields
	 *            要转换的成员变量名数组
	 * @version 1.1 修改成员变量为空是导出错乱的bug
	 * */
	@SuppressWarnings("all")//消除所有警告
	public static String[] readEntity(Object obj, String[] fields) {
		Class<?> clazz = obj.getClass();
		String[] values = new String[fields.length];
		int i = 0;
		for (String field : fields) {
			try {
				// Field f = clazz.getField(field);
				Method method = clazz.getMethod(
						"get" + field.substring(0, 1).toUpperCase()
								+ field.substring(1),null);
				Object value = method.invoke(obj, null);
				if (value == null) {
					values[(i++)] = "";
				}
				if (value instanceof String){
					values[(i++)] = ((String) value);
				}
				if (value instanceof Date) {
					values[(i++)] = new SimpleDateFormat("yyyy-MM-dd")
							.format((Date) value);
				}
				if (value instanceof Long)
					values[(i++)] = ((Long) value).toString();
				if (value instanceof Integer)
					values[(i++)]=((Integer) value).toString();
				if (value instanceof Double)
					values[(i++)]=((Double) value).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return values;
	}
}
