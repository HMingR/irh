package imuster;

import com.codingapi.txlcn.tc.txmsg.MessageCreator;
import com.codingapi.txlcn.tc.txmsg.TCSideRpcInitCallBack;
import com.codingapi.txlcn.txmsg.dto.MessageDto;
import com.codingapi.txlcn.txmsg.exception.RpcException;
import com.codingapi.txlcn.txmsg.netty.impl.NettyRpcClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.goods.GoodsProviderApplication;

@SpringBootTest(classes = GoodsProviderApplication.class)
@RunWith(SpringRunner.class)
public class testRpc {

    @Autowired
    TCSideRpcInitCallBack tcSideRpcInitCallBack;


    @Test
    public void test02() throws RpcException {
        NettyRpcClient rpcClient = new NettyRpcClient();
    }
}
