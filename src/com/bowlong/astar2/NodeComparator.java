package com.bowlong.astar2;

import java.util.Comparator;

/**
 * 比较器
 * @author coolgo
 */
public class NodeComparator implements Comparator<Node> {
	@Override
	public int compare(Node x, Node y) {
		return x.f - y.f;
	}
}