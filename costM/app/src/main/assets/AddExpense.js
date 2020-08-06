
$("input[name='isIncome']").change(function(){
});

function resolveIsIncome(){
var radioValue = $("input[name='isIncome']:checked"). val();
if(radioValue == 'true')
    return true;
return false;
}

function buildJsonBodyReq(){
    var user = getLoggedUser();
    var retailId = document.querySelector('#retails-choose-list').value;
    var isIncome = resolveIsIncome();
    var price = $("#sum-input").val();
    var description = $("#description-input").val();
    var jsonData = {};
    jsonData["User"] = {"Guid": user.Guid};
    jsonData["Retail"] = {"Guid" : retailId};
    jsonData["Price"] = price;
    jsonData["IsIncome"] = isIncome;
    jsonData["Description"] = description;
    console.log(JSON.stringify(jsonData));
    console.log(jsonData);
    return jsonData;
}

function onAddExpenseSuccess(data){
}

function addExpense(){
var isIncome = resolveIsIncome();
jsonBodyReq = buildJsonBodyReq();
  $.ajax({
                     url: 'http://'+serverIp +':'+port+'/api/home/addtransaction',
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
                           onAddExpenseSuccess(data);
                     },
                     error: function(a,b,c) {
                          console.log('something went wrong1:',a);
                          console.log('something went wrong2:',b);
                          console.log('something went wrong:3',c);
                     }
                });
}
