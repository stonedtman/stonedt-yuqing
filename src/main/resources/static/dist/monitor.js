/**
 * @author huajiancheng
 * @date 2020/04/15
 * @description 数据监测js
 */


/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description 初次调用
 */

let monitor_groupid = analysis_groupid;
let monitor_projectid = analysis_projectid;
let article_public_idList = [];
let currentPageByDetail = 1;

var timea = null;
var timeb = null;

$(document).ready(function () {
    // if (!searchflag) {
    if (!projectFlag) {
        if (monitor_projectid != null && monitor_groupid != null) {
            let data = new Object();
            data.projectId = monitor_projectid;
            data.group_id = monitor_groupid;
            let parambasic = new Object(); // ajax基本的参数
            parambasic.type = "POST";
            parambasic.url = ctxPath + "monitor/getCondition";
            parambasic.contentType = 'application/json;charset=utf-8';
            sendArticle(parambasic, JSON.stringify(data), installCondition);
            //sendArticleIndustry(parambasic, JSON.stringify(data), funcIndustry);
        } else if (monitor_groupid != null && monitor_projectid == null) { // 没有方案
            let data = new Object();
            data.projectId = monitor_projectid;
            data.group_id = monitor_groupid;
            let parambasic = new Object(); // ajax基本的参数
            parambasic.type = "POST";
            parambasic.url = ctxPath + "monitor/getgroupname";
            parambasic.contentType = 'application/json;charset=utf-8';
            sendArticle(parambasic, JSON.stringify(data), installGroupName);

        } else if (monitor_projectid == null && monitor_groupid == null) {
            nodata('#monitor-content', "暂无方案，去创建>", ctxPath + 'project');
            $("#group_name").html("暂无方案组");
            $("#project_name").html("暂无方案");
        }
    } else {
        $("#condition").empty();
        $("#filter_item_id").hide();
        $("#nav-monitor").empty();
        $("#left-filtrates").removeClass("left-filtrate");
        $("#left-filtrates").addClass("left-filtrates");
        $("#card-body").removeClass("card");
        let str = '<div class="p-20">' +
            '    <div class="card">' +
            '        <div class="card-body p-45">' +
            '            <div class="full-search-box">' +
            '                <div class="search-group d-flex" style="padding-left: 0;">' +
            '                    <div style="width: 100%;">' +
            '                        <input id="searchWord" type="text" class="form-control"' +
            '                               placeholder="请输入企业名称、企业简称、产品名、行业名称、政策法规、人名等，组合查询使用+号分隔，或查询使用空格分隔">' +
            '                    </div>' +
            '                    <button id="searchBtn" style="z-index: 1; border-radius: 0 2px 2px 0 !important;width: auto;"' +
            '                            class="btn btn-info">全文搜索' +
            '                    </button>' +
            '                </div>' +
            '            </div>' +
            '        </div>' +
            '    </div>' +
            '    <div class="card">' +
            '        <div class="card-body">' +
            '            <div class="hot-con-box m-b-20">' +
            '                <div class="hot-content" style="margin-left: 40px;">' +
            '                    <span>搜索记录</span>' +
            '                </div>' +
            '            </div>' +
            '            <div class="search-record" style="width: 1000px; margin: 0 auto;">' +
            '                <div style="display: flex; width: calc(100% - 80px); margin: 0 auto;" id="searchwords">' +
            '                </div>' +
            '            </div>' +
            '        </div>' +
            '    </div>' +
            '</div>';
        $("#monitor-content").html(str);
        getSearchWordById();
    }


    // } else {  // 全文搜索逻辑
    //     // $("#group_name").html("全文搜索");
    //     // $("#project_name").html(search_text);
    //     $("#sidebarnav li").each(function () {
    //         var finda = $(this).find(".sidebar-link.has-arrow.waves-effect.waves-dark")
    //         var liactive = finda.hasClass("active")
    //         console.log(liactive)
    //         if (liactive == true) {
    //             finda.removeClass("active")
    //             $(this).find("ul").removeClass("in")
    //         }
    //     })
    //     monitor_groupid = 1;
    //     monitor_projectid = 1;
    //     $("#searchkeyword").val(search_text);
    //     let data = new Object();
    //     data.projectId = monitor_projectid;
    //     data.opinion_condition_id = 1587611487000;
    //     // data.group_id = monitor_groupid;
    //     let parambasic = new Object(); // ajax基本的参数
    //     parambasic.type = "POST";
    //     parambasic.url = ctxPath + "monitor/getCondition";
    //     parambasic.contentType = 'application/json;charset=utf-8';
    //     sendArticle(parambasic, JSON.stringify(data), installCondition);
    // }
});

/**
 * @author huajiancheng
 * @date 2020/04/15
 * @description 切换方案请求数据
 */
function switchProject(event) {
   /* $("#condition").animate({scrollTop:0}) ;*//*2021.6.28添加*/
    let $event = $(event);
    let projectid = $event.attr("data-index");
    let group_id = $event.attr("data-groupid");
    page = 1;
    //setdefult time
    // var filltertype =
    //     {"msg":"获取条件成功！","code":200,"data":{"similar":0,"opinion_condition_id":"1251809026178682880","matchs":2,"create_time":"2020-04-19 17:45:03","timeType":5,"sort":1,"timee":"","times":"","emotion":"[1,2,3]","project_id":"1251809026082213888","id":30,"precise":0,"time":4},"group_name":"产业链条","project_name":"汽车发动机"}
    // installCondition(filltertype)
    // 条件绑定成功，获取文章列表数据
    // let articleParam = new Object();
    // articleParam.type = "POST";
    // articleParam.url = ctxPath + "monitor/getarticle";
    // articleParam.contentType = 'application/json;charset=utf-8';
    monitor_groupid = group_id;
    monitor_projectid = projectid;
    $("#searchkeyword").val("");
    let seturl = "monitor?" + "projectid=" + monitor_projectid + "&groupid=" + monitor_groupid;
    setUrl(seturl);
    if (monitor_projectid != null && monitor_groupid != null) {
        let data = new Object();
        data.projectId = monitor_projectid;
        data.group_id = monitor_groupid;
        data.page = 1;
        let parambasic = new Object(); // ajax基本的参数
        parambasic.type = "POST";
        parambasic.url = ctxPath + "monitor/getCondition";
        parambasic.contentType = 'application/json;charset=utf-8';

        var active = "badge-info";
        var normal = "badge-light";

        $("#industrylist span,#eventlist span,#provincelist span,#citylist span").removeClass(active);
        $("#industrylist span,#eventlist span,#provincelist span,#citylist span").addClass(normal);

       /* $("#industrylist > span:nth-child(1),#citylist > span:nth-child(1),#provincelist > span:nth-child(1),#citylist > span:nth-child(1)").addClass(active);*/
        //  $("#industrylist > span:nth-child(1)").addClass(normal);
        /*2012.6.28修改*/
        $("#organizationtype input[type=checkbox],#enterprisetypelist input[type=checkbox],#categorylabledata input[type=checkbox],#hightechtypelist input[type=checkbox],#policylableflag input[type=checkbox],/*数据品类*/#datasource_type input[type=checkbox]").prop("checked", false);
        $("#classify input[type=checkbox]").prop("checked", false);
        /*$("#organizationtype  > ul > li:nth-child(1) > a > label > input[type=checkbox],#enterprisetypelist  > ul > li:nth-child(1) > a > label > input[type=checkbox],#categorylabledata  > ul > li:nth-child(1) > a > label > input[type=checkbox],#hightechtypelist  > ul > li:nth-child(1) > a > label > input[type=checkbox],#policylableflag  > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop("checked", true);*/
        /*修该，删除数据来源默认选中第一个*/
        /*2021.6.29修改*/


        sendArticle(parambasic, JSON.stringify(data), installCondition);
        /*2021.7.1修改*/
        /*sendArticleIndustry(parambasic, JSON.stringify(data), funcIndustry);
        sendArticleEvent(parambasic, JSON.stringify(data), funcEvent);
        sendArticleProvince(parambasic, JSON.stringify(data), funcProvince);
        sendArticleCity(parambasic, JSON.stringify(data), funcCity);*/


        //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
        //sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
        dataPushFunc()

    } else if (monitor_groupid != null && monitor_projectid == null) { // 没有方案
        let data = new Object();
        data.group_id = monitor_groupid;
        data.projectId = monitor_projectid;
        let parambasic = new Object(); // ajax基本的参数
        parambasic.type = "POST";
        parambasic.url = ctxPath + "monitor/getgroupname";
        parambasic.contentType = 'application/json;charset=utf-8';
        sendArticle(parambasic, JSON.stringify(data), installGroupName);
    } else if (monitor_projectid == null && monitor_groupid == null) {
        showInfo("请先创建方案组");
        $("#group_name").html("暂无方案组");
        $("#project_name").html("暂无方案");
    }
}

/**
 * contentType: 'application/json;charset=utf-8',
 * @author huajiancheng
 * @date 2020/04/16
 * @param param ajax请求基本参数
 * @param data 用户传的参数
 * @param funcname 方法名称
 * @description 组装当前的方案组名称
 */
function installGroupName(res) {
    let code = res.code;
    if (code == 200) {
        let data = res.data;
        let group_name = data.group_name;
        $("#group_name").html(group_name);
        $("#project_name").html("暂无方案");
        $("#page").html("");
        nodata('#monitor-content', '暂无方案，去创建>', ctxPath + 'project/addproject?groupid=' + monitor_groupid);
        var time = 5;
        $('#monitor-content .nodata p').append('<span style="margin-left:5px;">' + time + 's后将自动跳转</span>');
        timea = window.setInterval(function () {
            time--;
            if (time == 0) time = 1;
            $('#monitor-content .nodata p span').html(time + 's后将自动跳转');
        }, 1000);
        timeb = window.setTimeout(function () {
            window.location.href = ctxPath + 'project/addproject?groupid=' + monitor_groupid;
        }, time * 1000);
        //        showInfo("请先创建方案");
    } else {
        showInfo("请先创建方案组");
        $("#group_name").html("暂无方案组");
        $("#project_name").html("暂无方案");
        $("#page").html("");
        nodata('#monitor-content');
    }
}

/**
 * contentType: 'application/json;charset=utf-8',
 * @author huajiancheng
 * @date 2020/04/16
 * @param param ajax请求基本参数
 * @param data 用户传的参数
 * @param funcname 方法名称
 * @description 请求列表数据
 */

function sendArticle(param, data, funcname) {
    window.clearTimeout(timea);
    window.clearInterval(timeb);
    var aaa = JSON.parse(data);
    if (!aaa.group_id) {
        showInfo('请先创建方案组');
        return;
    }
    $("#page").html("");
    $.ajax({
        type: param.type,
        url: param.url,
        dataType: 'json',
        data: data,
        contentType: param.contentType,
        beforeSend: function () {
            loading("#monitor-content")
        },
        success: function (res) {
            funcname(res);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            } else {
                $("#page").html("");
                dataerror("#monitor-content");
            }
        }
    });
}


/**
 * contentType: 'application/json;charset=utf-8',
 * @author wangyi
 * @date 2021/04/22
 * @param param ajax请求基本参数
 * @param data 用户传的参数
 * @param funcname 方法名称
 * @description 请求行业数据
 */

function sendArticleIndustry(param, data, funcIndustry) {

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getindustry";
    articleParam.contentType = 'application/json;charset=utf-8';


    window.clearTimeout(timea);
    window.clearInterval(timeb);
    var aaa = JSON.parse(data);
    if (!aaa.group_id) {
        showInfo('请先创建方案组');
        return;
    }
    $("#page").html("");
    $.ajax({
        type: articleParam.type,
        url: articleParam.url,
        dataType: 'json',
        data: data,
        contentType: param.contentType,
        beforeSend: function () {
            loading("#monitor-content")
        },
        success: function (res) {
            funcIndustry(res);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            } else {
                $("#page").html("");
                dataerror("#monitor-content");
            }
        }
    });
}

/**
 * contentType: 'application/json;charset=utf-8',
 * @author wangyi
 * @date 2021/04/22
 * @param param ajax请求基本参数
 * @param data 用户传的参数
 * @param funcname 方法名称
 * @description 请求省份数据
 */

function sendArticleProvince(param, data, funcProvince) {

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getprovince";
    articleParam.contentType = 'application/json;charset=utf-8';


    window.clearTimeout(timea);
    window.clearInterval(timeb);
    var aaa = JSON.parse(data);
    if (!aaa.group_id) {
        showInfo('请先创建方案组');
        return;
    }
    $("#page").html("");
    $.ajax({
        type: articleParam.type,
        url: articleParam.url,
        dataType: 'json',
        data: data,
        contentType: param.contentType,
        beforeSend: function () {
            loading("#monitor-content")
        },
        success: function (res) {
            funcProvince(res);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            } else {
                $("#page").html("");
                dataerror("#monitor-content");
            }
        }
    });
}



var provplusbo = true

$('#provplus').click(function () {

    if (provplusbo) {
        $('#provincelist').removeClass('industrylist')
        $('#provincelist').addClass('industrylist1')
        // $("#provincelist").css({
        //     'display': 'none'
        // })

        // $("#provincelist1").css({
        //     'display': 'inline-block'
        // })
        var newaaa = `
        收起&nbsp<i data-v-8ad03c8e="" class="fa fa-angle-up"></i>`
        $("#provplus").html(newaaa);
        provplusbo = false
    } else {

        $('#provincelist').removeClass('industrylist1')
        $('#provincelist').addClass('industrylist')
        // $("#provincelist").css({
        //     'display': 'inline-block'
        // })

        // $("#provincelist1").css({
        //     'display': 'none'
        // })
        var newaaa = `
        更多&nbsp<i data-v-8ad03c8e="" class="fa fa-angle-down"></i>`
        $("#provplus").html(newaaa);
        provplusbo = true
    }
})

function funcProvince(res) {
    $("#provincelist").html('');
    $("#provincelist1").html('');
    let industrystr = "";
    let totalindustrystr = '';
    let industrystr1 = "";
    let totalindustrystr1 = '';
    let data = res.data.data;
    // 这是省份
    if (data.length == 0) {
        //2021.6.30修改totalindustrystr += '<span data-province="0" style="font-size:12px;width:60px" class="badge badge-pill badge-info">全部</span>';
        totalindustrystr += '<span data-province="0" style="font-size:12px;width:60px" class="badge badge-pill">全部</span>';
    }


    if (data.length <= 5) {
        $("#provplus").css({
            'display': 'none'
        })
    } else {
        $("#provplus").css({
            'display': 'block'
        })
    }
    // for (let i = 0; i < 19; i++) {
    //     if (data[i].key != '') {
    //         if (data[i].key != 'total') {
    //             industrystr += ' <span data-province="' + data[i].key + '"style="font-size:12px;width:55px" class="badge badge-pill badge-light">' + data[i].key + '</span>';
    //         }
    //     }
    // }
    for (let i = 0; i < data.length; i++) {
        if (data[i].key != '') {
            if (data[i].key == 'total') {
                //2021.6.30修改totalindustrystr += '<span data-province="0" style="font-size:12px;width:55px" class="badge badge-pill badge-info">全部</span>';
                totalindustrystr += '<span data-province="0" style="font-size:12px;width:55px" class="badge badge-pill">全部</span>';
            }
        }
    }
    /*2021.6.30,创建数组接收key值*/
    let keyarray = [];
    for (let i = 0; i < data.length; i++) {
        if (data[i].key != '') {
            keyarray.push(data[i].key);
            if (data[i].key != 'total') {
                industrystr1 += ' <span data-province="' + data[i].key + '"style="font-size:12px;width:55px" class="badge badge-pill badge-light">' + data[i].key + '</span>';
                //industrystr1 += ' <span data-province="' + data[i].key + '"style="font-size:12px;width:55px" class="badge badge-pill">' + data[i].key + '</span>';
            } else {
                //2021.6.30修改totalindustrystr1 += '<span data-province="0" style="font-size:12px;width:55px" class="badge badge-pill badge-info">全部</span>';
                totalindustrystr1 += '<span data-province="0" style="font-size:12px;width:55px" class="badge badge-pill">全部</span>';
            }
        }
    }

    $("#provincelist").html(totalindustrystr1 + industrystr1);

    $("#provincelist1").html(totalindustrystr1 + industrystr1);


    /*涉及省份*/
    if (province == null || province == ""){
        $("#provincelist > span:nth-child(1)").addClass("badge-info");
        $("#provincelist > span:nth-child(1)").removeClass("badge-light");
    }else {
        $("#provincelist > span:nth-child(1)").removeClass( "badge-info");
        $("#provincelist > span:nth-child(1)").addClass("badge-light");
        let provinces = province;

        if (keyarray.includes(provinces)){
            $("#provincelist > span[data-province=" + provinces + "]").addClass("badge-info");
            $("#provincelist > span[data-province=" + provinces + "]").removeClass("badge-light");
        }else {
            let industrystr2 = ' <span data-province="' + provinces + '"style="font-size:12px;width:55px" class="badge badge-pill badge-info">' + provinces + '</span>';
            $("#provincelist").html(totalindustrystr1 + industrystr1 + industrystr2);
            $("#provincelist1").html(totalindustrystr1 + industrystr1 + industrystr2);
        }



    }

    //2021.7.1
    determineCityAndprovincelistprovincelistShow(province , "provincelabledata" , "省份");
}


/**
 * contentType: 'application/json;charset=utf-8',
 * @author wangyi
 * @date 2021/04/22
 * @param param ajax请求基本参数
 * @param data 用户传的参数
 * @param funcname 方法名称
 * @description 请求省份数据
 */

function sendArticleCity(param, data, funcCity) {

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getcity";
    articleParam.contentType = 'application/json;charset=utf-8';


    window.clearTimeout(timea);
    window.clearInterval(timeb);
    var aaa = JSON.parse(data);
    if (!aaa.group_id) {
        showInfo('请先创建方案组');
        return;
    }
    $("#page").html("");
    $.ajax({
        type: articleParam.type,
        url: articleParam.url,
        dataType: 'json',
        data: data,
        contentType: param.contentType,
        beforeSend: function () {
            loading("#monitor-content")
        },
        success: function (res) {


            funcCity(res);
        },
        error: function (xhr, ajaxOptions, thrownError) {

            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            } else {
                $("#page").html("");
                dataerror("#monitor-content");
            }
        }
    });
}



var cityplusbo = true;

$('#cityplus').click(function () {

    if (cityplusbo) {

        $("#citylist").removeClass('industrylist')
        $("#citylist").addClass('industrylist1')
        //     'display': 'none'
        // })

        // $("#citylist1").css({
        //     'display': 'inline-block'
        // })
        var newaaa = `
        收起&nbsp<i data-v-8ad03c8e="" class="fa fa-angle-up"></i>`
        $("#cityplus").html(newaaa);
        cityplusbo = false
    } else {

        $("#citylist").removeClass('industrylist1')
        $("#citylist").addClass('industrylist')
        // console.log("false");
        // $("#citylist").css({
        //     'display': 'inline-block'
        // })

        // $("#citylist1").css({
        //     'display': 'none'
        // })
        var newaaa = `
        更多&nbsp<i data-v-8ad03c8e="" class="fa fa-angle-down"></i>`
        $("#cityplus").html(newaaa);
        cityplusbo = true
    }
})



