package MakeJson.MakeJson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Util.Util;
import net.sf.json.JSONArray;

public class Test {

	public static void main(String[] args) throws Exception {
		String path = "Source/";
		String basepath = "Baseinfo/";
		String basename = basepath +"baseinfo.txt";
		String casepath = "DataResultFolder/";
		File f = new File(path+casepath);
		List<String> list = new ArrayList<String>();
		File[] tmp = f.listFiles();

		Util.cleanDRF(path+casepath);
		
	}

}
