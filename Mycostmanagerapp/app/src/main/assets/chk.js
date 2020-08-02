function calc()
{
    var a = parseInt(document.myform.num_a.value,10);
    var b = parseInt(document.myform.num_b.value,10);
    var sum = window.ob.calc_sum(a,b);
    document.myform.result.value = sum;
}