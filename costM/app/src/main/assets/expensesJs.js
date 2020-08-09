
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

//$(function() {
//    $('.date-picker').datepicker( {
//        changeMonth: true,
//        changeYear: true,
//        showButtonPanel: true,
//        dateFormat: 'MM yy',
//        onClose: function(dateText, inst) {
//            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
//            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
//            $(this).datepicker('setDate', new Date(year, month, 1));
//        }
//    });
//});

function groupBy(list, keyGetter) {
    const map = new Map();
    list.forEach((item) => {
         const key = keyGetter(item);
         const collection = map.get(key);
         if (!collection) {
             map.set(key, [item]);
         } else {
             collection.push(item);
         }
    });
    return map;
}

function setPieGroupsAndValues(transactions){
    var results = [];
    var groupedData = groupBy(transactions, transaction => transaction.Retail.Guid)
    groupedData.forEach(function(group){
       results.push({label:group[0].Retail.Name, length: group.length})
    });
    console.log("kfsaf" + JSON.stringify(results));
    return results;
}

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

function getCurrentRetailsNames(chartData){
    var results = []
    chartData.forEach(function(ret){
        results.push(ret.label);
    })
    return results;
}

function createChart(chartData){
    var dataToShow = getCurrentRetailsNames(chartData);
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'bar',
                marginRight: 130,
                marginBottom: 25
            },
            title: {
                text: 'Monthly Expenses count',
                x: -20 //center
            },
            subtitle: {
                text: 'Your expenses fully detailed graph',
                x: -20
            },
            xAxis: {
                categories: dataToShow
            },
            yAxis: {
                title: {
                    text: 'Expenses Count'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        this.x +': '+ this.y +'';
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -10,
                y: 100,
                borderWidth: 0
            },
            series: [{
                name: 'Category',
                data: getChartsValue(chartData)
            }]
        });
    });
}

$(document).on('pageshow', '#category-detailed-page', function(){
    var chart;
    $.when(getAllUserTransactions()).done(function(results){
            console.log(results);
            console.log(JSON.stringify(results));
            var chartData = setPieGroupsAndValues(results);
            createChart(chartData);
         return results;
    });

});


function getChartsValue(array){
    var results = []
    array.forEach(function(cat){
        results.push(cat.length)
    });
    return results;
}




