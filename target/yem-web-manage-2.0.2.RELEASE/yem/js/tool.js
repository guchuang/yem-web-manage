/**
 * 公共js，包含域名和常用工具
 * @param {Object} active
 */
var _context = function(active) {
	if(active == 'test') {
		_context.path('http://127.0.0.1:9600/yem/');
	} else {
		_context.path('http://127.0.0.1:9300/yem/');
	}
}
_context.path = function(url) {
	this.path = url;
}
_context.init = function(active) {
	_context(active);
}
_context.init("test");

var ajax = {};
ajax.post = function (url, data, success) {
	$.ajax({
		type:"post",
		url:url,
		async:true,
		xhrFields: {
    		withCredentials: true // 设置运行跨域操作  
  		}, 
		data: data,
		type:"JSON",
		success:function (data) {
			error();
		},
		error:success
	});
}
