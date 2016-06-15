<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.portal.common.context.BaseContext"%>
<%@ page import="com.portal.manager.SessionManager"%>
<%@ page import="com.portal.manager.CategoryBrandManager" %>
<%@ page import="com.portal.bean.dto.UserInfo"%>
<%@ page import="com.portal.common.mapper.JsonMapper"%>
<%@ page import="com.portal.bean.console.Category"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.portal.bean.console.Brand" %>

<script src="/resources/wx/js/mui.min.js"></script>
<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<% 
SessionManager sessionManager = BaseContext.getBean("sessionManager",SessionManager.class);
Boolean live = false;
String userInfo = "{}";
Integer messageCount = 0;
Map<String, Category> categoryMap = new HashMap<>();
Map<String,Brand> brandMap = new HashMap<>();
String categorys = "{}";
String brands = "{}";
JsonMapper mapper = new JsonMapper();

if(sessionManager != null) {
	if(sessionManager.checkUserSession(request)) {
		live = true;
		UserInfo user = sessionManager.getUserSession(request);
		userInfo = mapper.toJson(user);		
	}
	messageCount = sessionManager.getMessageCount(request);
}

String signature = (String)request.getAttribute("signature");

CategoryBrandManager categoryBrandManager= BaseContext.getBean("categoryBrandManager",CategoryBrandManager.class);
if(categoryBrandManager != null) {
	categoryMap = categoryBrandManager.getCategory();
	brandMap = categoryBrandManager.getBrand();
	categorys = mapper.toJson(categoryMap);
	brands = mapper.toJson(brandMap);
}
%>
<script type = "text/javascript">
var daidian = {};
daidian.config = {
    'pageSize': 10,
    'loginUrl':'http://weixin.acarry.com/user/login',
    //'loginUrl':'http://localhost:8080/user/login',
    'imageAccessUrl': 'http://image.acarry.com/',
    'constellationConfig': {"aries":"白羊座","taurus":"金牛座","gemini":"双子座","cancer":"巨蟹座","leo":"狮子座","virgo":"处女座","libra":"天秤座","scorpio":"天蝎座","sagittarius":"射手座","capricornus":"摩羯座","aquarius":"水瓶座","pisces":"双鱼座"},
    'ageRange':{"00":"00后","90":"90后","80":"80后","70":"70后","60":"60后"},
    'identityType':{"overseastudent":"留学生","overseachinese":"华侨","guide":"专职买手","timebuyer":"兼职买手","family":"家属","clerk":"店员"},
    'serviceType' : {"milk":"奶粉","bag":"鞋包表","cosmetics":"化妆品","special":"本地特产"},
    'languageConfig':{"putong":"普通话","yueyu":"粤语","english":"英语","spanish":"西班牙语","french":"法语","japanese":"日语","korean":"韩语","arabic":"阿拉伯语","italian":"意大利语","russian":"俄语"},
    'gender':{"0":"女","1":"男"},
    'defaultPic' : {
        'country'  : '/images/default/country.jpg',
        'service'  : '/images/default/service.jpg',
        'user'     : '/images/default/user.jpg'
    },
    'orderStatus':{'0':'求购订单','1':'等待支付','2':'等待发货','3':'等待收货','4':'等待结算','6':'已结算','9':'订单取消'},
    'needStatus':{'1':'求购中','2':'成交','3':'失败'},
    'billtype':{'orderpay':'订单结算','transfer':'提现'}
};
daidian.signature = <%=signature%>;
daidian.session = {'user': <%=userInfo%>,'live': <%=live%>,'messageCount':<%=messageCount%>};
daidian.cache = {};
daidian.categorys = <%=categorys%>;
daidian.brands = <%=brands%>;
daidian.html = {};

Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        //quarter
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
            //millisecond
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};   

var getQueryString = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

var getPictureUrl = function(picture, type, use) {
	picture = picture || '';
    //0 城市 1 服务 2 用户 3 消息
    if(type == 0) {picture = picture + "!t480x240.png";}
    if(type == 1) {if(use == 'list') {picture = picture + "!t160x160.png";} else {picture = picture + "!t480x480.png";}}
    if(type == 2) {if(picture.indexOf('http://') < 0) {picture = picture + "!t100x100.png" } else {return picture};}
    if(type == 3) {if(use == 'list') {picture = picture + "!t100x100.png";}}
    if(type == 4) {}
    if(type == 5) {if(use == 'list') {picture = picture + "!t120x120.png";} else {picture = picture + "!t480x480.png";}}
    return daidian.config.imageAccessUrl + picture;
};

var getRate = function(rate) {
	rate = rate || 0;
	if(rate >= 5) {rate = 5;} 
	if(rate <= 0) {rate = 0;}
	var rateHtml = [];
	for(var r = 0; r < rate; r++) {
		rateHtml.push('<span class="mui-icon mui-icon-star"></span>');
	}
	return rateHtml.join('');
};

