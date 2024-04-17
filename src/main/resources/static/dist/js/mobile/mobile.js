function selectContent(id,data,value,funcname) {
    if($(".select-content").length>0){
        if($(".select-content .item")[0].innerText!=data[0].name){
            setTimeout(function(){
                selectContent(id,data,value,funcname)
            },100)
        }
        closeSelect()
        return
    }
    let htmlStart = `
        <div class="select-content">
            <div class="mask" onclick="closeSelect()"></div>
            <div class="select-content-box">
                <div class="list">
    `
    let htmlEnd = "</div></div></div>"
    let list = ""
    for (let i = 0; i < data.length; i++) {
        list += `
            <div class="item" data-value="${data[i].value}" onclick="selectActive(this,'${id}',${funcname})">
                <div class="name">${data[i].name}</div>
                <i class="fa fa-check"></i>
            </div>
        `
    }
    let html = htmlStart+list+htmlEnd
    $("body").append(html)
    if(id!="#dropdownMenu0"){
        $(".select-content").addClass("filter-select")
    }

    $(".select-content .item").each(function (){
        if(value==$(this).attr("data-value")){
            $(this).addClass("active")
        }else{
            $(this).removeClass("active")
        }
    })
    $("body").css({"height":"100vh","overflow":"hidden"})
}

function selectActive(e,id,funcname) {
    var value = e.dataset.value
    $(".select-content .item").each(function (){
        if(value==$(this).attr("data-value")){
            $(this).addClass("active")
            $(id).html($(this).text());
        }else{
            $(this).removeClass("active")
        }
    })
    if(id=="#dropdownMenu0"){
        funcname(e)
    }else{
        funcname(value)
    }

    closeSelect()
}

function closeSelect() {
    $(".select-content").remove()
    $("body").removeAttr("style")
}

function setUrl(url) {
    history.pushState({url: url, title: document.title}, document.title, url)
}