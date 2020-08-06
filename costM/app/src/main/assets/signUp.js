

function signUpValidation(){
    $('.validatedForm').validate({
    			rules : {
    				password : {
    					minlength : 5
    				},
    				password_confirm : {
    					minlength : 5,
    					equalTo : "#password"
    				}
    			},
                submitHandler:function(form){
                    doSign();
                },invalidHandler: function(form, validator) {
                      $('html, body').animate({scrollTop: '0px'}, 300);
                    }
    		});

}

function doSign(){
    console.log("arrived");
}

function setSignUpBody(){
    var jsonBody = {};
    jsonBody["UserName"] = $("#new-username-input").val();
    jsonBody["Password"] = $("#new-password-input").val();
    jsonBody["Email"] = $("#new-email-input").val();
    return jsonBody;
}

function signUp(){
    //signUpValidation();
    var jsonBodyReq = setSignUpBody();
    $.ajax({
         url: 'http://'+serverIp +':'+port+'/api/home/signup',
         type: 'POST',
         dataType: 'json',
          beforeSend: function() {
                     $.mobile.showPageLoadingMsg(true);
                 },
                 complete: function() {
                     $.mobile.hidePageLoadingMsg();
                 },
         data: JSON.stringify(jsonBodyReq),
         success: function(data, textStatus, jqXHR){
               goToLogin();
         },
         error: function(a,b,c) {
              console.log('something went wrong1:',a);
              console.log('something went wrong2:',b);
              console.log('something went wrong:3',c);
         }
    });
}