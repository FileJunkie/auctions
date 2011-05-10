package ru.spbstu.students.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ru.spbstu.students.web.UserCategories;

public class CounterThread implements ServletContextListener {
		
	private volatile boolean run = true;
	
	static private class CounterDiff{
		private boolean up;
		private UserCategories category;	
		
		public CounterDiff(UserCategories category, boolean up) {			
			this.category = category;
			this.up = up;
		}
		public boolean isUp() {
			return up;
		}
		public UserCategories getCategory() {
			return category;
		}			
	}
	
	private static LinkedBlockingQueue<CounterDiff> queue = new LinkedBlockingQueue<CounterDiff>();
	private static ConcurrentHashMap<UserCategories, Integer> counters = new ConcurrentHashMap<UserCategories, Integer>();
	private static ConcurrentHashMap<UserCategories, Integer> countersPermanent = new ConcurrentHashMap<UserCategories, Integer>();
	
	private Runnable runnable = new Runnable(){		
		public void run(){
			while(run){
				if(!queue.isEmpty()){
					CounterDiff cd = queue.poll();
					if(cd.isUp()){
						counters.put(cd.getCategory(), counters.get(cd.getCategory()) + 1); // I know it's not atomical. Fuck it. 
						countersPermanent.put(cd.getCategory(), counters.get(cd.getCategory()) + 1); // Only one thread can access it, anyway
					}
					else{
						counters.put(cd.getCategory(), counters.get(cd.getCategory()) - 1); // In worst case the old value will be returned by getter 
					}
				}
			}
		}
	};
	
	public void contextDestroyed(ServletContextEvent arg0) {
		for(UserCategories uc : UserCategories.values()){
			counters.put(uc, 0);
			countersPermanent.put(uc, 0);
		}
		new Thread(runnable).start();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		run = false;
	}
	
	public static void inc(UserCategories uc){
		queue.add(new CounterDiff(uc, true));
	}

	public static void dec(UserCategories uc){
		queue.add(new CounterDiff(uc, false));
	}

	public int getCounter(UserCategories uc){
		return counters.get(uc);
	}
	
	public int getCounterPermanent(UserCategories uc){
		return countersPermanent.get(uc);
	}
}
