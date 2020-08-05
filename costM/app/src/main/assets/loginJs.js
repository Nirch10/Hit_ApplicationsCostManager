var serverIp = 0;
var port = 0;

var Login = (function(){



Login.prototype.load = function(serverIpAddr, portNum){
serverIp = serverIpAddr;
port = portNum;
}

Login.prototype.login = function(){
    $("#login-btn").on('click',function(){
        console.log($("#userNameInputLogin").val())
        console.log($("#passwordInputLogin").val())
        var data1 = {}
        data1["UserName"] =  $("#userNameInputLogin").val();
        data1["Password"] = $("#passwordInputLogin").val();
        $.ajax({
             url: 'http://'+serverIp +':'+port+'/api/login',
             type: 'POST',
             dataType: 'json',
              beforeSend: function() {
                         $.mobile.showPageLoadingMsg(true);
                     },
                     complete: function() {
                         $.mobile.hidePageLoadingMsg();
                     },
             data: JSON.stringify(data1),
             success: function(data, textStatus, jqXHR){
                  console.log(textStatus);  // will display the content of data
                  console.log("ok", JSON.stringify(data));  // will display the content of data
                  window.location.href= "#new-transaction-page";
             },
             error: function(a,b,c) {
                  console.log('something went wrong1:',a);
                  console.log('something went wrong2:',b);
                  console.log('something went wrong:3',c);
             }
        });
    });

}




(function($) {
    $.fn.invisible = function() {
        return this.each(function() {
            $(this).css("visibility", "hidden");
        });
    };
    $.fn.visible = function() {
        return this.each(function() {
            $(this).css("visibility", "visible");
        });
    };
}(jQuery));
});