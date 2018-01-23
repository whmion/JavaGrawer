package com.ccw.crawer.work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ccw.crawer.domain.Book;

public class ExcelExports {

	
	public static void export(List<Book> books){
		
		//生成Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建sheet
		HSSFSheet sheet = workbook.createSheet("书目清单");
		//创建样式对象
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1,9000);
		sheet.setColumnWidth(2,2000);
		sheet.setColumnWidth(3,2000);
		sheet.setColumnWidth(4,6000);
		sheet.setColumnWidth(5,6000);
		sheet.setColumnWidth(6,2000);
		sheet.setColumnWidth(7,2000);
		
		HSSFRow headRow = sheet.createRow(0);
		
		HSSFCell createCell0 = headRow.createCell(0);
		HSSFCell createCell1 = headRow.createCell(1);
		HSSFCell createCell2 = headRow.createCell(2);
		HSSFCell createCell3 = headRow.createCell(3);
		HSSFCell createCell4 = headRow.createCell(4);
		HSSFCell createCell5 = headRow.createCell(5);
		HSSFCell createCell6 = headRow.createCell(6);
		HSSFCell createCell7 = headRow.createCell(7);
		
		createCell0.setCellStyle(cellStyle);
		createCell1.setCellStyle(cellStyle);
		createCell2.setCellStyle(cellStyle);
		createCell3.setCellStyle(cellStyle);
		createCell4.setCellStyle(cellStyle);
		createCell5.setCellStyle(cellStyle);
		createCell6.setCellStyle(cellStyle);
		createCell7.setCellStyle(cellStyle);

		createCell0.setCellValue("书名");
		createCell1.setCellValue("评分");
		createCell2.setCellValue("评价人数");
		createCell3.setCellValue("作者");
		createCell4.setCellValue("出版社");
		createCell5.setCellValue("出版日期");
		createCell6.setCellValue("价格");
		
		System.out.println("开始排序...");
		List<Book> topbooks = ExcelExports.sortBooks(books);
		System.out.println("开始写入excel文件...");
		for(Book book:topbooks){
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(book.getName());
			dataRow.createCell(1).setCellValue(book.getRating_nums());
			dataRow.createCell(2).setCellValue(book.getAllcomments());
			dataRow.createCell(3).setCellValue(book.getAuthor());
			dataRow.createCell(4).setCellValue(book.getPress());
			dataRow.createCell(5).setCellValue(book.getPressdate());
			dataRow.createCell(6).setCellValue(book.getPrice());
		}
		
		String filename = "书目清单.xls";
		File file = new File("D:\\"+filename);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件写入失败");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("文件写入失败");
		}finally{
			System.out.println("成功写入文件");
			if(null!=fos)
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	//排序
	public static List<Book> sortBooks(List<Book> books){
		List<Book> topbooks = new ArrayList<Book>();
		for(Book book:books){
			if(book.getAllcomments()>=1000)
				topbooks.add(book);
		}
		Collections.sort(topbooks, new Comparator<Book>() {
			public int compare(Book o1, Book o2) {
				if(o1.getRating_nums()<o2.getRating_nums())
					return 1;
				else if(o1.getRating_nums()==o2.getRating_nums())
					return 0;
				else
					return -1;
			}
		});
		int size = topbooks.size();
		//书目小于100直接返回否则返回前100条
		if(size<100)
			return topbooks;
		else
			return topbooks.subList(0, 99);
	}
}
