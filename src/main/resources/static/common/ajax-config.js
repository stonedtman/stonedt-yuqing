let tokenName = "token";



$.ajaxSetup(
    {
        beforeSend: function (xhr){
            let token = localStorage.getItem(tokenName)
            if (token && token !== '') {
                xhr.setRequestHeader(tokenName,token)
            }
        },
        complete: function (xhr) {
            let newToken = xhr.getResponseHeader(tokenName);
            if (newToken && newToken !== '') {
                localStorage.setItem(tokenName, newToken);
            }
        }
    }
)