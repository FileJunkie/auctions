package ru.spbstu.students.util;

import java.util.concurrent.PriorityBlockingQueue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ru.spbstu.students.dao.BidDAO;
import ru.spbstu.students.web.PriorityAddBid;
import ru.spbstu.students.web.PriorityGetBidList;

public class PriorityThread implements ServletContextListener {

	public static PriorityBlockingQueue<PriorityGetBidList> inQueue = new PriorityBlockingQueue<PriorityGetBidList>();
	public static PriorityBlockingQueue<PriorityGetBidList> outQueue = new PriorityBlockingQueue<PriorityGetBidList>();
	
	public static PriorityBlockingQueue<PriorityAddBid> addQueue = new PriorityBlockingQueue<PriorityAddBid>();
	public static PriorityBlockingQueue<PriorityAddBid> resQueue = new PriorityBlockingQueue<PriorityAddBid>();
	
	private BidDAO bidDao;
	private volatile boolean activeGet = true;
	private volatile boolean activeAdd = true;

	Runnable getBids = new Runnable() {
		public void run() {
			while (activeGet) {
				if (!inQueue.isEmpty()) {
					PriorityGetBidList bid = inQueue.poll();
					bidDao = bid.getBidDao();
					bid.setBidList(bidDao.getBids(bid.getItemId()));
					outQueue.add(bid);
				}
			}
		}
	};
	
	Runnable addBid = new Runnable() {
		public void run() {
			while (activeAdd) {	
				if (!addQueue.isEmpty()) {
					PriorityAddBid addBid = addQueue.poll();
					bidDao = addBid.getBidDao();
					addBid.setResult(bidDao.addBid(addBid.getBid()));
					resQueue.add(addBid);
				}
			}
		}
	};

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		new Thread(getBids).start();
		new Thread(addBid).start();
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		activeGet = false;
		activeAdd = false;
	}
}
