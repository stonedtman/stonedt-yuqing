let groupAndProjectList = []
let page = 1
var groupData = []
var dateValue = 4
var dateData = [
    {
        name: "24小时",
        value: 1
    },
    {
        name: "今天",
        value: 2
    },
    {
        name: "昨天",
        value: 3
    },
    {
        name: "3天",
        value: 4
    },
    {
        name: "7天",
        value: 5
    },
    {
        name: "15天",
        value: 6
    },
    {
        name: "30天",
        value: 7
    }
]
var sourceValue = 0
var sourceData = [
    {
        name: "来源",
        value: 0
    },
    {
        name: "微信",
        value: 1
    },
    {
        name: "微博",
        value: 2
    },
    {
        name: "政务",
        value: 3
    },
    {
        name: "论坛",
        value: 4
    },
    {
        name: "新闻",
        value: 5
    },
    {
        name: "报刊",
        value: 6
    },
    {
        name: "客户端",
        value: 7
    },
    {
        name: "网站",
        value: 8
    },
    {
        name: "外媒",
        value: 9
    },
    {
        name: "视频",
        value: 10
    },
    {
        name: "博客",
        value: 11
    }
]
var emoValue = 4
var emoData = [
    {
        name: "属性",
        value: 0
    },
    {
        name: "正面",
        value: 1
    },
    {
        name: "中性",
        value: 2
    },
    {
        name: "负面",
        value: 3
    }
]

let conditionData = null;

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
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = "/login";
            }
        }
    });
}

