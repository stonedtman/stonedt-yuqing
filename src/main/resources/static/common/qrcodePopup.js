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
                            <div class="qrcode-container">
                                <img src="assets/images/QRcode.jpg">
                            </div>
                            <div class="tip">
                                微信扫码 <span class="key-word">关注公众号</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `
    $("body").append(html)
}

function closeModal() {
    $("#exampleModal").remove()
}