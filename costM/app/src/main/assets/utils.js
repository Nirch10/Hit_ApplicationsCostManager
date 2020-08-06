var retails = [];
var loggedUser = [];
var retailsCountDict = {};
var retailsDict = new Map();
var totalExpensesSum = 0;
transactions = [];
var serverIp = 0;
var port = 0;



function initGenerics(){
totalExpensesSum = 0;
transactions = []
loggedUser = [];

}

function addNewExpensePrice(priceToAdd){
    totalExpensesSum += priceToAdd;
    document.getElementById("total-expenses-num").innerHTML = totalExpensesSum;
}

function load(serverIpAddr, portNum){
serverIp = serverIpAddr;
port = portNum;
}

function getRetails(){return retails;}

function getLoggedUser(){return loggedUser;}

function setLoggedUser(user){
loggedUser = user;
console.log(JSON.stringify(loggedUser))
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
                      retails.forEach(function(retail){
                        retailsDict[retail] = 0;
                      });
                      addToDL('retails-choose-list', getRetails());

                 },
                 error: function(a,b,c) {
                      retails = [];
                      console.log('something went wrong1:',a);
                      console.log('something went wrong2:',b);
                      console.log('something went wrong:3',c);
                 }
});
};

function initUserTransactions(){
   $.when(getAllUserTransactions()).done(function(results){
        console.log("res", results);
        transactions = results;
        results.forEach(function(res){
            var viewedT = setTransactionForView(res);
            //addToTransactionsTable("transactions-table-body",viewedT);
            addToTransactionsListView("transactions-list",viewedT);
            if(res.IsIncome == true)
            {addNewExpensePrice(res.Price);}
            else{
            addNewExpensePrice(-1* res.Price);
            }
        });
        console.log(totalExpensesSum);

         return results;
});
};

function setTransactionForView(res){
    var toView = {};
    if(res.IsIncome == true){
        toView["IsIncome"] = '<a class="ui-btn ui-shadow ui-corner-all ui-icon-plus ui-btn-icon-notext ui-btn-b ui-btn-inline"></a>';

        //toView["IsIncome"] = "+";
    }
    else{
        toView["IsIncome"] = '<a class="ui-btn ui-shadow ui-corner-all ui-icon-minus ui-btn-icon-notext ui-btn-b ui-btn-inline"></a>';
        //toView["IsIncome"] = "-";
    }
    toView["Guid"] = res.Guid;
    toView["Price"] = res.Price;
    toView["Description"] = res.Description;
    toView["Category"] = res.Retail.Name;
    toView["Date"] = res.DateOfTransaction;
    return toView;
}

function addToTransactionsListView(id, item){
  var html = ' <div data-role="collapsible" id="'+item.Guid
  +'" data-collapsed="true"><h3>'+item.Category +' : '+item.Price+'</h3><p>'+ item.Date
  +', '+ item.IsIncome +', '+item.Description+'</p></div>'
  $("#"+id).append(html).collapsibleset('refresh');
}

function addToTransactionsTable(id, item){
    var html = '<tr><td>'+item["Category"]+
    '</td><td>'+item["Description"]+'</td><td>'+item["IsIncome"]+
    '</td><td>'+item["Price"]+'</td><td>'+item["Date"]+'</td></tr>'

   $("#"+id).append(html);

}

function getAllUserTransactions(){
 return $.when($.ajax({
                 url: 'http://10.0.0.8:1234/api/home/getusertransactions/' + loggedUser.Guid,
                 type: 'GET',
                 dataType: 'json',
                  beforeSend: function() {
                             $.mobile.showPageLoadingMsg(true);
                         },
                         complete: function() {
                             $.mobile.hidePageLoadingMsg();
                         },
                 //data: JSON.stringify(data1),
                 }).then(function(res){
                 return res;}));
};

function addToDL(id,array){
var options = '';

  for(var i = 0; i < array.length; i++)
    options += '<option label="'+array[i].Name +'" value="'+array[i].Guid+'" />';

  document.getElementById(id).innerHTML = options;
};

function addToHomeTransactionsList(id,array){
var options = '';

  for(var i = 0; i < array.length; i++)
    options += '<li id=\"'+array[i].Guid+'-tran\">'+array[i].IsIncome+' <span class="ui-listview-item-count-bubble" style="">'+array[i].Price+'</span></li>';

  document.getElementById(id).innerHTML = options;
};

function signOut(){
initGenerics();
 $("#transactions-table-body").empty();
goToLogin();
}





function goToHome(){
window.location.href= "#home-page";
}

function goToAddExpense(){
window.location.href= "#new-transaction-page";
}

function goToLogin(){
window.location.href= "#login-page";
}


function goToSignUp(){
window.location.href= "#sign-up-page";
}

