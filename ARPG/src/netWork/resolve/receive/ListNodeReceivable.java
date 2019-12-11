package netWork.resolve.receive;

import java.nio.ByteBuffer;

import netWork.resolve.ReceivableBuffer;

/**
 * 接收前端发来的节点
 * @author djl
 * @date 2012-10-19
 */
public class ListNodeReceivable extends ReceivableBuffer 
{
	

	@Override
	public Object read(ByteBuffer byteBuffer) 
	{
/*		List<Node> list = new ArrayList<Node>();
		int length = readShort(byteBuffer);
		Node node;
		for(int i = 0; i < length; i++)O
		{
			node = new Node(readShort(byteBuffer), readShort(byteBuffer));
	//		node.setDirection(readByte(byteBuffer));
			list.add(node);
		}
		return list;*/
		return null;
	}

}
