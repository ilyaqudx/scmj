package freedom.scmj.net;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;


/**
 * 数据读取处理器
 * */
public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

	
	private AsynchronousSocketChannel socket;
	public ReadHandler(AsynchronousSocketChannel socket)
	{
		this.socket = socket;
	}
	
	public void completed(Integer result, ByteBuffer buffer) {
		// TODO Auto-generated method stub
		if(result > 0){
			System.out.println("有数据返回 : " + result);
			buffer.flip();
			int packetLength = buffer.getInt();
			byte[] array     = buffer.array();
			String response = new String(array,4,packetLength);
			System.out.println(response);
		}
	}

	public void failed(Throwable exc, ByteBuffer buffer) {
		// TODO Auto-generated method stub
		
	}


}
