<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>商品详情</title>
    <!-- 引入字体图标 -->
    <link rel="stylesheet" href="./css/font/iconfont.css">
    <link rel="stylesheet" href="./css/index.css">
    <link rel="stylesheet" href="./css/leaveM.css">
    <link rel="stylesheet" href="./css/Dialog.css">
    <style>

    </style>
    <!-- 引入Vue -->
    <script src="https://unpkg.com/vue/dist/vue.js"></script>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <!-- 引入组件库 -->
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
</head>

<body>
<div id="app">
    <div class="header">
        <el-row style="height: 60px; overflow: hidden; font-size: 40px; line-height: 60px;">
            <el-col :span="12">
                <img src="./images/LOGO2.png" alt="" style="height: 60px;">
            </el-col>
            <el-col :span="12" style="display: flex; justify-content: flex-end; font-size: 12px;color: #666">
                <span class="iconfont icon-weizhi" style="font-size: 24px;"></span><span>位置信息</span>
            </el-col>
        </el-row>
    </div>
    <!-- 商品内容区域 -->
    <div class="main" style="margin: 6px 0px;">
        <div style="overflow:hidden;height: 350px; width: 350px; background-color: #999;">
            <img src="./images/shoe.jpg" alt="" style="width: 350px;height: 350px;">
        </div>
        <div class="text_description" style="width: 600px;">
            <p>探巡 鞋子男潮流男鞋手工定制2019冬季新款加绒保暖高帮老爹鞋男时尚百搭韩版运动休闲鞋板鞋 A006-灰黑探巡 </p>
            <p class="price">价格: <span>998</span></p>
            <p>服务支持: <a href="#">包邮</i> <a href="#">闪电退款</a> <a href="#">店家实名</a> <a href="#">平台担保</a></p>
            <p>交易方式: <a href="#">支持当面交易</a> <a href="#">支持包邮</a></p>
            <el-button type="danger" @click="submitBuy" style="margin-top: 30px;">立即购买 &raquo;</el-button>
        </div>

    </div>

    <!-- 详情区域 -->
    <div class="container_footer" style="margin-top: 20px;">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="商品介绍" name="first" class="first">
                <el-row>
                    <el-col :span="6">商品名称:</el-col>
                    <el-col :span="6">商品计量单位:</el-col>
                    <el-col :span="6">商品原价:</el-col>
                    <el-col :span="6">售卖价格:</el-col>
                </el-row>
                <el-row>
                    <el-col :span="6">卖家入手时间:</el-col>
                    <el-col :span="6">新旧程度:</el-col>
                    <el-col></el-col>
                    <el-col></el-col>
                </el-row>
                <el-divider></el-divider>
                <el-row>
                    <!-- 这个地方放图片文字介绍之类的 -->
                    <img src="//img10.360buyimg.com/imgzone/jfs/t1/99628/26/7916/676796/5dff8da5Ea4a67663/2e4d65d342440c79.jpg"
                         alt="" style="max-width: 1000px;">
                </el-row>
            </el-tab-pane>
            <el-tab-pane label="卖家信息" name="second">
                <el-card shadow='never'>
                    <el-row>
                    </el-row>
                </el-card>
            </el-tab-pane>
            <el-tab-pane label="全部留言" name="three">
                <div class="leaveM">
                    <!-- 留言内容区域 -->
                    <el-row>
                        <el-col :span="2" class="img">
                            <!-- 这是头像 -->
                            <img
                                    src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586863386046&di=52a5b634344a38117cb5578fa0749a08&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg"
                                    alt="" class="headImg1">
                        </el-col>
                        <el-col :span="18" class="content to_reply_act" >
                            <div class="nike"><span>张三李四</span></div>
                            <div class="content">
                                <span>这个鞋子还不错奥</span>
                            </div>
                            <div class="mess">
                  <span class="creatT">
                    2020-09-12 19:00
                  </span>
                                <span class="like">
                    <i class="iconfont icon-zan-"></i> <span>990</span>
                  </span>
                                <span class="reply">
                    <span @click="to_reply">回复</span>
                                    <!-- 如果回复内容超过0条 下面隐藏   这里放一个一个按钮 -->
                    <span @click="openFloding">查看更多</span>
                  </span>
                            </div>
                            <!-- 这里增加一个折叠  当我点击查看回复的时候  打开折叠框 -->
                            <!-- 二级留言 s  second -->
                            <div class="s_box clo_b">
                                <el-row>
                                    <el-col :span="1" class="s_img">
                                        <!-- 这是头像 -->
                                        <img
                                                src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586863386046&di=52a5b634344a38117cb5578fa0749a08&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg"
                                                alt="" class="headImg1">
                                    </el-col>
                                    <el-col :span="18" class="s_content">
                                        <div class="nike_content"><span class="nike">张三李四</span> <span>这个鞋子还不错奥</span></div>
                                        <div class="s_mess">
                        <span class="s_creatT">
                          2020-09-12 19:00
                        </span>
                                            <span class="s_like">
                          <i class="iconfont icon-zan-"></i> <span>990</span>
                        </span>
                                            <span class="s_reply">
                          <span @click="to_reply">回复</span>
                                                <!-- 如果回复内容超过0条 下面隐藏   这里放一个一个按钮 -->
                                                <!-- <span></span> -->
                        </span>
                                        </div>

                                </el-row>
                                <el-row>
                                    <el-col :span="1" class="s_img">
                                        <!-- 这是头像 -->
                                        <img
                                                src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586863386046&di=52a5b634344a38117cb5578fa0749a08&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg"
                                                alt="" class="headImg1">
                                    </el-col>
                                    <el-col :span="18" class="s_content">
                                        <div class="nike_content"><span class="nike">张三李四</span> <span>这个鞋子还不错奥</span></div>
                                        <div class="s_mess">
                        <span class="s_creatT">
                          2020-09-12 19:00
                        </span>
                                            <span class="s_like">
                          <i class="iconfont icon-zan-"></i> <span>990</span>
                        </span>
                                            <span class="s_reply">
                          <span @click="to_reply">回复</span>
                                                <!-- 如果回复内容超过0条 下面隐藏   这里放一个一个按钮 -->
                                                <!-- <span></span> -->
                        </span>
                                        </div>

                                </el-row>
                                <!-- 二级留言分页 -->
                                <div class="s_pagination">
                                    <el-pagination small layout="prev, pager, next" :total="50">
                                    </el-pagination>
                                </div>
                            </div>
                            <!-- 发表回复区域 -->
                            <div class="up_reply_mess clo_b">
                                <el-input class="" size="mini"></el-input>
                                <el-button size="mini" type="primary">回复</el-button>
                            </div>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="2" class="img">
                            <!-- 这是头像 -->
                            <img
                                    src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586863386046&di=52a5b634344a38117cb5578fa0749a08&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg"
                                    alt="" class="headImg1">
                        </el-col>
                        <el-col :span="18" class="content to_reply_act" >
                            <div class="nike"><span></span></div>
                            <div class="content">
                                <span></span>
                            </div>
                            <div class="mess">
                  <span class="creatT">

                  </span>
                                <span class="like">
                    <i class="iconfont icon-zan-"></i> <span></span>
                  </span>
                                <span class="reply">
                    <span @click="to_reply">回复</span>
                                    <!-- 如果回复内容超过0条 下面隐藏   这里放一个一个按钮 -->
                    <span @click="openFloding">查看更多</span>
                  </span>
                            </div>
                            <!-- 这里增加一个折叠  当我点击查看回复的时候  打开折叠框 -->
                            <!-- 二级留言 s  second -->
                            <div class="s_box clo_b">
                                <el-row>
                                    <el-col :span="1" class="s_img">
                                        <!-- 这是头像 -->
                                        <img
                                                src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586863386046&di=52a5b634344a38117cb5578fa0749a08&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg"
                                                alt="" class="headImg1">
                                    </el-col>
                                    <el-col :span="18" class="s_content">
                                        <div class="nike_content"><span class="nike">张三李四</span> <span>这个鞋子还不错奥</span></div>
                                        <div class="s_mess">
                        <span class="s_creatT">
                          2020-09-12 19:00
                        </span>
                                            <span class="s_like">
                          <i class="iconfont icon-zan-"></i> <span>990</span>
                        </span>
                                            <span class="s_reply">
                          <span @click="to_reply">回复</span>
                                                <!-- 如果回复内容超过0条 下面隐藏   这里放一个一个按钮 -->
                                                <!-- <span></span> -->
                        </span>
                                        </div>

                                </el-row>
                                <el-row>
                                    <el-col :span="1" class="s_img">
                                        <!-- 这是头像 -->
                                        <img
                                                src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586863386046&di=52a5b634344a38117cb5578fa0749a08&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg"
                                                alt="" class="headImg1">
                                    </el-col>
                                    <el-col :span="18" class="s_content">
                                        <div class="nike_content"><span class="nike">张三李四</span> <span>这个鞋子还不错奥</span></div>
                                        <div class="s_mess">
                        <span class="s_creatT">
                          2020-09-12 19:00
                        </span>
                                            <span class="s_like">
                          <i class="iconfont icon-zan-"></i> <span>990</span>
                        </span>
                                            <span class="s_reply">
                          <span @click="to_reply">回复</span>
                                                <!-- 如果回复内容超过0条 下面隐藏   这里放一个一个按钮 -->
                                                <!-- <span></span> -->
                        </span>
                                        </div>

                                </el-row>
                                <!-- 二级留言分页 -->
                                <div class="s_pagination">
                                    <el-pagination small layout="prev, pager, next" :total="50">
                                    </el-pagination>
                                </div>
                            </div>
                            <!-- 发表回复区域 -->
                            <div class="up_reply_mess clo_b">
                                <el-input class="" size="mini"></el-input>
                                <el-button size="mini" type="primary">回复</el-button>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <!-- 分页区域 -->
                <div class="pagination">
                    <el-pagination :page-size="100" layout="prev, pager, next, jumper" :total="1000">
                    </el-pagination>
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>

    <!-- 弹框 订单区域 -->
    <el-dialog :visible.sync="orderDialogVisible" title="待支付订单" width="60%">
        <div class="container_d">
            <div class="orderId">
                <span>订单编号: </span> <span>1233252344</span>
            </div>
            <div class="orderMessage">
                <ul>
                    <li>
                        <el-row class="listBox">
                            <el-col :span="3" class="img">
                                <!-- 这里是封皮 -->
                                <div class="envelop_img">
                                    <img src="./images/shoe.jpg"
                                         alt="">
                                </div>
                            </el-col>
                            <el-col :span="9">
                                <div class="desc">
                        <span class="content">外套男士2020春秋新款宽松上衣韩版松上衣韩版版松上衣韩版<span class="tag">
                            <el-tag size="mini">标签</el-tag>
                          </span></span>
                                    <!-- 循环的时候需要循环下面一项 -->

                                    <p class="goods_message">
                                        <span class="color">颜色 红白</span>
                                        <span class="size">尺码 XL</span>
                                    </p>
                                </div>
                            </el-col>
                            <el-col :span="3">
                                <div class="price">
                                    <span>￥</span>
                                    <span>998.00</span>
                                </div>
                            </el-col>
                            <el-col :span="3">
                                <div class="trade_type">
                                    <!-- 交易类型 二者只显示其一 -->
                                    <!-- <el-tag>正常交易</el-tag> -->
                                    <el-tag size="mini">公益捐赠</el-tag>
                                </div>
                            </el-col>
                            <el-col :span="6">
                                <div class="address">
                                    <div>
                                        <p>23号楼120宿舍 张三收 1996310288</p>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </li>
                </ul>
            </div>
        </div>
        <span slot="footer" class="dialog-footer">
        <el-button @click="orderDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitOrder">支 付</el-button>
      </span>
    </el-dialog>
