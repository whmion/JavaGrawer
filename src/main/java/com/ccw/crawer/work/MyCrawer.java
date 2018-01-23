package com.ccw.crawer.work;

import java.util.List;
import java.util.concurrent.Callable;

import com.ccw.crawer.domain.Book;

/**
 * �����߳�
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
		System.out.println("�������߳���ȡ����("+this.tag+")...");
		String htmlcode = GetHtmlData.getHtmldata(url);
		List<Book> booklist = GetHtmlData.getBooks(htmlcode);
		synchronized (DoCrawer.class) {
			DoCrawer.books.addAll(booklist);
			System.out.println("���ݳɹ���ӵ��鱾�б�...");
		}
		return "�ɹ���ȡ���ݣ��˳��߳�...";
	}

}
