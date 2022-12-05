function error(xhr){
    if(xhr.status === 404){
        location.href="404.html"
    } else if(xhr.status === 500){
        location.href="500.html"
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