function funcCity(res) {
    $("#citylist").html('');
    $("#citylist1").html('');
    let industrystr = "";
    let totalindustrystr = '';
    let industrystr1 = "";
    let totalindustrystr1 = '';
    let data = res.data.data;
    if (data.length == 0) {
        //2021.6.30修改totalindustrystr += '<span data-city="0" style="font-size:12px;width:55px" class="badge badge-pill badge-info">全部</span>';
        totalindustrystr += '<span data-city="0" style="font-size:12px;width:55px" class="badge badge-pill">全部</span>';
    }

    if (data.length <= 5) {
        $("#cityplus").css({
            'display': 'none'
        })
    } else {
        $("#cityplus").css({
            'display': 'block'
        })
    }

    // for (let i = 0; i < 20; i++) {
    //     if (data[i].key != '') {
    //         if (data[i].key != 'total') {
    //             industrystr += ' <span data-city="' + data[i].key + '"style="font-size:12px;width:55px" class="badge badge-pill badge-light">' + data[i].key + '</span>';
    //         }
    //     }
    // }
    for (let i = 0; i < data.length; i++) {
        if (data[i].key != '') {
            if (data[i].key == 'total') {
                //2021.6.30修改totalindustrystr += '<span data-city="0" style="font-size:12px;width:55px" class="badge badge-pill badge-info">全部</span>';
                totalindustrystr += '<span data-city="0" style="font-size:12px;width:55px" class="badge badge-pill">全部</span>';
            }
        }
    }
    /*2021.6.30,创建数组接收key值*/
    let keyarray = [];
    for (let i = 0; i < data.length; i++) {
        if (data[i].key != '') {
            /*接收key*/
            keyarray.push(data[i].key);
            if (data[i].key != 'total') {
                industrystr1 += ' <span data-city="' + data[i].key + '"style="font-size:12px;width:55px" class="badge badge-pill badge-light">' + data[i].key + '</span>';
                //industrystr1 += ' <span data-city="' + data[i].key + '"style="font-size:12px;width:55px" class="badge badge-pill">' + data[i].key + '</span>';
            } else {
                //2021.6.30修改totalindustrystr1 += '<span data-city="0" style="font-size:12px;width:55px" class="badge badge-pill badge-info">全部</span>';
                totalindustrystr1 += '<span data-city="0" style="font-size:12px;width:55px" class="badge badge-pill">全部</span>';
            }
        }
    }

    $("#citylist").html(totalindustrystr1 + industrystr1);

    $("#citylist1").html(totalindustrystr1 + industrystr1);





    if (city == null || city == ""){
        $("#citylist > span:nth-child(1)").addClass("badge-info");
        $("#citylist > span:nth-child(1)").removeClass("badge-light");
    }else {
        let citys = city;
        $("#citylist > span:nth-child(1)").removeClass("badge-info");
        $("#citylist > span:nth-child(1)").addClass("badge-light");
       if(keyarray.includes(citys)){
           $("#citylist > span[data-city=" + citys + "]").addClass("badge-info");
           $("#citylist > span[data-city=" + citys + "]").removeClass("badge-light");
       }else {
           let industrystr2 = ' <span data-city="' + citys + '"style="font-size:12px;width:55px" class="badge badge-pill badge-info">' + citys + '</span>';
           $("#citylist").html(totalindustrystr1 + industrystr1 + industrystr2);

           $("#citylist1").html(totalindustrystr1 + industrystr1 + industrystr2);
       }
    }

    /*2021.6.30修改*/

    //2021.7.1
    determineCityAndprovincelistprovincelistShow(city , "citylabledata" , "城市");
}




/**
 * contentType: 'application/json;charset=utf-8',
 * @author wangyi
 * @date 2021/04/22
 * @param param ajax请求基本参数
 * @param data 用户传的参数
 * @param funcname 方法名称
 * @description 请求事件数据
 */

function sendArticleEvent(param, data, funcEvent) {

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getevent";
    articleParam.contentType = 'application/json;charset=utf-8';
    window.clearTimeout(timea);
    window.clearInterval(timeb);
    var aaa = JSON.parse(data);
    if (!aaa.group_id) {
        showInfo('请先创建方案组');
        return;
    }

    $("#page").html("");
    $.ajax({
        type: articleParam.type,
        url: articleParam.url,
        dataType: 'json',
        data: data,
        contentType: param.contentType,
        beforeSend: function () {
            loading("#monitor-content")
        },
        success: function (res) {
            funcEvent(res);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            } else {
                $("#page").html("");
                dataerror("#monitor-content");
            }
        }
    });
}


var plusbo = true
$("#plus").click(function () {
    if (plusbo) {
        $("#industrylist").removeClass("industrylist");
        $("#industrylist").addClass("industrylist1");
        var newaaa = `
        收起&nbsp<i data-v-8ad03c8e="" class="fa fa-angle-up"></i>`
        $("#plus").html(newaaa);
        plusbo = false
    } else {
        $("#industrylist").removeClass("industrylist1");
        $("#industrylist").addClass("industrylist");
        var newaaa = `
        更多&nbsp<i data-v-8ad03c8e="" class="fa fa-angle-down"></i>`
        $("#plus").html(newaaa);
        plusbo = true
    }

})

function funcIndustry(res) {

    $("#industrylist1").html('');
    $("#industrylist").html('');
    let industrystr = "";
    let totalindustrystr = '';
    let industrystr1 = "";
    let industayrstrother = '';
    let totalindustrystr1 = '';
    let data = res.data.data;
    if (data.length <= 5) {
        $("#plus").css({
            'display': 'none'
        })
    } else {
        $("#plus").css({
            'display': 'block'
        })
    }
    $('#industrylist').children().length;
    // for (let i = 0; i < 11; i++) {
    //     if (data[i].key != '') {
    //         if (data[i].key != 'total') {
    //             industrystr += ' <span data-industry="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill badge-light">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
    //         } else {
    //             totalindustrystr += '<span data-industry="0" style="font-size:12px;width:90px" class="badge badge-pill badge-info">全部(' + data[i].doc_count + ')' + '</span>';
    //         }
    //     }
    // }
    for (let i = 0; i < data.length; i++) {
        if (data[i].key != '') {
            if (data[i].key == 'total') {
                //2021.6.30修改totalindustrystr += '<span data-industry="0" style="font-size:12px;width:90px" class="badge badge-pill badge-info">全部(' + data[i].doc_count + ')' + '</span>';
                totalindustrystr += '<span data-industry="0" style="font-size:12px;width:90px" class="badge badge-pill badge-light">全部(' + data[i].doc_count + ')' + '</span>';
            }
        }
    }
        /*2021.6.30,创一个数组，保存所有key值*/
    let keyarray = [];
    for (let i = 0; i < data.length; i++) {
        if (data[i].key != '') {
            /*2021.6.30,存入key值*/
            keyarray.push(data[i].key);
            if (data[i].key != 'total'&&data[i].key!='其它') {
                industrystr1 += ' <span data-industry="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill badge-light">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
                //industrystr1 += ' <span data-industry="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill ">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
            } else if(data[i].key=='其它'){
                industayrstrother += ' <span data-industry="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill badge-light">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
            	//industayrstrother += ' <span data-industry="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
            }else {
                //totalindustrystr1 += '<span data-industry="0" style="font-size:12px;width:90px" class="badge badge-pill badge-info">全部(' + data[i].doc_count + ')' + '</span>';
                totalindustrystr1 += '<span data-industry="0" style="font-size:12px;width:90px" class="badge badge-pill badge-light">全部(' + data[i].doc_count + ')' + '</span>';
            }
        }
    }

    $("#industrylist").html(totalindustrystr1 + industrystr1+industayrstrother);
    $("#industrylist1").html(totalindustrystr1 + industrystr1+industayrstrother);

    /*2021.6.30修改*/
    /*涉及行业*/
    if (industryIndex == null || industryIndex == ""){
        $("#industrylist > span:nth-child(1)").addClass("badge-info");
        $("#industrylist > span:nth-child(1)").removeClass("badge-light");
    }else {
        $("#industrylist > span:nth-child(1)").removeClass( "badge-info");
        $("#industrylist > span:nth-child(1)").addClass("badge-light");
        let industryIndexs = industryIndex.split(',');
        /*当该条件数据为零时*/
        let industrystr2 = "";
        for (var i = 0 ; i < industryIndexs.length ; i++){
            if (!keyarray.includes(industryIndexs[i])){
                industrystr2 += ' <span data-industry="' + industryIndexs[i] + '"style="font-size:12px;width:90px" class="badge badge-pill badge-info">' + industryIndexs[i] + '(' + 0 + ')' + '</span>';
            }
        }
        if (industrystr2 != "" && industrystr2 != null) {
            $("#industrylist").html(totalindustrystr1 + industrystr1 + industrystr2 + industayrstrother);
            $("#industrylist1").html(totalindustrystr1 + industrystr1 + industrystr2 + industayrstrother);
        }
        /*当该条件数据为零时*/
        for (var i = 0 ; i < industryIndexs.length ; i++){
            if (keyarray.includes(industryIndexs[i])) {
                $("#industrylist > span[data-industry=" + industryIndexs[i] + "]").addClass("badge-info");
                $("#industrylist > span[data-industry=" + industryIndexs[i] + "]").removeClass("badge-light");

            }

        }

    }
    /*2021.6.30修改*/
    //2021.7.1
    determineShow(industryIndex , "industrylist" , "industrylabledata" , "行业");
}




var eventplusbo = true;
$('#eventplus').click(function () {
    if (eventplusbo) {


        $("#eventlist").removeClass("industrylist");
        $("#eventlist").addClass("industrylist1");
        // $("#eventlist").css({
        //     'display': 'none'
        // })

        // $("#eventlist1").css({
        //     'display': 'inline-block'
        // })
        var newaaa = `
        收起&nbsp<i data-v-8ad03c8e="" class="fa fa-angle-up"></i>`
        $("#eventplus").html(newaaa);
        eventplusbo = false
    } else {

        $("#eventlist").removeClass("industrylist1");
        $("#eventlist").addClass("industrylist");
        // $("#eventlist").css({
        //     'display': 'inline-block'
        // })

        // $("#eventlist1").css({
        //     'display': 'none'
        // })
        var newaaa = `
        更多&nbsp<i data-v-8ad03c8e="" class="fa fa-angle-down"></i>`
        $("#eventplus").html(newaaa);
        eventplusbo = true
    }

})




function funcEvent(res) {
    $("#eventlist").html('');
    $("#eventlist1").html('');
    let eventstr = "";
    let totaleventstr = '';
    let eventstr1 = '';
    let eventotherstr = '';
    let totaleventstr1 = '';
    let data = res.data.data;
    if (data.length <= 5) {
        $("#eventplus").css({
            'display': 'none'
        })
    } else {
        $("#eventplus").css({
            'display': 'block'
        })
    }
    // for (let i = 0; i < 11; i++) {
    //     if (data[i].key != '') {
    //         if (data[i].key != 'total') {
    //             eventstr += ' <span data-event="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill badge-light">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
    //         } else {
    //             totaleventstr += ' <span data-event="0" style="font-size:12px;width:90px" class="badge badge-pill badge-info">全部(' + data[i].doc_count + ')' + '</span>';
    //         }
    //     }
    // }
    // for (let i = 0; i < data.length; i++) {
    //     if (data[i].key != '') {
    //         if (data[i].key == 'total') {
    //             totaleventstr += ' <span data-event="0" style="font-size:12px;width:90px" class="badge badge-pill badge-info">全部(' + data[i].doc_count + ')' + '</span>';
    //         }
    //     }
    // }

    /*2021.6.30修改，创建数组存key*/
    let keyarray = [];
    for (let i = 0; i < data.length; i++) {
        if (data[i].key != '') {
/*存入key*/  keyarray.push(data[i].key);
            if (data[i].key != 'total'&&data[i].key!='其它') {
                eventstr1 += ' <span data-event="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill badge-light">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
                //eventstr1 += ' <span data-event="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
            }else if(data[i].key=='其它'){
                eventotherstr += ' <span data-event="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill badge-light">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
            	//eventotherstr += ' <span data-event="' + data[i].key + '"style="font-size:12px;width:90px" class="badge badge-pill">' + data[i].key + '(' + data[i].doc_count + ')' + '</span>';
            }else {
                //2021.6.30修改totaleventstr1 += ' <span data-event="0" style="font-size:12px;width:90px" class="badge badge-pill badge-info">全部(' + data[i].doc_count + ')' + '</span>';
                totaleventstr1 += ' <span data-event="0" style="font-size:12px;width:90px" class="badge badge-pill">全部(' + data[i].doc_count + ')' + '</span>';
            }
        }
    }
    $("#eventlist").html(totaleventstr1 + eventstr1+eventotherstr);

    // $("#eventlist1").html(totaleventstr1 + eventstr1);

    /*2021.6.30修改*/
    /*涉及事件*/
    if (eventIndex == null || eventIndex == ""){
        $("#eventlist > span:nth-child(1)").addClass("badge-info");
        $("#eventlist > span:nth-child(1)").removeClass("badge-light");
    }else {
        $("#eventlist > span:nth-child(1)").removeClass("badge-info");
        $("#eventlist > span:nth-child(1)").addClass("badge-light");
        let eventIndexs = eventIndex.split(',');

        /*当该条件数据为零时*/
        let eventstr2 = "";
        for (var i = 0 ; i < eventIndexs.length ; i++){
            if (!keyarray.includes(eventIndexs[i])){
                eventstr2 += ' <span data-event="' + eventIndexs[i] + '"style="font-size:12px;width:90px" class="badge badge-pill badge-info">' + eventIndexs[i] + '(' + 0 + ')' + '</span>';
            }
        }
        if (eventstr2 != "" && eventstr2 != null) {
            $("#eventlist").html(totaleventstr1 + eventstr1+ eventstr2 +eventotherstr);
        }
        /*当该条件数据为零时*/

        for (var i = 0 ; i < eventIndexs.length ; i++){
            if (keyarray.includes(eventIndexs[i])) {
                $("#eventlist > span[data-event=" + eventIndexs[i] + "]").addClass("badge-info");
                $("#eventlist > span[data-event=" + eventIndexs[i] + "]").removeClass("badge-light");

            }
        }

    }
    /*2021.6.30修改*/

    //2021.7.1
    determineShow(eventIndex , "eventlist" , "eventlabledata" , "事件");
}






function dealCate(arg) {
    if (arg != '' && arg != undefined) {
        let parse = JSON.parse(arg);
        return parse[0].type;
    } else {
        return "";
    }

}


/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description 组装列表数据
 */