var checkSession = function() {
	if(!daidian.session.live) {
		mui.toast('没有鉴权，需要微信授权');
		var r = window.location.href
		if(r.indexOf('?') > 0) {
			r = r + "&rmd=" + new Date().getTime(); 
		} else {
			r = r + "?rmd=" + new Date().getTime(); 
		}
		window.location.href = daidian.config.loginUrl + "?r="+encodeURIComponent(r);
		return false;
	}
	return true;
};

var getShareContent = function(type) {
	var href = window.location.href;
	var content = {};
	content.title='卖菜通-最放心的果蔬交易平台';
	content.link=href;
	content.imgUrl='http://image.acarry.com/images/daidiansha-logo.png';
	
	if(type == 'app') {
		content.desc = '找可靠菜农购买放心果蔬';
		content.type='link';
		content.dataUrl='';
	} else {}
	
	content.success = function () { mui.toast('分享成功');};
	content.cancel= function () { mui.toast('分享取消');}
	
	if(daidian.session.live) {
		var user = daidian.session.user;
		if(href.indexOf('?') > 0) {
			href = href + "&shareId=" + user.userId ; 
		} else {
			href = href + "?shareId=" + user.userId; 
		}
		content.link = href;
	}
	
	if(href.indexOf('/wx/user') > 0) {
		content.title = "卖菜通-" + ($('.user-profile .user-name').text() || '');
		content.desc = '卖菜通平台实名认证的明星菜农';
		content.imgUrl = $('.user-profile .user-img img').attr('src'); // 分享图标	
	} else if(href.indexOf('/wx/service') > 0) {
		content.title = $('.service-info .service-title').text() || '卖菜通-最放心的果蔬交易平台';
		content.desc = $('.service-info .service-desc').text() || '找可靠菜农购买放心果蔬'
		content.imgUrl = $('.service-info .mui-media-object').attr('src'); // 分享图标
	}	
	
	return content;
}

