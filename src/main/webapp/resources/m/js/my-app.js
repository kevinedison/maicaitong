var myApp = new Framework7({modalTitle: '带点啥',material: true,pushState: true,template7Pages: true});

var $$ = Dom7;
var mainView = myApp.addView('.view-main', {});

$$(document).on('ajaxStart', function (e) {
    myApp.showIndicator();
});

$$(document).on('ajaxComplete', function () {
    myApp.hideIndicator();
});

$$('body').on('click','.add-backet',function () {
    myApp.pickerModal('.picker-modal-backet');
});

$$('body').on('infinite','.user-recommendation',function() {
	console.log('infinite');
});

var daidian = {};
daidian.pageSize = 10;
daidian.succCode = "000000";
daidian.imageAccessUrl = 'http://image.cellglo.com.cn/';
daidian.getPictureUrl = function(picture, type, use) {
	picture = picture || '';
    //0 城市 1 服务 2 用户 3 消息
    if(type == 0) {picture = picture + "!t480x240.png";}
    if(type == 1) {if(use == 'list') {picture = picture + "!t160x160.png";} else {picture = picture + "!t480x480.png";}}
    if(type == 2) {if(picture.indexOf('http://') < 0) {picture = picture + "!t100x100.png" } else {return picture};}
    if(type == 3) {if(use == 'list') {picture = picture + "!t100x100.png";}}
    if(type == 4) {}
    if(type == 5) {if(use == 'list') {picture = picture + "!t120x120.png";} else {picture = picture + "!t480x480.png";}}
    return daidian.imageAccessUrl + picture;
};

myApp.onPageInit('tao', function (page) {
    $$('.tao-hot-list').on('infinite', function () {
    	var _self = $$(this);
    	var page = _self.attr('page');
    	if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	    
	    var loading = _self.attr('loading') || 'loaded';
	    if(loading == 'loading') { return; }
	    _self.attr('loading','loading');
	    
    	var url =  '/service/list?pageSize='+daidian.pageSize+'&page='+page+"&rmd="+new Date().getTime();
    	$$.ajax({
	        type    : 'get',
	        url     :  url,
	        dataType: 'json',
	        complete: function() {_self.attr('loading','loaded');},
	        success : function(data){
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                	myApp.detachInfiniteScroll($$('.tao-hot-list'));
	                	$$('.tao-hot-list').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有服务</li>');
	                    return;
	                }
	                
	                if((page * daidian.pageSize) < data.data.count) {
	                	_self.attr('page',page);
	                } else {
	                	myApp.detachInfiniteScroll($$('.tao-hot-list'));
	                }
	                
	                var services = data.data.data || [];
	                var html =[];
	                $$.each(services,function(i,s) {
	                    var serviceUser = s.userInfo || {};
	                    html.push('<div class="card goods">');
	                    html.push('  <div class="card-content">');
	                    html.push('    <img src="'+daidian.getPictureUrl(s.cover,'1','list')+'" width="100%">');
	                    html.push('    <div class="card-content-inner">');
	                    html.push('      <p class="name"><a href="detail.html">'+(s.title || '')+'</a></p>');
	                    html.push('      <p class="description">'+(s.description || '')+'</p>');
	                    html.push('      <div class="item-content buyer-bar">');
	                    html.push('           <div class="item-media avatar"><img src="'+daidian.getPictureUrl(serviceUser.avatar,2)+'"></div>');
	                    html.push('           <div class="item-inner buyer-info">');
	                    html.push('             <div class="item-title"><span class="name">'+(serviceUser.nickName||'--')+'</span><span class="location">'+(serviceUser.country || '')+'</span><span class="identify">'+(serviceUser.identity || '')+'</span></div>');
	                    html.push('           </div>');
	                    html.push('      </div>');
	                    html.push('      <div class="action-bar">');
	                    
	                    html.push('         <span class="price">￥'+ s.price);
	                    if(s.priceType && s.priceType == 1) {
	                    	html.push('        <i class="price-detail">(￥'+(s.originPrice || 0)+'+￥'+(s.postPrice || 0)+'+￥'+(s.servicePrice || 0)+')</i>');
	                    }
	                    html.push('         </span>');
	                    html.push('         <span class="add-backet"><i class="icon icon-f7"></i></span>');
	                    html.push('      </div>');
	                    html.push('    </div>');
	                    html.push('  </div>');
	                    html.push('</div>');
	                });
	                $$('.tao-hot-list').append(html.join(''));
	            }
	        }
	    });
    }); 
    $$('.tao-hot-list').trigger('infinite');
});



