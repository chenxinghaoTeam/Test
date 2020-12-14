package lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class lambdaGroupingBy {
	public static void main(String[] args) {
		//按照字段分组,幷累加所有value值
		String codekey = "voltageLeve";
		String valuekey = "value";
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("voltageLeve", "37");
		map.put("value", 10);
		Map<String, Object> map1 = new HashMap<>();
		map1.put("voltageLeve", "48");
		map1.put("value", 20);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("voltageLeve", "48");
		map2.put("value", 30);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("voltageLeve", "35");
		map3.put("value", 40);
		Map<String, Object> map4 = new HashMap<>();
		map4.put("voltageLeve", "33");
		map4.put("value", 50);
		Map<String, Object> map5 = new HashMap<>();
		map5.put("voltageLeve", "33");
		map5.put("value", 60);
		list.add(map);
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		List<Map<String, Object>> resultlist = new ArrayList<>();
				list.stream().collect(Collectors.groupingBy(a ->a.get(codekey),
				Collectors.summingInt(a -> Integer.valueOf(String.valueOf(a.get(valuekey)))))).forEach((key,value) ->{
					Map<String, Object> a = new HashMap<>();
					a.put(codekey, key.toString());
					a.put(valuekey, value);
					resultlist.add(a);
				});
		System.out.println(resultlist);
	}
}
