
$("input[name='isIncome']").change(function(){
});

$(document).on("change","select",function(){
  console.log("§2");
  console.log(this.value);
//  $('option[value="' + this.value + '"]', this)
  $('#'+this.value+'Exp',this)
  .attr("selected", true).siblings()
  .removeAttr("selected")
});

function removeTransaction(id){
$.ajax({
                     url: 'http://'+serverIp +':'+port+'/api/home/deletetransaction/'+ id,
                     type: 'DELETE',
                     dataType: 'json',
                     headers:{
                        'Access-Control-Allow-Origin': '*'
//                        'Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD',
//                        "Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept"
                     },
                      beforeSend: function() {
                                 $.mobile.showPageLoadingMsg(true);
                             },
                             complete: function() {
                                 $.mobile.hidePageLoadingMsg();
                             },
                     success: function(data, textStatus, jqXHR){
                           onDeleteExpenseSuccess(data, id);
                     },
                     error: function(a,b,c) {
                          console.log('something went wrong1:',a);
                          console.log('something went wrong2:',b);
                          console.log('something went wrong:3',c);
                     }
                });

}

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

function onDeleteExpenseSuccess(data, id){
    initUserTransactions();
}

function onAddExpenseSuccess(data){
    var viewedT = setTransactionForView(data);
    addToTransactionsListView("transactions-list",viewedT);
    if(data.IsIncome == false){
        viewedT.Price *= -1;
    }
    addNewExpensePrice(viewedT.Price);
    goToHome();
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
