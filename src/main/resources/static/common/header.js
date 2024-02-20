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


// 移动端舆情二维码弹窗
function mobile_popup(t) {
    let html = `
        <div id="exampleModal" class="mobile_popup">
            <div class="modal-backdrop"></div>
            <div class="modal-content">
                <span class="modal-close" onclick="close_mobile_popup(${t})"></span>
                <div class="modal-main-top">
                    <img src="/assets/images/mobile-popup.png" alt="">
                    <div class="title">移动端舆情监测上线了!</div>
                    <div class="tips">
                        <div>实时推送</div>
                        <div>预警通知</div>
                    </div>
                </div>
                <div class="modal-main-container">
                    <div class="scan-login">
                        <div class="public-login">
                            <div class="header">
                                <h2 class="header-title">扫描下方二维码即可体验</h2>
                            </div>
                            <div class="qrcode-container">
                                <img src="/mobile/mobileQRCode">
                            </div>
                            <div class="tip">
                                <a href="https://www.bilibili.com/video/BV1gT4y1n7Y1/" target="_blank">如何使用移动端预警功能?</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `
    $("body").append(html)
}

function close_mobile_popup(t) {
    $("#exampleModal").remove()
    if(t=="button"){
        return
    }
    $.ajax({
        type: "post",
        url: "/popUp/close",
        success: function (res) {

        }
    })
}