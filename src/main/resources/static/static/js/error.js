function error(xhr){
    if(xhr.status === 404){
        location.href="404.html"
    } else if(xhr.status === 500){
        location.href="500.html"
    } else if(xhr.status === 403){
        confirm(xhr.status + ": " + "没有权限")
    } else{
        confirm(xhr.status + ": " + xhr.message)
    }
}

function setInfo(str, ctr){
    ctr.html(str)
    setTimeout(function (){
        ctr.empty()
    }, 1500)
}