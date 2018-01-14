function allSelect() {
    var obj = document.getElementsByName("deleteSome");
    var length = obj.length;
    for(var i = 0;i < length;i++){
        obj[i].checked = true;
    }
}
function noSelect() {
    var obj = document.getElementsByName("deleteSome");
    var length = obj.length;
    for(var i = 0;i < length;i++){
        obj[i].checked = false;
    }
}