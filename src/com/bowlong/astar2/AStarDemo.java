package com.bowlong.astar2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;


public class AStarDemo{
	public static void main(String [] argv) throws FileNotFoundException {
	
		FileInputStream fs = new FileInputStream("c.txt");
		Scanner s = new Scanner(fs);
		int n,m;
		n = s.nextInt();
		m = s.nextInt();
		AStar ast = new AStar(n,m,s);
		Date t = new Date();
		int res = ast.run();
		Long x = (new Date()).getTime() - t.getTime();
		ast.printRace();
		System.out.println("消耗"+res);
		System.out.println("运行时间"+x+"毫秒");
		
	}
}
