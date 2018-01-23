package com.ccw.crawer.work;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ccw.crawer.domain.Book;

/**
 * Java����
 * @author a
 *
 */
public class DoCrawer {
	public static List<Book> books = new ArrayList<Book>();
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		List<String>words = new ArrayList<String>();
		words.add("������");
		words.add("���");
		words.add("�㷨");
		String preurl = "https://book.douban.com/tag/";
		List<Future<String>> list = new ArrayList<Future<String>>();
		ExecutorService threadpoolone = Executors.newCachedThreadPool();
		for(int i = 0;i<words.size();i++){
			int j = 0;
			while(j<10){
				String name = preurl+words.get(i)+"?start="+j*20+"&type=T";
				MyCrawer mycrawer = new MyCrawer(name,words.get(i));
				Future<String>future = threadpoolone.submit(mycrawer);
				list.add(future);
				j++;
			}
		}
		for(Future future:list){
			System.out.println(future.get().toString());
		}
		threadpoolone.shutdown();
		System.out.println("������ȡ���...");
		System.out.println("��"+DoCrawer.books.size()+"������...");
		ExcelExports.export(DoCrawer.books);
	}
}
