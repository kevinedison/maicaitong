<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <title>FMS Console</title>
  <link href="/resources/console/css/bootstrap.min.css" rel="stylesheet">
  <link href="/resources/console/css/font-awesome.min.css" rel="stylesheet">
  <link href="/resources/console/css/select2.css" rel="stylesheet"> 
  <link href="/resources/console/css/formValidation.min.css" rel="stylesheet" >  
  <link href="/resources/console/css/bootstrap-datepicker.min.css" rel="stylesheet"> 
  <link href="/resources/console/css/web.css" rel="stylesheet">
</head>
<body style="overflow: visible;">
<div id="page-preloader">
    <div id="page-status"><i class="fa fa-recycle fa-spin"></i></div>
</div>
<section>
  <div class="leftpanel">
      <div class="login-user-info">
	      <div class="media">
	          <img class="media-object" src="/resources/console/images/img/loggeduser.png">
	          <div class="media-body">
	              <div class="login-account">18510213635</div>
	              <div class="company-list">嘉正禾科技</div>
	          </div>
	      </div>
	  </div>	  
      <ul class="nav nav-bracket">
        <li class="nav-parent" ><a href="javascript:void(0);"><i class="fa fa-users"></i> <span>用户管理</span></a>
          <ul class="children" style="display: none;">
            <li><a action="/resources/console/model/buyer/buyer-approve.html">买手审批</a></li>
            <li><a action="/resources/console/model/buyer/buyers.html">所有买手</a></li>
            <li><a action="/resources/console/model/buyer/users.html">所有用户</a></li>
            <li><a action="/resources/console/model/buyer/feedback.html">投诉建议</a></li>
          </ul>
        </li> 
        <li class="nav-parent" ><a href="javascript:void(0);"><i class="fa fa-tasks"></i> <span>服务管理</span></a>
          <ul class="children" style="display: none;">
            <li><a action="/resources/console/model/service/orders.html">所有订单</a></li>
            <li><a action="/resources/console/model/service/services.html">所有商品</a></li>
          </ul>
        </li> 
        <li class="nav-parent" ><a href="javascript:void(0);"><i class="fa fa-tasks"></i> <span>基础信息维护</span></a>
          <ul class="children" style="display: none;">
            <li><a action="/resources/console/model/base/location.html">热淘区</a></li>
            <li><a action="/resources/console/model/base/rate.html">准实时汇率</a></li>
            <li><a action="/resources/console/model/service/services.html">类型维护</a></li>
          </ul>
        </li>
        <li class="nav-parent" ><a href="javascript:void(0);"><i class="fa fa-users"></i> <span>分类品牌管理</span></a>
          <ul class="children" style="display: none;">
            <li><a action="/resources/console/model/brand/brand.html">品牌维护</a></li>
            <li><a action="/resources/console/model/brand/category.html">分类维护</a></li>
            <li><a action="/resources/console/model/brand/subcategory.html">子分类维护</a></li>
          </ul>
        </li>
        <li class="nav-parent" ><a href="javascript:void(0);"><i class="fa fa-users"></i> <span>经营分析</span></a>
          <ul class="children" style="display: none;">
            <li><a action="/resources/console/model/static/serviceview.html">商品访问统计</a></li>
            <li><a action="/resources/console/model/static/serviceorder.html">商品订单统计</a></li>
          </ul>
        </li>       
      </ul>
  </div>
  <div class="mainpanel">
    <div class="header">
      <!-- <a class="menutoggle"><i class="fa fa-bars"></i></a>  -->  
      <div class="header-right">
        <ul class="headermenu">
          <li>
            <div class="btn-group">
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                duanjia
                <span class="caret"></span>
              </button>
              <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                <li><a href="/console/account/logout"><i class="fa fa-sign-out"></i>退出</a></li>
              </ul>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div id="mainBody" class="main-content">
        <div class="index-nav">
          <img class="media-object" src="/resources/console/images/sinan-logo.png">
        </div>
    </div>
  </div>
</section>

<div class="modal fade" id="confirm-dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 class="modal-title confirm-dialog-title" id="myModalLabel">确认</h4>
      </div>
      <div class="modal-body confirm-dialog-body"></div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default confirm-dialog-no" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary confirm-dialog-ok">确认</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="alert-dialog" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" data-backdrop="static" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
            <h4 class="modal-title alert-dialog-title">警告</h4>
        </div>
        <div class="modal-body alert-dialog-body"></div>
        <div class="modal-footer">
	        <button type="button" class="btn btn-default alert-dialog-no" data-dismiss="modal">关闭</button>
	    </div>
    </div>
  </div>
</div>

<div class="modal fade" id="info-dialog" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" data-backdrop="static" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
            <h4 class="modal-title info-dialog-title">消息</h4>
        </div>
        <div class="modal-body info-dialog-body"></div>
    </div>
  </div>
</div>
    
