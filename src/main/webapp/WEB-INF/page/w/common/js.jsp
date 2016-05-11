<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/resources/w/js/jquery-1.11.1.min.js"></script>
<script src="/resources/w/js/bootstrap.min.js"></script>
<script src="/resources/w/js/bootstrap-datetimepicker.min.js"></script>
<script src="/resources/w/js/bootstrap-timepicker.min.js"></script>
<script src="/resources/w/js/chosen.jquery.min.js"></script>
<script src="/resources/w/js/jquery.cookies.js"></script>
<script src="/resources/w/js/jquery.validate.min.js"></script>
<script src="/resources/w/js/select2.min.js"></script>
<script src="/resources/w/js/swiper.animate.min.js"></script>
<script src="/resources/w/js/swiper.min.js"></script>
<script>
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

var getQueryString = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
</script>