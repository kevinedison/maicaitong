<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<style>
    .sub-menu-u a{float: left;text-align: center;padding-top:8px; width:20%!important}
    .sub-menu-u div.mui-icon .mui-badge {top: 5px; left: auto!important; }
    .sub-menu-u span.mui-icon .mui-badge {top: 5px; left: auto!important;  }
    
    .sub-menu a{float: left;text-align: center;padding-top:8px; width:25%!important}
    .sub-menu div.mui-icon .mui-badge {top: 5px; left: auto!important; }
    .sub-menu span.mui-icon .mui-badge {top: 5px; left: auto!important;  }
</style>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-我的账号</h1>
    <a action="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<nav class="mui-bar mui-bar-tab bottom-nav">
    <a class="mui-tab-item top" action="/wx/top">
        <span class="mui-icon mui-icon-home"></span>
        <span class="mui-tab-label">首页</span>
    </a>
    <a class="mui-tab-item needs" action="/wx/needs">
        <span class="mui-icon mui-icon-bars"></span>
        <span class="mui-tab-label">求购</span>
    </a>
    <a class="mui-tab-item buyers" action="/wx/buyers">
        <span class="mui-icon mui-icon-contact"></span>
        <span class="mui-tab-label">菜农</span>
    </a>
    <a class="mui-tab-item messages" action="/wx/messages">
        <span class="mui-icon mui-icon-chatbubble"></span>
        <span class="mui-tab-label">消息</span>
    </a>
    <a class="mui-tab-item account mui-active" action="/wx/account">
        <span class="mui-icon mui-icon-gear"></span>
        <span class="mui-tab-label">我的</span>
    </a>
</nav>

<nav class="mui-bar mui-bar-tab pay-cash-nav" style="display:none">
    <span class="mui-icon mui-icon-close-filled mui-pull-right cash-cancel"></span>
    <div class="weixin-cash"><h3>微信提现</h3></div>
    <div class="weixin-logo"><img src="/resources/wx/images/weixin-pay.png" /></div>
    <div class="mui-input-row cash-number">
        <label>现金</label>
        <input type="number" placeholder="提现钱数" id="cash-amount" min=0>
    </div>
    <div class="weixin-cash-bar">
        <button class="mui-btn mui-btn-danger cash-ok">提现</button>
        <button class="mui-btn cash-cancel">取消</button>
    </div>
</nav>

