<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<style>
    .rates-wrap h4{font-size:15px;}
</style>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-人民币准实时汇率表</h1>
    <a href="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<div class="mui-content">
    <ul class="mui-table-view mui-table-view-striped mui-table-view-condensed list-wrap rates-wrap">
       <li class="mui-table-view-cell">
            <div class="mui-table">
                <div class="mui-table-cell mui-col-xs-4">
                    <h4>币种</h4>
                </div>
                <div class="mui-table-cell mui-col-xs-5">
                    <h4>汇率</h4>
                </div>
                <div class="mui-table-cell mui-col-xs-3 mui-text-right">
                    <h4>更新时间</h4>
                </div>
            </div>
        </li>
    </ul>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	$(function() { 
		var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
		$('.rates-wrap').append(loadHtml);
		$.ajax({
	        url     : '/rate/list?page=1&pageSize=999&rmd='+new Date().getTime(),
	        success : function(data){
	        	$('.mui-loading').remove();
	            if(data && data.code == '000000') {
	                var rates = data.data || []; 
	                var html = [];
	                rates.forEach(function(rate) {
		                html.push('<li class="mui-table-view-cell">');
		                html.push('    <div class="mui-table">');
		                html.push('        <div class="mui-table-cell mui-col-xs-4">');
		                html.push('            <h5 class="mui-ellipsis">'+ rate.fromCurrencyName + ' ' + rate.fromCurrency +'</h5>');
		                html.push('       </div>');
		                html.push('       <div class="mui-table-cell mui-col-xs-6">');
		                html.push('            <span class="mui-h5 mui-ellipsis">'+rate.currency+'</span>');
		                html.push('       </div>');
		                html.push('       <div class="mui-table-cell mui-col-xs-2 mui-text-right">');
		                html.push('          <span class="mui-h5">'+rate.time+'</span>');
		                html.push('      </div>');
		                html.push('   </div>');
		                html.push(' </li>');
	                });
	                $('.rates-wrap').append(html.join(' '));
	            } else {
	                mui.toast('加载汇率信息失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载汇率信息失败，请刷新页面重试');
	        }
	    });
	});
</script>
<%@ include  file="common/footer.jsp"%>