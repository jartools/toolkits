package com.bowlong.astar2;

import java.util.PriorityQueue;
import java.util.Scanner;

public class AStar {
	private int stepx[] = { 0, 1, 1, 1, 0, -1, -1, -1 };// 第一个是上，顺时针
	private int stepy[] = { 1, 1, 0, -1, -1, -1, 0, 1 };// 第一个是上，顺时针
	private int gvalue[] = { 10, 14, 10, 14, 10, 14, 10, 14 };

	private int n;
	private int m;
	private char map[][];
	private Node mapNode[][];

	public AStar(int n, int m, Scanner s) {
		this.n = n;
		this.m = m;
		this.init(s);
	}

	public int run() {
		NodeComparator cmp = new NodeComparator();
		PriorityQueue<Node> open = new PriorityQueue<Node>(n * m, cmp);
		int x, y;
		int nxti, nxtj;
		Node nxt;

		mapNode[0][0].setInbox(true);
		mapNode[0][0].g = 0;
		mapNode[0][0].f = mapNode[0][0].g + mapNode[0][0].h;
		open.add(mapNode[0][0]);
		while (!open.isEmpty()) {
			Node top = open.poll();// 获取并移除此队列的头，如果此队列为空，则返回 null。
			top.setSteped(true);
			// 如果终点在结束列表中，表示表示找到了最优解
			if (mapNode[n - 1][m - 1].isSteped())
				break;
			x = top.i;
			y = top.j;
			for (int i = 0; i < 8; i++) {
				nxti = x + stepx[i];
				nxtj = y + stepy[i];
				if (!inRange(nxti, nxtj))
					continue;
				if(map[nxti][nxtj]=='x')continue;
				nxt = mapNode[nxti][nxtj];
				int gg = top.g + gvalue[i];
				if (gg < nxt.g) {
					nxt.g = gg;
					nxt.f = gg + nxt.h;
					nxt.parent = top;
				}
				if (!nxt.isInbox()){
					nxt.setInbox(true);
					open.add(nxt);
//					System.out.println("--> ("+nxt.i+", "+nxt.j+")");
				}
			}
		}
		return mapNode[n-1][m-1].f;
	}

	public void printRace(){
		Node cur = mapNode[n-1][m-1];
		while(!cur.parent.equals(cur)){
//			System.out.println("[ "+cur.i+", "+cur.j+"]");
			map[cur.i][cur.j] = 'O';
			cur = cur.parent;
		}
		
		for( int i = 0 ; i < n; i++){
			for( int j = 0; j < m; j++){
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	private boolean inRange(int nxti, int nxtj) {
		if (nxti >= n || nxtj >= m || nxti < 0 || nxtj < 0)
			return false;
		return true;
	}

	private void init(Scanner s) {
		map = new char[n][m];
		mapNode = new Node[n][m];
		for (int i = 0; i < n; i++) {
			String tmp = s.next();
			for (int j = 0; j < m; j++) {
				map[i][j] = tmp.charAt(j);
				mapNode[i][j] = new Node(i, j);
				mapNode[i][j].h = getH(i + 1, j + 1);
			}
		}
	}

	/**
	 * 曼哈顿距离
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private int getH(int i, int j) {
		return (n - i + m - j) * 10;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}

}
