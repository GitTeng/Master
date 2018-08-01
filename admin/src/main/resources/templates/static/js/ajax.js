// ajax封装
function ajax(url, data, callback, cache, async, type, dataType, traditional, error) {
    layer.load();
    var type = type || 'post';//请求类型
    var dataType = dataType || 'json';//接收数据类型
    var async = async || false;//异步请求
    // var alone = alone || false;//独立提交（一次有效的提交）
    var cache = cache || false;//浏览器历史缓存
    var traditional = traditional || false;//如果你想要用传统的方式来序列化数据，那么就设置为true
    var success = function (data) {
        console.log(data);
        layer.closeAll();
        if (data.error == 0) {//服务器处理成功
            if (callback != null) {
                callback(data.data);
            }
        } else {//服务器处理失败
            toastr.error(data.info);
        }
    };
    var error = error || function (data) {
        console.log(data);
        layer.closeAll();
        if (data.status == 404) {
            toastr.error('请求失败，请求未找到');
        } else if (data.status == 503) {
            toastr.error('请求失败，服务器内部错误');
        } else {
            toastr.error(data.responseText);
        }
    };
    $.ajax({
        'url': url,
        'data': data,
        'type': type,
        'dataType': dataType,
        'async': async,
        'success': success,
        'cache': cache,
        'error': error,
        'traditional': traditional,
        'jsonpCallback': 'jsonp' + (new Date()).valueOf().toString().substr(-4),
    });
}

// submitAjax(post方式提交)同步
function submitAjax(form, success, cache) {
    cache = cache || true;
    var url = form.attr('action');
    var data = form.serialize();
    ajax(url, data, success, cache, false, 'post', 'json');
}

// ajax提交(post方式提交)traditional 值为true 用传统的方式来序列化数据,同步
function post(url, data, success, cache) {
    ajax(url, data, success, cache, false, 'post', 'json', true);
}

// ajax提交(get方式提交)同步
function get(url, success, cache) {
    ajax(url, {}, success, cache, false, 'get', 'json');
}

// jsonp跨域请求(get方式提交)
function jsonp(url, success, cache) {
    ajax(url, {}, success, cache, false, 'get', 'jsonp');
}