function installArticle(res) {
	
	
	
    let code = res.code;
    let msg = res.msg;
    let strAll = '';
    if (code == 200) {
        let data = res.data.data;
        let totalPage = res.data.totalPage;
        let totalCount = res.data.totalCount;
        let currentPage = res.data.currentPage;
        //        if (totalCount > 5000) {
        //            totalCount = "5000+";
        //        }
        if(totalCount < 50){
            isNeedContact(totalCount)
        }
        let similarflag = 0;

        //判断是否合并
        $('span[data-similar]').each(function () {
            if ($(this).hasClass('badge-info')) {
                similarflag = $(this).data('similar');
            }
        });
        // //debugger;
        if (similarflag == 1) {
            $("#totalCount").html(res.data.totalNum);
        } else {
            $("#totalCount").html(totalCount);
        }


        if (res.data.hasOwnProperty("article_public_idList")) {
            article_public_idList = res.data.article_public_idList;
        }
        if (data.length > 0) {
            pageHelper(currentPage, totalPage);
            for (let i = 0; i < data.length; i++) {
                let dataJson = data[i];
                let datasource = dataJson.datasource; // 媒体分类
                let websitelogo = dataJson.websitelogo?dataJson.websitelogo:"/assets/images/default_source.png";
                let article_public_id = dataJson.article_public_id;

                /*2021.6.28修改*/
                let authorShow = ''
                let author = dataJson.author;;
                if(author != null && author != ""){
                    authorShow = ' • '+ author ;
                }

                /*2021.6.28修改*/

                let key_words = dataJson.key_words;
                let sourcewebsitename = dataJson.sourcewebsitename;
                let title = dataJson.title;
                /*增加title副本,以下所有title_copy原本都是title*/
                let title_copy = title;
                let reg=/<\/?.+?\/?>/g;
                title_copy = title_copy.replace(reg,'');
                /*以上为修改*/
                let source_url = dataJson.source_url;
                let content = dataJson.content;
                content = content.trim().replace(/\s\s/g,"")
                let emotionalIndex = dataJson.emotionalIndex;
                let publish_time = dataJson.publish_time;
                let pub_data = dataJson.publish_time;
                let extend_string_one = dataJson.extend_string_one;
                let forwardingvolume = dataJson.forwardingvolume; // 转发量
                let commentsvolume = dataJson.commentsvolume; // 评论量
                let praisevolume = dataJson.praisevolume; // 点赞数
                publish_time = timeParse(publish_time);
                var relatedWord = dataJson.relatedWord;
                let ner = dataJson.ner;
                let eventlable = dataJson.eventlable;
                let industrylable = dataJson.industrylable;
                let article_category = dataJson.article_category;
                let titlekeyword = dataJson.titlekeyword;
                let num = 0
                let similarflag = 0;

                //判断是否合并
                $('span[data-similar]').each(function () {
                    if ($(this).hasClass('badge-info')) {
                        similarflag = $(this).data('similar');
                    }
                });

                if (similarflag == '1') {
                    num = dataJson.num;
                }






                let category = dealCate(article_category);

                let copytext = '';
                let read = '';
                if (datasource == 2) { // 微博
                    let strStart = '<div class="wb-content just-bet" >';
                    let strCheck = '<div class="monitor-check"><input type="checkbox" data-index="' + article_public_id + '" id="check2"><span></span></div>';
                    let strContentStart = '<div class="monitor-right">';
                    let strTitle = '<div class="monitor-content-title"><a target="_blank" title="'+title_copy+'" style="width: 65%" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&publish_time=' + pub_data + '&relatedWord=' + relatedWord.join("，") + '" class="link font-bold">'
                    strTitle+='<img class="content-logo" src="' + websitelogo + '" onerror="javascript:this.src=\'/assets/images/default_source.png\'"><span style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">'+title+'</span></a>';
                    if (industrylable != '') {
                        strTitle += '<span class="sl-date industrylable" >' + industrylable + ' </span>';
                    }

                    if (eventlable != '') {
                        strTitle += '<span class="sl-date eventlable">' + eventlable + ' </span>';
                    }

                    if (category != '') {
                        strTitle += '<span class="sl-date categorylable">' + category + ' </span>';
                    }


                    strTitle += '<span class="sl-date">' + publish_time + ' </span></div>';
                    // let strTitle = '<div class="monitor-content-title"><a onclick="toDetail(&apos;' + article_public_id + '&apos;,&apos;' + monitor_groupid + '&apos;,&apos;' + monitor_projectid + '&apos;)" class="link font-bold"><span class="content-logo" style="background: url(' + websitelogo + ');"></span>' + title + '</a><span class="sl-date">' + publish_time + ' </span></div>';

                    var str = "________________";
                    /* 16 */
                    let contentStart = ''; // 自己发的
                    let contentEnd = '';
                    if (content.indexOf(str) != -1) {
                        let index = content.lastIndexOf(str);
                        contentStart = content.substring(0, index);
                        contentEnd = content.substring(index + 16, content.length);
                    } else {
                        contentStart = content;
                    }

                    let strContent = '<div class="monitor-content-con font-13">' + contentStart + '​</div>'; // 自己的正文
                    let strTranspond = '<div class="monitor-content-con font-13 wb-zf">' + contentEnd + '</div>'; // 转发的原文
                    let strImgStart = '<div class="img-group">';
                    let strImgGroup = '';
                    if (typeof (extend_string_one) == "object") {
                        let imglist = extend_string_one.imglist;
                        if (imglist != null) {
                            for (let i = 0; i < imglist.length; i++) {
                                let imgurl = imglist[i].imgurl;
                                let imgurlstr = '<div class="img-box"><img src="' + imgurl + '" alt="" class="img-cover "></div>'
                                strImgGroup += imgurlstr;
                                if (i > 3) {
                                    break;
                                }
                            }
                        }
                    }
                    let strImgEnd = '</div>';
                    let strLikeStr = '<div class="like-comm">';
                    let strSource = '<span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' + sourcewebsitename + authorShow +'</span>';/*2021.6.28添加作者显示*/
                    let strForward = '<span class="link m-r-10"> <i class="mdi mdi-shape-square-plus "></i> 转发 ' + forwardingvolume + '</span>';
                    let strPraise = '<span class="link m-r-10"> <i class="mdi mdi-heart-outline "></i> 点赞 ' + praisevolume + '</span>';
                    let strComment = '<span class="link m-r-10"> <i class="mdi mdi-comment-processing-outline"></i> 评论 ' + commentsvolume + '</span>';
                    let keyword = '';
                    if (similarflag == '1') {
                        keyword = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>' + '<span class="link m-r-10" onclick="similarArticles(\''+titlekeyword+'\')" style="cursor: pointer"> <i class="mdi mdi-projector-screen"></i> 相似文章数 ' + num + '</span>';
                    } else {
                        keyword = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>';
                    }

                    let kuaijie = '<span class="link m-r-0">' +
                        '<div class="btn-group inline-block" role="group"> <ul class="font-size-0" style="list-style-type: none;">' +
                        '<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-collection dropdown-toggle ng-scope" id="scj_916007411683191177512726" data-tippy="" title="添加至收藏夹"> </button><ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> <li role="presentation" ng-repeat="collect in collectFoldersList" ng-if="collect!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(collect.folderId,1,icc)"> <span class="fa-key ng-binding datafavorite" data-type="' + article_public_id + '" ng-bind="collect.name">收藏夹</span> </a> </li> </ul> </li>' +
                        //					'<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-news-material dropdown-toggle ng-scope" id="sck_916007411683191177512726" data-tippy="" title="发送、移动"> </button> <ul class="dropdown-menu ng-scope" role="menu" ng-if="briefFoldersList!=null&amp;&amp;briefFoldersList.length>0"> <li role="presentation" ng-repeat="brief in briefFoldersList" ng-if="brief!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(brief.folderId,2,icc)"> <span class="fa-key ng-binding sending" data-type="'+article_public_id+'" ng-bind="brief.name">发送、移动</span> </a> </li>  </ul> </li>'+
                        //					'<li class="inline-block dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-send-way dropdown-toggle ng-scope" data-tippy="" title="舆情下发渠道"></button> <ul class="dropdown-menu pt15 pb15" role="menu"> <li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="getAddressBook(2,icc,1);"> <span class="fa-key">短信下发</span> </a> </li> <li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="getAddressBook(1,icc,1);"> <span class="fa-key">邮件下发</span> </a> </li></ul> </li>'+
                        '<li class="inline-block" style="float: left;"> <a href="' + source_url + '" target="_blank" class=""> <button type="button" data-tippy-placement="top" aria-expanded="false" class="tippy btn btn-default-icon fa-check-origin-link" data-tippy="" data-original-title="查看原文"></button> </a> </li>' +
                        '<li class="inline-block dropdown" style="float: left;"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-copy-direct dropdown-toggle" data-tippy="" data-original-title="复制"> </button> <ul class="dropdown-menu" role="menu"> ' +




                        '<li role="presentation"> <a href="javascript:void(0)" id="link_916007411683191177512726" role="menuitem" ng-click="copyLink(icc.webpageUrl,icc.id);"> <span class="fa-key copyurl" data-type="' + article_public_id + '" data-flag="' + source_url + '" >拷贝地址</span> </a> </li>' +
                        '<li role="presentation"> <a href="javascript:void(0)" class="copy-link-custom" role="menuitem" ng-click="aKeyToCopy(icc);" data-type="' + article_public_id + '" data-flag="' + copytext + '"> <span class="fa-key copytext" >一键复制</span> </a> </li> </ul> </li>' +
                        //	'<li class="inline-block ng-scope" style="float: left;" ng-show="icc.readFlag==undefined &amp;&amp; view.resultPresent != 3" ng-if="view.showBatchLabel==0"> <button type="button" data-tippy-placement="top" class="tippy btn btn-default-icon fa-unread-status" ng-click="readNews($event,icc)" data-tippy="" title="标已读"> </button> </li>'+
                        //	'<li class="inline-block ng-hide" style="float: left;" ng-show="icc.readFlag==\'1\' &amp;&amp; view.resultPresent != 3"> <button type="button" data-tippy-placement="top" class="tippy btn btn-default-icon fa-read-status waves-effect waves-light color-blue" data-tippy="" data-original-title="已读"> </button> </li>'+
                        //	'<li class="inline-block dropdown" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-distribute dropdown-toggle" data-tippy="" title="上报"> </button> <ul class="dropdown-menu" role="menu"> <li role="presentation"> <a href="#group_fenfa" ng-click="groupDistribute(icc,1);" data-toggle="modal" role="menuitem"> <span class="fa-key">上报</span> </a> </li>  </ul> </li>'+
                        /*'<li class="inline-block" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-trash dropdown-toggle" data-tippy="" title="移除"> </button> <ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> ' +





                        '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)"> <span  data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="1" class="fa-key deletedata">删除信息</span> </a> </li>' +*/
                        //'<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)"> <span   data-type="'+article_public_id+'" data-flag="2" class="fa-key deletedata">删除信息并排除来源</span> </a> </li> '+
                        /*'</ul> </li>' +*/

                        '<li class="inline-block emotional_markers" style="float: left;" ng-show="view.resultPresent != 3"> ' +
                        '<button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-re-analysis dropdown-toggle" data-tippy="" title="情感标注"> </button>' +
                        '<ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0">' +
                        '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="1"> <span class="dataemtion">正面</span> </a> </li>' +
                        '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="2"> <span class="dataemtion">中性</span> </a> </li>' +
                        '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="3"> <span class="dataemtion">负面</span> </a> </li> ' +
                        '</ul>' +
                        '</li>' +

                        //read+
                        // '<li class="inline-block" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-suyuan dropdown-toggle" data-tippy="" data-original-title="已读、未读"> </button> ' +
                        // '<ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0">  ' +
                        // '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)"> <span data-type="' + article_public_id + '" data-flag="1" class="fa-key isread">已读</span> </a> </li>' +
                        // '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)"> <span data-type="' + article_public_id + '" data-flag="2" class="fa-key isread">未读</span> </a> </li> </ul> </li>' +

                        //跟踪项
                        //'<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-distribute dropdown-toggle ng-scope" id="scj_916007411683191177512726" data-tippy="" title="创建跟踪项"></button> '+
                        //					'<ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> '+
                        //					'<li role="presentation" ng-repeat="collect in collectFoldersList" ng-if="collect!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(collect.folderId,1,icc)"> <span class="fa-key track" data-type="'+article_public_id+'" data-keywords="'+relatedWord.join("，")+'" ng-bind="collect.name">创建跟踪项</span> </a> </li>'+
                        //					'</ul> '+
                        '</li>' +



                        '</ul></div></span>';

                    let strEmotion = '';
                    if (emotionalIndex == 1) {
                        strEmotion = '<span class="link f-right moodzm">正面</span>';
                    } else if (emotionalIndex == 2) {
                        strEmotion = '<span class="link f-right moodzx">中性</span>';
                    } else if (emotionalIndex == 3) {
                        strEmotion = '<span class="link f-right moodfm">负面</span>';
                    }

                    let strcompanyandgov = '';
                    if(ner!=""){
                        //拼接机构
                        let org = JSON.parse(ner).org;
                        //政府机构
                        let nto = JSON.parse(ner).nto;
                        //上市公司
                        let ipo = JSON.parse(ner).IPO;

                        if(!((Object.keys(org).length==0)&&(Object.keys(nto).length==0)&&(Object.keys(ipo).length==0))){
                            strcompanyandgov = '<div class="like-comm font-13"><span class="link m-r-10">'
                            let orgstr = "";
                            var orgflag = 1;
                            for (var key in org) {
                                orgflag++;
                                orgstr += '<a style="" class="ntag" href="###">' + key + '</a>'
                                if (orgflag == 3) break;
                            }
                            strcompanyandgov = strcompanyandgov + orgstr + '</span><span class="link m-r-10">'

                            let ntovstr = "";
                            var netoflag = 1;
                            for (var key in nto) {
                                netoflag++;
                                strcompanyandgov += '<a style="" class="govtag" href="###">' + key + '</a>'
                                if (netoflag == 3) break;
                            }
                            strcompanyandgov += '</span>';

                            //上市公司
                            strcompanyandgov += '<span class="link m-r-10">';

                            let ipostr = "";
                            var ipoflag = 1;
                            for (var key in ipo) {
                                ipoflag++;
                                strcompanyandgov += '<a style="" class="ipotag" href="###">' + key + '</a>'
                            }
                            strcompanyandgov += '</span></div>';
                        }
                    }




                    let strLikeEnd = '</div>';
                    let strContentEnd = '</div>';




                    let strEnd = '</div><hr>';
                    strAll += strStart + strCheck + strContentStart + strTitle + strContent + strTranspond + strImgStart + strImgGroup + strImgEnd + strLikeStr + strSource + strForward + strPraise + strComment +/*2021.6.28添加作者*/strauthor + keyword + strEmotion + strLikeEnd + strcompanyandgov + strContentEnd + strEnd;
                } else {
                    if (typeof (extend_string_one) == "object") {
                        let imglist = extend_string_one.imglist;
                        if (imglist.length > 0) {
                            let strStart = '<div class="wb-content just-bet" >';
                            let strCheck = '<div class="monitor-check"><input type="checkbox" data-index="' + article_public_id + '" id="check1"><span></span></div>';
                            let strContentStart = '<div class="monitor-right">';
                            //let strTitle = '<div class="monitor-content-title"><a target="_blank" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&relatedWord=' + relatedWord.join("，") +'&publish_time='+pub_data+ '"  class="link font-bold"><span class="content-logo" style="background: url(' + websitelogo + ');"></span>' + title + '</a><span class="sl-date eventlable">' + industrylable + ' </span><span class="sl-date eventlable">' + eventlable + ' </span><span class="sl-date eventlable">' + category + ' </span><span class="sl-date  ">' + publish_time + ' </span></div>';

                            //let strTitle = '<div class="monitor-content-title"><a target="_blank" style="width: 65%" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&publish_time=' + pub_data + '&relatedWord=' + relatedWord.join("，") + '" class="link font-bold"><span class="content-logo" style="background: url(' + websitelogo + ');"></span>' + title + '</a>';
                            
                            let strTitle = '<div class="monitor-content-title"><a target="_blank" title="'+title_copy+'"style="width: 65%" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&publish_time=' + pub_data + '&relatedWord=' + relatedWord.join("，") + '" class="link font-bold">'
                            strTitle+='<img class="content-logo" src="' + websitelogo + '" onerror="javascript:this.src=\'/assets/images/default_source.png\'"><span style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">'+title+'</span></a>';
                            
                            
                            if (industrylable != '') {
                                strTitle += '<span class="sl-date industrylable" >' + industrylable + ' </span>';
                            }

                            if (eventlable != '') {
                                strTitle += '<span class="sl-date eventlable">' + eventlable + ' </span>';
                            }

                            if (category != '') {
                                strTitle += '<span class="sl-date categorylable">' + category + ' </span>';
                            }


                            strTitle += '<span class="sl-date">' + publish_time + ' </span></div>';

                            // let strTitle = '<div class="monitor-content-title"><a onclick="toDetail(&apos;' + article_public_id + '&apos;,&apos;' + monitor_groupid + '&apos;,&apos;' + monitor_projectid + '&apos;)" class="link font-bold"><span class="content-logo" style="background: url(' + websitelogo + ');"></span>' + title + '</a><span class="sl-date  ">' + publish_time + ' </span></div>';
                            let strContent = '<div class="wb-content-imgbox">' +
                                // '<div class="wb-left-imgbox"><img src="' + imglist[0].imgurl + '" class="img-cover" alt=""></div>' +
                                '<div class="wb-right-content"><div class="monitor-content-con font-13">' + content + '</div></div></div>';
                            if(sourcewebsitename=="抖音"||sourcewebsitename=="快手"||sourcewebsitename=="bilibili"){
                                strContent = ""
                            }
                            let strLikeStrat = '<div class="like-comm m-t-10 font-13">';
                            let strSource = '<span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' + sourcewebsitename + authorShow +'</span>';/*2021.6.28添加作者显示*/
                            //let strKeywords = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>';

                            let strKeywords = '';
                            if (similarflag == '1') {
                                strKeywords = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>' + '<span class="link m-r-10" onclick="similarArticles(\''+titlekeyword+'\')" style="cursor: pointer"> <i class="mdi mdi-projector-screen"></i> 相似文章数 ' + num + '</span>';
                            } else {
                                strKeywords = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>';
                            }


                            let kuaijie = '<span class="link m-r-0">' +
                                '<div class="btn-group inline-block" role="group"> <ul class="font-size-0" style="list-style-type: none;">' +
                                '<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-collection dropdown-toggle ng-scope" id="scj_916007411683191177512726" data-tippy="" title="添加至收藏夹"> </button><ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> <li role="presentation" ng-repeat="collect in collectFoldersList" ng-if="collect!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(collect.folderId,1,icc)"> <span class="fa-key ng-binding datafavorite" data-type="' + article_public_id + '" ng-bind="collect.name">收藏夹</span> </a> </li> </ul> </li>' +
                                //						'<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-news-material dropdown-toggle ng-scope" id="sck_916007411683191177512726" data-tippy="" title="发送、移动"> </button> <ul class="dropdown-menu ng-scope" role="menu" ng-if="briefFoldersList!=null&amp;&amp;briefFoldersList.length>0"> <li role="presentation" ng-repeat="brief in briefFoldersList" ng-if="brief!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(brief.folderId,2,icc)"> <span class="fa-key ng-binding sending" data-type="'+article_public_id+'" ng-bind="brief.name">发送、移动</span> </a> </li>  </ul> </li>'+
                                //							'<li class="inline-block dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-send-way dropdown-toggle ng-scope" data-tippy="" title="舆情下发渠道"></button> <ul class="dropdown-menu pt15 pb15" role="menu"> <li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="getAddressBook(2,icc,1);"> <span class="fa-key">短信下发</span> </a> </li> <li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="getAddressBook(1,icc,1);"> <span class="fa-key">邮件下发</span> </a> </li></ul> </li>'+
                                '<li class="inline-block" style="float: left;"> <a href="' + source_url + '" target="_blank" class=""> <button type="button" data-tippy-placement="top" aria-expanded="false" class="tippy btn btn-default-icon fa-check-origin-link" data-tippy="" data-original-title="查看原文"></button> </a> </li>' +
                                '<li class="inline-block dropdown" style="float: left;"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-copy-direct dropdown-toggle" data-tippy="" data-original-title="复制"> </button> <ul class="dropdown-menu" role="menu"> ' +




                                '<li role="presentation"> <a href="javascript:void(0)" id="link_916007411683191177512726" role="menuitem" ng-click="copyLink(icc.webpageUrl,icc.id);"> <span class="fa-key copyurl" data-type="' + article_public_id + '" data-flag="' + source_url + '" >拷贝地址</span> </a> </li>' +
                                '<li role="presentation"> <a href="javascript:void(0)" class="copy-link-custom" role="menuitem" ng-click="aKeyToCopy(icc);" data-type="' + article_public_id + '" data-flag="' + copytext + '"> <span class="fa-key copytext" >一键复制</span> </a> </li> </ul> </li>' +
                                //'<li class="inline-block ng-scope" style="float: left;" ng-show="icc.readFlag==undefined &amp;&amp; view.resultPresent != 3" ng-if="view.showBatchLabel==0"> <button type="button" data-tippy-placement="top" class="tippy btn btn-default-icon fa-unread-status" ng-click="readNews($event,icc)" data-tippy="" title="标已读"> </button> </li>'+
                                //'<li class="inline-block ng-hide" style="float: left;" ng-show="icc.readFlag==\'1\' &amp;&amp; view.resultPresent != 3"> <button type="button" data-tippy-placement="top" class="tippy btn btn-default-icon fa-read-status waves-effect waves-light color-blue" data-tippy="" data-original-title="已读"> </button> </li>'+
                                //'<li class="inline-block dropdown" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-distribute dropdown-toggle" data-tippy="" title="上报"> </button> <ul class="dropdown-menu" role="menu"> <li role="presentation"> <a href="#group_fenfa" ng-click="groupDistribute(icc,1);" data-toggle="modal" role="menuitem"> <span class="fa-key">上报</span> </a> </li>  </ul> </li>'+
                                /*'<li class="inline-block" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-trash dropdown-toggle" data-tippy="" title="移除"> </button> <ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> ' +





                                '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)"> <span  data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="1" class="fa-key deletedata">删除信息</span> </a> </li>' +
                                //'<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)"> <span   data-type="'+article_public_id+'" data-flag="2" class="fa-key deletedata">删除信息并排除来源</span> </a> </li> '+
                                '</ul> </li>' +*/

                                '<li class="inline-block emotional_markers" style="float: left;" ng-show="view.resultPresent != 3"> ' +
                                '<button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-re-analysis dropdown-toggle" data-tippy="" title="情感标注"> </button>' +
                                '<ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0">' +
                                '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="1"> <span class="dataemtion">正面</span> </a> </li>' +
                                '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="2"> <span class="dataemtion">中性</span> </a> </li>' +
                                '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="3"> <span class="dataemtion">负面</span> </a> </li> ' +
                                '</ul>' +
                                '</li>' +

                                //read+
                                // '<li class="inline-block" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-suyuan dropdown-toggle" data-tippy="" data-original-title="已读、未读"> </button> ' +
                                // '<ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0">  ' +
                                // '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)"> <span data-type="' + article_public_id + '" data-flag="1" class="fa-key isread">已读</span> </a> </li>' +
                                // '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)"> <span data-type="' + article_public_id + '" data-flag="2" class="fa-key isread">未读</span> </a> </li> </ul> </li>' +


                                //'<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-distribute dropdown-toggle ng-scope" id="scj_916007411683191177512726" data-tippy="" title="创建跟踪项"> </button><ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> <li role="presentation" ng-repeat="collect in collectFoldersList" ng-if="collect!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(collect.folderId,1,icc)"> <span class="fa-key track" data-type="'+article_public_id+'" data-keywords="'+relatedWord.join("，")+'" ng-bind="collect.name">创建跟踪项</span> </a> </li> </ul> </li>'+




                                '</ul></div></span>';


                            let strEmotion = '';
                            if (emotionalIndex == 1) {
                                strEmotion = '<span class="link f-right moodzm">正面</span>';
                            } else if (emotionalIndex == 2) {
                                strEmotion = '<span class="link f-right moodzx">中性</span>';
                            } else if (emotionalIndex == 3) {
                                strEmotion = '<span class="link f-right moodfm">负面</span>';
                            }


                            let strcompanyandgov = '';
                            if(ner!=''){
                            	//拼接机构
                            	let org = JSON.parse(ner).org;
                            	let nto = JSON.parse(ner).nto;
                            	let ipo = JSON.parse(ner).IPO;
                            	if(!((Object.keys(org).length==0)&&(Object.keys(nto).length==0)&&(Object.keys(ipo).length==0))){
                            		strcompanyandgov = '<div class="like-comm font-13"><span class="link m-r-10">'
                                        let orgstr = "";
                                        var orgflag = 1;
                                        for (var key in org) {
                                            orgflag++;
                                            orgstr += '<a style="" class="ntag" href="###">' + key + '</a>'
                                            if (orgflag == 3) break;
                                        }
                                        strcompanyandgov = strcompanyandgov + orgstr + '</span><span class="link m-r-10">'
                                        //政府机构
                                        
                                        let ntovstr = "";
                                        var netoflag = 1;
                                        for (var key in nto) {
                                            netoflag++;
                                            strcompanyandgov += '<a style="" class="govtag" href="###">' + key + '</a>'
                                            if (netoflag == 3) break;
                                        }
                                        strcompanyandgov += '</span>';

                                        //上市公司
                                        strcompanyandgov += '<span class="link m-r-10">';
                                        
                                        let ipostr = "";
                                        var ipoflag = 1;
                                        for (var key in ipo) {
                                            ipoflag++;
                                            strcompanyandgov += '<a style="" class="ipotag" href="###">' + key + '</a>'
                                            if (ipoflag == 3) break;
                                        }

                                        strcompanyandgov += '</span></div>';
                            	}
                            	
                            	
                            	
                                
                            }
                            






                            let strLikeEnd = '</div>';
                            let strContentEnd = '</div>';
                            let strEnd = '</div><hr>';
                            strAll += strStart + strCheck + strContentStart + strTitle + strContent + strLikeStrat + strSource + strKeywords + kuaijie + strEmotion + strLikeEnd + strcompanyandgov + strContentEnd + strEnd;
                        } else {
                            let strStart = '<div class="wb-content just-bet">';
                            let strCheck = '<div class="monitor-check"><input type="checkbox" data-index="' + article_public_id + '" id="check3"><span></span></div>';
                            let strContentStart = '<div class="monitor-right">';
                            //let strTitle = '<div class="monitor-content-title"><a target="_blank" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&relatedWord=' + relatedWord.join("，") +'&publish_time='+pub_data+ '"  class="link font-bold"><span class="content-logo" style="background: url(' + websitelogo + ');"></span>' + title + '</a><span class="sl-date eventlable">' + industrylable + ' </span><span class="sl-date eventlable">' + eventlable + ' </span><span class="sl-date eventlable">' + category + ' </span><span class="sl-date  ">' + publish_time + '</span></div>';
                            //let strTitle = '<div class="monitor-content-title"><a target="_blank" style="width: 65%" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&publish_time=' + pub_data + '&relatedWord=' + relatedWord.join("，") + '" class="link font-bold"><span class="content-logo" style="background: url(' + websitelogo + ');"></span>' + title + '</a>';
                            
                            let strTitle = '<div class="monitor-content-title"><a target="_blank" title="'+title_copy+'"style="width: 65%" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&publish_time=' + pub_data + '&relatedWord=' + relatedWord.join("，") + '" class="link font-bold">'
                            strTitle+='<img class="content-logo" src="' + websitelogo + '" onerror="javascript:this.src=\'/assets/images/default_source.png\'"><span style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">'+title+'</span></a>';
                            
                            if (industrylable != '') {
                                strTitle += '<span class="sl-date industrylable" >' + industrylable + ' </span>';
                            }

                            if (eventlable != '') {
                                strTitle += '<span class="sl-date eventlable">' + eventlable + ' </span>';
                            }

                            if (category != '') {
                                strTitle += '<span class="sl-date categorylable">' + category + ' </span>';
                            }


                            strTitle += '<span class="sl-date">' + publish_time + ' </span></div>';



                            // let strTitle = '<div class="monitor-content-title"><a onclick="toDetail(&apos;' + article_public_id + '&apos;,&apos;' + monitor_groupid + '&apos;,&apos;' + monitor_projectid + '&apos;)"  class="link font-bold">' + title + '</a><span class="sl-date  ">' + publish_time + '</span></div>';
                            let strContent = '<div class="monitor-content-con font-13">' + content + '</div>';
                            if(sourcewebsitename=="抖音"||sourcewebsitename=="快手"||sourcewebsitename=="bilibili"){
                                strContent = ""
                            }
                            let strLikeStart = '<div class="like-comm m-t-10 font-13">';
                            let strSource = '<span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' + sourcewebsitename + authorShow + '</span>';/*2021.6.28添加作者显示*/
                            //let strKeywors = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>';

                            let strKeywords = '';
                            if (similarflag == '1') {
                                strKeywords = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>' + '<span class="link m-r-10" onclick="similarArticles(\''+titlekeyword+'\')" style="cursor: pointer"> <i class="mdi mdi-projector-screen"></i> 相似文章数 ' + num + '</span>';
                            } else {
                                strKeywords = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>';
                            }




                            let kuaijie = '<span class="link m-r-0">' +
                                '<div class="btn-group inline-block" role="group"> <ul class="font-size-0" style="list-style-type: none;">' +
                                '<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-collection dropdown-toggle ng-scope" id="scj_916007411683191177512726" data-tippy="" title="添加至收藏夹"> </button><ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> <li role="presentation" ng-repeat="collect in collectFoldersList" ng-if="collect!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(collect.folderId,1,icc)"> <span class="ng-binding datafavorite" data-type="' + article_public_id + '" ng-bind="collect.name">收藏夹</span> </a> </li> </ul> </li>' +
                                //   						'<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-news-material dropdown-toggle ng-scope" id="sck_916007411683191177512726" data-tippy="" title="发送、移动"> </button> <ul class="dropdown-menu ng-scope" role="menu" ng-if="briefFoldersList!=null&amp;&amp;briefFoldersList.length>0"> <li role="presentation" ng-repeat="brief in briefFoldersList" ng-if="brief!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(brief.folderId,2,icc)"> <span class="ng-binding sending" data-type="'+article_public_id+'" >发送、移动</span> </a> </li>  </ul> </li>'+
                                //    						'<li class="inline-block dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-send-way dropdown-toggle ng-scope" data-tippy="" title="舆情下发渠道"></button> <ul class="dropdown-menu pt15 pb15" role="menu"> <li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="getAddressBook(2,icc,1);"> <span class="fa-key">短信下发</span> </a> </li> <li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="getAddressBook(1,icc,1);"> <span class="fa-key">邮件下发</span> </a> </li></ul> </li>'+
                                '<li class="inline-block" style="float: left;"> <a href="' + source_url + '" target="_blank" class=""> <button type="button" data-tippy-placement="top" aria-expanded="false" class="tippy btn btn-default-icon fa-check-origin-link" data-tippy="" data-original-title="查看原文"></button> </a> </li>' +
                                '<li class="inline-block dropdown" style="float: left;"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-copy-direct dropdown-toggle" data-tippy="" data-original-title="复制"> </button> <ul class="dropdown-menu" role="menu"> ' +



                                '<li role="presentation"> <a href="javascript:void(0)" id="link_916007411683191177512726" role="menuitem" ng-click="copyLink(icc.webpageUrl,icc.id);"> <span class="copyurl" data-type="' + article_public_id + '" data-flag="' + source_url + '" >拷贝地址</span> </a> </li>' +
                                '<li role="presentation"> <a href="javascript:void(0)" class="copy-link-custom" role="menuitem" ng-click="aKeyToCopy(icc);" data-type="' + article_public_id + '" data-flag="' + copytext + '"> <span class="copytext" >一键复制</span> </a> </li> </ul> </li>' +
                                //'<li class="inline-block ng-scope" style="float: left;" ng-show="icc.readFlag==undefined &amp;&amp; view.resultPresent != 3" ng-if="view.showBatchLabel==0"> <button type="button" data-tippy-placement="top" class="tippy btn btn-default-icon fa-unread-status" ng-click="readNews($event,icc)" data-tippy="" title="标已读"> </button> </li>'+
                                //'<li class="inline-block ng-hide" style="float: left;" ng-show="icc.readFlag==\'1\' &amp;&amp; view.resultPresent != 3"> <button type="button" data-tippy-placement="top" class="tippy btn btn-default-icon fa-read-status waves-effect waves-light color-blue" data-tippy="" data-original-title="已读"> </button> </li>'+
                                //'<li class="inline-block dropdown" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-distribute dropdown-toggle" data-tippy="" title="上报"> </button> <ul class="dropdown-menu" role="menu"> <li role="presentation"> <a href="#group_fenfa" ng-click="groupDistribute(icc,1);" data-toggle="modal" role="menuitem"> <span class="fa-key">上报</span> </a> </li>  </ul> </li>'+
                               /* '<li class="inline-block" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-trash dropdown-toggle" data-tippy="" title="移除"> </button> <ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> ' +





                                '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)"> <span data-time="' + pub_data + '"  data-type="' + article_public_id + '" data-flag="1" class="deletedata">删除信息</span> </a> </li>' +
                                //'<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)"> <span   data-type="'+article_public_id+'" data-flag="2" class="fa-key deletedata">删除信息并排除来源</span> </a> </li> '+
                                '</ul> </li>' +*/

                                '<li class="inline-block emotional_markers" style="float: left;" ng-show="view.resultPresent != 3"> ' +
                                '<button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-re-analysis dropdown-toggle" data-tippy="" title="情感标注"> </button>' +
                                '<ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0">' +
                                '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="1"> <span class="dataemtion">正面</span> </a> </li>' +
                                '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="2"> <span class="dataemtion">中性</span> </a> </li>' +
                                '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="3"> <span class="dataemtion">负面</span> </a> </li> ' +
                                '</ul>' +
                                '</li>' +

                                //read+
                                // '<li class="inline-block" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-suyuan dropdown-toggle" data-tippy="" data-original-title="已读、未读"> </button>' +
                                // '<ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0">  ' +
                                // '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)"> <span data-type="' + article_public_id + '" data-flag="1" class="isread">已读</span> </a> </li>' +
                                // '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)"> <span data-type="' + article_public_id + '" data-flag="2" class="isread">未读</span> </a> </li> </ul> </li>' +

                                //'<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-distribute dropdown-toggle ng-scope" id="scj_916007411683191177512726" data-tippy="" title="创建跟踪项"> </button><ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> <li role="presentation" ng-repeat="collect in collectFoldersList" ng-if="collect!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(collect.folderId,1,icc)"> <span class="track" data-type="'+article_public_id+'" data-keywords="'+relatedWord.join("，")+'" ng-bind="collect.name">创建跟踪项</span> </a> </li> </ul> </li>'+




                                '</ul></div></span>';



                            let strEmotion = '';
                            if (emotionalIndex == 1) {
                                strEmotion = '<span class="link f-right moodzm">正面</span>';
                            } else if (emotionalIndex == 2) {
                                strEmotion = '<span class="link f-right moodzx">中性</span>';
                            } else if (emotionalIndex == 3) {
                                strEmotion = '<span class="link f-right moodfm">负面</span>';
                            }

                            let strcompanyandgov = '';
                            if(ner!=""){
                                //拼接机构
                                let org = JSON.parse(ner).org;
                                //政府机构
                                let nto = JSON.parse(ner).nto;
                                //上市公司
                                let ipo = JSON.parse(ner).IPO;

                                if(!((Object.keys(org).length==0)&&(Object.keys(nto).length==0)&&(Object.keys(ipo).length==0))){
                                    strcompanyandgov = '<div class="like-comm font-13"><span class="link m-r-10">'
                                    let orgstr = "";
                                    var orgflag = 1;
                                    for (var key in org) {
                                        orgflag++;
                                        orgstr += '<a style="" class="ntag" href="###">' + key + '</a>'
                                        if (orgflag == 3) break;
                                    }
                                    strcompanyandgov = strcompanyandgov + orgstr + '</span><span class="link m-r-10">'
                                    ' </span>'

                                    let ntovstr = "";
                                    var netoflag = 1;
                                    for (var key in nto) {
                                        netoflag++;
                                        strcompanyandgov += '<a style="" class="govtag" href="###">' + key + '</a>'
                                        if (netoflag == 3) break;
                                    }
                                    strcompanyandgov += '</span>';


                                    strcompanyandgov += '<span class="link m-r-10">';

                                    let ipostr = "";
                                    var ipoflag = 1;
                                    for (var key in ipo) {
                                        ipoflag++;
                                        strcompanyandgov += '<a style="" class="ipotag" href="###">' + key + '</a>'
                                        if (ipoflag == 3) break;
                                    }

                                    strcompanyandgov += '</span></div>';
                                }
                            }
                            
                            
                            let strLikeEnd = '</div>';
                            let strContentEnd = '</div>';

                            let strEnd = '</div><hr>';
                            strAll += strStart + strCheck + strContentStart + strTitle + strContent + strLikeStart + strSource + strKeywords + kuaijie + strEmotion + strLikeEnd + strcompanyandgov + strContentEnd + strEnd;
                        }
                    } else {
                        let strStart = '<div class="wb-content just-bet">';
                        let strCheck = '<div class="monitor-check"><input type="checkbox" data-index="' + article_public_id + '" id="check3"><span></span></div>';
                        let strContentStart = '<div class="monitor-right">';
                        //let strTitle = '<div class="monitor-content-title"><a target="_blank" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&relatedWord=' + relatedWord.join("，") +'&publish_time='+pub_data+ '"  class="link font-bold"><span class="content-logo" style="background: url(' + websitelogo + ');"></span>' + title + '</a><span class="sl-date eventlable">' + industrylable + ' </span><span class="sl-date eventlable">' + eventlable + ' </span><span class="sl-date eventlable">' + category + ' </span><span class="sl-date">' + publish_time + '</span></div>';

                        //let strTitle = '<div class="monitor-content-title"><a target="_blank" style="width: 65%" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&publish_time=' + pub_data + '&relatedWord=' + relatedWord.join("，") + '" class="link font-bold"><span class="content-logo" style="background: url(' + websitelogo + ');"></span>' + title + '</a>';
                        
                        let strTitle = '<div class="monitor-content-title"><a target="_blank" title="'+title_copy+'" style="width: 65%" href="' + ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + monitor_groupid + '&projectid=' + monitor_projectid + '&publish_time=' + pub_data + '&relatedWord=' + relatedWord.join("，") + '" class="link font-bold">'
                        strTitle+='<img class="content-logo" src="' + websitelogo + '" onerror="javascript:this.src=\'/assets/images/default_source.png\'"><span style="width:95%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">'+title+'</span></a>';
                        
                        if (industrylable != '') {
                            strTitle += '<span class="sl-date industrylable" >' + industrylable + ' </span>';
                        }

                        if (eventlable != '') {
                            strTitle += '<span class="sl-date eventlable">' + eventlable + ' </span>';
                        }

                        if (category != '') {
                            strTitle += '<span class="sl-date categorylable">' + category + ' </span>';
                        }


                        strTitle += '<span class="sl-date">' + publish_time + ' </span></div>';


                        // let strTitle = '<div class="monitor-content-title"><a onclick="toDetail(&apos;' + article_public_id + '&apos;,&apos;' + monitor_groupid + '&apos;,&apos;' + monitor_projectid + '&apos;)" class="link font-bold">' + title + '</a><span class="sl-date  ">' + publish_time + '</span></div>';
                        let strContent = '<div class="monitor-content-con font-13">' + content + '</div>';
                        if(sourcewebsitename=="抖音"||sourcewebsitename=="快手"||sourcewebsitename=="bilibili"){
                            strContent = ""
                        }
                        let strLikeStart = '<div class="like-comm m-t-10 font-13">';
                        let strSource = '<span class="link m-r-10"> <i class="mdi mdi-earth "></i> 来源 ' + sourcewebsitename + authorShow + '</span>';/*2021.6.28 添加作者显示*/
                        //let strKeywors = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>';

                        let strKeywors = '';
                        if (similarflag == '1') {
                            strKeywors = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>' + '<span class="link m-r-10" onclick="similarArticles(\''+titlekeyword+'\')" style="cursor: pointer"> <i class="mdi mdi-projector-screen"></i> 相似文章数 ' + num + '</span>';
                        } else {
                            strKeywors = '<span class="link m-r-10"> <i class="mdi mdi-tag-outline"></i> 涉及词 ' + relatedWord.join("，") + '</span>';
                        }




                        let kuaijie = '<span class="link m-r-0">' +
                            '<div class="btn-group inline-block" role="group"> <ul class="font-size-0" style="list-style-type: none;">' +
                            '<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-collection dropdown-toggle ng-scope" id="scj_916007411683191177512726" data-tippy="" title="添加至收藏夹"> </button><ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> <li role="presentation" ng-repeat="collect in collectFoldersList" ng-if="collect!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(collect.folderId,1,icc)"> <span class="ng-binding datafavorite" data-type="' + article_public_id + '" ng-bind="collect.name">收藏夹</span> </a> </li> </ul> </li>' +
                            //						'<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-news-material dropdown-toggle ng-scope" id="sck_916007411683191177512726" data-tippy="" title="发送、移动"> </button> <ul class="dropdown-menu ng-scope" role="menu" ng-if="briefFoldersList!=null&amp;&amp;briefFoldersList.length>0"> <li role="presentation" ng-repeat="brief in briefFoldersList" ng-if="brief!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(brief.folderId,2,icc)"> <span class="ng-binding sending" data-type="'+article_public_id+'" >发送、移动</span> </a> </li>  </ul> </li>'+
                            //						'<li class="inline-block dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-send-way dropdown-toggle ng-scope" data-tippy="" title="舆情下发渠道"></button> <ul class="dropdown-menu pt15 pb15" role="menu"> <li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="getAddressBook(2,icc,1);"> <span class="fa-key">短信下发</span> </a> </li> <li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="getAddressBook(1,icc,1);"> <span class="fa-key">邮件下发</span> </a> </li></ul> </li>'+
                            '<li class="inline-block" style="float: left;"> <a href="' + source_url + '" target="_blank" class=""> <button type="button" data-tippy-placement="top" aria-expanded="false" class="tippy btn btn-default-icon fa-check-origin-link" data-tippy="" data-original-title="查看原文"></button> </a> </li>' +
                            '<li class="inline-block dropdown" style="float: left;"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-copy-direct dropdown-toggle" data-tippy="" data-original-title="复制"> </button> <ul class="dropdown-menu" role="menu"> ' +




                            '<li role="presentation"> <a href="javascript:void(0)" id="link_916007411683191177512726" role="menuitem" ng-click="copyLink(icc.webpageUrl,icc.id);"> <span class="copyurl" data-type="' + article_public_id + '" data-flag="' + source_url + '" >拷贝地址</span> </a> </li>' +
                            '<li role="presentation"> <a href="javascript:void(0)" class="copy-link-custom" role="menuitem" ng-click="aKeyToCopy(icc);" data-type="' + article_public_id + '" data-flag="' + copytext + '"> <span class="copytext" >一键复制</span> </a> </li> </ul> </li>' +
                            //'<li class="inline-block ng-scope" style="float: left;" ng-show="icc.readFlag==undefined &amp;&amp; view.resultPresent != 3" ng-if="view.showBatchLabel==0"> <button type="button" data-tippy-placement="top" class="tippy btn btn-default-icon fa-unread-status" ng-click="readNews($event,icc)" data-tippy="" title="标已读"> </button> </li>'+
                            //'<li class="inline-block ng-hide" style="float: left;" ng-show="icc.readFlag==\'1\' &amp;&amp; view.resultPresent != 3"> <button type="button" data-tippy-placement="top" class="tippy btn btn-default-icon fa-read-status waves-effect waves-light color-blue" data-tippy="" data-original-title="已读"> </button> </li>'+
                            //'<li class="inline-block dropdown" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-distribute dropdown-toggle" data-tippy="" title="上报"> </button> <ul class="dropdown-menu" role="menu"> <li role="presentation"> <a href="#group_fenfa" ng-click="groupDistribute(icc,1);" data-toggle="modal" role="menuitem"> <span class="fa-key">上报</span> </a> </li>  </ul> </li>'+
                           /* '<li class="inline-block" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-trash dropdown-toggle" data-tippy="" title="移除"> </button> <ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> ' +
                            '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)"> <span  data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="1" class="deletedata">删除信息</span> </a> </li>' +
                            //'<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)"> <span   data-type="'+article_public_id+'" data-flag="2" class="fa-key deletedata">删除信息并排除来源</span> </a> </li> '+
                            '</ul> </li>' +*/

                            '<li class="inline-block emotional_markers" style="float: left;" ng-show="view.resultPresent != 3"> ' +
                            '<button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-re-analysis dropdown-toggle" data-tippy="" title="情感标注"> </button>' +
                            '<ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0">' +
                            '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="1"> <span class="dataemtion">正面</span> </a> </li>' +
                            '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="2"> <span class="dataemtion">中性</span> </a> </li>' +
                            '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)" data-time="' + pub_data + '" data-type="' + article_public_id + '" data-flag="3"> <span class="dataemtion">负面</span> </a> </li> ' +
                            '</ul>' +
                            '</li>' +

                            //read+
                            // '<li class="inline-block" style="float: left;" ng-show="view.resultPresent != 3"> <button type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-suyuan dropdown-toggle" data-tippy="" data-original-title="已读、未读"> </button>' +
                            // '<ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0">  ' +
                            // '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="excludeCaptureWebsiteNameList(icc)"> <span data-type="' + article_public_id + '" data-flag="1" class="isread">已读</span> </a> </li>' +
                            // '<li role="presentation"> <a href="javascript:void(0)" role="menuitem" ng-click="deleteSolrIds(icc)"> <span data-type="' + article_public_id + '" data-flag="2" class="isread">未读</span> </a> </li> </ul> </li>' +

                            //'<li class="inline-block  dropdown" style="float: left;" ng-class="{\'dropdown\':icclist.length != $index+1,\'dropup\':icclist.length == $index+1}" ng-show="view.resultPresent != 3"> <button ng-if="icclist.length != $index+1" type="button" data-tippy-placement="top" data-hover="dropdown" data-toggle="dropdown" data-animation="scale-up" data-delay="300" aria-expanded="false" class="tippy btn btn-default-icon fa-distribute dropdown-toggle ng-scope" id="scj_916007411683191177512726" data-tippy="" title="创建跟踪项"> </button><ul class="dropdown-menu ng-scope" role="menu" ng-if="collectFoldersList!=null&amp;&amp;collectFoldersList.length>0"> <li role="presentation" ng-repeat="collect in collectFoldersList" ng-if="collect!=null" class="ng-scope"> <a href="javascript:void(0)" role="menuitem" ng-click="insertMaterial(collect.folderId,1,icc)"> <span class="track" data-type="'+article_public_id+'" data-keywords="'+relatedWord.join("，")+'" ng-bind="collect.name">创建跟踪项</span> </a> </li> </ul> </li>'+

                            '</ul></div></span>';
                        let strEmotion = '';
                        if (emotionalIndex == 1) {
                            strEmotion = '<span class="link f-right moodzm">正面</span>';
                        } else if (emotionalIndex == 2) {
                            strEmotion = '<span class="link f-right moodzx">中性</span>';
                        } else if (emotionalIndex == 3) {
                            strEmotion = '<span class="link f-right moodfm">负面</span>';
                        }

                        
                        let strcompanyandgov = '';
                        if(ner!=''){
                        	let org = JSON.parse(ner).org;
                        	let nto = JSON.parse(ner).nto;
                        	let ipo = JSON.parse(ner).IPO;
                        	//拼接机构
                        	if(!((Object.keys(org).length==0)&&(Object.keys(nto).length==0)&&(Object.keys(ipo).length==0))){
                                    strcompanyandgov = '<div class="like-comm font-13"><span class="link m-r-10">'
                                    
                                    let orgstr = "";
                                    var orgflag = 1;
                                    for (var key in org) {
                                        orgflag++;
                                        orgstr += '<a style="" class="ntag" href="###">' + key + '</a>'
                                        if (orgflag == 3) break;
                                    }
                                    strcompanyandgov = strcompanyandgov + orgstr + '</span><span class="link m-r-10">'
                                    ' </span>'
                                    //政府机构
                                    
                                    let ntovstr = "";
                                    var netoflag = 1;
                                    for (var key in nto) {
                                        netoflag++;
                                        strcompanyandgov += '<a style="" class="govtag" href="###">' + key + '</a>'
                                        if (netoflag == 3) break;
                                    }
                                    strcompanyandgov += '</span>';

                                    //上市公司
                                    strcompanyandgov += '<span class="link m-r-10">';
                                    
                                    let ipostr = "";
                                    var ipoflag = 1;
                                    for (var key in ipo) {
                                        ipoflag++;
                                        strcompanyandgov += '<a style="" class="ipotag" href="###">' + key + '</a>'
                                        if (ipoflag == 3) break;
                                    }
                                    strcompanyandgov += '</span></div>';  
                        		
                        	}
                        	}
                        let strLikeEnd = '</div>';
                        let strContentEnd = '</div>';



                        let strEnd = '</div><hr>';
                        strAll += strStart + strCheck + strContentStart + strTitle + strContent + strLikeStart + strSource + strKeywors + kuaijie + strEmotion + strLikeEnd + strcompanyandgov + strContentEnd + strEnd;
                    }
                }
                $("#monitor-content").html(strAll);
            }
        } else {
            $("#page").html("");
            nodata('#monitor-content');
        }
    } else if(code==404) {
        $("#page").html("");
        nokeyword("#monitor-content");
    } else {
        $("#page").html("");
        dataerror("#monitor-content");
    }
    $('body,html').animate({
        scrollTop: 0
      },
      1);
    
}


