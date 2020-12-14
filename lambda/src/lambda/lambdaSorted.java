package lambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class lambdaSorted {
	public static void main(String[] args) {
		// 排序方法
		String codekey = "voltageLeve";
		String value = "value";
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("voltageLeve", "37");
		map.put("value", "1");
		Map<String, Object> map1 = new HashMap<>();
		map1.put("voltageLeve", "48");
		map1.put("value", "20");
		Map<String, Object> map2 = new HashMap<>();
		map2.put("voltageLeve", "38");
		map2.put("value", "15");
		Map<String, Object> map3 = new HashMap<>();
		map3.put("voltageLeve", "35");
		map3.put("value", "100");
		Map<String, Object> map4 = new HashMap<>();
		map4.put("voltageLeve", "33");
		map4.put("value", "35");
		Map<String, Object> map5 = new HashMap<>();
		map5.put("voltageLeve", "36");
		map5.put("value", "40");
		Map<String, Object> map6 = new HashMap<>();
		map6.put("voltageLeve", "48");
		map6.put("value", "40");
		list.add(map);
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		//对调 正序 倒序
		//{33=35, 35=100, 36=40, 48=60, 37=1, 38=15}
		//相同類型合併,value累加
		Map<Object,Integer> info = list.stream().collect(Collectors.groupingBy(a ->a.get(codekey),Collectors.summingInt(a ->Integer.valueOf((String) a.get(value)))));
		List<Map<String, Object>> infoList = new ArrayList<>();
		info.forEach((a,b) ->{
			Map<String, Object> aa = new HashMap<>();
			aa.put(codekey, a);
			aa.put(value, b);
			infoList.add(aa);
		});
		List<Map<String, Object>> aaa = new ArrayList<>();
		System.out.println(infoList);
		List<Map<String, Object>> list1 = new ArrayList<>();
		List<Map<String, Object>> list2 = new ArrayList<>();
		//根据codekey排序
		list2 = infoList.stream().sorted((a, b) -> b.get(codekey).toString().compareTo(a.get(codekey).toString()))
				.collect(Collectors.toList());	
		System.out.println("codekey排序"+ list2);
		//根据value排序
		list1 = infoList.stream().sorted((p, m) -> Integer.valueOf(m.get(value).toString())-(Integer.valueOf(p.get(value).toString()))).collect(Collectors.toList());
		System.out.println("value排序"+list1);
	}
}
