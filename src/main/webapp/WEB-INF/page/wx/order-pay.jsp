<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<link href="/resources/wx/css/mui.picker.css" rel="stylesheet" />
<link href="/resources/wx/css/mui.poppicker.css" rel="stylesheet" />
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-订单</h1>
</header>

<nav class="mui-bar mui-bar-tab pay-result-nav" style="display:none" time=5>
    <div class="weixin-pay"><h3>微信支付</h3></div>
    <div class="weixin-logo"><img src="/resources/wx/images/weixin-pay.png" /></div>
    <div class="weixin-pay-result"><span class="mui-badge mui-badge-success">支付成功</span></div>
    <div class="redirect"><span class="mui-badge mui-badge-danger mui-badge-inverted">5</span>秒之后跳转到我的订单</div>
</nav>

<nav class="mui-bar mui-bar-tab pay-result-error" style="display:none" time=5>
    <div class="weixin-pay"><h3>微信支付</h3></div>
    <div class="weixin-logo"><img src="/resources/wx/images/weixin-pay.png" /></div>
    <div class="weixin-pay-result"><span class="mui-badge mui-badge-success">支付失败</span></div>
    <div class="redirect"><span class="mui-badge mui-badge-danger mui-badge-inverted">5</span>秒之后跳转到我的订单</div>
</nav>

<nav class="mui-bar mui-bar-tab service-nav" style="display:none">
    <button class="mui-tab-item pay">
        <span class="mui-tab-label">微信支付 ¥ </span>
    </button>
</nav>
<div class="mui-content">
    <div class="buyer-identifying"></div> 
    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">菜农信息</div>
        </li>
        <li class="mui-table-view-cell saler-info" style="height:150px;"></li>
    </ul>
    <div class="dividing"></div>
    <ul class="mui-table-view">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">订单信息</div>
        </li>
        <li class="mui-table-view-cell">
            <div class="mui-pull-left">名称</div>
            <div class="mui-pull-right order-service-title"></div>
        </li>
        <li class="mui-table-view-cell">
            <div class="mui-pull-left">单价</div>
            <div class="mui-pull-right order-service-price">¥ <em> /份</em></div>
        </li>
        <li class="mui-table-view-cell">
            <div class="mui-pull-left">数量</div>
            <div class="mui-pull-right order-number"></div>
        </li>
        <li class="mui-table-view-cell">
            <div class="mui-pull-right total-price"></div>
        </li>
    </ul>
    <div class="dividing"></div>
    <form class="mui-input-group">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">收件人信息</div>
        </li>
        <div class="mui-text-right notice-msg">
          <p>身份证信息仅供海关报关时使用，我们将严格保密，请填写真实信息，避免报关失败</p>
        </div>
        <div class="mui-input-row">
            <label>真实姓名</label>
            <input type="text" class="mui-text-right contact-real-name" placeholder="真实姓名">
        </div>
        <div class="mui-input-row">
            <label>联系电话</label>
            <input type="text" class="mui-text-right contact-phone" placeholder="联系电话">
        </div>

        <div class="mui-input-row">
            <label>身份证号</label>
            <input type="text" class="mui-text-right contact-id" placeholder="身份证号">
        </div>

        <div class="mui-input-row">
            <label>地址</label>
            <input id='cityPicker' type="text" class="mui-text-right contact-simple-addr" placeholder="选择地址">
        </div>

        <div class="mui-input-row" style="height:auto;">
            <label>详细地址</label>
            <textarea class="mui-text-right contact-detail-addr" rows="5" placeholder="详细地址"></textarea>
        </div>
    </form>
    
    <div class="dividing"></div>
    <form class="mui-input-group">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">支付方式</div>
        </li>
        <!--li class="mui-table-view-cell mui-radio mui-left">
			<input name="paytype" type="radio" value="1" checked="checked">直接支付给菜农
		</li-->
		<li class="mui-table-view-cell mui-radio mui-left">
			<input name="paytype" type="radio" value="0" checked="checked">卖菜通平台担保支付
		</li>
    </form>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script src="/resources/wx/js/city.data-3.js" type="text/javascript" charset="utf-8"></script>
