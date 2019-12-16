package HiveMVN.HiveMVN;

import java.io.File;

import Util.JDBCUtil;

public class Test {

	public static void main(String[] args) throws Exception {
		String path = "./Test";
		String name = "Heatmap" + "201809201900" + ".txt"; 
		File f = new File(path);
		JDBCUtil.FileInitialize(path + "/" + name);
		
		
		
	}

}