myApp.onPageInit('search', function (page) {   
	$$('.search-history-content').on('infinite', function () {
		var _self = $$(this);
		var page = _self.attr('page');
		if(!page || page < 1) {
	        page = 0;
	    }
	    page++;
	    
	    var loading = _self.attr('loading') || 'loaded';
	    if(loading == 'loading') { return; }
	    _self.attr('loading','loading');
	    
		var url =  '/service/list?pageSize='+daidian.pageSize+'&page='+page+"&rmd="+new Date().getTime();
		$$.ajax({
	        type    : 'get',
	        url     :  url,
	        dataType: 'json',
	        complete: function() {_self.attr('loading','loaded');},
	        success : function(data){
	            if(data && data.code == '000000') {
	                if(!data.data.count || data.data.count == 0) {
	                	myApp.detachInfiniteScroll($$('.search-history-content'));
	                	$$('.search-history ul').append('<li class="mui-table-view-cell mui-table-panel mui-col-xs-12 mui-text-center">没有服务</li>');
	                    return;
	                }
	                
	                if((page * daidian.pageSize) < data.data.count) {
	                	_self.attr('page',page);
	                } else {
	                	myApp.detachInfiniteScroll($$('.search-history-content'));
	                }
	                
	                var services = data.data.data || [];
	                var html =[];
	                $$.each(services,function(i,s) {
	                    var serviceUser = s.userInfo || {};
	                    html.push('<li>');
	                    html.push('    <div class="item-content">');
	                    html.push('      <a href="/resources/m/model/search-result.html" class="item-inner">');
	                    html.push('        <div class="item-title">John Doe</div>');
	                    html.push('        <div class="item-after">2010-11-01</div>');
	                    html.push('      </a>');
	                    html.push('    </div>');
	                    html.push('</li>');
	                });
	                $$('.search-history ul').append(html.join(''));
	            }
	        }
	    });
	}); 
	$$('.search-history-content').trigger('infinite'); 
	
	$$('.search-history-clear').click(function() {
		console.log('clear..');
	});
	
	$$('.search-button').click(function() {
		var keyword = $$('input[type=search]').val();
		if(keyword && keyword.length > 0) {
			mainView.router.loadPage('/resources/m/model/search-result.html');
			return;
		}
	});
});

myApp.onPageInit('search-result', function (page) {  
   var html = [];
   html.push('<li class="item-content buyer-info">');
   html.push('  <div class="item-media"><img src="http://lorempixel.com/88/88/fashion/4" width="50"></div>');
   html.push('  <div class="item-inner">');
   html.push('     <div class="item-title-row"><div class="item-title">何学苗</div></div>');
   html.push('     <div class="item-subtitle"><span class="location">迪拜</span><span class="identity">专职买手</span></div>');
   html.push('</div></li>');
   $$('.search-buyer-result').append(html.join(''));
    
   var good = [];
   good.push('<li class="item-content">');
   good.push('<div class="item-media"><img src="http://lorempixel.com/88/88/fashion/5" width="50"></div>');
   good.push('<div class="item-inner">');
   good.push('<div class="item-title-row"><div class="item-title">Dont Stop Me Now</div></div>');
   good.push('<div class="item-subtitle"><span class="price">￥0000</span><span class="add-backet"><i class="icon icon-f7"></i></span></div>');
   good.push('</div></li>');
   $$('.search-goods-result').append(good.join(''));
});