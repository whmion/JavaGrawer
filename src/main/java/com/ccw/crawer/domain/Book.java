package com.ccw.crawer.domain;

/**
 * Book POJO
 * @author a
 *
 */
public class Book {

	private String name;
	private String price;
	private String author;
	private int allcomments;
	private double rating_nums;
	private String press;
	private String pressdate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getAllcomments() {
		return allcomments;
	}

	public void setAllcomments(int allcomments) {
		this.allcomments = allcomments;
	}

	public double getRating_nums() {
		return rating_nums;
	}

	public void setRating_nums(double rating_nums) {
		this.rating_nums = rating_nums;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getPressdate() {
		return pressdate;
	}

	public void setPressdate(String pressdate) {
		this.pressdate = pressdate;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", price=" + price + ", author=" + author
				+ ", allcomments=" + allcomments + ", rating_nums="
				+ rating_nums + ", press=" + press + ", pressdate=" + pressdate
				+ "]";
	}

}
