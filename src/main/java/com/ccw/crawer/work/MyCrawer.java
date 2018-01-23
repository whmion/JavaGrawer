package com.ccw.crawer.work;

import java.util.List;
import java.util.concurrent.Callable;

import com.ccw.crawer.domain.Book;

/**
 * 爬虫线程
 * @author a
 *
 */
public class MyCrawer implements Callable<String> {
	
	private String url;
	private String tag;
	public MyCrawer(String url,String tag){
		this.url = url;
		this.tag = tag;
	}
	
	public String call() throws Exception {
		System.out.println("开启新线程爬取数据("+this.tag+")...");
		String htmlcode = GetHtmlData.getHtmldata(url);
		List<Book> booklist = GetHtmlData.getBooks(htmlcode);
		synchronized (DoCrawer.class) {
			DoCrawer.books.addAll(booklist);
			System.out.println("数据成功添加到书本列表...");
		}
		return "成功爬取数据，退出线程...";
	}

}
