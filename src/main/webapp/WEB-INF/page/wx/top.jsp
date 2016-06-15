<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a  action="/wx/top" class="mui-icon mui-icon-home mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-首页</h1>
</header>
<nav class="mui-bar mui-bar-tab bottom-nav">
    <a class="mui-tab-item top mui-active" action="/wx/top">
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
    <a class="mui-tab-item account" action="/wx/account">
        <span class="mui-icon mui-icon-gear"></span>
        <span class="mui-tab-label">我的</span>
    </a>
</nav>
<div class="mui-content">
    <div id="top" class="mui-control-content mui-active">
		<ul class="mui-table-view mui-grid-view mui-grid-9 top-nav-bar">
            <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                <a action="/wx/search?type=fruit">
                    <span class="categroy-icon categroy-icon-milk"></span>
                    <div class="mui-media-body">水果</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                <a action="/wx/search?type=vegetables">
                    <span class="categroy-icon categroy-icon-cosmetics"></span>
                    <div class="mui-media-body">蔬菜</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                <a action="/wx/search?type=animal">
                    <span class="categroy-icon categroy-icon-shoes"></span>
                    <div class="mui-media-body">粮油副食</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                <a action="/wx/search?type=aquatic">
                    <span class="categroy-icon categroy-icon-bag"></span>
                    <div class="mui-media-body">肉禽蛋奶</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                <a action="/wx/search?type=watch">
                    <span class="categroy-icon categroy-icon-watch"></span>
                    <div class="mui-media-body">休闲食品</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                <a action="/wx/search?type=food">
                    <span class="categroy-icon categroy-icon-food"></span>
                    <div class="mui-media-body">水产海鲜</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                <a action="/wx/search?type=other">
                    <span class="categroy-icon categroy-icon-other"></span>
                    <div class="mui-media-body">其他</div>
                </a>
            </li>
             <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                <a action="/wx/account-buyer">
                    <span class="categroy-icon categroy-icon-buyer"></span>
                    <div class="mui-media-body">成为菜农</div>
                </a>
            </li>
        </ul>
        
        <div class="dividing"></div>
        <ul class="mui-table-view hot-buyer">
            <li class="mui-table-view-cell mui-table-panel mui-col-xs-12">
                <div class="panel-title">明星菜农</div>
                <div class="panel-more"><a action="/wx/buyers">所有菜农 <span class="mui-icon mui-icon-arrowright" style="font-size: 13px;"></span>  </a></div>
            </li>
        </ul>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
    var renderTop = function() {	   		   	
	   	$.ajax({
	        url     : '/buyer/list?recommendation=1&page=1&pageSize=999&rmd='+new Date().getTime(),
	        success : function(data){
	            if(data && data.code == '000000') {
	            	if(!data.data || !data.data.count || data.data.count == 0) {
	                    return;
	                }
	
	                var buyers = data.data.data || []; 
	                var buyerHtml = [];
	                buyers.forEach(function(b){
	        	   		buyerHtml.push('<li class="mui-table-view-cell">');
	        	   		if(daidian.session.user && daidian.session.user.userId == b.userId && daidian.session.user.identityAuth == 1){
                        	buyerHtml.push('<a action="/wx/account-service">');
	                    }else{
	                    	buyerHtml.push('<a action=/wx/user?id='+b.userId+' class="mui-navigate-right">');
	                    }
	        	   		buyerHtml.push('        <div class="mui-slider-cell">');
	        	   		buyerHtml.push('            <div class="oa-contact-cell mui-table">');
	        	   		buyerHtml.push('                <div class="oa-contact-avatar mui-table-cell">');
	        	   		buyerHtml.push('                    <img src="' + getPictureUrl(b.avatar,2) + '" />');
	        	   		buyerHtml.push('                </div>');
	        	   		buyerHtml.push('                <div class="oa-contact-content mui-table-cell">');
	        	   		buyerHtml.push('                    <div class="mui-clearfix">');
	        	   		buyerHtml.push('                        <h4 class="oa-contact-name">'+ (b.nickName)+'</h4>');
	        	   		buyerHtml.push('                        <span class="oa-contact-position mui-h6">（'+ ((dataconfig.countrys[b.country] || {}).name || '') +' '+(daidian.config.identityType[b.identity] || b.identity) +'）</span>');
	        	   		buyerHtml.push('                    </div>');
	        	   		
	        	   		buyerHtml.push('	<div class="mui-table oa-contact-business">');
	        			buyerHtml.push('	 <h3>营</h3>');
	        			buyerHtml.push('	 <div class="business-list">');
	        			var type = JSON.parse(b.servicetype || '[]');
            			(type || []).forEach(function(cate) {
            				buyerHtml.push('      <span class="service-type">'+(daidian.config.serviceType[cate] || cate)+'</span>');
            			}); 
	        			buyerHtml.push('	 </div>');
	        			
	        	   		buyerHtml.push('                </div>');
	        	   		buyerHtml.push('            </div>');
	        	   		buyerHtml.push('        </div>'); 
	        	   		
	        	   		if(b.introduce) {
	        	   			buyerHtml.push('        <p class="mui-ellipsis-2" style="text-align: left;padding-top: 5px;">'+ (b.introduce||'') +'</p>');
	        	   		}
	        	   		
	        			buyerHtml.push('	</div>');
	        	   		buyerHtml.push('    </a>');
	        	   		buyerHtml.push('</li>');
	                });
	        	   	$('.hot-buyer').append(buyerHtml.join(''));
	            } else {
	                mui.toast('加载明星菜农失败，请刷新页面重试');
	            }
	        },
	        error : function() {
	            mui.toast('加载明星菜农失败，请刷新页面重试');
	        }
	    });	   	
    };
    $(function () {
    	renderTop();
    });
</script>
<%@ include  file="common/footer.jsp"%>