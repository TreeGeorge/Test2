package study.spring.clip.model;

import lombok.Data;

@Data
public class WishList {
	private int wish_list_no;
	private int user_no;
	private int movie_no;
	private String name;
	private int price;
	private String runtime;
	private String thumbnail;
	private String type;

}
