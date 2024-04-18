var groupAndProjectList = [];

var groupData = []
var pageNum = 1

sendProjectAndProject()
function sendProjectAndProject() {
    $.ajax({
        type: "get",
        url: "/mobile/getGroupAndProject",
        beforeSend: function() {
            loading(".dataList")
        },
        success: function (res) {
            if(res.code==200){
                installGroupAndProject(res.data);
            }

        },
        error: function (err) {
            console.log(err);
        }
    });
}

function installGroupAndProject(data) {
    let html = ""
    let isgroup = false
    for (let i = 0; i < data.length; i++) {
        let dataJson = data[i];
        let isproject = false
        for (let key in dataJson) {
            let value = dataJson[key];
            let group_id = key.split("-")[0];
            if (groupId == group_id) {
                isgroup = true
                for (let j = 0; j < value.length; j++) {
                    if (projectId == value[j].project_id) {
                        isproject = true
                    }
                }
                if (!isproject) {
                    projectId = value[0].project_id
                }
            }
        }
    }
    if (!isgroup) {
        groupId = ""
    }
    for (let i = 0; i < data.length; i++) {
        let dataJson = data[i];
        for (let key in dataJson) {
            let value = dataJson[key];
            let group_id = key.split("-")[0];
            let group_name = key.split("-")[1];
            if(!groupId&&i==0){
                groupId = group_id
                projectId = value[0].project_id
            }
            groupAndProjectList.push({
                group_id,
                group_name,
                project_list: value
            })
            groupData.push({
                name: group_name,
                value: group_id
            })
            html+=`<li data-value="${group_id}" onclick="changegroup(this)"><a>${group_name}</a></li>`
        }
    }
    $(".schema_group ul").html(html)
    if(groupAndProjectList.length>0){
        for (let i = 0; i < groupData.length; i++) {
            if(groupId==groupData[i].value){
                $("#dropdownMenu0").html(groupData[i].name);
            }
        }
        changeProjectList()
    }else{
        $(".monitor-search").remove()
        $(".projectList").remove()
        $(".total").remove()
        nodata(".dataList")
    }
}
function changegroup(e) {
    groupId = e.dataset.value
    projectId = ""
    $(".schema_group li").each(function (){
        if(groupId==$(this).attr("data-value")){
            $(this).addClass("active")
            $(".schema_group button").html($(this).text());
        }else{
            $(this).removeClass("active")
        }
    })
    changeProjectList()
}

function changeProjectList() {
    let html = ""
    for (let i = 0; i < groupAndProjectList.length; i++) {
        if(groupAndProjectList[i].group_id==groupId){
            let projectList = groupAndProjectList[i].project_list
            for (let j = 0; j < projectList.length; j++) {
                if(projectId&&projectId==projectList[j].project_id){
                    html+=`<div class="item active" onclick="switchProject(this)" data-projectid="${projectList[j].project_id}"><span>${projectList[j].project_name}</span></div>`
                }else if(!projectId&&j==0){
                    projectId = projectList[j].project_id
                    html+=`<div class="item active" onclick="switchProject(this)" data-projectid="${projectList[j].project_id}"><span>${projectList[j].project_name}</span></div>`
                }else{
                    html+=`<div class="item" onclick="switchProject(this)" data-projectid="${projectList[j].project_id}"><span>${projectList[j].project_name}</span></div>`
                }
            }
        }
    }
    $(".projectList").html(html)
    let seturl = "warning?" + "groupId=" + groupId + "&projectId=" + projectId;
    setUrl(seturl);

    var aList = JSON.parse(sessionStorage.getItem('aList'));
    var aParam = JSON.parse(sessionStorage.getItem('aParam'));
    if(aList){
        $(".dataList").html(aList)
        setTimeout(function (){
            $(window).scrollTop(aParam.top)
        },100)
        pageNum = aParam.page;
        setStorage()
        sessionStorage.removeItem('aList');
        sessionStorage.removeItem('aParam');
        return;
    }

    sendArticle()
}

