<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include  file="common/header.jsp"%>
<header class="mui-bar mui-bar-nav">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
    <h1 class="mui-title">卖菜通-</h1>
    <a href="/wx/top" class="mui-icon mui-icon-home mui-pull-right"></a>
</header>
<div class="mui-content">
    <div class="country-info" style="background-color:#fff">
        <div id="country-cover-slider" class="mui-slider">
            <div class="mui-slider-group"></div>
            <div class="mui-slider-indicator"></div>
        </div> 
        <h4 class="country-title" style="padding:10px 5px 5px 5px;"></h4>
        <p class="country-desc" style="padding:2px 5px;"></p>
    </div>

    <div id="slider" class="mui-slider">
        <div id="sliderSegmentedControl" class="mui-slider-indicator mui-segmented-control chat-tabs">
            <a class="mui-control-item mui-active" href="#buyer">菜农列表</a>
            <a class="mui-control-item" href="#service">商品列表</a>
        </div>
        <div class="mui-slider-group">
            <div id="buyer" class="mui-slider-item mui-control-content mui-active">
                <ul class="mui-table-view buyer-wrap"></ul>
                <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
            </div>
            <div id="service" class="mui-slider-item mui-control-content">
                <ul class="mui-table-view service-wrap service-row"></ul>
                <div class="paging-bar" style="display: none" page=0><a>加载更多</a></div>
            </div>
        </div>
    </div>