//  舆情系统中新创建方案数据较少的用户提示
function isNeedContact(total) {
    $.ajax({
        type: "get",
        url: "/popUp/needContact?projectId="+monitor_projectid+"&total="+total,
        success: function (res) {
            if(res){
                lessDataPopup()
            }
        }
    })
}
function lessDataPopup() {
    let html = `
        <div id="exampleModal">
            <div class="modal-backdrop"></div>
            <div class="modal-content" style="width: 600px;">
                <span class="modal-close" onclick="close_lessDataPopup()"></span>
                <div class="modal-main-container">
                    <div class="scan-login">
                        <div class="public-login">
                            <div class="header">
                                <h2 class="header-title" style="margin-top: 12px">
                                    联系我们
                                </h2>
                            </div>
                            <div class="header-title-s" style="text-align: center;font-size: 18px;margin-bottom: 32px;">
                                当前是体验版展示数据量有限<br>如需获取更多全量数据，请与我们联系
                            </div>
                            <div style="display: flex;justify-content: space-around;">
                                <div style="text-align: center">
                                    <h5>添加产品经理微信，获取更多数据</h5>
                                    <img style="width: 120px" src="/assets/images/expireCode.jpg">
                                </div>
                                <div style="width: 1px;height: 120px;margin-top: 40px;background-color: #eee;"></div>
                                <div style="font-size: 16px">
                                    <h5 style="text-align: center">联系方式</h5>
                                    <p><svg t="1645618105389" class="icon" viewBox="0 0 1228 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="12002" width="200" height="200"><path d="M653.824 256a51.2 51.2 0 0 0-54.784-54.784A61.44 61.44 0 0 0 532.992 256a61.952 61.952 0 0 0 66.048 56.32A51.2 51.2 0 0 0 653.824 256zM307.2 200.192A61.44 61.44 0 0 0 239.104 256 61.44 61.44 0 0 0 307.2 311.808 51.2 51.2 0 0 0 358.4 256a51.2 51.2 0 0 0-51.2-55.808z m921.6 450.56a307.2 307.2 0 0 0-102.4-223.232 402.944 402.944 0 0 0-241.152-106.496v-4.096C847.872 136.192 657.408 0 441.856 0 198.144 0 0 172.544 0 384.512a365.568 365.568 0 0 0 163.84 295.424l-36.864 113.152a22.016 22.016 0 0 0 30.208 26.624l140.288-71.168 16.384 3.584a602.112 602.112 0 0 0 128 15.872 201.216 201.216 0 0 0 51.2-8.192 379.392 379.392 0 0 0 358.4 220.672 515.584 515.584 0 0 0 122.88-19.968l107.52 59.392a23.552 23.552 0 0 0 10.24 3.072 18.432 18.432 0 0 0 13.824-5.12 22.016 22.016 0 0 0 7.168-23.552l-27.648-91.648a337.92 337.92 0 0 0 143.36-251.904zM480.256 711.68h-38.912a534.016 534.016 0 0 1-114.688-15.36l-23.04-4.608a29.696 29.696 0 0 0-13.824 0l-96.256 51.2 24.576-74.752a21.504 21.504 0 0 0-8.192-24.064 314.88 314.88 0 0 1-153.6-259.584C57.856 204.8 229.888 56.32 441.344 56.32c184.832 0 350.208 114.688 386.56 266.24-197.12 4.608-358.4 146.432-358.4 318.976a261.632 261.632 0 0 0 10.752 70.144z m557.056 156.16a19.968 19.968 0 0 0-7.168 22.528l15.36 51.2-65.024-35.84a23.552 23.552 0 0 0-10.24-3.072 12.288 12.288 0 0 0-5.12 0 476.672 476.672 0 0 1-114.688 19.968c-175.616 0-318.464-122.368-318.464-272.896s142.848-272.896 318.464-272.896 317.952 124.928 317.952 272.896a282.112 282.112 0 0 1-131.072 218.112z m-302.08-358.4a51.2 51.2 0 0 0-44.544 45.568 51.2 51.2 0 0 0 44.544 45.568 51.2 51.2 0 0 0 54.784-45.568 51.2 51.2 0 0 0-54.784-43.008z m230.4 0a45.568 45.568 0 0 0 0 91.136 51.2 51.2 0 0 0 54.784-45.568 51.2 51.2 0 0 0-54.784-43.008z" p-id="12003" fill="#2c2c2c"></path></svg>
                                        javabloger</p>
                                    <p><svg t="1645616433087" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5234" width="200" height="200"><path d="M876.557442 210.565089H144.379094c-44.805222 0-81.256563 36.450318-81.256564 81.256563v514.471659c0 44.805222 36.450318 81.256563 81.256564 81.256563h732.178348c44.805222 0 81.256563-36.450318 81.256563-81.256563v-514.471659c0-44.806246-36.450318-81.256563-81.256563-81.256563z m0 55.918473c4.118067 0 8.004735 0.993169 11.44499 2.741965l-377.534164 272.261346-377.53826-272.255203a25.159934 25.159934 0 0 1 11.450109-2.748108h732.177325z m0 565.147839H144.379094c-13.965796 0-25.33809-11.372294-25.338091-25.33809V328.15627l391.427265 282.272898 391.427264-282.282112v478.146255c0 13.965796-11.35796 25.33809-25.33809 25.33809z" fill="#2c2c2c" p-id="5235"></path></svg>
                                        huangyi@stonedt.com</p>
                                    <p><svg t="1645616485468" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="6129" width="200" height="200"><path d="M969.154011 895.670857 912.504869 954.112C869.423726 998.509714 809.483154 1023.963429 748.006583 1023.963429 721.748297 1023.963429 695.819154 1019.428571 671.023726 1010.505143 364.116297 899.876571 120.98944 645.668571 20.674011 330.459429-9.460846 235.739429 22.502583 131.510857 100.216869 71.168L149.734583 32.694857C177.236297 11.300571 210.150583-0.036571 244.89344-0.036571 303.992869-0.036571 357.53344 33.28 384.596297 86.893714L468.344869 252.672C476.280869 268.434286 480.486583 286.061714 480.486583 303.725714 480.486583 331.812571 470.210011 358.656 451.558583 379.392L434.114011 398.701714C416.779154 417.901714 413.012297 446.317714 424.751726 469.467429 453.350583 525.933714 497.784869 571.172571 553.263726 600.283429 575.608869 612.059429 604.354011 608.073143 622.786011 590.774857L641.803154 573.074286C674.827154 542.098286 726.356297 534.966857 766.255726 555.885714L929.071726 641.097143C981.771154 668.708571 1014.539154 723.2 1014.539154 783.396571 1014.539154 825.636571 998.447726 865.536 969.154011 895.670857ZM941.396297 768C941.396297 715.373714 721.967726 621.714286 721.967726 621.714286 691.174583 610.450286 648.824869 658.285714 648.824869 658.285714 572.536869 714.788571 518.81344 671.926857 502.539154 658.285714 454.923154 618.459429 390.191726 532.370286 356.25344 475.428571 330.872869 432.749714 341.734583 382.171429 356.25344 365.714286 356.25344 365.714286 405.40544 323.291429 392.824869 292.571429 354.46144 197.376 294.594011 56.393143 246.539154 73.142857 204.847726 86.893714 30.255726 144.786286 109.21344 356.388571 249.282011 731.465143 540.792869 882.468571 718.23744 950.820571 841.958583 959.926857 951.234011 803.730286 941.396297 768Z" p-id="6130" fill="#2c2c2c"></path></svg>
                                        13913853100</p>
                                    <p><svg t="1645618144633" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="13635" width="200" height="200"><path d="M559.2 66.3C274.5 37.1 36.9 274.7 66.1 559.4c20.3 197.8 200.6 378.1 398.4 398.4C749.2 987 986.8 749.4 957.6 464.7 937.3 266.9 757 86.6 559.2 66.3zM832 298.6l42.7 85.4c-57.2-10.8-73-10.3-128.1 0-32.6 8.6-49.6 31.9-57.6 47.9-4.1 8.2-6.1 17.3-5.7 26.5 1.1 27.2 0 99.9-43.4 117.7-19.2 7.9-42.7-19.1-42.7-42.7 3-134.5 63.8-160.8 80.4-169.4 17.4-9 69-44 69-44 37.4-19 85.4-21.4 85.4-21.4zM319.8 191.9l26.7 26.7c7.1 7.1 5.3 19.1-3.6 23.8-24.7 13.2-68.4 47.2-104.7 132.4-2.4 5.5-7.8 9.1-13.8 9.1h-54c0 0.1 3.6-112.6 149.4-192z m-4.4 648.9l-95.5-80c10.8-21.6 33.5-36.1 59.5-35.3 33.3 1.1 60.5 28.1 61.8 61.4 0.7 22.1-9.6 41.8-25.8 53.9z m196.4 55.4c-48.1 0-93.9-9.2-136.3-25.3 18.3-22.2 29.6-50.4 29.6-81.4 0-70.7-57.3-128.1-128.1-128.1-38.6 0-73.1 17.3-96.6 44.3-33.4-56.9-52.8-123-52.8-193.7 0-16 1.3-31.7 3.2-47.3L275 450.3c13.1-1.3 23.8-10.9 26.4-23.8 18.2-89 98.2-145.4 148.1-172.6 18.6-10.1 21.1-35.8 4.9-49.4l-66.9-55.7c39-13.4 80.7-20.9 124.3-20.9 104.8 0 199.7 42.1 269 110.1l-93 37.2S469.1 387.5 533.2 554.7c21.5 50.2 60.1 81 95.2 81 77.5 0 121.9-69 118.2-166.4 14.5-14.9 19-14.3 42.7-21.3 49.7-8.3 66 6.7 104.1 20.3 1.6 14.4 2.7 28.9 2.7 43.7C896 724.2 724 896.2 511.8 896.2z" fill="#2c2c2c" p-id="13636"></path></svg>
                                        <a href="https://www.stonedt.com" target="_blank" id="officialWebsite"><span style="color: blue;text-decoration: underline">www.stonedt.com</span></a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `
    $("body").append(html)
}
function close_lessDataPopup() {
    $("#exampleModal").remove()

    $.ajax({
        type: "post",
        url: "/popUp/closeContact?projectId="+monitor_projectid,
        dataType: 'json',
        contentType: "application/json;charset=utf-8",
        success: function () {},
    })
}


