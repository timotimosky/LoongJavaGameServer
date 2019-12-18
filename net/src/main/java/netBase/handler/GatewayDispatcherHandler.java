package netBase.handler;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import netBase.packet.AbstractPacket;
import netBase.util.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;
//import org.apache.log4j;

/**
 *
 */
@ChannelHandler.Sharable
public class GatewayDispatcherHandler extends BaseDispatcherHandler {

    private static final Logger logger = LoggerFactory.getLogger(GatewayDispatcherHandler.class);

    private BiFunction<Session, AbstractPacket, Boolean> packetFilter;

    public GatewayDispatcherHandler(BiFunction<Session, AbstractPacket, Boolean> packetFilter) {
        this.packetFilter = packetFilter;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        logger.info("GatewayDispatcherHandler ----channelRead ");
        /*var session = SessionUtils.getSession(ctx);
        if (session == null) {
            return;
        }

        var decodedPacketInfo = (DecodedPacketInfo) msg;
        var packet = decodedPacketInfo.getPacket();
        if (packet.protocolId() == Heartbeat.heartbeatProtocolId()) {
            return;
        }

        // 过滤非法包
        if (packetFilter != null && !packetFilter.apply(session, packet)) {
            return;
        }


        var loadBalancer = NetContext.getConfigManager().getLocalConfig().getConsumerConfig().getLoadBalancer();
        Session consumerSession = null;
        try {
            consumerSession = loadBalancer.loadBalancer(packet, session);
        } catch (Exception e) {
            logger.error("网关发生错误", e);
            return;
        }

        var signalAttachment = (SignalPacketAttachment) decodedPacketInfo.getPacketAttachment();
        NetContext.getDispatcherManager().send(consumerSession, packet, new GatewayPacketAttachment(session, signalAttachment));*/
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
       /* var session = SessionUtils.getSession(ctx);
        if (session == null) {
            return;
        }

        var sid = session.getSid();
        var uid = (Long) session.getAttribute(AttributeType.UID);
        EventContext.getEventBus().asyncSubmit(GatewaySessionInactiveEvent.valueOf(sid, uid == null ? 0 : uid.longValue()));

        super.channelInactive(ctx);*/
    }

}

