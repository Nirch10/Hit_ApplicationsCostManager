

jQuery(document).ready(function($){

    $.support.cors = true;
    var login = new Login();
    login.load('10.0.0.8', '1234');
    initRetails();

});
   $( document ).bind( "mobileinit", function() {
            // Make your jQuery Mobile framework configuration changes here!

                $.mobile.allowCrossDomainPages = true;
            });