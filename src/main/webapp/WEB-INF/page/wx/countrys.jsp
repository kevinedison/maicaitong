<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">带点啥-全部国家</h1>
    <a href="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<div class="mui-content">
    <ul class="mui-table-view mui-grid-view list-wrap">
        <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
            <div class="panel-title">全部国家</div>
        </li>
    </ul>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	$(function() { 
        $.ajax({
	        url     : '/location/list?page=1&pageSize=999&rmd='+new Date().getTime(),
	        success : function(data){
	            if(data && data.code == '000000') {
	            	if(!data.data || !data.data.count || data.data.count == 0) {
	                    return;
	                }
	
	                var countrys = data.data.data || []; 
	                var html = [];
	                countrys.forEach(function(country) {
	                    html.push('<li class="mui-table-view-cell mui-media mui-col-xs-6">');
	                    html.push(' <a action="/wx/country?country='+country.id+'">');
	                    html.push('  <div class="country-info">');
	                    html.push('   <div class="country-name">'+country.name +'<span>' + country.code + '</span></div>');
	                    html.push('   <div class="country-number"><button type="button" class="mui-btn mui-btn-danger mui-btn-outlined" country="'+country.code+'">'+country.buyerCount+'买手,'+country.serviceCount+'商品'+'</button></div>');
	                    html.push('  </div>');
	                    html.push(' </a>');
	                    html.push('</li>');
	                	$('.country-number button[country="'+country.code+'"]').text();
	                });
	                $('.list-wrap').append(html.join(' '));
	            } else {
	                mui.toast('加载国家信息失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载国家信息失败，请刷新页面重试');
	        }
	    });
	});
</script>
<%@ include  file="common/footer.jsp"%>