package lambda;

import java.util.Calendar;

public class BigDecimal {

	
	public static void main(String[] args) {
		/**
	    BigDecimal.setScale()这个方法是用于格式化小数点
		setScale(0)表示保留整数
		setScale(1)表示保留一位小数，默认用四舍五入方式
		setScale(1,BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3
		setScale(1,BigDecimal.ROUND_UP)进位处理，2.35变成2.4
		setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
		setScaler(1,BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍
		 */
		String a ="2016.0";
		java.math.BigDecimal b = new java.math.BigDecimal(a);
		System.out.println(Integer.valueOf(String.valueOf(b.setScale(0))));
	}
	
}
