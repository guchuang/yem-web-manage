/**
 * 公共js，包含域名和常用工具
 * @param {Object} active
 */

/**
 * 初始化上下文路径
 */
var _context = function(active) {
	if(active == 'test') {
		_context.path('http://localhost:9600/yem/');
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

/**
 * 公共ajax请求
 */
var ajax = {};
ajax.post = function (url, data, success) {
	$.ajax({
		type:"post",
		url:url,
		async:false,
		data: JSON.stringify(data),
		contentType: "application/json;charset=utf-8",
		success:function (data) {
			this.error(data);
		},
		error:function (data) {
			success(data);
		}
	});
}
/**
 * 公共请求数据包
 */
var requestHeader;
function RequestData() {
	this.requestHeader = {
		sourceId:"wechat",
		time:new Date(),
		act:"20006040000001"
	}
}

/**
 * 公共分页工具
 * @param node 展现的标签
 * @param pageNo 当前页
 * @param totalPage 总页数
 * @param divNums 页码展示数量
 * @returns
 */
function getPage(node, pageNo, totalPage, divNums) {
	//若非单数，自动加1
	if (divNums % 2 == 0) {
		divNums ++;
	}
	//初始化前翻页
	var prev = prevPage(pageNo);
	//初始化后翻页
	var next = nextPage(pageNo, totalPage);
	//初始化页码
	var page = divPage(pageNo, totalPage, divNums);
	//初始化标签
	$("#" + node).html("");
	$("#" + node).append(prev).append(page).append(next);
}
/**
 * 设置前翻页
 * @param pageNo
 * @returns
 */
function prevPage(pageNo) {
	var str = '';
	if (pageNo > 1) {
		str = '<a class="prev" onclick="searchData(' + (pageNo - 1) + ')">&lt;&lt;</a>';
	} else {
		str = '<a class="prev">&lt;&lt;</a>';
	}
	return str;
}
/**
 * 设置后翻页
 * @param pageNo
 * @param totalPage
 * @returns
 */
function nextPage(pageNo, totalPage) {
	var str = '';
	if (pageNo < totalPage) {
		str += '<a class="prev" onclick="searchData(' + (pageNo + 1) + ')">&gt;&gt;</a>';
	} else {
		str += '<a class="prev">&gt;&gt;</a>';
	}
	return str;
}
/**
 * 设置页码
 * @param pageNo
 * @param totalPage
 * @param divNums
 * @returns
 */
function divPage(pageNo, totalPage, divNums) {
	var median = parseInt(divNums / 2);//取地板值
	var str = '';
	
	if (totalPage < divNums) {
		//总页数小于页码数时，说明不够剧中展示，则顺序写出页码，并标注当前页
		for (var i = 0; i < totalPage; i ++) {
			if (pageNo == (i + 1)) {
				str += '<span class="current">' + pageNo + '</span>';
			} else {
				str += '<a class="num" onclick="searchData(' + (i + 1) + ')">' + (i + 1) + '</a>';
			}
		}
	} else {
		//当前页减去 总页码中间值（即3取2，即5取3，7取4，依此类推）小于1时表示当前页在中间值左边，则顺序写出1，2，3，4...标注当前页
		if ((pageNo - median) < 1) {
			for (var i = 0; i < divNums; i ++) {
				if (pageNo == (i + 1)) {
					str += '<span class="current">' + pageNo + '</span>';
				} else {
					str += '<a class="num" onclick="searchData(' + (i + 1) + ')">' + (i + 1) + '</a>';
				}
			}
		} else if ((pageNo - median) >= 1 && (pageNo + median) <= totalPage) {
			//当前页减去 总页码中间值大于1，并且相加小于总页数时，即表示当前页前后页数相同，取中间值median分次写入即可
			for (var i = median; i > 0; i --) {
				str += '<a class="num" onclick="searchData(' + (pageNo - i) + ')">' + (pageNo - i) + '</a>';
			}
			
			str += '<span class="current">' + pageNo + '</span>';
			
			for (var i = 0; i < median; i ++) {
				str += '<a class="num" onclick="searchData(' + (pageNo + i + 1) + ')">' + (pageNo + i + 1) + '</a>';
			}
		} else if ((pageNo + median) > totalPage) {
			//当前页加上 总页码中间值（即3取2，即5取3，7取4，依此类推）大于总页数时表示当前页在中间值右边，则顺序从总页数减1至中间值即可，标注当前页
			for (var i = divNums; i > 0; i --) {
				if (pageNo == (totalPage - i + 1)) {
					str += '<span class="current">' + pageNo + '</span>';
				} else {
					str += '<a class="num" onclick="searchData(' + (totalPage - i + 1) + ')">' + (totalPage - i + 1) + '</a>';
				}
			}
		}
	}
	return str;
}

/**
 * 获取指定名称复选框选中值
 * @param name
 * @returns
 */
function getChecked(name) {
	var array = new Array();
	$("input[name=" + name + "]").each(function() {
		if (this.checked) {
			array.push(this.value);
		}
	});
	return array;
}
