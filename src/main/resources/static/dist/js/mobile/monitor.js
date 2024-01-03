let groupAndProjectList = []
let groupId = ""
let projectId = ""
let groupName = ""
let projectName = ""
let page = 1

sendProjectAndProject()
function sendProjectAndProject() {
    $.ajax({
        type: "POST",
        url: ctxPath + "project/getGroupAndProject",
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
    installCondition()
}

// 获取条件
function installCondition(){
    $.ajax({
        type: "POST",
        url: ctxPath + "monitor/getCondition",
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
                let date = data.time>0?data.time:4
                let source = JSON.parse(data.classify)[0]
                let emo = JSON.parse(data.emotion).length==3?0:JSON.parse(data.emotion)[0]

                $(".dateSelect li").each(function (){
                    if(date==$(this).attr("data-value")){
                        $(this).addClass("active")
                        $(".dateSelect button").html($(this).text());
                    }else{
                        $(this).removeClass("active")
                    }
                })
                $(".sourceSelect li").each(function (){
                    if(source==$(this).attr("data-value")){
                        $(this).addClass("active")
                        $(".sourceSelect button").html($(this).text());
                    }else{
                        $(this).removeClass("active")
                    }
                })
                $(".emoSelect li").each(function (){
                    if(emo==$(this).attr("data-value")){
                        $(this).addClass("active")
                        $(".emoSelect button").html($(this).text());
                    }else{
                        $(this).removeClass("active")
                    }
                })
                sendArticle()
            }
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

$(".search input").keydown(function(e){
    if(e.keyCode==13) {
        sendArticle()
    }
})
$(".search i").click(function(){
    sendArticle()
})

function changedate(v) {
    $('.dateSelect li').each(function() {
        if (v==$(this).attr("data-value")) {
            $(this).addClass('active');
            $(".dateSelect button").html($(this).text());
        } else {
            $(this).removeClass('active');
        }
    });
    sendArticle()
}
function changesource(v) {
    $('.sourceSelect li').each(function() {
        if (v==$(this).attr("data-value")) {
            $(this).addClass('active');
            $(".sourceSelect button").html($(this).text());
        } else {
            $(this).removeClass('active');
        }
    });
    sendArticle()
}
function changeemo(v) {
    $('.emoSelect li').each(function() {
        if (v==$(this).attr("data-value")) {
            $(this).addClass('active');
            $(".emoSelect button").html($(this).text());
        } else {
            $(this).removeClass('active');
        }
    });
    sendArticle()
}

function sendArticle(t) {
    let date = 1;
    let source = [0];
    let emo = [1,2,3];
    $(".dateSelect li").each(function (){
        if($(this).hasClass("active")){
            date = $(this).attr("data-value")-0
        }
    })
    $(".sourceSelect li").each(function (){
        if($(this).hasClass("active")){
            source = $(this).attr("data-value").split("")
        }
    })
    $(".emoSelect li").each(function (){
        if($(this).hasClass("active")){
            emo = $(this).attr("data-value")
            if(emo==0){
                emo = [1,2,3]
            }else{
                emo = [emo-0];
            }
        }
    })
    let obj = {
        "projectid":projectId,
        "groupid":groupId,
        "timeType":date,
        "classify":source,
        "emotionalIndex":emo,
        "page":page,
        "times":"",
        "timee":"",
        "searchType":1,
        "similar":0,
        "matchingmode":1,
        "province":[""],
        "city":[""],
        "eventIndex":[""],
        "industryIndex":[""],
        "searchkeyword":"",
        "group_id":groupId,
        "projectId":projectId,
        "precise":0,
        "organizationtype":["0"],
        "categorylabledata":["0"],
        "enterprisetypelist":["0"],
        "hightechtypelist":["0"],
        "policylableflag":["0"],
        "datasource_type":[],
        "sourceWebsite":"",
        "author":""
    }
    if($(".search input").val().trim()!=""){
        obj.searchkeyword = $(".search input").val().trim()
    }
    $.ajax({
        type: "POST",
        url: ctxPath + "monitor/getarticle",
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
                    html+=`<a href="/mobile/monitor/detail?id=${data[i].article_public_id}&projectid=${projectId}&publish_time=${data[i].publish_time}&relatedword=${data[i].relatedWord.join()}">
                        <div class="item">
                            <div class="title-box">
                                <div class="title-left">
                                    ${data[i].industrylable?('<span class="industrylable">'+data[i].industrylable+'</span>'):''}
                                    ${data[i].eventlable?('<span class="eventlable">'+data[i].eventlable+'</span>'):''}
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
                                <span>${data[i].publish_time}</span>
                                <span>来源:${data[i].source_name}</span>
                            </div>
                        </div>
                    </a>`
                }
                if(t=="append"){
                    $(".dataList").append(html)
                    if(data.length==0){
                        page--
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
                window.location.href = ctxPath + "login";
            } else {
                dataerror(".dataList");
            }
        }
    });
}


var scroll_flag = true
window.addEventListener('scroll', function() {
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