</div>
<%@ include  file="common/js.jsp"%>
<script src="/resources/wx/js/dataconfig.js"></script>
<script type="text/javascript">
	var loadAndRenderCountryBuyer = function(country) {
	    var page = $('#buyer .paging-bar').attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.buyer-wrap').append(loadHtml);
	    $.ajax({
	        type    :   'get',
	        url     :   '/buyer/list?pageSize='+daidian.config.pageSize+'&page='+page+'&country='+country,
	        success :   function(data){
	            $('.buyer-wrap .mui-loading').remove();
	
	            if (data && data.code == '000000'){
	                if(!data.data.count || data.data.count == 0) {
	                    $('.buyer-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有菜农</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $('#buyer .paging-bar').show().attr('page',page);
	                } else {
	                    $('#buyer .paging-bar').hide();
	                }
	
	                var buyers = data.data.data || [];
	                var buyerHtml =[];
	                buyers.forEach(function(b) {
	                    var serviceType = JSON.parse(b.servicetype || '{}');
	                    buyerHtml.push('<li class="mui-table-view-cell" userId="'+ b.userId +'">');
	                    
	                    
	                    if(daidian.session.user && daidian.session.user.userId == b.userId && daidian.session.user.identityAuth == 1){
	                    	buyerHtml.push('<a action="/wx/account-service">');
	                    }else{
	                    	buyerHtml.push('<a action="/wx/user?id='+ b.userId+'" class="mui-navigate-right">');
	                    }
	                    
	                    buyerHtml.push('    <div class="mui-slider-cell">');
	                    buyerHtml.push('        <div class="oa-contact-cell mui-table">');
	                    buyerHtml.push('            <div class="oa-contact-avatar mui-table-cell">');
	                    buyerHtml.push('                <img src="'+ getPictureUrl(b.avatar,2) +'" />');
	                    buyerHtml.push('            </div>');
	                    buyerHtml.push('            <div class="oa-contact-content mui-table-cell">');
	                    buyerHtml.push('                <div class="mui-clearfix">');
	                    buyerHtml.push('                    <h4 class="oa-contact-name">'+ (b.nickName) +'</h4>');
	                    buyerHtml.push('                    <span class="oa-contact-position mui-h6">（'+ ((dataconfig.countrys[b.country] || {}).name || '') +' '+(daidian.config.identityType[b.identity] || b.identity) +'）</span>');
	                    buyerHtml.push('                </div>');
	                    
	                    buyerHtml.push('	<div class="mui-table oa-contact-business" style="margin-top: 10px;">');
	                    buyerHtml.push('	    <h3>营</h3>');
            			buyerHtml.push('		<div class="business-list">');
            			
            			var type = JSON.parse(b.servicetype || '[]');
            			(type || []).forEach(function(cate) {
            				buyerHtml.push('      <span class="service-type">'+(daidian.config.serviceType[cate] || cate)+'</span>');
            			}); 
            			buyerHtml.push('		</div>');
            			buyerHtml.push('	</div>');
            			
	                    buyerHtml.push('            </div>');
	                    buyerHtml.push('        </div>');
	                    buyerHtml.push('    </div>');
            			
	        	   		if(b.introduce) {
	        	   			buyerHtml.push('        <p class="mui-ellipsis-2" style="text-align: left;padding-top: 5px;">'+ (b.introduce||'') +'</p>');
	        	   		}
	                    
	                    buyerHtml.push('</a>');
	                    buyerHtml.push('</li>');
	                });
	                $('.buyer-wrap').append(buyerHtml.join(''));
	            }else{
	                mui.toast('加载菜农出现异常');
	            }
	        },
	        error   :   function(){
	            mui.toast('加载菜农失败');
	        }
	    });
	};
	
	var loadAndRenderCountryService = function(country) {
	    var page = $('#service .paging-bar').attr('page');
	    if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	
	    var loadHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>';
	    $('.service-wrap').append(loadHtml);
	    $.ajax({
	        type    :   'get',
	        url     :   '/service/list?pageSize='+daidian.config.pageSize+'&page='+page+'&country='+country,
	        success :   function(data){
	            $('.service-wrap .mui-loading').remove();
	
	            if (data && data.code == '000000'){
	                if(!data.data.count || data.data.count == 0) {
	                    $('.service-wrap').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有商品</li>');
	                    return;
	                }
	                if((page * daidian.config.pageSize) < data.data.count) {
	                    $('#service .paging-bar').show().attr('page',page);
	                } else {
	                    $('#service .paging-bar').hide();
	                }
	
	                var services = data.data.data || [];
	                var serviceHtml =[];
	                services.forEach(function(s) {
	                    var serviceUser = s.userInfo || {};
	                    serviceHtml.push('<li class="mui-table-view-cell mui-media mui-col-xs-12">');
	
			            if(daidian.session.user && daidian.session.user.userId == serviceUser.userId && daidian.session.user.identityAuth == 1){
			            	serviceHtml.push('<a action="/wx/account-service">');
		                }else{
		                	serviceHtml.push('<a action="/wx/user?id='+serviceUser.userId+'">');
		                }
	                    
	                    serviceHtml.push('    <div class="service-buyer">');
	                    serviceHtml.push('      <span class="user-img"><img src="'+ getPictureUrl(serviceUser.avatar,2) +'"></span>');
	                    serviceHtml.push('      <span class="user-info">');
	                    serviceHtml.push('          <div class="user-name">'+(serviceUser.nickName)+'</div>');
	                    serviceHtml.push('          <div class="user-location">'+((dataconfig.countrys[serviceUser.country] || {}).name || '')+'</div>');
	                    serviceHtml.push('      </span>');
	                    serviceHtml.push('      <button class="mui-btn mui-icon mui-icon-chat" userId='+serviceUser.userId+'></button>');  
	                    serviceHtml.push('    </div>');
	                    serviceHtml.push('</a>');
	
	                    serviceHtml.push('<a action="/wx/service?id='+ s.id+'">');
	                    serviceHtml.push('    <img class="mui-media-object service-cover mui-pull-left" src="'+ getPictureUrl(s.cover,'1','list') +'">');
	                    serviceHtml.push('    <div class="mui-media-body">');
	                    serviceHtml.push('      <div class="service-title mui-ellipsis">'+ s.title+'</div>');
	                    serviceHtml.push('      <p class="service-desc mui-ellipsis-2">'+ s.description+'</p>');
	                    serviceHtml.push('      <div class="service-bar">');
	                    serviceHtml.push('        <button class="mui-btn-danger mui-btn-outlined service-price">¥ '+ s.price+'</button>');
	                    serviceHtml.push('        <div class="service-number">');
	                    serviceHtml.push('          <button class="mui-btn mui-btn-outlined mui-icon mui-icon-flag" type="service" serviceId="'+service.id+'"> '+(s.ordercount || 0)+' </button>');
	                    serviceHtml.push('        </div>');
	                    serviceHtml.push('      </div>');
	                    serviceHtml.push('    </div>');
	                    serviceHtml.push('</a>');
	
	                    serviceHtml.push('</li>').push;
	                });
	                $('.service-wrap').append(serviceHtml.join(''));
	            }else{
	                mui.toast('加载服务出现异常');
	            }
	        },
	        error   :   function(){
	            mui.toast('加载服务失败');
	        }
	    });
	};
	
	var initCountry = function(country) {
		var country = dataconfig.countrys[country] || {};
	    $('.mui-title').text('卖菜通-'+ country.name + ' ' + country.code);		
 		var countryImage = [],countryIndicator = [],activeFlag = true;
 		
		(country.picture||[]).forEach(function(pic){
			countryImage.push('<div class="mui-slider-item"><img src="'+pic+'"></div>');
			countryIndicator.push('<div class="mui-indicator '+(activeFlag ? 'mui-active' : '')+'"></div>');
			 activeFlag = false;
        });
		$('#country-cover-slider .mui-slider-group').append(countryImage.join(''));
		$('#country-cover-slider .mui-slider-indicator').append(countryIndicator.join(''));
        
		mui("#country-cover-slider").slider({ interval: 3000 }); 
		$('.country-title').append(country.name + ' ' + country.code);
		$('.country-desc').append(country.desc); 
	};
	
	$(function () {
	    var country = getQueryString('country') || 'dubai';
	    initCountry(country);
	
	    $('#service .paging-bar').click(function(evt) {
	        evt.preventDefault();
	        loadAndRenderCountryService(country);
	    });
	    $('#service .paging-bar').trigger('click');
	
	    $('#buyer .paging-bar').click(function(evt) {
	        evt.preventDefault();
	        loadAndRenderCountryBuyer(country);
	    });
	    $('#buyer .paging-bar').trigger('click');
	});
</script>
<%@ include  file="common/footer.jsp"%>