let articleData = null;
let obj = null
// 相似文章
function similarArticles(titlekeyword){
    var create =
        '<div class="shadebox" id="similarArticlesmodel">' +
        '    <div class="modal-dialog" role="document" style="max-width: 800px">' +
        '        <div class="modal-content">' +
        '            <i class="mdi mdi-close-circle-outline font-18 cursor-po" id="closethis" style="position: absolute;right: 8px;top: 6px;z-index: 1;"></i>' +
        '            <div class="modal-body" style="padding: 20px;">' +
        '                <div class="similar_articles" style="height: 80vh;overflow-y: auto;"></div>' +
        '                <div class="all-pagebox m-t-20">' +
        '                   <ul class="pagination float-right" id="similarArticlespage"></ul>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '    </div>' +
        '</div>'

    $("body").append(create)
    $("#closethis").click(function (param) {
        $("#similarArticlesmodel").remove()
    })

    obj = JSON.parse(JSON.stringify(articleData))
    obj.titlekeyword = titlekeyword
    similarArticlesData()
}
function similarArticlesData(){
    $.ajax({
        type: "post",
        url: window.location.origin+"/monitor/getSimilarArticle",
        dataType: 'json',
        data: JSON.stringify(obj),
        contentType: "application/json;charset=utf-8",
        beforeSend: function () {
            loading(".similar_articles")
        },
        success: function (res) {
            fuction_similarArticles(res)
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            } else {
                $(".similar_articles").html("");
                dataerror(".similar_articles");
            }
        }
    });
}
function fuction_similarArticles(res){
    if(res.code==200){
        let data = res.data.data
        fuction_pageHelper(res.data.currentPage,res.data.totalPage)
        let html = ""
        for (let i = 0; i < data.length; i++) {
            let publish_time = timeParse(data[i].publish_time);
            html+=`
            <div class="wb-content just-bet mb-3">
                <div class="monitor-right">
                    <div class="monitor-content-title">
                        <a href="${window.location.origin}/monitor/detail/${data[i].article_public_id}?groupid=${monitor_groupid}&projectid=${monitor_projectid}&publish_time=${data[i].publish_time}&relatedWord=${data[i].relatedWord.join('，')}" target="_blank" title="${data[i].title}" class="link font-bold" style="display:inline-block;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">
                            ${data[i].title}
                        </a>
                    </div>
                    <div class="monitor-content-con font-13 mb-2">
                        ${data[i].content}
                    </div>
                    <div class="like-comm font-13">
                        <img class="content-logo" style="margin-left: 4px;" src="${data[i].websitelogo}" onerror="javascript:this.src='/assets/images/default_source.png'" alt="">
                        <span class="m-r-10">${data[i].sourcewebsitename}</span>
                        <span>${publish_time}</span>
                    </div>
                </div>
            </div>
            `
        }

        $(".similar_articles").html(html)
    }

}

function fuction_pageHelper(currentPage, totalPages) {

    if (totalPages > 167) {
        totalPages = 167;
    }


    $("#similarArticlespage").bootstrapPaginator({
        bootstrapMajorVersion: 3, //版本
        currentPage: currentPage, //当前页数
        numberOfPages: 10, //每次显示页数
        totalPages: totalPages, //总页数
        shouldShowPage: true, //是否显示该按钮
        useBootstrapTooltip: false,
        onPageClicked: function (event, originalEvent, type, page) {
            if (page > 167) {
                page = 167;
            }
            obj.page = page
            similarArticlesData()
            // let seturl = "monitor?" + "projectid=" + monitor_projectid + "&groupid=" + monitor_groupid + "&page=" + page;
            // setUrl(seturl);
            //
            // let articleParam = new Object();
            // articleParam.type = "POST";
            // articleParam.url = ctxPath + "monitor/getarticle";
            // articleParam.contentType = 'application/json;charset=utf-8';
            // let searchkeyword = $("#searchkeyword").val();
            // let articleData = getArticleData(page, searchkeyword, monitor_projectid, monitor_groupid);
            // let similar = articleData.similar;
            // if (similar == 1) { // 合并
            //     let start = 30 * page - 30
            //     let end = start + 30
            //     if (end > article_public_idList.length) {
            //         end = article_public_idList.length
            //     }
            //
            //     let ids = "";
            //     for (let s = start; s < end; s++) {
            //         if (s == (article_public_idList.length - 1)) {
            //             ids += article_public_idList[s];
            //         } else {
            //             ids += article_public_idList[s] + ","
            //         }
            //     }
            //
            //     let totalPage = 1;
            //     if (article_public_idList.length % 30 == 0) {
            //         totalPage = article_public_idList.length / 30;
            //     } else {
            //         totalPage = article_public_idList.length / 30 + 1;
            //     }
            //     articleData.article_public_idstr = ids;
            //     articleData.totalCount = article_public_idList.length;
            //     articleData.totalPage = totalPage;
            // }
            // sendArticle(articleParam, JSON.stringify(articleData), installArticle);
            // currentPageByDetail = page;
        }
    });
}



/**
 * @author 获取页面的用户选择的条件
 * @date 2020/04/16
 * @description 分页
 */
function getArticleData(page, searchkeyword, currentProjectid, currentGroupid) {
    articleData = new Object();
    let times = $("input[name='start']").val();
    let timee = $("input[name='end']").val();
    let precise;
    let emotionalIndex = [];
    let eventIndex = [];
    let industryIndex = [];
    let province = [];
    let city = [];
    let organizationtype = [];
    let categorylabledata = [];
    let enterprisetypelist = [];
    let hightechtypelist = [];
    let policylableflag = [];
    let classify = [];
    let similar;
    let searchType;
    let matchingmode;
    let timeType;
    let datasource_type = [];/*数据品类*/

    /*2021.6.25修改*/
    /**
     * 作者和网站数据组装
     * */
    let sourceWebsite = $("#websitename").val();
    let author = $("#author").val();
    /*2021.6.25修改*/

    $('span[data-emotion]').each(function () {
        if ($(this).hasClass('badge-info')) {
            emotionalIndex.push($(this).data('emotion'));
        }
    });

    $('span[data-event]').each(function () {
        if ($(this).hasClass('badge-info')) {
            eventIndex.push($(this).data('event'));
        }
    });
    $('span[data-industry]').each(function () {
        if ($(this).hasClass('badge-info')) {
            industryIndex.push($(this).data('industry'));
        }
    });

    $('span[data-province]').each(function () {
        if ($(this).hasClass('badge-info')) {
            province.push($(this).data('province'));
        }
    });
    $('span[data-city]').each(function () {
        if ($(this).hasClass('badge-info')) {
            city.push($(this).data('city'));
        }
    });

    $('span[data-similar]').each(function () {
        if ($(this).hasClass('badge-info')) {
            similar = $(this).data('similar');
        }
    });


    $('span[data-sort]').each(function () {
        if ($(this).hasClass('badge-info')) {
            searchType = $(this).data('sort');
        }
    });

    $('span[data-matchs]').each(function () {
        if ($(this).hasClass('badge-info')) {
            matchingmode = $(this).data('matchs');
        }
    });


    $('span[data-time]').each(function () {
        if ($(this).hasClass('badge-info')) {
            timeType = $(this).data('time');
        }
    });

    $('span[data-precise]').each(function () {
        if ($(this).hasClass('badge-info')) {
            precise = $(this).data('precise');
        }
    });


    /**/


    //机构类型
    $("#organizationtype input[type='checkbox']:checked").each(function () {
        organizationtype.push($(this).val());
    });

    //文章分类
    $("#categorylabledata input[type='checkbox']:checked").each(function () {
        categorylabledata.push($(this).val());
    });

    //企业类型
    $("#enterprisetypelist input[type='checkbox']:checked").each(function () {
        enterprisetypelist.push($(this).val());
    });

    //高新技术企业
    $("#hightechtypelist input[type='checkbox']:checked").each(function () {
        hightechtypelist.push($(this).val());
    });

    //政策
    $("#policylableflag input[type='checkbox']:checked").each(function () {
        policylableflag.push($(this).val());
    });

    //数据来源
    $("#classify input[type='checkbox']:checked").each(function () {
        classify.push($(this).val());
    });

    /*数据品类*/
    $("#datasource_type input[type='checkbox']:checked").each(function () {
        datasource_type.push($(this).val());
    })


    articleData.times = times;
    articleData.timee = timee;
    articleData.page = page;
    articleData.searchType = searchType; // 排序
    articleData.similar = similar;
    articleData.matchingmode = matchingmode;
    articleData.emotionalIndex = emotionalIndex;
    articleData.province = province;
    articleData.city = city;
    articleData.eventIndex = eventIndex;
    articleData.industryIndex = industryIndex;
    articleData.searchkeyword = searchkeyword;
    articleData.projectid = currentProjectid;
    articleData.groupid = currentGroupid;
    articleData.group_id = currentProjectid;
    articleData.projectId = currentProjectid;
    articleData.timeType = timeType;
    articleData.precise = precise;
    articleData.organizationtype = organizationtype;
    articleData.categorylabledata = categorylabledata;
    articleData.enterprisetypelist = enterprisetypelist;
    articleData.hightechtypelist = hightechtypelist;
    articleData.policylableflag = policylableflag;
    articleData.classify = classify;
    articleData.datasource_type = datasource_type;/*数据品类*/

    /*2021.6.25修改*/
    /**
     * 加入作者和网站数据*/
    articleData.sourceWebsite = sourceWebsite;
    articleData.author = author;
    /*2021.6.25修改*/
    return articleData;
}


