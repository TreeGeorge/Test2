package study.spring.clip.model;

import lombok.Data;

@Data
public class UserCoupon {
	private int user_coupon_no;
	private String is_used;
	private int user_no;
	private int coupon_no;
	private int price;
	private String name;
	private String period;
}
