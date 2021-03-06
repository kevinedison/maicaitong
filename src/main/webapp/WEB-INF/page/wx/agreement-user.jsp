<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<style>
    p {padding: 5px 5px;}
    html,body,.mui-content {background-color: #fff;}
    h4,h5 {margin-left: 5px;}
    .mui-plus header.mui-bar {display: none;}
    .mui-plus .mui-bar-nav~.mui-content {padding: 0;}
    ol li{list-style: inherit;}
    ul li{list-style: initial;}
</style>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-买家协议</h1>
    <a href="/wx/account" class="mui-icon mui-icon-person mui-pull-right"></a>
</header>
<div class="mui-content">
    <div class="mui-content-padded">
        <p>「带点啥」海淘市场汇聚了社交，代购，商品导购的业务属性，海外买手搜罗全球好产品、国内价高产品、国内缺货产品、当地特色产品、大牌名牌商品。品类齐全，买手信誉度好等，货真价实，买家随时随地轻松下单，用最给力的折扣代您体验真实的海外血拼，而不需您亲自到现场购物。相关买家注意事项详见下方：</p>
        <h4>一、发货及收货</h4>
        <ol>
            <li>
                <p><strong> 发货承诺定义: </strong> 在买家拍下买手出售的商品并成功付款后，买手需要在买家付款成功后七天内完成发货，第一条物流记录记录出现的时间或物流记录显示的揽件时间须在买家付款完成后7天内。</p>
            </li>
            <li>
                <p>买手发货超时免责场景</p>
                <ul>
                    <li>
                        <p>若因买家原因（如：身份证提交不及时、身份信息虚假）导致买手发货延迟，不算买手违背承诺。</p>
                    </li>
                    <li>
                        <p>商家若因不可抗力因素：例如地震、台风、海啸、战争等造成未能按约定时间发货，参照国家法律关于不可抗力因素的规定，不算买手违背承诺。</p>
                    </li>
                    <li>
                        <p>若遇到国外节假日导致买手无法在规定时间内发货，且买手在商品页中说明此情况，不算买手违背承诺。</p>
                    </li>
                    <li>
                        <p>买手在商品页中说明有无法在7天内备到货的可能，且同时说明大致能备到货的时间。此种情况不算买手违背承诺。</p>
                    </li>
                    <li>
                        <p><strong>收货：</strong> 系统在买手发货后25天自动确认收货。</p>
                    </li>
                </ul>
            </li>
        </ol>

        <h4>二、邮寄方式</h4>
        <ol>
            <li>
                <p>买手	提供直邮方式。相关物流进程，买家可在订单详情中进行查看跟进。具体使用的邮寄方式以买手在商品页说明为准。</p>
            </li>
            <li><strong> 直邮： </strong> 买手直接将商品从海外邮寄到买家手中。买家需要在付款时，立即将身份证信息，以及清晰的身份证正反面照片上传到系统中。（身份证信息用于海关清关，「带点啥」平台会对用户身份信息严格保密）</li>
            <li>
                <h5> 到货时间： 指从买手点击发货之日起至商品到买家手里的时间。</h5>
                <div> 日韩地区一般为： 7-10天左右 </div>
                <div> 北美地区一般为：10-14天左右 </div>
                <div> 中东地区一般为：12-16天左右 </div>
                <div> 欧洲地区一般为：14-18天左右 </div>
                <div> 澳洲地区一般为：16-20天左右 </div>
            </li>
            <span class="mui-badge mui-badge-danger mui-badge-inverted"><strong>注意:因到货时间会受物流及海关等因素影响，非买手所能掌控，故以上时间仅作到货时间的参考。请买家根据实际物流情况，耐心等待。</strong></span>
        </ol>

        <h4>三、退款</h4>
        <ol>
            <li>
                <p>买家可申请退款场景</p>
                <ul>
                    <li>
                        <p>买家在付款后24小时内支持退款，逾期无法给于退款，请买家在购买前慎重考虑。</p>
                    </li>
                    <li>
                        <p>买家付款后，买手无法在规定时间内发出商品，买家可以提出退款申请。</p>
                    </li>
                    <li>
                        <p>若买家在发货后20天内，仍然没有收到货，则可以申请退款。</p>
                    </li>
                </ul>
            </li>
            <li>
                <p>如何退款：</p>
                <p>买家在“我的订单”中找到需要退款订单，点击“退款”。选择退款原因并提交退款申请即可。</p>
                <p>退款成功后，货款退回至买家原支付账户，根据不同付款方式，退款到账时间、支付银行略有不同。</p>
                <span class="mui-badge mui-badge-danger mui-badge-inverted"><strong>注意:一旦确认收货，退款入口关闭。</strong></span>
            </li>
        </ol>

        <h4>四、退货</h4>
        <p>「带点啥」海淘不支持无理由退货。买家不能因为喜好，尺码等个人问题要求退货。</p>

        <h4>五、换货</h4>
        <p>「带点啥」不支持换货。若买家有换货需求，请自行与买手协商解决。「带点啥」对此不承担责任。</p>

        <h4>六、正品保证，假一赔三</h4>
        <p>“假一赔三”是指买手使用「带点啥」提供的技术及服务向买家提供的特别售后服务，允许买家按“假一赔三”服务规则及「带点啥」其他公示规则的规定对已购得的商品认定为假货的前提下，要求买手支付三倍赔偿。买家申请“假一赔三”时，可在「带点啥」对应订单的维权入口申请维权，选择“商品质量问题”，「带点啥」会根据凭证有效性进行处理。</p>
        <p>买家申请投诉时需提供以下证明材料：</p>
        <ol>
            <li>
                <p><strong>化妆品类：</strong> 品牌商鉴定报告，商家承认商品假货的聊天记录截图、工商局假货没收函、同时提供真假化妆品对比图片作为参考等。</p>
            </li>
            <li>
                <p><strong>3C数码类：</strong> 买手承认商品假货的聊天记录截图、品牌商鉴定报告、工商局假货没收函等。</p>
            </li>
            <li>
                <p><strong>其他：</strong> 买手承认商品假货的聊天记录截图、品牌商鉴定报告、工商局假货没收函等。</p>
            </li>
        </ol>

        <h4>七、买家义务</h4>
        <p>“假一赔三”是指买手使用「带点啥」提供的技术及服务向买家提供的特别售后服务，允许买家按“假一赔三”服务规则及「带点啥」其他公示规则的规定对已购得的商品认定为假货的前提下，要求买手支付三倍赔偿。买家申请“假一赔三”时，可在「带点啥」对应订单的维权入口申请维权，选择“商品质量问题”，「带点啥」会根据凭证有效性进行处理。</p>
        <p>买家申请投诉时需提供以下证明材料：</p>
        <ol>
            <li>
                <p>买家应当在订单中向卖家提供准确的收货地址和收货人信息。买家需要变更订单中的收货地址或收货人信息的，应当征得卖家的明确同意。</p>
            </li>
            <li>
                <p>买家只填写了收货地址，填写的收货人信息不特定，商品在收货地址被签收的，该签收视为买家本人签收。</p>
            </li>
            <li>
                <p>收货人可以本人签收商品或委托他人代为签收商品，被委托人的签收视为收货人本人签收。</p>
            </li>
            <li>
                <p>收货人签收商品时，应当对商品进行验收。收货人签收商品时发现表面不一致（“表面一致”，是指凭肉眼即可判断所收到的商品表面状况良好且与网上描述相符，表面一致的判断范围可包括但不限于货物的形状、大小、数量、重量……等。）等情形，有权拒绝签收商品。</p>
            </li>
            <li>
                <p>收货人签收商品后，非因商品质量的原因，商品毁损、灭失的风险由卖家转移给买家。</p>
            </li>
        </ol>
        <div class="mui-text-center"><span class="mui-badge mui-badge-danger mui-badge-inverted"><strong>以上规则最终解释权归「带点啥」所有。</strong></span></div>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<%@ include  file="common/footer.jsp"%>