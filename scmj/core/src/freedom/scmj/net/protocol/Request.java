package freedom.scmj.net.protocol;

public class Request {

	private short cmd;
	private long mid;
	private byte oneWay;
	private Object body;
	private long uid;
	public short getCmd() {
		return cmd;
	}
	public void setCmd(short cmd) {
		this.cmd = cmd;
	}
	public long getMid() {
		return mid;
	}
	public void setMid(long mid) {
		this.mid = mid;
	}
	public byte getOneWay() {
		return oneWay;
	}
	public void setOneWay(byte oneWay) {
		this.oneWay = oneWay;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "Request [cmd=" + cmd + ", mid=" + mid + ", oneWay=" + oneWay
				+ ", body=" + body + ", uid=" + uid + "]";
	}
}