function switchProject(e) {
    projectId = e.dataset.projectid
    $(".projectList .item").each(function (){
        if(projectId==$(this).attr("data-projectid")){
            $(this).addClass("active")
        }else{
            $(this).removeClass("active")
        }
    })
    let seturl = "warning?" + "groupId=" + groupId + "&projectId=" + projectId;
    setUrl(seturl);
    sendArticle()
}

$(".search input").keydown(function(e){
    if(e.keyCode==13) {
        pageNum = 1
        sendArticle()
    }
})
$(".search i").click(function(){
    pageNum = 1
    sendArticle()
})

$("#dropdownMenu0").click(function(){
    selectContent("#dropdownMenu0",groupData,groupId,"changegroup")
})

function sendArticle(t) {
    let obj = {
        project_id: projectId,
        pageNum: pageNum,
        openFlag: 0,
    }
    if($(".search input").val().trim()!=""){
        obj.keyword = $(".search input").val().trim()
    }
    $.ajax({
        type: "POST",
        url: "/system/getWarningArticle",
        dataType: 'json',
        data: obj,
        beforeSend: function() {
            if(t!="append"){
                loading(".dataList")
            }
        },
        success: function (res) {
            if(res.status==200){
                $(".total").html("<div class='allNum'>"+res.data.pageInfo.total+"条预警消息</div>")
                let data = res.data.warningArticle
                let html = ""
                for (let i = 0; i < data.length; i++) {
                    let sourcewebsitename = JSON.parse(data[i].article_detail).sourcewebsitename

                    html+=`<a href="/mobile/monitor/detail?id=${data[i].article_id}&groupId=${data[i].group_id}&projectId=${data[i].project_id}">
                        <div class="item">
                            <div class="title">${data[i].article_title}</div>
                            <div class="like-comm">
                                <span>来源:${sourcewebsitename}</span>
                                <span>方案组:${data[i].groupName}</span>
                                <text>方案名称:${data[i].project_name}</text>
                            </div>
                            <div class="like-comm">${getMyDate(data[i].article_time)}</div>
                        </div>
                    </a>`
                }
                if(t=="append"){
                    $(".dataList").append(html)
                    if(data.length==0){
                        pageNum--
                        $(".dataList").append('<div class="nomore text-center" style="color: #777;padding: 8px 0;">没有更多了</div>')
                    }
                    $(".dataList .loading").remove()
                    setTimeout(function () {
                        scroll_flag = true
                    },100)
                    setStorage()
                    return
                }
                $(".dataList").html(html)
                if(data.length==0){
                    nodata(".dataList")
                }
                setStorage()
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = "/login";
            } else {
                dataerror(".dataList");
            }
        }
    });
}

function getMyDate(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' +
            getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
    return oTime;
};
//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}

function setStorage(){
    $(".dataList a").click(function (){
        var aList = JSON.stringify($(".dataList").html());//把json数据转为string字符串
        var aParam = {
            page: pageNum,  //当前页码
            top: $(window).scrollTop(),
        };
        aParam = JSON.stringify(aParam);
        sessionStorage.setItem('aList', aList);//sessionStorage只能存储string字符串
        sessionStorage.setItem('aParam', aParam);
    })
}

var scroll_flag = true
window.addEventListener('scroll', function() {
    if($(".nomore").length>0){
        return
    }
    let Top = window.scrollY
    let Height = window.innerHeight
    let listTop = document.querySelector(".dataList").offsetTop
    let listH = document.querySelector(".dataList").offsetHeight
    if(scroll_flag){
        if(listTop+listH-Height<=Top){
            pageNum++
            $(".dataList").append('<div class="loading text-center"><div class="spinner-border spinner-border text-info" role="status"></div></div>')
            scroll_flag = false
            sendArticle("append")
        }
    }
});