
function onLoginSuccess(user){
console.log(JSON.stringify(user));
initGenerics();
setLoggedUser(user);
initUserTransactions()
goToHome();
}
function onLoginError(){
    initGenerics();
    goToLogin();

}

var Login = (function(){

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
            
             data: JSON.stringify(data1),
             success: function(data, textStatus, jqXHR){
                $.mobile.hidePageLoadingMsg();
                   onLoginSuccess(data);
             },
             complete: function(){},
             error: function(a,b,c) {
                  $.mobile.hidePageLoadingMsg();
                  onLoginError();
                  console.log('something went wrong1:',a);
                  console.log('something went wrong2:',b);
                  console.log('something went wrong:3',c);
             }
        });
    });

}

(function($) {

}(jQuery));
});