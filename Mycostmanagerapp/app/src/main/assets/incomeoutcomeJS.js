//$(document).ready(function(){
var retails = [{name:"food"} , {name:"clothes"} , {name:"bills"}];



//init details of all data when the user login
function initData(){
    window.viewModel.Init();

}




//init the details of the current user

function inituser()
{
window.viewModel.initcurrentuser();

}

function getDetailsFromUser()
{
//implements request to server that recieve all the details of specific user
//init the details on the webview

}



/*
function addOutCome(){
    let retail = $('#retail').val();
    let cost = $('#cost').val();
    let description = $('#description').val();
    window.viewModel.addOutCome(retail,cost,description);
        alert(retail);
}
*/






/*
function sendRetail(){

var sendRetailToSum = {

   retail : $('#retail').val(),
   description : $('#description').val()
   cost : $('#cost').val(),
   id: $('#retail').id()

   };

const jsonString = JSON.stringify(sendRetailToSum);
cosnt xhr = new XMLHttpRequest();

xhr.open("POST" , )
xhr.setRequestHeader("Content-Type" , "application/json");
xhr.send(sendRetailToSum);



}
*/



/*
function chk(){
    ret = window.ob.chks();
        alert(1);
}
*/

//}

//http request for get all the expenses of the choosen retail
/*
function displayExpenses() {
  var xhr;

  var xhr = new XMLHttpRequest();
  xhr.open("GET","data.json",true);
  xhr.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
    //document.getElementById("retaildetails").innerHTML = this.responseText;
    }
  };
  xhttp.open("GET", "data.json", true);
  xhttp.send();
}
}
*/


//add outcome function

function displayExpenses()
 {
 alert(1);

 /*

    var xhr = new XMLHttpRequest();
    xhr.open("GET","",true);
    xhr.onreadystatechange = function(){
        if (xhr.readyState == 4 && xhr.status == 200)
        {
            var list = document.getElementById('expensesList');
            var data = xhr.responseText;
            var vec = JSON.parse(data);
            vec.forEach(
                function(ob)
                 {
                 addItemToDetailList(ob); //adding object to details list

                 /*
                    var li = document.createElement("li");
                    var a = document.createElement("a");
                    var text = document.createTextNode(ob.country);
                    a.appendChild(text);
                    a.setAttribute("href","#");
                    li.appendChild(a);
                    list.appendChild(li);

                 }
              );
           $('#expensesList').listview('refresh');
        }
    }
    xhr.send();
    */

 }



function addItemToDetailList(){
//implements of adding object to details list
}

//Init all the initial information
function initAllData(){
//alert("addItemToRetailList");
initRetails();
}

function initRetails(){

let datalist = updateDataList();
$('#retailist').append(datalist).trigger('create');
$('#retailsnewoutcome').append(datalist).trigger('create');

}


function updateDataList(){

let datalist = document.createElement("datalist");

retails.forEach(
function(ob){
    var opt = document.createElement("option");
    opt.setAttribute("value" , ob.name);
    datalist.appendChild(opt);

});


return (datalist);
}












