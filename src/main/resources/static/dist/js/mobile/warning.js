var groupAndProjectList = [];
var groupId = "";
var projectId = "";
var pageNum = 1

sendProjectAndProject()
function sendProjectAndProject() {
    $.ajax({
        type: "POST",
        url: "/project/getGroupAndProject",
        dataType: 'json',
        data: {
            groupid: "",
            page: 1,
            projectsearch: "",
            menu: ""
        },
        async: false,
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
    for (let i = 0; i < data.length; i++) {
        let dataJson = data[i];
        for (let key in dataJson) {
            let value = dataJson[key];
            let group_id = key.split("-")[0];
            let group_name = key.split("-")[1];
            if(i==0){
                groupId = group_id
                groupName = group_name
                projectId = value[0].project_id
            }
            groupAndProjectList.push({
                group_id,
                group_name,
                project_list: value
            })
            html+=`<li data-value="${group_id}" onclick="changegroup(this)"><a>${group_name}</a></li>`
        }
    }
    $(".schema_group ul").html(html)
    if(groupAndProjectList.length>0){
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
}
function changegroup(e) {
    groupId = e.dataset.value
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
                if(j==0){
                    projectId = projectList[j].project_id
                    html+=`<div class="item active" onclick="switchProject(this)" data-projectid="${projectList[j].project_id}"><span>${projectList[j].project_name}</span></div>`
                }else{
                    html+=`<div class="item" onclick="switchProject(this)" data-projectid="${projectList[j].project_id}"><span>${projectList[j].project_name}</span></div>`
                }
            }
        }
    }
    $(".projectList").html(html)
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
    sendArticle()
}

$(".search input").keydown(function(e){
    if(e.keyCode==13) {
        sendArticle()
    }
})
$(".search i").click(function(){
    sendArticle()
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
                $(".allNum").html(res.data.pageInfo.total+"条预警消息")
                let data = res.data.warningArticle
                let html = ""
                for (let i = 0; i < data.length; i++) {
                    let sourcewebsitename = JSON.parse(data[i].article_detail).sourcewebsitename

                    html+=`<a href="/mobile/monitor/detail?id=${data[i].article_id}&projectid=${data[i].project_id}">
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
                    }
                    $(".dataList .loading").remove()
                    setTimeout(function () {
                        scroll_flag = true
                    },100)
                    return
                }
                $(".dataList").html(html)
                if(data.length==0){
                    nodata(".dataList")
                }
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

var scroll_flag = true
window.addEventListener('scroll', function() {
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