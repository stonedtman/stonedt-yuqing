let tokenName = "token";



$.ajaxSetup(
    {
        beforeSend: function (xhr){
            let token = localStorage.getItem(tokenName)
            if (token && token !== '') {
                //设置cookie
                let expirationDate = new Date(Date.now() + 8 * 60 * 60 * 1000);
                document.cookie = "token=" + token + "; expires=" + expirationDate.toUTCString() + "; path=/";
            }
        },
        complete: function (xhr) {
            let newToken = xhr.getResponseHeader(tokenName);
            if (newToken && newToken !== '') {
                localStorage.setItem(tokenName, newToken);
                let expirationDate = new Date(Date.now() + 8 * 60 * 60 * 1000);
                document.cookie = "token=" + token + "; expires=" + expirationDate.toUTCString() + "; path=/";
            }
        }
    }
)