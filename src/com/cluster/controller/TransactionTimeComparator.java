package com.cluster.controller;

import java.util.Comparator;

public class TransactionTimeComparator implements Comparator<TransactionTO> {

	@Override
	public int compare(TransactionTO t1, TransactionTO t2) {

		/*TransactionTO tranTime1 = (TransactionTO) t1;
		TransactionTO tranTime2 = (TransactionTO) t2;*/

		Long time1 = t1.getTime();
		Long time2 = t2.getTime();
		System.out.println(time1.compareTo(time2));
		return time2.compareTo(time1);
		

	}

}