function installGroupAndProject(data) {
    let html = ""
    for (let i = 0; i < data.length; i++) {
        let dataJson = data[i];
        for (let key in dataJson) {
            let value = dataJson[key];
            if(value.length==0){
                data.splice(i,1)
                i--
                continue;
            }
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
    let seturl = "monitor?" + "groupId=" + groupId + "&projectId=" + projectId;
    setUrl(seturl);
    installCondition()
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
    let seturl = "monitor?" + "groupId=" + groupId + "&projectId=" + projectId;
    setUrl(seturl);
    installCondition()
}

// 获取条件
function installCondition(){
    $.ajax({
        type: "POST",
        url: "/monitor/getCondition",
        dataType: 'json',
        data: JSON.stringify({
            group_id: groupId,
            projectId: projectId,
        }),
        contentType: 'application/json;charset=utf-8',
        beforeSend: function () {
            loading(".dataList")
        },
        success: function (res) {
            if(res.code==200){
                let data = res.data
                conditionData = JSON.parse(JSON.stringify(data))
                let date = data.time>0?data.time:4
                let source = JSON.parse(data.classify)[0]
                let emo = JSON.parse(data.emotion).length==3?0:JSON.parse(data.emotion)[0]

                dateValue = date
                for (let i = 0; i < dateData.length; i++) {
                    if(dateData[i].value==dateValue){
                        $(".dateSelect button").html(dateData[i].name);
                    }
                }
                sourceValue = source
                for (let i = 0; i < sourceData.length; i++) {
                    if(sourceData[i].value==sourceValue){
                        $(".sourceSelect button").html(sourceData[i].name);
                    }
                }
                emoValue = emo
                for (let i = 0; i < emoData.length; i++) {
                    if(emoData[i].value==emoValue){
                        $(".emoSelect button").html(emoData[i].name);
                    }
                }

                var aList = JSON.parse(sessionStorage.getItem('aList'));
                var aParam = JSON.parse(sessionStorage.getItem('aParam'));
                if(aList){
                    $(".dataList").html(aList)
                    setTimeout(function (){
                        $(window).scrollTop(aParam.top)
                    },100)
                    page = aParam.page;
                    setStorage()
                    sessionStorage.removeItem('aList');
                    sessionStorage.removeItem('aParam');
                    return;
                }

                sendArticle()
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (xhr.status == 403) {
                window.location.href = "/login";
            } else {
                $("#page").html("");
                dataerror("#monitor-content");
            }
        }
    });
}

$(".search input").keydown(function(e){
    if(e.keyCode==13) {
        sendArticle()
    }
})
$(".search i").click(function(){
    sendArticle()
})

$("#dropdownMenu0").click(function(){
    selectContent("#dropdownMenu0",groupData,groupId,"changegroup")
})

$("#dropdownMenu").click(function(){
    selectContent("#dropdownMenu",dateData,dateValue,"changedate")
})
function changedate(v) {
    dateValue = v
    sendArticle()
}

$("#dropdownMenu1").click(function(){
    selectContent("#dropdownMenu1",sourceData,sourceValue,"changesource")
})
function changesource(v) {
    sourceValue = v
    sendArticle()
}

$("#dropdownMenu2").click(function(){
    selectContent("#dropdownMenu2",emoData,emoValue,"changeemo")
})
function changeemo(v) {
    emoValue = v
    sendArticle()
}


function sendArticle(t) {
    let source = [sourceValue]
    let emo = [1,2,3];
    if(emoValue!=0){
        emo = [emoValue-0];
    }

    let obj = {
        "projectid":projectId,
        "groupid":groupId,
        "timeType":dateValue,
        "classify":source,
        "emotionalIndex":emo,
        "page":page,
        "searchType":1,
        "matchingmode":conditionData.matchs,
        "province":conditionData.province.split(","),
        "city":conditionData.city.split(","),
        "eventIndex":conditionData.eventIndex.split(","),
        "industryIndex":conditionData.industryIndex.split(","),
        "similar":conditionData.similar,
        "precise":conditionData.precise,
        "organizationtype":conditionData.organization.split(","),
        "categorylabledata":conditionData.categorylable.split(","),
        "enterprisetypelist":conditionData.enterprisetype.split(","),
        "hightechtypelist":conditionData.hightechtype.split(","),
        "policylableflag":conditionData.policylableflag.split(","),
        "datasource_type":conditionData.datasource_type.split(","),
        "sourceWebsite":conditionData.websitename,
        "times":conditionData.times.split(" ")[0],
        "timee":conditionData.timee.split(" ")[0],
        "author":conditionData.author
    }

    if($(".search input").val().trim()!=""){
        obj.searchkeyword = $(".search input").val().trim()
    }
    $.ajax({
        type: "POST",
        url: "/monitor/getarticle",
        dataType: 'json',
        data: JSON.stringify(obj),
        contentType: 'application/json;charset=utf-8',
        beforeSend: function() {
            if(t!="append"){
                loading(".dataList")
            }
        },
        success: function (res) {
            if(res.code==200){
                let data = res.data.data
                let html = ""
                for (let i = 0; i < data.length; i++) {
                    html+=`<a href="/mobile/monitor/detail?id=${data[i].article_public_id}&groupId=${groupId}&projectId=${projectId}&publish_time=${data[i].publish_time}&relatedword=${data[i].relatedWord.join()}">
                        <div class="item">
                            <div class="title-box">
                                <div class="title-left">
                                    ${data[i].categorylable?('<span class="categorylable">'+data[i].categorylable+'</span>'):''}
                                    <span class="title">${data[i].title}</span>
                                </div>
                                <span class="emotion ${data[i].emotionalIndex==1?"zm":data[i].emotionalIndex==2?"zx":"fm"}">${data[i].emotionalIndex==1?"正面":data[i].emotionalIndex==2?"中性":"负面"}</span>
                            </div>
                            <div class="content-con font-13">${data[i].content}</div>
                            <div class="like-comm m-t-10">
                                <span>
                                    <img class="content-logo" src="${data[i].author_avatar}" alt="" onerror="javascript:this.src='/assets/images/default_source.png'">
                                    ${data[i].author}
                                </span>
                                <span>${data[i].publish_time.slice(0,-3)}</span>
                                <span>来源:${data[i].source_name}</span>
                            </div>
                        </div>
                    </a>`
                }
                if(t=="append"){
                    $(".dataList").append(html)
                    if(data.length==0){
                        page--
                        $(".dataList").append('<div class="nomore text-center" style="color: #777">没有更多了</div>')
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
            if(res.code==500){
                dataerror(".dataList");
            }
            if(res.code==404){
                nokeyword(".dataList")
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

function setStorage(){
    $(".dataList a").click(function (){
        var aList = JSON.stringify($(".dataList").html());//把json数据转为string字符串
        var aParam = {
            page: page,  //当前页码
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
            page++
            scroll_flag = false
            $(".dataList").append('<div class="loading text-center"><div class="spinner-border spinner-border text-info" role="status"></div></div>')
            sendArticle("append")
        }
    }
});