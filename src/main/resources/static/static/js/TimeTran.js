//转换时间
function getDate(timestamp){
    let a = new Date(timestamp).getTime();
    const date = new Date(a);
    const Y = date.getFullYear() + '-';
    const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    const D = (date.getDate() < 10 ? '0'+date.getDate() : date.getDate()) + '  ';
    const h = (date.getHours() < 10 ? '0'+date.getHours() : date.getHours()) + ':';
    const m = (date.getMinutes() <10 ? '0'+date.getMinutes() : date.getMinutes()) ;
    // const s = date.getSeconds(); // 秒
    const dateString = Y + M + D + h + m;
    // console.log('dateString', dateString); // > dateString 2021-07-06 14:23
    return dateString;
}

//转换时间
function setTime(timestamp, str){
    let a = new Date(timestamp).getTime();
    let b = 95400000

    if(str === "sendTime1"){
        b += 8100000
    }
    if(str === "sendTime2"){
        b += (12900000 + 8100000)
    }
    if(str === "sendTime3"){
        b += (4320000 + 12900000 + 8100000)
    }
    a += b
    let date = new Date(a)
    const Y = date.getFullYear() + '-';
    const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    const D = (date.getDate() < 10 ? '0'+date.getDate() : date.getDate()) + '  ';
    const h = (date.getHours() < 10 ? '0'+date.getHours() : date.getHours()) + ':';
    const m = (date.getMinutes() <10 ? '0'+date.getMinutes() : date.getMinutes()) ;
    // const s = date.getSeconds(); // 秒
    const dateString = Y + M + D + h + m;
    // console.log('dateString', dateString); // > dateString 2021-07-06 14:23
    return dateString;
}