package MakeJson.MakeJson;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

public class Test {

	public static void main(String[] args) {
//		long num1s = 1560826800000l;
//		long num1e = 1560861990452l;
//		long num2s = 1560823200000l;
//		long num2e = 1560861990452l;
//		long res = num1e-num1s+num2e-num2s;
//		System.out.println(res/2);
		
//		Object[][] o = new Object[][] {
//			{"Frist",2213,"fugkkv"},
//			{"Second",5678}
//		};
//		
//		System.out.println(o[1].length);
		
		List<String> list = new ArrayList<String>();
		list = null;
		JSONArray jarr = JSONArray.fromObject(list);
		System.out.println(jarr.toString().split("]")[0]);
	}

}
