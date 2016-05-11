<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">带点啥-账单列表</h1>
    <a href="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<div class="mui-content">
    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed  list-wrap pay-wrap">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">账单列表</div>
        </li>
    </ul>
    <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
</div>
<%@ include  file="common/js.jsp"%>
<script type="text/javascript">
	var loadAndRenderPay = function(userId,serviceId){
	    var page = $(".paging-bar").attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var url = '/user/bill?pageSize='+daidian.config.pageSize+'&page='+page;
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.pay-wrap').append(loadHtml);
	    $.ajax({
	        type    : 'get',
	        url     : url,
	        success : function(data){
	        	$('.mui-loading').remove();
	            if(data && data.code == '000000') {
	                if(!data.data ||!data.data.count || data.data.count == 0) {
	                    $('.pay-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有账单</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $(".paging-bar").show().attr('page',page);
	                } else {
	                    $(".paging-bar").hide();
	                }
	                var html = [];
                    var tempDate;
	                (data.data.data || []).forEach(function(curData) {
	                	var date = new Date(curData.operateTime).format('MM-dd');
	                	if(!tempDate || tempDate != date) {
	                		html.push('<div class="mui-text-left notice-msg">'+date+'</div>');
	                		tempDate = date;
	                	}
	                	
	                	html.push('<li class="mui-table-view-cell pay-info">');
	                	html.push('    <div class="mui-table">');
	                	html.push('      <div class="mui-table-cell mui-col-xs-2 mui-text-left">');
	                	html.push('         <div><img src="/resources/wx/images/money.png"/></div>');
	                	html.push('      </div>');
	                	html.push('      <div class="mui-table-cell mui-col-xs-7">');
	                	if('transfer' == curData.payInfo){
	                		html.push('          <h4 class="pay-money out">-'+curData.payAmount+'</h4>');
	                	}else{
	                		html.push('          <h4 class="pay-money in">+'+curData.payAmount+'</h4>');
	                	}
	                	html.push('          <h5 class="pay-action">'+daidian.config.billtype[curData.payInfo] || '账单'+'</h5>');
	                	html.push('      </div>');
	                	html.push('      <div class="mui-table-cell mui-col-xs-3 mui-text-right">');
	                	html.push('           <div class="pay-time">'+date+'</div>');
	                	if('orderpay' == curData.payInfo){
	                	   html.push('	  <a class="mui-h6 pay-order" action="/wx/order-pay?id='+curData.orderId+'&type=1">查看订单</a>');
	                	}
	                	html.push('      </div>');
	                	html.push('   </div> ');
	                	html.push('</li>');
	                });
	
	                $('.pay-wrap').append(html.join(''))
	            } else {
	                mui.toast('加载账单失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载账单失败，请刷新页面重试');
	        }
	    });
	};
	
	$(function() {
	    $('.paging-bar').click(function(evt) {
	        evt.preventDefault();
	        loadAndRenderPay();
	    });
	    $('.paging-bar').trigger('click');
	});
</script>
<%@ include  file="common/footer.jsp"%>