$(function() {
	  if(daidian.signature && daidian.signature.signature && daidian.signature.nonceStr && daidian.signature.timestamp) {
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: 'wxe8626eeb45600bbc', // 必填，公众号的唯一标识
            timestamp: daidian.signature.timestamp, // 必填，生成签名的时间戳
            nonceStr:  daidian.signature.nonceStr, // 必填，生成签名的随机串
            signature: daidian.signature.signature,// 必填，签名，见附录1
            jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','scanQRCode','chooseImage','previewImage','uploadImage','getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        
        var href = window.location.href;
        if(href.indexOf('/wx/user') > 0 || href.indexOf('/wx/service') > 0) {
					
		} else {
			wx.ready(function(){
	        	 wx.onMenuShareTimeline(getShareContent("timeLine"));
	        	 wx.onMenuShareAppMessage(getShareContent("app"));
	        });
		}
	  }  

	  $(document).ajaxComplete(function(event, xhr, settings) {
	    if (settings.url) {
	      var responseText = xhr.responseText;
	      var responseJson, code;
	      if (responseText && responseText.trim().length > 0) {
	        try {
	          responseJson = JSON.parse(responseText);
	        } catch (e) {
	          return;
	        }
	
	        code = responseJson.code ;
	        if(code == '000009') {
	        	mui.toast('没有鉴权，需要微信授权');
	        	
	    		var r = window.location.href
	    		if(r.indexOf('?') > 0) {
	    			r = r + "&rmd=" + new Date().getTime(); 
	    		} else {
	    			r = r + "?rmd=" + new Date().getTime(); 
	    		} 
	    		window.location.href = daidian.config.loginUrl + "?r="+encodeURIComponent(r);
	    		return;
	        }
	      }
	    }
	  });

	  $(document).ajaxSend(function(evt, request, settings){
		  var url = settings.url;
		  if(url.indexOf('?') > 0) {
			  settings.url = url + "&random="+new Date().getTime();
	      } else {
	      	  settings.url = url + "?random="+new Date().getTime();
	      }
	  });
	  
	  $('body').on('tap','a',function(evt){
		evt.preventDefault();
        var target = $(evt.target);
        if(target.is('button')) {
        	if(target.hasClass('mui-icon-chat') && target.attr('userId')) {
        		window.location.href="/wx/message?to="+target.attr('userId')+"&rmd="+new Date().getTime();
            	return;
        	} 
        	
        	var type = target.attr('type');
        	if(type && type == 'service') {
        		var serviceId = target.attr('serviceId');
        		if(serviceId) {
        			if(target.hasClass('mui-icon-flag')) {
        				window.location.href="/wx/order?target="+serviceId+"&rmd="+new Date().getTime();
            			return;
            		} else if(target.hasClass('mui-icon-chatboxes')) {
            			window.location.href="/wx/comment?target="+serviceId+"&rmd="+new Date().getTime();
            			return;
            		}	
        		}	
        	}
        	
            if(type && type == 'buyer') {
            	var userId = target.attr('userId');
            	if(userId) {
            		if(target.hasClass('mui-icon-flag')) {
            			window.location.href="/wx/order?user="+userId+"&rmd="+new Date().getTime();
            			return;
            		} else if(target.hasClass('mui-icon-chatboxes')) {
            			window.location.href="/wx/comment?user="+userId+"&rmd="+new Date().getTime();
            			return;
            		}
            	}   
        	}
        }
        
        if(!target.is('a')) {
        	target = target.closest('a');
        }
        var href = target.attr('action');
        if(href) {
        	if(href.indexOf('?') > 0) {
        		href = href + "&rmd="+new Date().getTime();
        	} else {
        		href = href + "?rmd="+new Date().getTime();
        	}
        	window.location.href=href;
        }
      });
	  
     $('body').on('tap','.bottom-nav',function(evt){
	        var target = $(evt.target);
	        if(!target.hasClass('mui-tab-item')) {
	            target = target.closest('.mui-tab-item');
	        }
	        var url = target.attr('href');
	        if(url.indexOf('?') > 0) {
	        	url = url + "&rmd=" + new Date().getTime(); 
			} else {
				url = url + "?rmd=" + new Date().getTime(); 
			}
	        window.location.href = url;
	 });
     
     $('body').on('tap', '.image-upload[number=single]', function(evt) {
     	var target = $(evt.target);
     	wx.chooseImage({
     	    count: 1, // 默认9
     	    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
     	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
     	    success: function (res) {
     	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
     	        if(!localIds || localIds.length == 0) {
     	        	mui.toast('没有选中任何图片');
     	        	return;
     	        }
     	        var localId = localIds[0];
     	        target.attr('src', localId);
     	        setTimeout(function(){
     	        	wx.uploadImage({
         	            localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
         	            isShowProgressTips: 1, // 默认为1，显示进度提示
         	            success: function (res) {
         	                var serverId = res.serverId; // 返回图片的服务器端ID
         	                $.ajax({
         	  	      	      type : "get",
         	  	      	      url  : "/user/wx/file/download?id="+serverId,
         	  	      	      success     : function(data){
         	  	      	    	JSON.stringify(data);
         	  	      	        if(data.code == '000000') {
         	  	      	          mui.toast('图片上传成功');
         	  	      	          var showPath = daidian.config.imageAccessUrl + data.data;
         	  	      	          target.attr('pic-path',data.data).attr('src',showPath);
         	  	      	        } else {
         	  	      	          mui.toast('上传图片失败,失败原因:'+data.msg);
         	  	      	        }
         	  	      	      },
         	  	      	      error     : function() {
         	  	      	        mui.toast('上传图片出现异常,请重试');
         	  	      	      }
         	  	      	    });
         	            }
         	        });
     	        },300);
     	    }
     	});
     });

    $('body').on('tap', '.image-upload[number=multi]', function(evt) {
    	var target = $(evt.target);
    	wx.chooseImage({
    	    count: 9, // 默认9
    	    sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
    	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
    	    success: function (res) {
    	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
    	        if(!localIds || localIds.length == 0) {
    	        	mui.toast('没有选中任何图片');
    	        	return;
    	        }
    	        localIds.forEach(function(localId) {
    	        	setTimeout(function(){ 
        	        	var id = 'image-'+ new Date().getTime() + "-" + Math.floor(Math.random() * 100000);
        	        	target.closest('.image-list').prepend('<div id="'+id+'" class="image-item" style="background-image:url(' + localId + ')"><div class="image-close">X</div></div>');
       	        		wx.uploadImage({
               	            localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
               	            isShowProgressTips: 1, // 默认为1，显示进度提示
               	            success: function (res) {
               	                var serverId = res.serverId; // 返回图片的服务器端ID
               	                $.ajax({
               	  	      	      type : "get",
               	  	      	      url  : "/user/wx/file/download?id="+serverId,
               	  	      	      success     : function(data){
               	  	      	        if(data.code == '000000') {
               	  	      	          var showPath = daidian.config.imageAccessUrl + data.data;
                                	      $('#'+id).attr('pic-path',data.data).css({'background-image':'url('+showPath+')'});
               	  	      	        } else {
               	  	      	          mui.toast('上传图片失败,失败原因:'+data.msg);
               	  	      	        }
               	  	      	      },
               	  	      	      error     : function() {
               	  	      	        mui.toast('上传图片出现异常,请重试');
               	  	      	      }
               	  	      	    });
               	            }
               	        });	        	
    	        	},300);
    	        });    	        
    	    }
    	});
    });


    $('body').on('tap', '.image-item .image-close', function(evt) {
        var target = $(evt.target);
        if(!target.hasClass('image-item')) {
            target = target.closest('.image-item');
        }
        target.remove();
    });  
    
    
    if(daidian.session.live && daidian.session.user && daidian.session.messageCount && daidian.session.messageCount > 0) {
        $('.bottom-nav .mui-icon-chatbubble').append('<span class="mui-badge">'+daidian.session.messageCount+'</span>');
    }
});
</script>