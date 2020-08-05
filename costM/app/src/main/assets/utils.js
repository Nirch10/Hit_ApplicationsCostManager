var retails = []
var retailsNames = []

function getRetails(){
return retails;
}

function initRetails(){
    $.ajax({
                 url: 'http://10.0.0.8:1234/api/home/getallretails',
                 type: 'GET',
                 dataType: 'json',
                  beforeSend: function() {
                             $.mobile.showPageLoadingMsg(true);
                         },
                         complete: function() {
                             $.mobile.hidePageLoadingMsg();
                         },
                 //data: JSON.stringify(data1),
                 success: function(data, textStatus, jqXHR){
                      retails = data;
                      addToDL('retailslist', getRetails());
                 },
                 error: function(a,b,c) {
                      retails = [];
                      console.log('something went wrong1:',a);
                      console.log('something went wrong2:',b);
                      console.log('something went wrong:3',c);
                 }
});
};

function addToDL(id,array){
var options = '';

  for(var i = 0; i < array.length; i++)
    options += '<option value="'+array[i].Name+'" />';

  document.getElementById(id).innerHTML = options;
};