<div class="mui-content">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <div class="mui-slider-cell">
                <div class="oa-contact-cell mui-table">
                    <div class="oa-contact-avatar mui-table-cell">
                        <img src="" />
                    </div>
                    <div class="oa-contact-content mui-table-cell">
                        <a class="mui-navigate-right" action="/wx/account-edit">
                            <div class="mui-clearfix">
                                <h4 class="oa-contact-name"></h4>
                                <span class="oa-contact-position mui-h6" style="padding-left:10px;"></span>
                            </div>
                            <p class="oa-contact-email mui-h6"></p>
                        </a>
                        <a class="mui-btn mui-btn-primary my-page-button">个人首页</a>
                    </div>
                </div>
            </div>
        </li>
    </ul>

    <ul class="mui-table-view buyer-subMenu" style="margin-top: 15px; display:none">
        <li class="mui-table-view-cell" style="background-color: #dd524d;color:#fff;font-size:16px;font-weight:bold;text-align:center">
            <a action="/wx/account-service"><span class="mui-icon mui-icon-upload" style="color:#dd524d"></span> 商品上架</a>
        </li>
        
        <li class="mui-table-view-cell">
            <nav class="sub-menu">
                <a class="mui-col-xs-6" style="width:50%!important">
                  <div class="total-price account-balance" style="text-align:left;line-height: 45px;">￥ -</div>
                </a>
                <a class="mui-col-xs-3" style="width:25%!important">
                    <button class="mui-btn mui-btn-danger balance-cash" style="padding: 12px 12px;">提现</button>
                </a>
                <a class="mui-col-xs-3" style="width:25%!important"  action="/wx/account-pay">
                    <div class="mui-icon mui-icon-bars"></div>
                    <div class="mui-tab-label">全部账单</div>
                </a>
            </nav>
        </li>
    </ul>
    <ul class="mui-table-view user-subMenu" style="margin-top: 15px;display:none">
        <li class="mui-table-view-cell" style="height:auto">
            <a class="mui-navigate-right" action="/wx/account-order"> 消费订单 </a>
            <nav class="sub-menu-u" style="margin-top:15px;">
                <a class="mui-col-xs-3" action="/wx/account-order?status=-1">
                    <div class="mui-icon mui-icon-bars"></div>
                    <div class="mui-tab-label">全部</div>
                </a>
                <a class="mui-col-xs-3" action="/wx/account-order?status=1">
                    <div class="mui-icon mui-icon-refreshempty user-pay"></div>
                    <div class="mui-tab-label">待付款</div>
                </a>
                <a class="mui-col-xs-3" action="/wx/account-order?status=2">
                    <div class="mui-icon mui-icon-refresh user-sendout"></div>
                    <div class="mui-tab-label">待发货</div>
                </a>
                <a class="mui-col-xs-3" action="/wx/account-order?status=3">
                    <div class="mui-icon mui-icon-reload user-receive"></div>
                    <div class="mui-tab-label">待收货</div>
                </a>
                <a class="mui-col-xs-3" action="/wx/account-order?status=99">
                    <div class="mui-icon mui-icon-chatboxes user-comment"></div>
                    <div class="mui-tab-label">待评论</div>
                </a>
            </nav>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right" action="/wx/account-favorite">
                心愿单
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right buyer-apply" action="/wx/account-buyer" ><span class="mui-icon mui-icon-location" style="color:#dd524d"></span> 菜农认证</a>
        </li>
        <p class="notice-msg">申请成为菜农,在平台上销售自己的产品</p>
    </ul>

    <ul class="mui-table-view buyer-subMenu" style="margin-top: 15px;display:none">
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right" action="/wx/account-service-order">服务订单</a>
            <nav class="sub-menu" style="margin-top:15px;">
                <a class="mui-col-xs-3" action="/wx/account-service-order?status=-1">
                    <div class="mui-icon mui-icon-bars"></div>
                    <div class="mui-tab-label">全部</div>
                </a>
                <a class="mui-col-xs-3" action="/wx/account-service-order?status=2">
                    <div class="mui-icon mui-icon-refresh buyer-sendout"></div>
                    <div class="mui-tab-label">待发货</div>
                </a>
                <a class="mui-col-xs-3" action="/wx/account-service-order?status=3">
                    <div class="mui-icon mui-icon-chatboxes buyer-receive"></div>
                    <div class="mui-tab-label">待收货</div>
                </a>
                <a class="mui-col-xs-3" action="/wx/account-service-order?status=4">
                    <div class="mui-icon mui-icon-loop buyer-settle"></div>
                    <div class="mui-tab-label">待结算</div>
                </a>
            </nav>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right" action="/wx/account-buyer">菜农信息变更</a>
        </li>
    </ul>

    <div class="buyer-agreement">
       <a action="/wx/agreement-user" class="text-center local-info"> 「查看用户协议」 </a>
      |<a action="/wx/agreement-buyer" class="text-center local-info"> 「查看菜农协议」 </a>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
    $(function () {
    	if(!checkSession()) {return;}; 	
    	var user = daidian.session.user;
    	$('.oa-contact-avatar img').attr('src',getPictureUrl(user.avatar,2));
    	$('.oa-contact-name').text(user.nickName);
    	var identify = user.identityAuth == 1 ? (daidian.config.identityType[user.identity] || user.identity) : '客官';
    	var location = ((dataconfig.countrys[user.country] || {}).name || user.hometown) || '';
    	$('.oa-contact-position').text('  ' + location +'  '+ identify);
        $('.my-page-button').attr('href','/wx/user?id='+user.userId);
        
        var identityAuth = user.identityAuth || 0;
        var countString = user.orderStatusCount || "{}";
		var countObj = JSON.parse(countString);
		
    	if(identityAuth == 1) {
    		$('.buyer-subMenu').fadeIn(100);
    		$('.account-balance').text('￥ ' + user.balance);
    		$('.balance-cash').attr('cash',user.balance);
    		$('#cash-amount').attr('max',user.balance);
    		if(countObj['waitPay'] && countObj['waitPay'] > 0) {
    			$('.buyer-pay').html('<span class="mui-badge">'+countObj['waitPay']+'</span>');
    		}
    		if(countObj['waitSend'] && countObj['waitSend'] > 0) {
    			$('.buyer-sendout').html('<span class="mui-badge">'+countObj['waitSend']+'</span>');
    		}
    		if(countObj['waitReceive'] && countObj['waitReceive'] > 0) {
    			$('.buyer-receive').html('<span class="mui-badge">'+countObj['waitReceive']+'</span>');
    		}
    		if(countObj['waitSettle'] && countObj['waitSettle'] > 0) {
    			$('.buyer-settle').html('<span class="mui-badge">'+countObj['waitSettle']+'</span>');
    		}
    	} else {
    		$('.user-subMenu').fadeIn(100);
    		if(identityAuth == 2) {
    			$('.buyer-apply').html('<span style="color:red">菜农认证中</span>');
    		} else if(identityAuth == 3) {
    			$('.buyer-apply').html('<span style="color:red">菜农认证失败</span>');
    		}
    		if(countObj['waitPay'] && countObj['waitPay'] > 0) {
    			$('.user-pay').html('<span class="mui-badge">'+countObj['waitPay']+'</span>');
    		}
    		if(countObj['waitSend'] && countObj['waitSend'] > 0) {
    			$('.user-sendout').html('<span class="mui-badge">'+countObj['waitSend']+'</span>');
    		}
    		if(countObj['commentCount'] && countObj['commentCount'] > 0) {
    			$('.user-comment').html('<span class="mui-badge">'+countObj['commentCount']+'</span>');
    		}
    		if(countObj['waitReceive'] && countObj['waitReceive'] > 0) {
    			$('.user-receive').html('<span class="mui-badge">'+countObj['waitReceive']+'</span>');
    		}
        }
    	
    	$('body').on('input','#cash-amount', function (evt) {
	        var target = $(evt.target);
	        var cash = target.val() || 0;
	        var max = target.attr('max');
	        var min = target.attr('min');
	        if(cash > max) {
	        	target.val(max);
	        }
	        if(cash < min) {
	        	target.val(min);
	        }
	    });
    	
    	$('body').on('tap','.sub-menu .balance-cash,.weixin-cash-bar button,.cash-cancel', function (evt) {
	        var target = $(evt.target);
	        if(target.hasClass('cash-ok')) {
	        	var bill = {};
	        	bill.payAmount = $('#cash-amount').val();
	        	
	        	if(bill.payAmount <= 0) {
	    	        mui.toast('提现金额不能为0');
	    	        $('#cash-amount').addClass('input-error');
	    	        return;
	    	    }
	        	
	        	var cash = $('.balance-cash').attr('cash') || 0; 	
	        	if(bill.payAmount > cash) {
	    	        mui.toast('提现金额不能超过总金额￥'+cash);
	    	        $('#cash-amount').addClass('input-error');
	    	        return;
	    	    }
	        	
	        	$.ajax({
	      	      type : "post",
	      	      url  : "/user/transfer",
	      	      contentType : "application/json;charset=UTF-8",
	      	      data        : JSON.stringify(bill),
	      	      success     : function(data){
	      	        if(data.code == '000000') {
	      	          mui.toast('提现成功');
	      	          window.location.href = '/wx/account?rmd='+new Date().getTime();
	      	        } else {
	      	          mui.toast('提现失败,失败原因:'+data.msg);
	      	        }
	      	      },
	      	      error     : function() {
	      	        mui.toast('提现出现异常,请重试');
	      	      }
	      	    });
	        	
        		$('.pay-cash-nav').fadeOut(3000,function() {
        			window.location.reload();
        		});	        		
        	} else if(target.hasClass('cash-cancel')) {
        		$('.pay-cash-nav').fadeOut(300);
        	} else if(target.hasClass('balance-cash')) {
        		var cash = $('.balance-cash').attr('cash') || 0;
        		$('#cash-amount').val(cash);
        		$('.pay-cash-nav').fadeIn(300);
        	}
	    });
    });
</script>
<%@ include  file="common/footer.jsp"%>