<script src="/resources/wx/js/mui.picker.js"></script>
<script src="/resources/wx/js/mui.poppicker.js"></script>
<script type="text/javascript">
	var loadRenderOrderInfo = function(orderId,type) {
		$.ajax({
	        url     : '/user/pay/order?id=' + orderId+'&type='+type+'&rmd='+new Date().getTime(),
	        success : function(data){
	            if(data && data.code == '000000') {
	            	var order = data.data || {}; 
	            	var orderService = order.userService || {};
	            	var orderSaler = order.salerUser || {};	
	            	var contact = {};
	            	
	            	if(order.status == 1) {
	            		$('.buyer-identifying').html('<span class="mui-badge mui-badge-danger">'+daidian.config.orderStatus[order.status]+'</span>');
                    } else {
                    	$('.buyer-identifying').html('<span class="mui-badge mui-badge-warning">'+daidian.config.orderStatus[order.status]+'</span>');
                    }
	            	
	            	$('.buyer-identifying').html(daidian.config.orderStatus[order.status]);

	                var saleHtml = [];
	                
	                if(daidian.session.user && daidian.session.user.userId == orderSaler.userId && daidian.session.user.identityAuth == 1){
	                	saleHtml.push('<a action="/wx/account-service">');
                    }else{
                    	saleHtml.push('<a action="/wx/user?id='+orderSaler.userId+'">');
                    }
	                
	                saleHtml.push(' <div class="user-profile service-user">');
	                saleHtml.push('  <div class="user-img"><img src="'+getPictureUrl(orderSaler.avatar,2)+'" /></div>');
	                saleHtml.push('  <div class="user-name"><span style="font-size:16px;font-weight:bold;">'+((orderSaler.nickName))+ '</span> <span>'+ daidian.config.identityType[orderSaler.identity] || user.identity +'</span></div>');
	                saleHtml.push('  <div class="location-info">'+ ((dataconfig.countrys[orderSaler.country] || {}).name || '') +'</div>');
	                saleHtml.push(' </div>');
	                saleHtml.push('</a>');
	            	$('.saler-info').append(saleHtml.join(''));
	            	
	            	$('.order-service-title').text(orderService.title);
	            	$('.order-service-price').html('¥ '+order.price+'<em> / 份</em>');
	            	$('.order-number').html(order.count || 0);
	            	$('.total-price').html('<span>¥ '+order.totalPrice+'</span>');
	
	            	if(order.status == 1 || order.status == 0) {
	            		$('.service-nav').show();
	            		$('.pay .mui-tab-label').text('微信支付 ¥ '+order.totalPrice);
	            	}
	            	
	            	var buyerInfo = JSON.parse(order.buyerInfo || "{}");
	            	if(order.status == 1 || order.status == 0) {
	            		$('.contact-real-name').val(buyerInfo.realName || '');
		            	$('.contact-phone').val(buyerInfo.phone || '');
		            	$('.contact-id').val(buyerInfo.certNo || '');
		            	$('.contact-simple-addr').val(buyerInfo.addr || '');
		            	$('.contact-detail-addr').text(buyerInfo.addrdetail || '');
	            	} else {
	            		$('.contact-real-name').val(buyerInfo.realName || '').attr('readonly',true);
		            	$('.contact-phone').val(buyerInfo.phone || '').attr('readonly',true);
		            	$('.contact-id').val(buyerInfo.certNo || '').attr('readonly',true);
		            	$('.contact-simple-addr').val(buyerInfo.addr || '').attr('readonly',true);
		            	$('.contact-detail-addr').text(buyerInfo.addrdetail || '').attr('readonly',true);
	            	}
	            } else {
	                mui.toast(data.msg);
	            }
	        },
	        error : function() {
	            mui.toast('加载订单信息失败，请刷新页面重试');
	        }
	    });
	};

	$(function () {
		if(!checkSession()) {return;};
		var orderId = getQueryString('id');
		var type = getQueryString('type');
		loadRenderOrderInfo(orderId,type);
		
		var cityPicker =  new mui.PopPicker({layer: 3});
	    cityPicker.setData(cityData3);
	    $('#cityPicker').click(function(evt) {
	        cityPicker.show(function(items) {
	            var result = (items[0] || {}).text + " " + (items[1] || {}).text + " " + (items[2] || {}).text;
	            $('#cityPicker').val(result);
	        });
	    });
		
	   
	    
	    var onBridgeReady = function(payInfo){
	        WeixinJSBridge.invoke('getBrandWCPayRequest',payInfo,
	            function(res) {
	        	    $('.pay-result-nav').slideDown(300);
	        	    if (res.err_msg == "get_brand_wcpay_request:ok") {
	        	    	$('.pay-result-nav').slideDown(300);
			           	 setInterval(function() {
			       	    	var time = $('.pay-result-nav').attr('time') || 5;
			       	    	time--;
			       	    	$('.pay-result-nav').attr('time',time);
			       	        $('.redirect span').text(time);
			       	        if(time <=0) {
			       	        	window.location.href="/wx/account-order?rmd="+new Date().getTime();
			       	        }
			       	    },1000); 
	                }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
	                else{
	                	$('.pay-result-error').slideDown(300);
			           	 setInterval(function() {
			       	    	var time = $('.pay-result-error').attr('time') || 5;
			       	    	time--;
			       	    	$('.pay-result-error').attr('time',time);
			       	        $('.redirect span').text(time);
			       	        if(time <=0) {
			       	        	window.location.href="/wx/account-order?rmd="+new Date().getTime();
			       	        }
			       	    },1000); 
	                }
	            });
	    }
	    
	    
	
	    $('body').on('tap','.pay',function(evt) {
	        var payInfo = {};
	        var orderId = getQueryString('id');
	        payInfo.id = orderId;
	        
	        var buyerInfo = {};
	        buyerInfo.realName = $('.contact-real-name').val(); 
	        /**
	        if(!buyerInfo.realName || buyerInfo.realName.trim().length == 0){
	            mui.toast('收件人名称不能为空！');
	            $('.contact-real-name').addClass('input-error');
	            return;
	        }*/
	        
	        buyerInfo.phone = $('.contact-phone').val();
	        /**
	        if(!buyerInfo.phone || buyerInfo.phone.trim().length == 0){
	            mui.toast('收件人电话不能为空！');
	            $('.contact-phone').addClass('input-error');
	            return;
	        }*/
	        
	        buyerInfo.certNo = $('.contact-id').val();
	        /**
	        if(!buyerInfo.certNo || buyerInfo.certNo.trim().length == 0){
	            mui.toast('收件人身份证号码不能为空！');
	            $('.contact-id').addClass('input-error');
	            return;
	        }*/
	        
	        buyerInfo.addr = $('.contact-simple-addr').val();
	        /**
	        if(!buyerInfo.certNo || buyerInfo.certNo.trim().length == 0){
	            mui.toast('收件人地址不能为空！');
	            $('.contact-simple-addr').addClass('input-error');
	            return;
	        }*/
	        
	        buyerInfo.addrdetail = $('.contact-detail-addr').val();
	        /**
	        if(!buyerInfo.addrdetail || buyerInfo.addrdetail.trim().length == 0){
	            mui.toast('收件人详细地址不能为空！');
	            $('.contact-detail-addr').addClass('input-error');
	            return;
	        }*/
	        
	        payInfo.buyerInfo = JSON.stringify(buyerInfo);
	        payInfo.payType = $('input[name=paytype]:checked').val() || 0;

	        var btn = $('.pay');
	        btn.text('正在提交').attr('disabled',true);
	        $.ajax({
	            type : "post",
	            url  : "/user/pay/unifiedorder",
	            contentType : "application/json;charset=UTF-8",
	            data        : JSON.stringify(payInfo),
	            success     : function(data){
	            	btn.text('微信支付').attr('disabled',false);
	                if(data.code == '000000') {
	                	var pay = data.data;
	                	pay.package = pay.packages;
	                	delete pay['packages'];
	                    onBridgeReady(pay);
	                } else {
	                    mui.toast(data.msg);
	                }
	            },
	            error     : function() {
	            	btn.text('微信支付').attr('disabled',false);
	                mui.toast('提交订单出现异常');
	            }
	        });
	    });
	});
</script>
<%@ include  file="common/footer.jsp"%>