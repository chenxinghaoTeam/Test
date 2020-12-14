package lambda;

import java.time.LocalDateTime;

public class testLocalDateTime {

	public static void main(String[] args) {
	    // 获取当前的日期时间
	    LocalDateTime currentTime = LocalDateTime.now();
	    System.out.println("当前时间: " +currentTime.getYear());
	}
}
