package freedom.scmj.net;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.alibaba.fastjson.JSON;

import freedom.scmj.net.protocol.Cmd;
import freedom.scmj.net.protocol.LoginPacket;
import freedom.scmj.net.protocol.Request;


/**
 * 连接处理器
 * */
public class ConnectHandler implements CompletionHandler<Void, AsynchronousSocketChannel> {

	
	/**
	 * 连接成功
	 * */
	public void completed(Void result, AsynchronousSocketChannel socket)
	{
		System.out.println("成功连接到服务器" + socket);
		LoginPacket packet = new LoginPacket();
		packet.setUserId(1);
		packet.setImei("fjldjsfljdslfj");
		packet.setLoginType(1);
		packet.setPassword("123456");
		packet.setEmail("aa@qq.com");
		
		Request request = new Request();
		request.setUid(1000);
		request.setCmd(Short.parseShort(Cmd.Req.LOGIN));
		request.setBody(packet);
		
		byte[] data = JSON.toJSONBytes(request);
		ByteBuffer buffer = ByteBuffer.allocate(data.length + 4);
		buffer.putInt(data.length);
		buffer.put(data);
		buffer.flip();
		socket.write(buffer);
		
		//注册异步读
		ByteBuffer readBuffer = ByteBuffer.allocate(2048);
		socket.read(readBuffer,readBuffer, new ReadHandler(socket));
	}

	/**
	 * 连接异步
	 * */
	public void failed(Throwable exc, AsynchronousSocketChannel socket) {
		// TODO Auto-generated method stub
		System.out.println("服务器连接异常");
		exc.printStackTrace();
	}

}
