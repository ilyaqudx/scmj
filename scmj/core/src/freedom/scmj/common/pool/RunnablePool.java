package freedom.scmj.common.pool;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class RunnablePool {

	private int core;
	private int mini;
	private int max;
	private Queue<Runnable> runnables;
	
	private RunnablePool()
	{
		this.core = 10;
		this.mini = 3;
		this.max  = 20;
		this.runnables = new ArrayBlockingQueue<Runnable>(max);
		this.init();
	}
	
	private void init()
	{
		for (int i = 0; i < mini; i++) 
		{
			this.runnables.add(new Task());
		}
	}
	
	public Runnable getRunnable()
	{
		return this.runnables.peek();
	}
	
	public static final RunnablePool inst()
	{
		return RunnablePoolSinglton._instance;
	}
	
	private static final class RunnablePoolSinglton
	{
		private static final RunnablePool _instance = new RunnablePool();
	}
	
	class Task implements Runnable
	{
		public void run() 
		{
			
		}
	}
}
