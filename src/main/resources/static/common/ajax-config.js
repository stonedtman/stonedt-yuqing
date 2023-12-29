let tokenName = "token";



$.ajaxSetup(
    {
        complete: function (xhr) {
            let newToken = xhr.getResponseHeader(tokenName);
            if (newToken && newToken !== '') {
                let expirationDate = new Date(Date.now() + 8 * 60 * 60 * 1000);
                document.cookie = "token=" + token + "; expires=" + expirationDate.toUTCString() + "; path=/";
            }
        }
    }
)