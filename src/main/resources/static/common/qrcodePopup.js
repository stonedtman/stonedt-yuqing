function QRCodePopup(){
    let html = `
        <div id="exampleModal">
            <div class="modal-backdrop" onclick="closeModal()"></div>
            <div class="modal-content">
                <span class="modal-close" onclick="closeModal()"></span>
                <div class="modal-main-container">
                    <div class="scan-login">
                        <div class="public-login">
                            <div class="header">
                                <h2 class="header-title">关注我们</h2>
                            </div>
                            <div class="accountName"></div>
                            <div class="qrcode-container">
                                <img src="assets/images/QRcode.jpg">
                            </div>
                            <div class="tip">
                                微信扫码 <span class="key-word">关注公众号</span>
                            </div>
                            <div class="tip">
                                及时接收最新人工智能技术与资讯
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `
    $("body").append(html)

    $.ajax({
        type: "get",
        url: "/wechat/getBindQrCode",
        success: function (res) {
            if(res.status==200){
                if($("#exampleModal")){
                    $("#exampleModal .accountName").html(res.data.name)
                    $("#exampleModal .qrcode-container img").attr("src",res.data.qrcodeUrl)
                    wechatwasBind(res.data.sceneStr)
                }
            }else{
                showtips(res.msg)
            }
        }
    })
}

function wechatwasBind(str) {
    $.ajax({
        method: "GET",
        url: "/wechat/wasBind",
        contentType: "application/json",
        data: { sceneStr:str },
        success: function (res) {
            if (res.status == 200) {
                closeModal()
                save()
            }else if(res.status==204){
                setTimeout(() => {
                    if($("#exampleModal")){
                        wechatwasBind(str);
                    }
                }, 1000)
            }else{
                showtips(res.msg);
            }
        },
    });
}

function closeModal() {
    $("#exampleModal").remove()
}