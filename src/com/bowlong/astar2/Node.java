package com.bowlong.astar2;


/**
 * 地图中的每个位置
 * @author coolgo
 */
public class Node {

	public int i,j;
	/**
	 * f = g + h; h为估价函数
	 */
	public int f=1<<30;
	public int g=1<<30;
	public int h=1<<30;
	/**
	 * if steped= true;表示已经在close列表中
	 */
	private boolean steped = false;
	/**
	 * if inbox = true;表示已经在open列表中
	 */
	private boolean inbox = false;
	public Node parent = this;

	public Node(int ii, int jj){
		i = ii;
		j = jj;
	}

	public void setSteped(boolean steped) {
		this.steped = steped;
	}

	public boolean isSteped() {
		return steped;
	}

	public void setInbox(boolean inbox) {
		this.inbox = inbox;
	}

	public boolean isInbox() {
		return inbox;
	}
}
