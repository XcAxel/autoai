package JsonProducer.JsonProducer;

import java.util.Random;

import Util.Util;

public class test {
	public static void main(String[] args) {
		Random random = new Random();
		int range = 10;
		for(int i = 0;i<10;i++) {
			int num = Util.randomMethod(random, range);
			System.out.println(num);
		}
		int total = 870870;
		System.out.println(total);
	}
}