/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description 组装列表条件数据
 */
let eventIndex;/*涉及事件*/
let industryIndex;/*涉及行业*/
let province;/*涉及省份*/
let city;/*涉及城市*/
function installCondition(res) {
    let code = res.code;
    if (code == 200) {
        let data = res.data;
        let times = data.times;
        let timee = data.timee;
        let sort = data.sort;
        let similar = data.similar;
        let project_id = data.project_id;
        let precise = data.precise;
        let matchs = data.matchs;
        let emotionArray = JSON.parse(data.emotion);
        let group_name = res.group_name;
        let project_name = res.project_name;
        let slectedtime = data.timeType;

        eventIndex = data.eventIndex;/*涉及事件*/
        if (eventIndex == null){
            eventIndex="";
        }
        industryIndex = data.industryIndex;/*涉及行业*/
        if (industryIndex == null){
            industryIndex = "";
        }
        province = data.province;/*涉及省份*/
        if (province == null){
            province = "";
        }
        city = data.city;/*涉及城市*/
        if (city == null){
            city = "";
        }

        let datasource_type = data.datasource_type;/*数据品类*/


        if (null != data.classify && "" != data.classify) {
            var classify = JSON.parse(data.classify);//数据来源

            if (classify != null) {
                for (var i = 0; i < classify.length; i++) {
                    $("#classify input[value=" + classify[i] + "]").prop('checked', true);/*查到的数据选中*/
                }
            }
        }else {
            $("#classify input[type=checkbox]").prop('checked', false);
            $("#classify input[value=" + 0 + "]").prop('checked', true);
        }
        /*2021.6.25添加,作者和网站回显*/
        let websitename = data.websitename;
        $("#websitename").val(websitename);
        let author = data.author;
        $("#author").val(author);
        /*2021.6.25添加*/

        /*2021.6.28修改*/
        let organization = data.organization;/*涉及机构*/
        if (data.organization == null || "" == data.organization){
            $("#organizationtype input[value=0]").prop('checked', true);
        }else {
            organization = organization.split(',');
            for (let i = 0 ; i < organization.length ; i++)
            $("#organizationtype input[value="+organization[i]+"]").prop("checked" , true);
        }

        let categorylable = data.categorylable;/*文章分类*/
        if (categorylable == null || "" == categorylable){
            $("#categorylabledata input[value=0]").prop('checked', true);
        }else {
            categorylable = categorylable.split(',');
            for (let i = 0 ; i < categorylable.length ; i++)
                $("#categorylabledata input[value = "+categorylable[i]+"]").prop("checked" , true);
        }

        let enterprisetype = data.enterprisetype;/*涉及企业*/
        if (enterprisetype == null || "" == enterprisetype){
            $("#enterprisetypelist input[value=0]").prop('checked', true);
        }else {
            enterprisetype = enterprisetype.split(',');
            for (let i = 0 ; i < enterprisetype.length ; i++)
                $("#enterprisetypelist input[value = "+enterprisetype[i]+"]").prop("checked" , true);
        }

        let hightechtype = data.hightechtype;/*高科技型企业*/
        if (hightechtype == null || "" == hightechtype){
            $("#hightechtypelist input[value=0]").prop('checked', true);
        }else {
            hightechtype = hightechtype.split(',');
            for (let i = 0 ; i < hightechtype.length ; i++)
                $("#hightechtypelist input[value = "+hightechtype[i]+"]").prop("checked" , true);
        }

        let policylableflag = data.policylableflag;/*涉及政策*/
        if (policylableflag == null || "" == policylableflag){
            $("#policylableflag input[value=0]").prop('checked', true);
        }else {
            policylableflag = policylableflag.split(',');
            for (let i = 0 ; i < policylableflag.length ; i++)
                $("#policylableflag input[value = "+policylableflag[i]+"]").prop("checked" , true);
        }
        /*2021.6.28修改*/



        /*2021.6.29修改*/
        if (datasource_type == null || datasource_type == ""){
            $("#datasource_type input[value=0]").prop("checked" , true);
        }else {
            datasource_type = datasource_type.split(',');
            for (var i = 0 ; i < datasource_type.length ; i++){
            $("#datasource_type input[value="+datasource_type[i]+"]").prop("checked" , true);
                }
        }


        /**/

        $("#group_name").html(group_name);
        $("#project_name").html(project_name);

        $('span[data-emotion]').removeClass('badge-info');
        var emotion = JSON.parse(data.emotion);
        for (var i = 0; i < emotion.length; i++) {
            $('span[data-emotion=' + emotion[i] + ']').addClass('badge-info');
            $('span[data-emotion=' + emotion[i] + ']').removeClass('badge-light');
        }

        $('span[data-precise=' + precise + ']').addClass('badge-info');
        $('span[data-precise=' + precise + ']').removeClass('badge-light');
        $('span[data-precise=' + precise + ']').siblings().removeClass('badge-info');

        $('span[data-sort=' + sort + ']').addClass('badge-info');
        $('span[data-sort=' + sort + ']').removeClass('badge-light');
        $('span[data-sort=' + sort + ']').siblings().removeClass('badge-info');

        $('span[data-matchs=' + matchs + ']').addClass('badge-info');
        $('span[data-matchs=' + matchs + ']').removeClass('badge-light');
        $('span[data-matchs=' + matchs + ']').siblings().removeClass('badge-info');

        $('span[data-similar=' + similar + ']').addClass('badge-info');
        $('span[data-similar=' + similar + ']').removeClass('badge-light');
        $('span[data-similar=' + similar + ']').siblings().removeClass('badge-info');

        $('span[data-time=0]').removeClass('badge-info');
        $('span[data-time=1]').removeClass('badge-info');
        $('span[data-time=2]').removeClass('badge-info');
        $('span[data-time=3]').removeClass('badge-info');
        $('span[data-time=4]').removeClass('badge-info');
        $('span[data-time=5]').removeClass('badge-info');
        $('span[data-time=6]').removeClass('badge-info');
        $('span[data-time=7]').removeClass('badge-info');
        $('span[data-time=8]').removeClass('badge-info');
        if (slectedtime <= 3) {
            $(".inlineTimebox span[data-time=" + slectedtime + "]").removeClass("badge-light")
            $(".inlineTimebox span[data-time=" + slectedtime + "]").addClass("badge-info")
        } else if (slectedtime == 8) {
            $(".inlineTimebox span[data-time=0]").removeClass("badge-light")
            $(".inlineTimebox span[data-time=0]").addClass("badge-info")
            $(".inlineTimebox span[data-time=8]").removeClass("badge-light")
            $(".inlineTimebox span[data-time=8]").addClass("badge-info")
            $("#date-range input[name='start']").val(times.substring(0, 10).trim());
            $("#date-range input[name='end']").val(timee.substring(0, 10).trim());
            $("#time-box").show();
            $("#date-range").show();
        } else {
            $(".inlineTimebox span[data-time=0]").removeClass("badge-light")
            $(".inlineTimebox span[data-time=0]").addClass("badge-info")
            $(".inlineTimebox span[data-time=" + slectedtime + "]").removeClass("badge-light")
            $(".inlineTimebox span[data-time=" + slectedtime + "]").addClass("badge-info")
            $("#time-box").show()
            $("#date-range").hide();
            // $("#date-range").show()
        }

        // 条件绑定成功，获取文章列表数据
        let articleParam = new Object();
        articleParam.type = "POST";
        articleParam.url = ctxPath + "monitor/getarticle";
        articleParam.contentType = 'application/json;charset=utf-8';
        let searchkeyword = $("#searchkeyword").val();
        if (currentPage == null) {
            currentPage = 1;
        }
        let articleData = getArticleData(currentPage, searchkeyword, monitor_projectid, monitor_groupid);




        sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
        /*2021.7.2*/
        articleData.industryIndex = industryIndex.split(",");
        /*2021.7.2*/
        sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
        /*2021.7.2*/
        articleData.eventIndex = eventIndex.split(",");
        /*2021.7.2*/
        sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
        /*2021.7.2*/
        articleData.province = province.split(",");
        /*2021.7.2*/
        sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);
        /*2021.7.2*/
        articleData.city = city.split(",");
        /*2021.7.2*/
        sendArticle(articleParam, JSON.stringify(articleData), installArticle);

/*是否选中*/
        checkedShow("datasource_type" , "datasource_type_show" , "数据品类");
        checkedShow("classify" , "classify_show" , "数据来源");
        checkedShow("policylableflag" , "policylableflag_show" , "涉及政策");
        checkedShow("hightechtypelist" , "hightechtypelist_show" , "涉及高科技型企业");
        checkedShow("enterprisetypelist" , "enterprisetypelist_show" , "涉及企业");
        checkedShow("categorylabledata" , "categorylabledata_show" , "文章分类");
        checkedShow("organizationtype" , "organizationtype_show" , "涉及机构");


    } else {
        console.log("偏好设置返回异常！");
    }
}


/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description 分页
 */
function pageHelper(currentPage, totalPages) {
	
	if (totalPages > 167) {
		totalPages = 167;
    }

	
    $("#page").bootstrapPaginator({
        bootstrapMajorVersion: 3, //版本
        currentPage: currentPage, //当前页数
        numberOfPages: 10, //每次显示页数
        totalPages: totalPages, //总页数
        shouldShowPage: true, //是否显示该按钮
        useBootstrapTooltip: false,
        onPageClicked: function (event, originalEvent, type, page) {
            if (page > 167) {
                page = 167;
            }
            // let seturl = "monitor?" + "projectid=" + monitor_projectid + "&groupid=" + monitor_groupid + "&page=" + page;
            // setUrl(seturl);

            let articleParam = new Object();
            articleParam.type = "POST";
            articleParam.url = ctxPath + "monitor/getarticle";
            articleParam.contentType = 'application/json;charset=utf-8';
            let searchkeyword = $("#searchkeyword").val();
            let articleData = getArticleData(page, searchkeyword, monitor_projectid, monitor_groupid);
            let similar = articleData.similar;
            if (similar == 1) { // 合并
                let start = 30 * page - 30
                let end = start + 30
                if (end > article_public_idList.length) {
                    end = article_public_idList.length
                }

                let ids = "";
                for (let s = start; s < end; s++) {
                    if (s == (article_public_idList.length - 1)) {
                        ids += article_public_idList[s];
                    } else {
                        ids += article_public_idList[s] + ","
                    }
                }

                let totalPage = 1;
                if (article_public_idList.length % 30 == 0) {
                    totalPage = article_public_idList.length / 30;
                } else {
                    totalPage = article_public_idList.length / 30 + 1;
                }
                articleData.article_public_idstr = ids;
                articleData.totalCount = article_public_idList.length;
                articleData.totalPage = totalPage;
            }
            sendArticle(articleParam, JSON.stringify(articleData), installArticle);
            currentPageByDetail = page;
        }
    });
}

/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description 匹配方式 ppinline
 */
$("#ppshow").click(function (e) {
    $(this).hide();
    $("#pphide").show();
    $("#ppinline").show();
});

$("#pphide").click(function (e) {
    $(this).hide();
    $("#ppshow").show();
    $("#ppinline").hide();
});

/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description ppinline
 */
$("#ppinline span").click(function (e) {
    var active = "badge-info";
    var normal = "badge-light";
    var act = $(this).hasClass(active);
    if (act) {} else {
        $(this).siblings().removeClass(active);
        $(this).siblings().addClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
    }

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle)
    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);


});

/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description accurate 精准 筛选
 */
$("#sxshow").click(function (e) {
    $(this).hide();
    $("#sxhide").show();
    $("#sxinline").show();
});

$("#sxhide").click(function (e) {
    $(this).hide()
    $("#sxshow").show();
    $("#sxinline").hide();
});

$("#sxinline span").click(function (e) {
    var active = "badge-info";
    var normal = "badge-light";
    var act = $(this).hasClass(active);
    if (act) {} else {
        $(this).siblings().removeClass(active);
        $(this).siblings().addClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
    }

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);


});

/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description 相似文章 xsinline
 */
$("#xsshow").click(function (e) {
    $(this).hide();
    $("#xshide").show();
    $("#xsinline").show();
});

$("#xshide").click(function (e) {
    $(this).hide();
    $("#xsshow").show();
    $("#xsinline").hide();
});
//相似文章
$("#xsinline span").click(function (e) {
    var active = "badge-info";
    var normal = "badge-light";
    var act = $(this).hasClass(active);
    if (act) {} else {
        $(this).siblings().removeClass(active);
        $(this).siblings().addClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
    }

    /*2021.6.30修改*/
   /* $("#industrylist span,#eventlist span,#provincelist span,#citylist span").removeClass(active);
    $("#industrylist span,#eventlist span,#provincelist span,#citylist span").addClass(normal);*/

    //$("#industrylist span:nth-child(0),#eventlist span:nth-child(1),#provincelist span:nth-child(1),#citylist span:nth-child(1)").addClass(active);
    //  $("#industrylist > span:nth-child(1)").addClass(normal);

    /*2021.6.28修改*/
    /*$("#organizationtype input[type=checkbox],#enterprisetypelist input[type=checkbox],#categorylabledata input[type=checkbox],#hightechtypelist input[type=checkbox],#policylableflag input[type=checkbox]").prop("checked", false);*//*修改，删除数据来源取消选中*/
   /*$("#organizationtype  > ul > li:nth-child(1) > a > label > input[type=checkbox],#enterprisetypelist  > ul > li:nth-child(1) > a > label > input[type=checkbox],#categorylabledata  > ul > li:nth-child(1) > a > label > input[type=checkbox],#hightechtypelist  > ul > li:nth-child(1) > a > label > input[type=checkbox],#policylableflag  > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop("checked", true);*/
    /*修改，删除数据来源默认选中第一个*/



    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle)
    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);


});

/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description 数据导出 dcinline
 */
$("#dcshow").click(function (e) {
    $(this).hide();
    $("#dchide").show();
    $("#dcinline").show();
});

$("#dchide").click(function (e) {
    $(this).hide();
    $("#dcshow").show();
    $("#dcinline").hide();
});

/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description 全部导出
 */
$("#allexport").click(function (param) {
    swal({
        text: "最大导出5000条数据",
        showCancelButton: false,
        confirmButtonColor: "#36bea6",
        confirmButtonText: "导出",
    }).then(function (that) {
        if (that.value) {
            let searchkeyword = $("#searchkeyword").val();
            let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
            articleData.exportType = 1;
            exportData(articleData);
        }
    });
});

/**
 * @author huajiancheng
 * @date 2020/04/17
 * @description 导出数据
 */
function exportData(data) {
    let form = $('<form method="POST"  action="monitor/exportarticle">');
    let value = data[name];
    form.append($("<input type='hidden' name='data' value='" + JSON.stringify(data) + "'/>"));
    $('body').append(form);
    form.submit();
}

/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description 部分导出
 */
$("#selectexport").click(function (param) {
    $(".monitor-content").find(".monitor-check").show();
});

/**
 * @author liyoulin
 * @date 2020/04/16
 * @description 确认导出
 */

$("#sureexport").click(function (param) {
    let exportlist = [];
    $("#check1:checked").each(function () {
        exportlist.push($(this).attr("data-index"));
    });

    $("#check2:checked").each(function () {
        exportlist.push($(this).attr("data-index"));
    });

    $("#check3:checked").each(function () {
        exportlist.push($(this).attr("data-index"));
    });

    if (exportlist.length == 0) {
        swal({
            text: "请选择需要导出的数据",
            timer: 2000,
            showConfirmButton: false
        });
    } else {
        let exportlistLen = exportlist.length;
        let tips = "已选择" + exportlistLen + "条信息";
        swal({
            text: tips,
            showCancelButton: true,
            confirmButtonColor: "#36bea6",
            confirmButtonText: "确认导出",
            cancelButtonText: "取消"
        }).then(function (that) {
            if (that.value) {
                let searchkeyword = $("#searchkeyword").val();
                let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
                articleData.exportType = 2;
                articleData.exportlist = exportlist;
                exportData(articleData);
            }
        });
    }
});



/*2021.6.25修改*/
/**
 * 作者和网站
 * */

$("#washow").click(function (e) {
    $(this).hide();
    $("#wahide").show();
    $("#wainline").show();
});

$("#wahide ").click(function (e) {
    $(this).hide()
    $("#washow").show();
    $("#wainline").hide();
});
// 回车搜索
$("#wainline input").keydown(function (e) {
    if (e.keyCode == 13) {
        // 条件绑定成功，获取文章列表数据
        let articleParam = new Object();
        articleParam.type = "POST";
        articleParam.url = ctxPath + "monitor/getarticle";
        articleParam.contentType = 'application/json;charset=utf-8';

        let searchkeyword = $("#searchkeyword").val();
        let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
        sendArticle(articleParam, JSON.stringify(articleData), installArticle);
        sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
        sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
        sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
        sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

    }

});

/*2021.6.25修改*/

/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description inlineList
 */
$("#inlineList span").click(function (e) {
    var active = "badge-info";
    var normal = "badge-light";
    var act = $(this).hasClass(active);
    if (act) {} else {
        $(this).siblings().removeClass(active);
        $(this).siblings().addClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
    }

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
});

//time
$("#inlineTime span").click(function () {
    var active = "badge-info";
    var normal = "badge-light";
    var type = $(this).attr("data-time")
    var act = $(this).hasClass(active);
    var timeselect = 1
    if (type == 0) {
        $(this).removeClass(normal);
        $(this).addClass(active)
        $("#time-box").show()
        timeselect = type
    } else {
        for (var i = 0; i < 9; i++) {
            $(".inlineTimebox span[data-time=" + (i) + "]").removeClass(active)
            $(".inlineTimebox span[data-time=" + (i) + "]").addClass(normal)
        }
        if (type > 3) {
            $(".inlineTimebox span[data-time=0]").removeClass(normal);
            $(".inlineTimebox span[data-time=0]").addClass(active);
            if (type == 8) {
                $("#date-range").show()
            } else {
                $("#date-range").hide()
            }
        } else {
            $("#time-box").hide()
            $("#date-range").hide()
        }
        $(this).removeClass(normal);
        $(this).addClass(active);
        timeselect = type
        let articleParam = new Object();
        articleParam.type = "POST";
        articleParam.url = ctxPath + "monitor/getarticle";
        articleParam.contentType = 'application/json;charset=utf-8';
        let searchkeyword = $("#searchkeyword").val();
                        /*2021.6.30修改*/
        /*$("#industrylist span,#eventlist span,#provincelist span,#citylist span").removeClass(active);
        $("#industrylist span,#eventlist span,#provincelist span,#citylist span").addClass(normal);*/

        //$("#industrylist span:nth-child(0),#eventlist span:nth-child(1),#provincelist span:nth-child(1),#citylist span:nth-child(1)").addClass(active);
        //  $("#industrylist > span:nth-child(1)").addClass(normal);

        /*2021.6.28修改*/
        /*$("#organizationtype input[type=checkbox],#enterprisetypelist input[type=checkbox],#categorylabledata input[type=checkbox],#hightechtypelist input[type=checkbox],#policylableflag input[type=checkbox]").prop("checked", false);*//*修改，删除数据来源取消选中*/
        /*$("#organizationtype  > ul > li:nth-child(1) > a > label > input[type=checkbox],#enterprisetypelist  > ul > li:nth-child(1) > a > label > input[type=checkbox],#categorylabledata  > ul > li:nth-child(1) > a > label > input[type=checkbox],#hightechtypelist  > ul > li:nth-child(1) > a > label > input[type=checkbox],#policylableflag  > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop("checked", true);*/
        /*修改，删除数据来源默认选中第一个*/




        let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
        articleData.timeType == timeselect;
        if (timeselect == 8) {
            let myDate = new Date();
            var month = myDate.getMonth() + 1;
            if (month < 10) {
                month = '0' + month;
            }
            var day = myDate.getDate();
            if (day < 10) day = '0' + day;
            let times = myDate.getFullYear() + "-" + month + "-" + day;
            let timee = myDate.getFullYear() + "-" + month + "-" + day;
            articleData.times = times;
            articleData.timee = timee;
            $("#date-range input[name='start']").val(times.trim());
            $("#date-range input[name='end']").val(timee.trim());
        }


        sendArticle(articleParam, JSON.stringify(articleData), installArticle);
        sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
        sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
        sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
        sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);


    }
})
//时间框变化
$("#date-range input").change(function () {
    var times = $("#date-range input[name=start]").val();
    var timed = $("#date-range input[name=end]").val();

    if (times > timed) {
        showInfo("开始时间不能迟于结束时间");
    } else {
        if (times != "" && timed != "") {
            let articleParam = new Object();
            articleParam.type = "POST";
            articleParam.url = ctxPath + "monitor/getarticle";
            articleParam.contentType = 'application/json;charset=utf-8';
            let searchkeyword = $("#searchkeyword").val();
            let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
            articleData.timeType = 8
            sendArticle(articleParam, JSON.stringify(articleData), installArticle);
            sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
            sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
            sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
            sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);
        } else {
            showInfo("开始时间或结束时间不能为空");
        }
    }
})