</div>

<script>
    new Vue({
        el: '#app',
        data() {
            return {
                // 控制tab栏默认选择项
                activeName: 'first',
                // 待支付订单弹框
                orderDialogVisible:false,
            }
        },

        methods: {
            // 切换tab
            handleClick(tab, event) {
                console.log(tab, event);
            },
            // 留言打开折叠
            openFloding(event) {
                console.log(event.target);
                let myNode = event.target
                let boxNode = myNode.parentNode.parentNode.nextElementSibling
                boxNode.classList.remove("clo_b")
                myNode.style.display = 'none'
                console.log(boxNode);
            },
            to_reply(event) {
                // 先关闭其他所有的
                document.querySelector('.up_reply_mess').classList.add('clo_b')
                // 通过js原生的属性  选择 点击的这个按钮的祖先元素是否为to_reply_act 并选中其class 为to_reply_act的元素
                let myNode = event.target
                // console.log(myNode.parentNode.classList);
                while(!this.indexFind(myNode.classList,'to_reply_act')){
                    myNode = myNode.parentNode;
                }
                // 经过循环之后   myNode就是我们要控制的回复区域的父元素了
                myNode.querySelector('.up_reply_mess').classList.remove('clo_b')
                console.log(myNode);
            },
            // 判断数组中是否含有某一项  第一个参数是数组  第二个参数是 要查的数
            indexFind(arr,i){
                for(let j = 0;j<arr.length;j++){
                    if(arr[j] === i){
                        return true
                    }
                }
                return false
            },
            // 确认购买 弹出订单
            submitBuy(){
                // 发起请求  生成订单 弹框显示
                this.orderDialogVisible = true
            },
            // 提交订单
            submitOrder(){
                // 提交订单
            }
        }
    })
</script>
</body>
</html>