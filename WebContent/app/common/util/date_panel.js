var month_names = new Array ( );
month_names[month_names.length] = "January";
month_names[month_names.length] = "February";
month_names[month_names.length] = "March";
month_names[month_names.length] = "April";
month_names[month_names.length] = "May";
month_names[month_names.length] = "June";
month_names[month_names.length] = "July";
month_names[month_names.length] = "August";
month_names[month_names.length] = "September";
month_names[month_names.length] = "October";
month_names[month_names.length] = "November";
month_names[month_names.length] = "December";

var day_names = new Array ( );
day_names[day_names.length] = "Sun";
day_names[day_names.length] = "Mon";
day_names[day_names.length] = "Tue";
day_names[day_names.length] = "Wed";
day_names[day_names.length] = "Thu";
day_names[day_names.length] = "Fri";
day_names[day_names.length] = "Sat";

var day_names_jap = new Array ( );
day_names_jap[day_names_jap.length] = "日";
day_names_jap[day_names_jap.length] = "月";
day_names_jap[day_names_jap.length] = "火";
day_names_jap[day_names_jap.length] = "水";
day_names_jap[day_names_jap.length] = "木";
day_names_jap[day_names_jap.length] = "金";
day_names_jap[day_names_jap.length] = "土";

var g_dates = new Array();
var g_range = 0;
var g_one_day=1000*60*60*24;
var g_sheet_obj;

function setDatePanel(paramObj, disabled, sheetObj) {
	g_sheet_obj = sheetObj;
	var startDate = paramObj.startDate;
	var endDate = paramObj.endDate;
	var selectedDate = paramObj.selectedDate;
	var allDatesView = paramObj.allDatesView;
	
	var returnString = "";
	
	if (endDate == '')
		endDate = startDate;
	
	var year;
    var month;
    var date;
    var day;
    var fill = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    
    var startDateObj = toDateObj(startDate);
    var endDateObj = toDateObj(endDate);
    
    var range = (endDateObj.getTime() - startDateObj.getTime())/g_one_day;
    g_range = range;
    
    var tmpDateObj = new Date(startDateObj);
    var ctr = 0;
    for (i=0; i<=range; i++)
    {
    	ctr++;
        tmpDateObj = new Date(startDateObj.getTime() + i*g_one_day);
        //alert(tmpDateObj);
        //alert(day_names[tmpDateObj.getDay()]);
        year = getMalupetYear(tmpDateObj);
        month = pad(tmpDateObj.getMonth()+1, 2);
        date = pad(tmpDateObj.getDate(), 2);
        day = day_names_jap[tmpDateObj.getDay()];
        yyyymmdd = year + month + date + '';
        
        g_dates[i] = yyyymmdd;

        //alert(year + ' ' + month + ' ' + date);
        
        if (disabled) {
        	returnString = returnString + "<span id='" + yyyymmdd + "'";
        	if (selectedDate == yyyymmdd || allDatesView)
            	returnString = returnString + " style='color:black; font-weight:bold'>" + month + "/" + date + "(" + day + ")" + "</span>";
            else
            	returnString = returnString + " style='color:gray; font-weight:bold'>" + month + "/" + date + "(" + day + ")" + "</span>";
        }
        else {
        	returnString = returnString + "<span onclick=\"loadDate(this)\" onmouseover=\"this.style.cursor='hand'\" id='" + yyyymmdd + "'";
        	if (selectedDate == yyyymmdd || allDatesView)
            	returnString = returnString + " style='color:red; font-weight:bold'>" + month + "/" + date + "(" + day + ")" + "</span>";
            else
            	returnString = returnString + " style='color:blue; font-weight:bold'>" + month + "/" + date + "(" + day + ")" + "</span>";
        }
        
        if (i<range) returnString = returnString + fill;
    }
    //alert(returnString);
    return returnString;
}

function getNewStartEndDate(paramObj, whatDoYouWant) {
	var startDate = paramObj.startDate;
	var endDate = paramObj.endDate;
	var selectedDate = paramObj.selectedDate;
	if (endDate == '')
		endDate = startDate;
	
	var startDateObj = toDateObj(startDate);
    var endDateObj = toDateObj(endDate);
    var selectedDateObj = toDateObj(selectedDate);
	var newStartDateObj;
	var newEndDateObj;
	var newSelectedDateObj;
	
    if (whatDoYouWant == 'previous') {
    	newStartDateObj = new Date(startDateObj.getTime() - (g_range+1)*g_one_day);
    	newEndDateObj = new Date(endDateObj.getTime() - (g_range+1)*g_one_day);
    	newSelectedDateObj = new Date(selectedDateObj.getTime() - (g_range+1)*g_one_day);
    }
    else { //'next'
    	newStartDateObj = new Date(startDateObj.getTime() + (g_range+1)*g_one_day);
    	newEndDateObj = new Date(endDateObj.getTime() + (g_range+1)*g_one_day);
    	newSelectedDateObj = new Date(selectedDateObj.getTime() + (g_range+1)*g_one_day);
    }
    
    var obj = new Object();
    obj.startDate = toYYYYMMDD(newStartDateObj);
    obj.endDate = toYYYYMMDD(newEndDateObj);
    obj.selectedDate = toYYYYMMDD(newSelectedDateObj);
    
    return obj;
}


function loadDate(spanObj) {
    /*
	for (i=0; i<=g_range; i++) {
    	var tmpObj = document.getElementById(g_dates[i]);
    	tmpObj.style.color = 'blue';
    }
    
    spanObj.style.color = 'red';
    */
	g_orderSheetParam.selectedDate = spanObj.id;
	g_orderSheetParam.allDatesView = false;
	//g_orderSheetParam.checkDBOrder = false;
	g_orderSheetParam.checkDBOrder = true;
	if (g_sheet_obj.confirmNavigation()) {
		g_sheet_obj.loadOrderSheet();
	}
}
