package freedom.scmj.entity;

public class MoveRange 
{
	private int begin;
	private int end;
	private int size;
	
	public MoveRange(int begin,int end,int size)
	{
		this.begin = begin;
		this.end   = end;
		this.size  = size;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "MoveRange [begin=" + begin + ", end=" + end + ", size=" + size
				+ "]";
	}
}