/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description emotionList
 */
$("#qgshow").click(function (e) {
    $(this).hide();
    $("#qghide").show();
    $("#emotionList").show();
});

$("#qghide").click(function (e) {
    $(this).hide();
    $("#qgshow").show();
    $("#emotionList").hide();
});
$("#emotionList span").click(function (e) {
    var active = "badge-info";
    var normal = "badge-light";
    var act = $(this).hasClass(active);
    if (act) {
        $(this).removeClass(active);
        $(this).addClass(normal);
    } else {
        $(this).removeClass(normal);
        $(this).addClass(active);
    }

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle)



    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);


});


$("#eventlist").on('click', 'span', function (e) {
    var active = "badge-info";
    var normal = "badge-light";
    var eventdata = $(this).attr("data-event");
    var act = $(this).hasClass(active);
    if (eventdata == '0') {
        if (!act) {
            $("#eventlist span").removeClass(active);
            $("#eventlist span").removeClass(normal);
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#provincelist span,#citylist span").removeClass(active);
            $("#provincelist span,#citylist span").addClass(normal);
            $("#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
            /*2021.6.30修改*/
            eventIndex = "";
            province = "";
            city = "";
            /*2021.6.30修改*/
        } else {
            $("#eventlist span").removeClass(active);
            $("#eventlist span").removeClass(normal);
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#provincelist span,#citylist span").removeClass(active);
            $("#provincelist span,#citylist span").addClass(normal);
            $("#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);

        }
    } else {
        if (act) {/*取消选中*/
            $(this).removeClass(active);
            $(this).addClass(normal);
            $("#eventlist > span:nth-child(1)").removeClass(active);
            $("#eventlist > span:nth-child(1)").addClass(normal);
            $("#provincelist span,#citylist span").removeClass(active);
            $("#provincelist span,#citylist span").addClass(normal);
            $("#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);

            /*2021.6.30修改*/
            let eventIndexs = eventIndex.split(',');
            for (var i = 0; i < eventIndexs.length; i++){
                if (eventIndexs[i] == $(this).attr("data-event")){
                    eventIndexs.splice(i , 1);
                }
            }
            eventIndex = eventIndexs.toString();
            province = "";
            city = "";
            /*2021.6.30修改*/

            if (eventIndex == "" || eventIndex == null){
                $("#eventlist > span:nth-child(1)").addClass(active);
                $("#eventlist > span:nth-child(1)").removeClass(normal);
            }


        } else {
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#eventlist > span:nth-child(1)").removeClass(active);
            $("#eventlist > span:nth-child(1)").addClass(normal);
            $("#provincelist span,#citylist span").removeClass(active);
            $("#provincelist span,#citylist span").addClass(normal);
            $("#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
            /*2021.6.30修改*/
            if (eventIndex != "") {
                let eventIndexs = eventIndex.split(',');
                eventIndexs.push($(this).attr("data-event"));
                eventIndex = eventIndexs.toString();
            }else {
                eventIndex = $(this).attr("data-event");
            }
            province = "";
            city = "";
            /*2021.6.30修改*/
        }
    }
    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);




    /*2021.7.1 , 标签选中显示*/

    determineShow(eventIndex , "eventlist" , "eventlabledata" , "事件");
});

$("#eventlist1").on('click', 'span', function (e) {
    var active = "badge-info";
    var normal = "badge-light";
    var eventdata = $(this).attr("data-event");
    var act = $(this).hasClass(active);
    if (eventdata == '0') {
        if (!act) {
            $("#eventlist1 span").removeClass(active);
            $("#eventlist1 span").removeClass(normal);
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#provincelist span,#citylist span").removeClass(active);
            $("#provincelist span,#citylist span").addClass(normal);
            $("#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);

        } else {
            $("#eventlist1 span").removeClass(active);
            $("#eventlist1 span").removeClass(normal);
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#provincelist span,#citylist span").removeClass(active);
            $("#provincelist span,#citylist span").addClass(normal);
            $("#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);


        }
    } else {
        if (act) {
            $(this).removeClass(active);
            $(this).addClass(normal);
            $("#eventlist1 > span:nth-child(1)").removeClass(active);
            $("#eventlist1 > span:nth-child(1)").addClass(normal);
            $("#provincelist span,#citylist span").removeClass(active);
            $("#provincelist span,#citylist span").addClass(normal);
            $("#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
        } else {
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#eventlist1 > span:nth-child(1)").removeClass(active);
            $("#eventlist1 > span:nth-child(1)").addClass(normal);
            $("#provincelist span,#citylist span").removeClass(active);
            $("#provincelist span,#citylist span").addClass(normal);
            $("#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
        }
    }
    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle)
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);
});

$("#industrylist").on('click', 'span', function (e) {
    var active = "badge-info";
    var normal = "badge-light";

    var industrydata = $(this).attr("data-industry");
    var act = $(this).hasClass(active);
    if (industrydata == '0') {
        if (!act) {
            $("#industrylist span").removeClass(active);
            $("#industrylist span").addClass(normal);
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#eventlist span,#provincelist span,#citylist span").removeClass(active);
            $("#eventlist span,#provincelist span,#citylist span").addClass(normal);
            $("#eventlist > span:nth-child(1),#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
            /*2021.6.30修改*/
            industryIndex = '';
            eventIndex = '';
            province = '';
            city = '';
            /*2021.6.30修改*/
        } else {
            $("#industrylist span").removeClass(active);
            $("#industrylist span").removeClass(normal);
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#eventlist span,#provincelist span,#citylist span").removeClass(active);
            $("#eventlist span,#provincelist span,#citylist span").addClass(normal);
            $("#eventlist > span:nth-child(1),#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);

        }
    } else {
        if (act) {/*取消选中*/
            $(this).removeClass(active);
            $(this).addClass(normal);
            $("#industrylist > span:nth-child(1)").removeClass(active);
            $("#industrylist > span:nth-child(1)").addClass(normal);
            $("#eventlist span,#provincelist span,#citylist span").removeClass(active);
            $("#eventlist span,#provincelist span,#citylist span").addClass(normal);
            $("#eventlist > span:nth-child(1),#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
            /*2021.6.30修改*/
            let industryIndexs = industryIndex.split(',');
            for (var i = 0; i < industryIndexs.length; i++){
                if (industryIndexs[i] == $(this).attr('data-industry')){
                    industryIndexs.splice(i , 1);
                }
            }
            industryIndex = industryIndexs.toString();
            eventIndex = '';
            province = '';
            city = '';
            /*2021.6.30修改*/

            if (industryIndex == "" || industryIndex == null){
                $("#industrylist > span:nth-child(1)").addClass(active);
                $("#industrylist > span:nth-child(1)").removeClass(normal);
            }

        } else {/*选中*/
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#industrylist > span:nth-child(1)").removeClass(active);
            $("#industrylist > span:nth-child(1)").addClass(normal);
            $("#eventlist span,#provincelist span,#citylist span").removeClass(active);
            $("#eventlist span,#provincelist span,#citylist span").addClass(normal);
            $("#eventlist > span:nth-child(1),#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
            /*2021.6.30修改*/
            if (industryIndex != "") {
                let industryIndexs = industryIndex.split(',');
                industryIndexs.push($(this).attr('data-industry'));
                industryIndex = industryIndexs.toString();
            }else {
                industryIndex = $(this).attr('data-industry');
            }
            eventIndex = '';
            province = '';
            city = '';
            /*2021.6.30修改*/
        }
    }

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

    determineShow(industryIndex , "industrylist" , "industrylabledata" , "行业");

});

function determineShow(data , id , showId , name){
    var active = "badge-info";
    var normal = "badge-light";

    /*2021.7.1 , 标签选中显示*/
    if (data != "" && data != null) {
        $("#"+showId).show();
        $("#"+showId).html(name+"：" + data);
    }

    //2021.7.1,没有选中则选全部
    if(data == "" || data == null){
        $("#"+id+" > span:nth-child(1)").add(active);
        $("#"+id+" > span:nth-child(1)").removeClass(normal);
        $("#"+showId).hide();
    }
//关掉显示
    if(eventIndex == "" && industryIndex == "" && province == "" && city == ""){
        $("#iepcShow").hide()
    }else{
        $("#iepcShow").show()
    }
}

$("#industrylist1").on('click', 'span', function (e) {
    var active = "badge-info";
    var normal = "badge-light";

    var industrydata = $(this).attr("data-industry");
    var act = $(this).hasClass(active);
    if (industrydata == '0') {
        if (!act) {
            $("#industrylist1 span").removeClass(active);
            $("#industrylist1 span").addClass(normal);
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#eventlist span,#provincelist span,#citylist span").removeClass(active);
            $("#eventlist span,#provincelist span,#citylist span").addClass(normal);
            $("#eventlist > span:nth-child(1),#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
        } else {
            $("#industrylist1 span").removeClass(active);
            $("#industrylist1 span").removeClass(normal);
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#eventlist span,#provincelist span,#citylist span").removeClass(active);
            $("#eventlist span,#provincelist span,#citylist span").addClass(normal);
            $("#eventlist > span:nth-child(1),#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
        }
    } else {
        if (act) {
            $(this).removeClass(active);
            $(this).addClass(normal);
            $("#industrylist1 > span:nth-child(1)").removeClass(active);
            $("#industrylist1 > span:nth-child(1)").addClass(normal);
            $("#eventlist span,#provincelist span,#citylist span").removeClass(active);
            $("#eventlist span,#provincelist span,#citylist span").addClass(normal);
            $("#eventlist > span:nth-child(1),#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);
        } else {
            $(this).removeClass(normal);
            $(this).addClass(active);
            $("#industrylist1 > span:nth-child(1)").removeClass(active);
            $("#industrylist1 > span:nth-child(1)").addClass(normal);
            $("#eventlist span,#provincelist span,#citylist span").removeClass(active);
            $("#eventlist span,#provincelist span,#citylist span").addClass(normal);
            $("#eventlist > span:nth-child(1),#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);

        }
    }

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);


});
$("#provincelist").on('click', 'span', function (e) {
    var active = "badge-info";
    var normal = "badge-light";

    var industrydata = $(this).attr("data-province");
    var act = $(this).hasClass(active);
    if (!act) {
        $("#provincelist span").removeClass(active);
        $("#provincelist span").addClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
        $("#citylist span").removeClass(active);
        $("#citylist span").addClass(normal);
        /*2021.6.30修改*/
        city = "";
        province = $(this).attr("data-province");
        if ($(this).attr("data-province") == "0"){
            province = '';
        }
        /*2021.6.30修改*/
    } else {
        $("#provincelist span").removeClass(active);
        $("#provincelist span").removeClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
        $("#citylist span").removeClass(active);
        $("#citylist span").addClass(normal);

        /*2021.6.30修改*/
        city = "";
        province = $(this).attr("data-province");
        if ($(this).attr("data-province") == "0"){
            province = '';
        }
        /*2021.6.30修改*/
    }

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';
    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

    //2021.7.1

    determineCityAndprovincelistprovincelistShow(province , "provincelabledata" , "省份");
});


$("#provincelist1").on('click', 'span', function (e) {
    var active = "badge-info";
    var normal = "badge-light";

    var industrydata = $(this).attr("data-province");
    var act = $(this).hasClass(active);
    if (!act) {
        $("#provincelist1 span").removeClass(active);
        $("#provincelist1 span").addClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
        $("#citylist span").removeClass(active);
        $("#citylist span").addClass(normal);

    } else {
        $("#provincelist1 span").removeClass(active);
        $("#provincelist1 span").removeClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
        $("#citylist span").removeClass(active);
        $("#citylist span").addClass(normal);
    }

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';
    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);



});


//$("#organizationtype").on('change','span',function(e){
//	
//	var checks = "";
//    $("input[name='checkbox']").each(function(){
//        if($(this).attr("checked") == true){
//            checks += $(this).val() + "|";            //动态拼取选中的checkbox的值，用“|”符号分隔
//        }
//    })
//    alert(checks);
//})	
//机构类型
$("#organizationtype input[type=checkbox]").change(function () {
    if ($(this).is(':checked')) {
        if ($(this).val() == '0') {
            $("#organizationtype input[type=checkbox]").prop('checked', false);

            $(this).prop("checked", true);
        } else {
            $("#organizationtype > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $(this).prop("checked", true);
        }
    } else {
        let orgtypelist = [];
        //alert("未选中:"+$(this).val())
        if ($(this).val() == '0') {
            $("#organizationtype input[type=checkbox]").prop('checked', false);
            $(this).prop('checked', false);
            $("#organizationtype input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#organizationtype > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        } else {
            $(this).prop('checked', false);
            $("#organizationtype > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $("#organizationtype input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#organizationtype > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        }

    }


    //加载文章

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();

    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    sendArticle(articleParam, JSON.stringify(articleData), installArticle);


    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

    //2021.7.1,是否变为蓝色
    checkedShow("organizationtype" , "organizationtype_show" , "涉及机构");
});


//文章类型
$("#categorylabledata input[type=checkbox]").change(function () {
    if ($(this).is(':checked')) {
        if ($(this).val() == '0') {
            $("#categorylabledata input[type=checkbox]").prop('checked', false);

            $(this).prop("checked", true);
        } else {
            $("#categorylabledata > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $(this).prop("checked", true);
        }
    } else {
        let orgtypelist = [];
        //alert("未选中:"+$(this).val())
        if ($(this).val() == '0') {
            $("#categorylabledata input[type=checkbox]").prop('checked', false);
            $(this).prop('checked', false);
            $("#categorylabledata input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#categorylabledata > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        } else {
            $(this).prop('checked', false);
            $("#categorylabledata > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $("#categorylabledata input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#categorylabledata > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        }

    }

    //加载文章

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();

    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    sendArticle(articleParam, JSON.stringify(articleData), installArticle);


    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

    //2021.7.1,是否变为蓝色
    checkedShow("categorylabledata" , "categorylabledata_show" , "文章分类");
});

//企业类型
$("#enterprisetypelist input[type=checkbox]").change(function () {
    if ($(this).is(':checked')) {
        if ($(this).val() == '0') {
            $("#enterprisetypelist input[type=checkbox]").prop('checked', false);

            $(this).prop("checked", true);
        } else {
            $("#enterprisetypelist > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $(this).prop("checked", true);
        }
    } else {
        let orgtypelist = [];
        //alert("未选中:"+$(this).val())
        if ($(this).val() == '0') {
            $("#enterprisetypelist input[type=checkbox]").prop('checked', false);
            $(this).prop('checked', false);
            $("#enterprisetypelist input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#enterprisetypelist > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        } else {
            $(this).prop('checked', false);
            $("#enterprisetypelist > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $("#enterprisetypelist input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#enterprisetypelist > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        }

    }

    //加载文章

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();

    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    sendArticle(articleParam, JSON.stringify(articleData), installArticle);


    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

    //2021.7.1,是否变为蓝色
    checkedShow("enterprisetypelist" , "enterprisetypelist_show" , "涉及企业");

});

//企业类型
$("#hightechtypelist input[type=checkbox]").change(function () {
    if ($(this).is(':checked')) {
        if ($(this).val() == '0') {
            $("#hightechtypelist input[type=checkbox]").prop('checked', false);

            $(this).prop("checked", true);
        } else {
            $("#hightechtypelist > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $(this).prop("checked", true);
        }
    } else {
        let orgtypelist = [];
        //alert("未选中:"+$(this).val())
        if ($(this).val() == '0') {
            $("#hightechtypelist input[type=checkbox]").prop('checked', false);
            $(this).prop('checked', false);
            $("#hightechtypelist input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#hightechtypelist > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        } else {
            $(this).prop('checked', false);
            $("#hightechtypelist > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $("#hightechtypelist input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#hightechtypelist > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        }

    }

    //加载文章

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();

    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    sendArticle(articleParam, JSON.stringify(articleData), installArticle);


    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

    //2021.7.1,是否变为蓝色
    checkedShow("hightechtypelist" , "hightechtypelist_show" , "涉及高科技型企业");
});

//政策
$("#policylableflag input[type=checkbox]").change(function () {
    if ($(this).is(':checked')) {
        if ($(this).val() == '0') {
            $("#policylableflag input[type=checkbox]").prop('checked', false);

            $(this).prop("checked", true);
        } else {
            $("#policylableflag > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $(this).prop("checked", true);
        }
    } else {
        let orgtypelist = [];
        //alert("未选中:"+$(this).val())
        if ($(this).val() == '0') {
            $("#policylableflag input[type=checkbox]").prop('checked', false);
            $(this).prop('checked', false);
            $("#policylableflag input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#policylableflag > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        } else {
            $(this).prop('checked', false);
            $("#policylableflag > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $("#policylableflag input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#policylableflag > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        }

    }

    //加载文章

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();

    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    sendArticle(articleParam, JSON.stringify(articleData), installArticle);


    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

//2021.7.1,是否变为蓝色
    checkedShow("policylableflag" , "policylableflag_show" , "涉及政策");

});

//数据来源
$("#classify input[type=checkbox]").change(function () {
    if ($(this).is(':checked')) {
        if ($(this).val() == '0') {
            $("#classify input[type=checkbox]").prop('checked', false);

            $(this).prop("checked", true);
        } else {
            $("#classify > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $(this).prop("checked", true);
        }
    } else {
        let orgtypelist = [];
        //alert("未选中:"+$(this).val())
        if ($(this).val() == '0') {
            $("#classify input[type=checkbox]").prop('checked', false);
            $(this).prop('checked', false);
            $("#classify input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#classify > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        } else {
            $(this).prop('checked', false);
            $("#classify > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $("#classify input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#classify > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        }

    }

    //加载文章

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();

    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    sendArticle(articleParam, JSON.stringify(articleData), installArticle);


    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

    //2021.7.1,是否变为蓝色
    checkedShow("classify" , "classify_show" , "数据来源");

});


/*2021.6.29修改*/
//数据品类
$("#datasource_type input[type=checkbox]").change(function () {
    if ($(this).is(':checked')) {
        if ($(this).val() == '0') {
            $("#datasource_type input[type=checkbox]").prop('checked', false);

            $(this).prop("checked", true);
        } else {
            $("#datasource_type > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $(this).prop("checked", true);
        }
    } else {
        let orgtypelist = [];
        //alert("未选中:"+$(this).val())
        if ($(this).val() == '0') {
            $("#datasource_type input[type=checkbox]").prop('checked', false);
            $(this).prop('checked', false);
            $("#datasource_type input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#datasource_type > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        } else {
            $(this).prop('checked', false);
            $("#datasource_type > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', false);
            $("#datasource_type input[type='checkbox']:checked").each(function () {
                orgtypelist.push($(this).val());
            });
            if (orgtypelist.length == 0) {
                $("#datasource_type > ul > li:nth-child(1) > a > label > input[type=checkbox]").prop('checked', true);
            }
        }

    }

    //加载文章

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();

    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    sendArticle(articleParam, JSON.stringify(articleData), installArticle);


    sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);

//2021.7.1,是否变为蓝色
    checkedShow("datasource_type" , "datasource_type_show" , "数据品类");


});
/*2021.6.29修改*/




$("#citylist").on('click', 'span', function (e) {
    var active = "badge-info";
    var normal = "badge-light";

    var industrydata = $(this).attr("data-city");
    var act = $(this).hasClass(active);
    if (!act) {
        $("#citylist span").removeClass(active);
        $("#citylist span").addClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
        /*2021.6.30修改*/
        city = $(this).attr("data-city");
        if ($(this).attr("data-city") == "0"){
            city = "";
        }
        /*2021.6.30修改*/

    } else {
        $("#citylist span").removeClass(active);
        $("#citylist span").removeClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
        /*2021.6.30修改*/
        city = $(this).attr("data-city");
        if ($(this).attr("data-city") == "0"){
            city = "";
        }
        /*2021.6.30修改*/
    }


    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    //sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);


    //2021.7.1,显示是否展示
    if (city != "" && city != null){
        $("#citylabledata").html("城市：" + city);
    }else{
        $("#citylabledata").hide();
    }
    determineCityAndprovincelistprovincelistShow(city , "citylabledata" , "城市")
});

function determineCityAndprovincelistprovincelistShow(data , showId , name){
    //2021.7.1,显示是否展示
    if (data != "" && data != null){
        $("#"+showId).show();
        $("#"+showId).html(name+ "：" + data);
    }else{
        $("#"+showId).hide();
    }

    if(eventIndex == "" && industryIndex == "" && province == "" && city == ""){
        $("#iepcShow").hide()
    }else{
        $("#iepcShow").show()
    }
}


$("#citylist1").on('click', 'span', function (e) {
    var active = "badge-info";
    var normal = "badge-light";

    var industrydata = $(this).attr("data-city");
    var act = $(this).hasClass(active);
    if (!act) {
        $("#citylist1 span").removeClass(active);
        $("#citylist1 span").addClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);


    } else {
        $("#citylist1 span").removeClass(active);
        $("#citylist1 span").removeClass(normal);
        $(this).removeClass(normal);
        $(this).addClass(active);
    }


    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    //sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);



});





//$("body #eventlist").$("span").click(function (e) {
//	//debugger;
//    var active = "badge-info";
//    var normal = "badge-light";
//    var act = $(this).hasClass(active);
//    if (act) {
//        $(this).removeClass(active);
//        $(this).addClass(normal);
//    } else {
//        $(this).removeClass(normal);
//        $(this).addClass(active);
//    }
//
////    let articleParam = new Object();
////    articleParam.type = "POST";
////    articleParam.url = ctxPath + "monitor/getarticle";
////    articleParam.contentType = 'application/json;charset=utf-8';
////
////    let searchkeyword = $("#searchkeyword").val();
////    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
////    sendArticle(articleParam, JSON.stringify(articleData), installArticle)
//});









/**
 * @author huajiancheng
 * @date 2020/04/16
 * @description emotionList
 */
$("#catgoryshow").click(function (e) {
    $(this).hide();
    $("#catgoryhide").show();
    $("#catgoryList").show();
});
$("#catgoryhide").click(function (e) {
    $(this).hide();
    $("#catgoryshow").show();
    $("#catgoryList").hide();
});
//$("#emotionList span").click(function (e) {
//    var active = "badge-info";
//    var normal = "badge-light";
//    var act = $(this).hasClass(active);
//    if (act) {
//        $(this).removeClass(active);
//        $(this).addClass(normal);
//    } else {
//        $(this).removeClass(normal);
//        $(this).addClass(active);
//    }
//
//    let articleParam = new Object();
//    articleParam.type = "POST";
//    articleParam.url = ctxPath + "monitor/getarticle";
//    articleParam.contentType = 'application/json;charset=utf-8';
//
//    let searchkeyword = $("#searchkeyword").val();
//    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
//    sendArticle(articleParam, JSON.stringify(articleData), installArticle)
//});



/**
 * @author lyoulin
 * @date 2020/04/16
 * @description rankList
 */
$("#rankList span").click(function (e) {
    var active = "rank-active";
    var act = $(this).hasClass(active);
    if (act) {} else {
        $(this).siblings().removeClass(active);
        $(this).addClass(active);
    }
});

/**
 * @author liyoulin
 * @date 2020/04/16
 * @description  Date Picker  时间控件
 */

$('#date-range').datepicker({
    language: 'zh-CN',
    format: "yyyy-mm-dd",
    orientation: "bottom auto",
    toggleActive: true,
    keyboardNavigation: true,
    enableOnReadonly: false,
    todayHighlight: true,
    endDate: getnow(),
    autoclose: true,
});

function getnow() {
    var now = new Date();
    var nowday = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate();
    return nowday
}

/**
 * @author liyoulin
 * @date 2020/04/16
 * @description  查看大图  Custom Image wb-left-imgbox
 */
$(document).on('click', ".wb-left-imgbox", function () {
    var url = $(this).children().attr("src");
    swal({
        title: "",
        text: " ",
        imageUrl: url
    });
    $(".swal2-actions").hide();
    $(".swal2-popup").click(function (e) {
        $(".swal2-confirm").click();
    });
});

$(document).on('click', '.img-box', function (e) {
    var url = $(this).children().attr("src")
    swal({
        title: "",
        text: " ",
        imageUrl: url
    });
    $(".swal2-actions").hide();
    $(".swal2-popup").click(function (e) {
        $(".swal2-confirm").click();
    });
});


// 回车搜索
$('#searchkeyword').keydown(function (e) {
    if (e.keyCode == 13) {
        // 条件绑定成功，获取文章列表数据
        let articleParam = new Object();
        articleParam.type = "POST";
        articleParam.url = ctxPath + "monitor/getarticle";
        articleParam.contentType = 'application/json;charset=utf-8';
        let searchkeyword = $("#searchkeyword").val();
        let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
        sendArticle(articleParam, JSON.stringify(articleData), installArticle)

        sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
        sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
        sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
        sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);
    }
});

/**
 * @author huajiancheng
 * @date 2020/04/15
 * @description 提示信息框
 * @param message 提示的信息
 */
function showInfo(message) {
    $.blockUI({
        message: message,
        fadeIn: 700,
        fadeOut: 700,
        timeout: 3000,
        showOverlay: false,
        centerY: false,
        css: {
            width: '250px',
            top: '20px',
            left: '',
            right: '20px',
            border: 'none',
            padding: '15px 5px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 0.9,
            color: '#fff'
        }
    });
}

/**
 * @author huajiancheng
 * @date 2020/04/20
 * @description json转url地址
 * @param message json对象
 */
function jsonChangeUrl(data) {
    try {
        var tempArr = [];
        for (var i in data) {
            var key = encodeURIComponent(i);
            var value = encodeURIComponent(data[i]);
            tempArr.push(key + '=' + value);
        }
        var urlParamsStr = tempArr.join('&');
        return urlParamsStr;
    } catch (err) {
        return '';
    }
}


/**
 * @author huajiancheng
 * @date 2020/05/06
 * @description 跳转详情
 * @param message
 */
// function toDetail(article_public_id, g_id, p_id) {
//     let searchword = $("#searchkeyword").val();
// let article_public_ids = '';
// let dataArray = new Array();
// $('div[data-articleid]').each(function () {
//     // article_public_ids += $(this).attr("data-articleid") + ",";
//     let articleid = $(this).attr("data-articleid");
//     let articletitle = $(this).attr("data-articletitle");
//     articletitle = articletitle.replace(/<\/?.+?>/g,"").replace(/ /g,"");
//     let dataJson = new Object();
//     dataJson.id = articleid;
//     dataJson.title = articletitle;
//     dataArray.push(dataJson);
// });
// window.location.href = ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + g_id + '&projectid=' + p_id + '&articleids=' + encodeURI(JSON.stringify(dataArray));
// window.location.href = ctxPath + 'monitor/detail/' + article_public_id + '?groupid=' + g_id + '&projectid=' + p_id + '&page=' + currentPageByDetail + "&searchkeyword=" + searchword;
// }



// 获取用户搜索的词
function getSearchWordById() {
    $.ajax({
        url: ctxPath + 'fullsearch/search',
        type: 'post',
        dataType: 'json',
        data: {},
        success: function (res) {
            installSearchWord(res);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });
}



/**
 * 组装数据
 * @param res
 */
function installSearchWord(res) {
    let code = res.code;
    if (code == 200) {
        let data = res.data;
        let StrAll = '';
        let str1 = '';
        let str2 = '';
        for (let i = 0; i < data.length; i++) {
            let dataJson = data[i];
            let search_word = dataJson.search_word;
            let index = i % 2;

            if (index == 0) {
                str1 += '<li><span class="v-hot">' + (i + 1) + '</span> <a href=""><span>' + search_word + '</span></a></li>';
            } else {
                str2 += '<li><span class="v-hot">' + (i + 1) + '</span> <a href=""><span>' + search_word + '</span></a></li>';
            }
        }
        let str3 = '<ul class="timeline timeline-left hot-content-list" style="padding-left: 200px;">' + str1 + '</ul>';
        let str4 = '<ul class="timeline timeline-left hot-content-list" style="padding-left: 100px;">' + str2 + '</ul>';
        StrAll = str3 + str4;
        $("#searchwords").html(StrAll);
    }
}

/**
 * 收藏功能
 * @returns
 */
$(document).on('click', '.datafavorite', function () {
    let id = $(this).data('type');
    let projectid = monitor_projectid;
    let groupid = monitor_groupid;

    $.ajax({
        url: ctxPath + 'datamonitor/addfavoritedata',
        type: 'post',
        dataType: 'json',
        data: {
            id: id,
            projectid: projectid,
            groupid: groupid
        },
        success: function (res) {
            let status = res.status;
            if (status == 200) {
                $.blockUI({
                    message: "收藏成功",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            }

        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });


});


/**
 * 发送、移动
 * @returns
 */
$(document).on('click', '.sending', function () {
    let id = $(this).data('type');
    let projectid = monitor_projectid;
    let groupid = monitor_groupid;

    $.ajax({
        url: ctxPath + 'datamonitor/sending',
        type: 'post',
        dataType: 'json',
        data: {
            id: id,
            projectid: projectid,
            groupid: groupid
        },
        success: function (res) {
            let status = res.status;
            if (status == 200) {
                $.blockUI({
                    message: "预警方案发送成功",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            } else if (status == 500) {
                $.blockUI({
                    message: "预警方案发送失败，请稍后尝试！",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            }

        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });

});

//情感标记

$(document).on('click', '.emotional_markers a', function () {
    let id = $(this).data('type');
    let flag = $(this).data('flag');
    let publish_time = $(this).data('time');
    $.ajax({
        url: ctxPath + 'datamonitor/updateemtion',
        type: 'post',
        dataType: 'json',
        data: {
            id: id,
            flag: flag,
            publish_time: publish_time
        },
        success: function (res) {
            if (res.status == 200) {
                $.blockUI({
                    message: "情感标记成功",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });
    //	$.ajax({
    //		url:ctxPath+'monitor/updateEmotion',
    //		type:'post',
    //		data:{
    //			article_public_id:id,
    //			emotion:flag
    //		},
    //		success:function(res){
    //			$.blockUI({
    //	            message: "情感标记成功",
    //	            fadeIn: 700,
    //	            fadeOut: 700,
    //	            timeout: 3000,
    //	            showOverlay: false,
    //	            centerY: false,
    //	            css: {
    //	                width: '250px',
    //	                top: '20px',
    //	                left: '',
    //	                right: '20px',
    //	                border: 'none',
    //	                padding: '15px 5px',
    //	                backgroundColor: '#000',
    //	                '-webkit-border-radius': '10px',
    //	                '-moz-border-radius': '10px',
    //	                opacity: 0.9,
    //	                color: '#fff'
    //	            }
    //	        });
    //			let articleParam = new Object();
    //			articleParam.type = "POST";
    //			articleParam.url = ctxPath + "monitor/getarticle";
    //			articleParam.contentType = 'application/json;charset=utf-8';
    //			let searchkeyword = $("#searchkeyword").val();
    //			let articleData = getArticleData(1, searchkeyword,
    //					monitor_projectid, monitor_groupid);
    //			sendArticle(articleParam, JSON.stringify(articleData),
    //					installArticle)
    //		},
    //		error:function(res){
    //			$.blockUI({
    //	            message: "情感标记失败",
    //	            fadeIn: 700,
    //	            fadeOut: 700,
    //	            timeout: 3000,
    //	            showOverlay: false,
    //	            centerY: false,
    //	            css: {
    //	                width: '250px',
    //	                top: '20px',
    //	                left: '',
    //	                right: '20px',
    //	                border: 'none',
    //	                padding: '15px 5px',
    //	                backgroundColor: '#000',
    //	                '-webkit-border-radius': '10px',
    //	                '-moz-border-radius': '10px',
    //	                opacity: 0.9,
    //	                color: '#fff'
    //	            }
    //	        });
    //		}
    //	})
});
//已读、未读标记
$(document).on('click', '.isread', function () {
    let id = $(this).data('type');
    let flag = $(this).data('flag');
    $.ajax({
        url: ctxPath + 'datamonitor/isread',
        type: 'post',
        dataType: 'json',
        data: {
            id: id,
            flag: flag
        },
        success: function (res) {
            let status = res.status;
            if (status == 200) {
                $.blockUI({
                    message: "已读标记成功",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            } else if (status == 500) {
                $.blockUI({
                    message: "已标记",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            } else if (status == 300) {
                $.blockUI({
                    message: "取消已读标记",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            } else if (status == 400) {
                $.blockUI({
                    message: "取消已读标记失败，请稍后尝试！",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            }

        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });




});

//删除信息
$(document).on('click', '.deletedata', function () {
    let id = $(this).data('type');
    let publish_time = $(this).data('time');
    let flag = $(this).data('flag');
    $.ajax({
        url: ctxPath + 'datamonitor/deletedata',
        type: 'post',
        dataType: 'json',
        data: {
            id: id,
            flag: flag,
            publish_time: publish_time
        },
        success: function (res) {
            let status = res.status;
            if (status == 200) {
                $.blockUI({
                    message: "删除信息成功",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            }

        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });




});


$(document).on('click', '.deletedata', function () {
    let flag = $(this).data('flag');
    $.ajax({
        url: ctxPath + 'datamonitor/deletedata',
        type: 'post',
        dataType: 'json',
        data: {
            id: id,
            flag: flag
        },
        success: function (res) {
            let status = res.status;
            if (status == 200) {
                $.blockUI({
                    message: "删除信息成功",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            }

        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });




});



//地址拷贝
$(document).on('click', '.copyurl', function () {
    let flag = $(this).data('flag');
    copyText(flag);
    $.blockUI({
        message: "拷贝地址成功",
        fadeIn: 700,
        fadeOut: 700,
        timeout: 3000,
        showOverlay: false,
        centerY: false,
        css: {
            width: '250px',
            top: '20px',
            left: '',
            right: '20px',
            border: 'none',
            padding: '15px 5px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 0.9,
            color: '#fff'
        }
    });

});


//文章拷贝	
$(document).on('click', '.copy-link-custom', function () {
    let flag = $(this).data('type');
    $.ajax({
        url: ctxPath + 'datamonitor/copytext',
        type: 'post',
        dataType: 'json',
        data: {
            id: flag,
            projectId: monitor_projectid
        },
        success: function (res) {
            let status = res.status;
            let text = res.result;
            if (status == 200) {
                copyText(text);
                $.blockUI({
                    message: "文章拷贝成功",
                    fadeIn: 700,
                    fadeOut: 700,
                    timeout: 3000,
                    showOverlay: false,
                    centerY: false,
                    css: {
                        width: '250px',
                        top: '20px',
                        left: '',
                        right: '20px',
                        border: 'none',
                        padding: '15px 5px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: 0.9,
                        color: '#fff'
                    }
                });
            }

        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = ctxPath + "login";
            }
        }
    });


});



function copyText(text) {
    let inputNode = document.createElement('input')  // 创建input
    inputNode.value = text // 赋值给 input 值
    document.body.appendChild(inputNode) // 插入进去
    inputNode.select() // 选择对象
    document.execCommand('Copy') // 原生调用执行浏览器复制命令
    inputNode.className = 'oInput'
    inputNode.style.display = 'none' // 隐藏
    inputNode.remove() // 删除自身

    // var textarea = document.createElement("textarea");
    // var currentFocus = document.activeElement;
    // let state = false;
    // document.body.appendChild(textarea);
    // textarea.value = text;
    // textarea.focus();
    // if (textarea.setSelectionRange) {
    //     textarea.setSelectionRange(0, textarea.value.length);
    // } else {
    //     textarea.select();
    // }
    // try {
    //     state = document.execCommand("copy");
    // } catch (err) {
    //     state = false;
    // }
    // document.body.removeChild(textarea);
    // currentFocus.focus();
    // return state;
};


// 搜索按钮
$(document).on('click', '#searchBtn', function () {
    var searchWord = $('#searchWord').val().trim();
    var params = '?searchword=' + searchWord + '&fulltype=&full_poly=';
    window.location.href = ctxPath + 'fullsearch/result' + params;
});

// 回车搜索
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('#searchBtn').trigger("click");
    }
});

//2021.7.1
function checkedShow(id , showId , text) {

    let boolean1 = true;
    let sum = 0;
    $("#"+id+" input[type='checkbox']:checked").each(function () {
        if ($("#"+id+" input[type='checkbox']:checked").length == 1 && $(this).val() == "0"){
            $("#"+showId).removeClass("checkedShow ");
            boolean1 = false;
        }
        sum++;
    });
    if (boolean1){
        let text1 = "<a\n" +
            "                            data-v-03cb9676=\"\"\n" +
            "                            style=\"font-weight: 300; font-size: 12px\"\n" +
            "                            class=\"dselect-text\"\n" +
            "                          >\n" +
            "                            "+text + "(" + sum + ")" + "\n" +
            "                            <!---->\n" +
            "                            <span data-v-03cb9676=\"\" class=\"caret\"></span\n" +
            "                          ></a>"
        $("#"+showId).html(text1);
        $("#"+showId).addClass("checkedShow ");

    }else {

        let text2 = "<a\n" +
            "                            data-v-03cb9676=\"\"\n" +
            "                            style=\"font-weight: 300; font-size: 12px\"\n" +
            "                            class=\"dselect-text\"\n" +
            "                          >\n" +
            "                            "+ text +"\n" +
            "                            <!---->\n" +
            "                            <span data-v-03cb9676=\"\" class=\"caret\"></span\n" +
            "                          ></a>"
        $("#"+showId).html(text2);

        $("#"+showId).removeClass("checkedShow ");
    }
}

//行业展示点击事件
$("#industrylabledata").click(function () {

    industryIndex = "";
    determineShow(industryIndex , "industrylist" , "industrylabledata" , "行业");
    eventIndex = "";
    determineShow(eventIndex , "eventlist" , "eventlabledata" , "事件");
    province = "";
    determineCityAndprovincelistprovincelistShow(province  , "provincelabledata" , "省份");
    city = "";
    determineCityAndprovincelistprovincelistShow(city  , "citylabledata" , "城市");

    var active = "badge-info";
    var normal = "badge-light";

    $("#industrylist span").removeClass(active);
    $("#industrylist span").addClass(normal);
    $("#industrylist > span:nth-child(1)").removeClass(normal);
    $("#industrylist > span:nth-child(1)").addClass(active);
    $("#eventlist span,#provincelist span,#citylist span").removeClass(active);
    $("#eventlist span,#provincelist span,#citylist span").addClass(normal);
    $("#eventlist > span:nth-child(1),#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);

    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleEvent(articleParam, JSON.stringify(articleData), funcEvent);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);


})

//事件展示点击事件
$("#eventlabledata").click(function () {

    eventIndex = "";
    determineShow(eventIndex , "eventlist" , "eventlabledata" , "事件");
    province = "";
    determineCityAndprovincelistprovincelistShow(province , "provincelabledata" , "省份");
    city = "";
    determineCityAndprovincelistprovincelistShow(city , "citylabledata" , "城市");

    var active = "badge-info";
    var normal = "badge-light";

            $("#eventlist span").removeClass(active);
            $("#eventlist span").removeClass(normal);
            $("#eventlist > span:nth-child(1)").removeClass(normal);
            $("#eventlist > span:nth-child(1)").addClass(active);
            $("#provincelist span,#citylist span").removeClass(active);
            $("#provincelist span,#citylist span").addClass(normal);
            $("#province > span:nth-child(1),#city > span:nth-child(1)").addClass(active);


    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);



})

//省份展示点击事件
$("#provincelabledata").click(function () {

    province = "";
    determineCityAndprovincelistprovincelistShow(province , "provincelabledata" , "省份");
    city = "";
    determineCityAndprovincelistprovincelistShow(city , "citylabledata" , "城市");


    var active = "badge-info";
    var normal = "badge-light";


        $("#provincelist span").removeClass(active);
        $("#provincelist span").addClass(normal);
        $("#province > span:nth-child(1)").removeClass(normal);
        $("#province > span:nth-child(1)").addClass(active);
        $("#citylist span").removeClass(active);
        $("#citylist span").addClass(normal);



    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';
    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    sendArticleProvince(articleParam, JSON.stringify(articleData), funcProvince);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);



})
//城市展示点击事件
$("#citylabledata").click(function (){

    city = "";

    var active = "badge-info";
    var normal = "badge-light";



        $("#citylist span").removeClass(active);
        $("#citylist span").addClass(normal);

        $("#citylist > span:nth-child(1)").addClass(active);
    $("#citylist > span:nth-child(1)").removeClass(normal);


    let articleParam = new Object();
    articleParam.type = "POST";
    articleParam.url = ctxPath + "monitor/getarticle";
    articleParam.contentType = 'application/json;charset=utf-8';

    let searchkeyword = $("#searchkeyword").val();
    let articleData = getArticleData(1, searchkeyword, monitor_projectid, monitor_groupid);

    //sendArticleIndustry(articleParam, JSON.stringify(articleData), funcIndustry);
    //sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);
    sendArticle(articleParam, JSON.stringify(articleData), installArticle);
    sendArticleCity(articleParam, JSON.stringify(articleData), funcCity);



    determineCityAndprovincelistprovincelistShow(city  , "citylabledata" , "城市");

});