<script src="/resources/console/js/jquery-1.11.1.min.js"></script>
<script src="/resources/console/js/bootstrap.min.js"></script>
<script src="/resources/console/js/jquery.cookies.js"></script>
<script src="/resources/console/js/select2.min.js"></script>
<script src="/resources/console/js/bootstrap-datepicker.min.js"></script>
<script src="/resources/console/js/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/resources/console/js/formValidation.min.js"></script>
<script src="/resources/console/js/formValidationBootstrap.js"></script>
<script src="/resources/console/js/ajaxfileupload.js"></script>
<script src="/resources/wx/js/dataconfig.js"></script>
<script>
var daidian = {};
daidian.config = {
    'pageSize': 10,
    'constellationConfig': {"aries":"白羊座","taurus":"金牛座","gemini":"双子座","cancer":"巨蟹座","leo":"狮子座","virgo":"处女座","libra":"天秤座","scorpio":"天蝎座","sagittarius":"射手座","capricornus":"摩羯座","aquarius":"水瓶座","pisces":"双鱼座"},
    'ageRange':{"00":"00后","90":"90后","80":"80后","70":"70后","60":"60后"},
    'identityType':{"overseastudent":"留学生","overseachinese":"华侨","guide":"专职买手","timebuyer":"兼职买手","family":"家属","clerk":"店员"},
    'serviceType' : {"milk":"奶粉","bag":"名牌包","cosmetics":"化妆品","special":"本地特产"},
    'validateConfig': {
        framework : 'bootstrap',
        excluded  : [':disabled'],
        icon      : {valid: 'glyphicon glyphicon-ok',invalid: 'glyphicon glyphicon-remove',validating: 'glyphicon glyphicon-refresh'},
        fields    : {}
    },
    'imageAccessUrl': 'http://image.acarry.com/',
};

var getQueryString = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

