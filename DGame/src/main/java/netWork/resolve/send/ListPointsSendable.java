package netWork.resolve.send;

import java.awt.Point;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import netWork.resolve.SendableBuffer;

/**
 * 点列表
 * @author djl
 * @date 2012-11-29
 */
public class ListPointsSendable extends SendableBuffer 
{

	@SuppressWarnings("unchecked")
	@Override
	public void write(ChannelBuffer buffer, Object arg1)
	{
		List<Point> points = (List<Point>) arg1; 
		
		writeShort(buffer, points.size());
		for(Point point : points)
		{
			writeShort(buffer, point.y);
			writeShort(buffer, point.x);
		}
	}
	///buffer = encode(buffer, moduleId, opcode, objects);
	///send(client, buffer);
}
