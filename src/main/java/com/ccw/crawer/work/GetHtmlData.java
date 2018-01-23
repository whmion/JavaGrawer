package com.ccw.crawer.work;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AutoRetryHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.ccw.crawer.domain.Book;

public class GetHtmlData {

	/**
	 * 获取网页源码
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String getHtmldata(String url) throws IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)
				.setConnectionRequestTimeout(5000)
				.setRedirectsEnabled(true)
				.build();
		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(requestConfig);
		
		HttpResponse response = httpclient.execute(httpget);
		
		if(response.getStatusLine().getStatusCode() == 200){
			String result = EntityUtils.toString(response.getEntity());
			return result;
		}else{
			System.out.println("fail to get html data!");
			return null;
		}
	}
	
	/**
	 * 通过正则表达式获取对应书目信息
	 * @param htmlcode
	 * @return
	 */
	public static List<Book> getBooks(String htmlcode){
		List<Book> books = new ArrayList<Book>();
		String regexpattern = "<div class=\"info\">\\s+<h2 class=\"\">\\s+<a\\s+(.*)\\s+title=(?<bookname>.*?)\\s+(.*)>\\s+(.*)\\s+(.*)\\s+</a>\\s+</h2>\\s+<div class=\"pub\">\\s+(?<bookdetails>.*?)\\s+</div>\\s+<div class=\"star clearfix\">\\s+(.*)\\s+<span class=\"rating_nums\">\\s*(?<ratingnums>.*?)</span>\\s+<span (.*)>\\s+(?<allcomments>.*?)\\s+</span>\\s+</div>";
		Pattern pattern = Pattern.compile(regexpattern);
		Matcher matcher = pattern.matcher(htmlcode);
		while(matcher.find()){
			String data = matcher.group("bookdetails");
			String bookname = matcher.group("bookname");
			String ratingnums = matcher.group("ratingnums").trim();
			String allcomments = matcher.group("allcomments").trim();
			String []datas = data.split("/");
			Book book = new Book();
			int len = datas.length;
			if(len>3){
			book.setName(bookname);
			book.setPrice(datas[len-1]);
			book.setPressdate(datas[len-2].trim());
			book.setPress(datas[len-3]);
			book.setAuthor(datas[0]);
			book.setAllcomments(Integer.valueOf(allcomments.substring(1,allcomments.indexOf("人评价"))));
			book.setRating_nums(Double.valueOf(ratingnums));
//			System.out.println(book.toString());
			books.add(book);
			}
		}
		
		return books;
	}

}