Date.prototype.format = function(format) {
	  var o = {
	    "M+": this.getMonth() + 1, //month
	    "d+": this.getDate(), //day
	    "h+": this.getHours(), //hour
	    "m+": this.getMinutes(), //minute
	    "s+": this.getSeconds(), //second
	    //quarter
	    "q+": Math.floor((this.getMonth() + 3) / 3),
	    "S": this.getMilliseconds() //millisecond
	  };
	  if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	  for (var k in o) {
	    if (new RegExp("(" + k + ")").test(format)) {
	      format = format.replace(
	        RegExp.$1, RegExp.$1.length == 1 ?
	        o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	    }
	  }
	  return format;
};

var getPictureUrl = function(picture, type, use) {
	picture = picture || '';
    //0 城市 1 服务 2 用户 3 消息 5 证件号码
    if(type == 0) {picture = picture + "!t480x240.png";}
    if(type == 1) {if(use == 'list') {picture = picture + "!t160x160.png";} else {picture = picture + "!t480x480.png";}}
    if(type == 2) {if(picture.indexOf('http://') < 0) {picture = picture + "!t100x100.png" } else {return picture};}
    if(type == 3) {if(use == 'list') {picture = picture + "!t100x100.png";}}
    if(type == 5) {if(use == 'list') {picture = picture + "!t160x160.png";} else {picture = picture + "!t480x480.png";}}
    return "http://image.acarry.com/" + picture;
};

var confirmDialog = function(option) {
  $('.confirm-dialog-title').text((option && option.title) || '确认');
  $('.confirm-dialog-body').html((option && option.content) || '');
  
  if(option && option.contentBind) {
	  option.contentBind();
  }
  
  $('.confirm-dialog-no').text((option && option.noText) || '取消');
  $('.confirm-dialog-ok').html((option && option.yesText) || '确认');

  if(option && option.yesBind) {
	  $('.confirm-dialog-ok').unbind("click");
	  $('.confirm-dialog-ok').click(function() { if(option.yesBind((option.data || {}))){ $('#confirm-dialog').modal('hide');}});	  
  } else {
	 console.log('没有给确认按钮绑事件，弹框无效！！'); 
  }
  
  $('#confirm-dialog').modal({'keyboard':false});  
};

var alertDialog = function(option) {
  $('.alert-dialog-title').text((option && option.title) || '警告');
  $('.alert-dialog-body').html((option && option.content) || '');
  $('#alert-dialog').modal({'keyboard':false});  
};
 
var infoDialog = function(option) {
  $('.info-dialog-title').text((option && option.title) || '消息');
  $('.info-dialog-body').html((option && option.content) || '');
  $('#info-dialog').modal({'keyboard':false});  
};

jQuery(window).load(function() {
   jQuery('#page-preloader').delay(350).fadeOut(function(){
      jQuery('body').delay(350).css({'overflow':'visible'});
   });
});
	
jQuery(document).ready(function() {
   jQuery('.leftpanel .nav-parent > a').bind('click', function() {

	  var parent = jQuery(this).parent();
      var sub = parent.find('> ul');

      // Dropdown works only when leftpanel is not collapsed
      if(!jQuery('body').hasClass('leftpanel-collapsed')) {
         if(sub.is(':visible')) {
            sub.slideUp(200, function(){
               parent.removeClass('nav-active');
               jQuery('.mainpanel').css({height: ''});
               adjustmainpanelheight();
            });
         } else {
            closeVisibleSubMenu();
            parent.addClass('nav-active');
            sub.slideDown(200, function(){
               adjustmainpanelheight();
            });
         }
      }
      return false;
   });

   function closeVisibleSubMenu() {
      jQuery('.leftpanel .nav-parent').each(function() {
         var t = jQuery(this);
         if(t.hasClass('nav-active')) {
            t.find('> ul').slideUp(200, function(){
               t.removeClass('nav-active');
            });
         }
      });
   }

   function adjustmainpanelheight() {
      // Adjust mainpanel height
      var docHeight = jQuery(document).height();
      if(docHeight > jQuery('.mainpanel').height())
         jQuery('.mainpanel').height(docHeight);
   }
   
   adjustmainpanelheight();


   // Add class everytime a mouse pointer hover over it
   jQuery('.nav-bracket > li').hover(function(){
      jQuery(this).addClass('nav-hover');
   }, function(){
      jQuery(this).removeClass('nav-hover');
   });


   // Menu Toggle
   jQuery('.menutoggle').click(function(){
      var body = jQuery('body');
      var bodypos = body.css('position');
      if(bodypos != 'relative') {
         if(!body.hasClass('leftpanel-collapsed')) {
            body.addClass('leftpanel-collapsed');
            jQuery('.nav-bracket ul').attr('style','');
            jQuery(this).addClass('menu-collapsed');
         } else {
            body.removeClass('leftpanel-collapsed chat-view');
            jQuery('.nav-bracket li.active ul').css({display: 'block'});
            jQuery(this).removeClass('menu-collapsed');
         }
      } else {
         if(body.hasClass('leftpanel-show'))
            body.removeClass('leftpanel-show');
         else
            body.addClass('leftpanel-show');
            adjustmainpanelheight();
      }

   });
   
   $('.nav-bracket .children li a').click(function(evt) {
	   var target = $(evt.target);
	   if(!target.is('a')) {
		   target = target.closest('a');
	   }
	   var href = target.attr('action');
	   if(href) {
		   jQuery('#mainBody').delay(350).fadeOut(function(){
			   $('#mainBody').empty().load(href+"?rmd="+new Date().getTime(),{},function(data) {
				   $('#mainBody').fadeIn(300);
			   });
		   });
		   return;
	   }
   });
   
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
   
   $('body').on('change', 'input[type=file][number=single]', function(evt) {
       var target = $(evt.target);
       var fileId = target.attr('id');
       var picTarget = target.closest('.image-item');
       var file = target[0].files[0];
       $.ajaxFileUpload({
           url: '/console/file/upload',
           dataType: 'text',
           data : {'file' : file},
           success: function(data, status) {
           	data = JSON.parse(data || '');
               if (data) {
               	var showPath = daidian.config.imageAccessUrl + data.data;
                   picTarget.attr('pic-path',data.data).css({'background-image': 'url(' + showPath + ')'});
               }
           },
           progress: function(evt) {
               if (evt.total && evt.total > 0) {
                   var percent = ((evt.loaded / evt.total) * 100);
                   percent = Math.ceil(percent);
                   $('.image-upload-process').html(percent+'%');
                   if(percent == 100) {
                       $('.image-upload-process').html('');
                   }
               }
           },
           error: function(data, status, e) {
           }
       });
   });

   $('body').on('change', 'input[type=file][number=multi]', function(evt) {
       var target = $(evt.target);
       var fileId = target.attr('id');
       var picTarget = target.closest('.image-item');
       var file = target[0].files[0];
       $.ajaxFileUpload({
           url: '/console/file/upload',
           dataType: 'text',
           data : {'file' : file},
           success: function(data, status) {
           	data = JSON.parse(data || '');
               if (data && data.code == '000000') {
               	var showPath = daidian.config.imageAccessUrl + data.data;
                   var picHtml =  '<div class="image-item" style="background-image: url('+ showPath +');" pic-path="'+data.data+'"><div class="image-close">X</div></div>';
                   target.closest('.image-list').prepend(picHtml);
               }
           },
           progress: function(evt) {
               if (evt.total && evt.total > 0) {
                   var percent = ((evt.loaded / evt.total) * 100);
                   percent = Math.ceil(percent);
                   $('.image-upload-process').html(percent+'%');
                   if(percent == 100) {
                       $('.image-upload-process').html('');
                   }
               }
           },
           error: function(data, status, e) {
               
           }
       });
   });
   
   $('body').on('click', '.image-item .image-close', function(evt) {
       var target = $(evt.target);
       if(!target.hasClass('image-item')) {
           target = target.closest('.image-item');
       }
       target.remove();
   });  
});
</script>
</body>
</html>