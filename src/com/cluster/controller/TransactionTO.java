package com.cluster.controller;

public class TransactionTO /*implements Comparable*/ {

	private int tranID = 0;
	private long time = 0;
	private int accNO = 0;
	private int amt = 0;
	private int balance = 0;
	private String type = null;

	public int getTranID() {
		return tranID;
	}

	public void setTranID(int tranID) {
		this.tranID = tranID;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getAccNO() {
		return accNO;
	}

	public void setAccNO(int accNO) {
		this.accNO = accNO;
	}

	public int getAmt() {
		return amt;
	}

	public void setAmt(int amt) {
		this.amt = amt;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	/*@Override
	public int compareTo(Object obj) {
		TransactionTO transactionTO1 = (TransactionTO) obj;
		String time1 = this.time;
		String time2 = transactionTO1.time;
	return time1.compareTo(time2);
		 
	}*/

}
