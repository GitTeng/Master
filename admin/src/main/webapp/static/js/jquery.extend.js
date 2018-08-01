//form表单填充
$.fn.loadJson = function (jsonValue) {
    var obj = this;
    $.each(jsonValue, function (name, ival) {
        var $oinput = obj.find(":input[name=" + name + "]");
        if ($oinput.attr("type") == "radio"
            || $oinput.attr("type") == "checkbox") {
            $oinput.each(function () {
                if (Object.prototype.toString.apply(ival) == '[object Array]') {//是复选框，并且是数组
                    for (var i = 0; i < ival.length; i++) {
                        if ($(this).val() == ival[i])
                            $(this).attr("checked", "checked");
                    }
                } else {
                    if ($(this).val() == ival)
                        $(this).attr("checked", "checked");
                }
            });
        } else if ($oinput.attr("type") == "textarea") {//多行文本框
            obj.find("[name=" + name + "]").html(ival);
        } else if ($oinput.attr("type") == "select") {
            setValueSelected($oinput[0],ival);
        }
        else {
            obj.find("[name=" + name + "]").val(ival);
        }
    });
};

/**
 * 设置select框回显
 * @param selectObj select对象或者id
 * @param value
 */
function setValueSelected(selectObj, value) {
    if (value == undefined) {
        return;
    }
    if (selectObj.nodeName != 'SELECT') {
        selectObj = document.getElementById(selectObj);
    }
    if (selectObj == null){
        return;
    }
    for (var i = 0; i < selectObj.options.length; i++) {
        if (selectObj.options[i].value == value) {
            selectObj.options[i].selected = 'selected';
        }
    }
}
