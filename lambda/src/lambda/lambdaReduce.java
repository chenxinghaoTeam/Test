package lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class lambdaReduce {
	public static void main(String[] args) {
		//用reduce我们可以做sum,min,max,average
		
		 List<Integer> integers = Arrays.asList(1, 2, 34, 4, 15);
	        // 没有起始值时返回为Optional类型
	        Optional<Integer> sumOptional = integers.stream().reduce(Integer::sum);
	        System.out.println(sumOptional.get());

	        // 可以给一个起始种子值
	        Integer sumReduce = integers.stream().reduce(0, Integer::sum);
	        System.out.println(sumReduce);
	        
	        // 可以给一个起始种子值
	        Integer Reduce = integers.stream().reduce(0,(a,b)->a+b);
	        System.out.println(Reduce);
	        
	        //直接用sum方法
	        int sum = integers.stream().mapToInt(a -> a).sum();
	        System.out.println(sum);
	        
	        //构造字符串流
	        List<String> strs = Arrays.asList("H", "E", "L", "L", "O");
	        // reduce
	        String concatReduce = strs.stream().reduce("", String::concat);
	        System.out.println(concatReduce);
	}
}
