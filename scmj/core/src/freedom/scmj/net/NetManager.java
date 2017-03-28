package freedom.scmj.net;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ThreadFactory;


public class NetManager extends Thread{

	private static final NetManager I = new NetManager();
	
	public static final NetManager getInstance()
	{
		return I;
	}
	
	public static final String SIP  = "localhost";
	public static final int    PORT = 9900;
	private static final int DELAY_RECONNECT_TIME = 5000;
	public NetManager()
	{
		connect0();
	}

	private void connect0()
	{
		this.start();
	}

	private void connect()
	{
		try 
		{
			AsynchronousChannelGroup group = AsynchronousChannelGroup.withFixedThreadPool(1, new ThreadFactory() {
				
				public Thread newThread(Runnable r) 
				{
					return new Thread(r,"connector-thread");
				}
			});
			AsynchronousSocketChannel socket = AsynchronousSocketChannel.open(group );
			socket.setOption(StandardSocketOptions.TCP_NODELAY, true);
			socket.connect(new InetSocketAddress(SIP, PORT), socket, new ConnectHandler());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		NetManager.getInstance();
		
	}
	
	public void run()
	{
		//handleNet();
		//resetConnect();
		connect();
	}

	private void handleNet() {
		
	}
	
	private void resetConnect()
	{
		try
		{
			this.wait(DELAY_RECONNECT_TIME);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
			resetConnect();
		}
	}
}
