/**
 * @author huajiancheng
 * @date 2020/04/23
 * @description 点击搜索获取数据
 */
$(document).on('click', '#searchI', function () {
    let searchText = $("#searchText").val();
    window.location.href = ctxPath + "monitor?searchword=" + searchText + "&searchflag=true";
});


// 回车搜索
// $(document).keydown(function (event) {
//     if (event.keyCode == 13) {
//         $('#searchI').trigger("click");
//         let searchText = $("#searchText").val();
//         window.location.href = ctxPath + "monitor?searchword=" + searchText + "&searchflag=true";
//     }
// });

$('#searchkeyword').keydown(function (e) {
    if (e.keyCode == 13) {
        let searchText = $("#searchText").val();
        window.location.href = ctxPath + "monitor?searchword=" + searchText + "&searchflag=true";
    }
});


function isMobile(){
    // 判断是否是手机端，如果是，返回true
    var flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
    return flag
}

if(isMobile()){
    if(window.location.pathname.indexOf("/login")==-1&&window.location.pathname.indexOf("/mobile")==-1){
        window.location.href = "/mobile/monitor"
    }
}
