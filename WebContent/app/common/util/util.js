//create by : jovsab 
//date created : 02/09/2010 

function message(str) {
	alert(str);
}

function SetCookie() {
    if(arguments.length < 2) { return; }
    var n = arguments[0];
    var v = arguments[1];
    var d = 0;
    if(arguments.length > 2) { d = parseInt(arguments[2]); }
    var exp = '';
    if(d > 0) {
        var now = new Date();
        then = now.getTime() + (d * 24 * 60 * 60 * 1000);
        now.setTime(then);
        exp = '; expires=' + now.toGMTString();
        }
    document.cookie = n + "=" + escape(String(v)) + '; path=/' + exp;
}

function ReadCookie(n) {
    var cookiecontent = new String();
    if(document.cookie.length > 0) {
        var cookiename = n+ '=';
        var cookiebegin = document.cookie.indexOf(cookiename);
        var cookieend = 0;
        if(cookiebegin > -1) {
            cookiebegin += cookiename.length;
            cookieend = document.cookie.indexOf(";",cookiebegin);
            if(cookieend < cookiebegin) { cookieend = document.cookie.length; }
            cookiecontent = document.cookie.substring(cookiebegin,cookieend);
            }
        }
    return unescape(cookiecontent);
}

function popmessage(str) {
    alert(str);
}

/*
 * reqFields = textbox/input
 * listFields = list
 * type = seller/buyer
 * flag = value 0, 1: company type is seller but creating user like admin, choai etc..
 * */
function checkIfFieldIsValid(reqFields, listFields, type, flag) {
	var notError = true;
	for (var i = 0; i < reqFields.length; i ++ ) {
		if(reqFields[i].getUIValue().length<1) {
			failedInputBackgroundColor(reqFields[i]);
			notError = false;
		}
		else {
			successInputBackgroundColor(reqFields[i]);
		}
	}
	if (flag == 1) {
		if (type.toLowerCase()=="seller") {
			if(listFields.getItems().length<1) {
				failedListBackgroundColor(listFields);
				notError = false;
			}
			else {
				successListBackgroundColor(listFields);
			}
		}
	}
	
	return notError;
}

function validateContactNumberFields(allFields) {
	var notError = true;
	for (var i = 0; i < allFields.length; i ++ ) {
		if (isDigit(allFields[i].getUIValue())) {
			var prf=allFields[i].get(0);
			var value = allFields[i].getUIValue();
			if(!prf.box._checkValid(prf,value)) {
				failedInputBackgroundColor(allFields[i]);
				notError = false;
			}else{
				successInputBackgroundColor(allFields[i]);
			}
		}
	}
	return notError;
}

function checkEmail(email) {
	var notError = true;
	if (email.getUIValue().length > 0 ) {
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	    if(reg.test(email.getUIValue()) == false) {
	    	failedInputBackgroundColor(email);
	    	notError = false;
	    }else{
	    	successInputBackgroundColor(email);
	    }
	}
	return notError;
}

function clearBackGroundColor(allFields) {
	for (var i = 0; i < allFields.length; i ++ ) {
		successInputBackgroundColor(allFields[i]);
	}
}

function failedInputBackgroundColor(field) {
	field.setCustomStyle({"INPUT":"background-color:#f0e68c;"});
}

function successInputBackgroundColor(field) {
	field.setCustomStyle({"INPUT":"background-color:#ffffff;"});
}

function failedListBackgroundColor(field) {
	field.setCustomStyle({"KEY":"background-color:#f0e68c;"});
}

function successListBackgroundColor(field) {
	field.setCustomStyle({"KEY":"background-color:#ffffff;"});
}

function utilTrim(str){
	return str.replace(/^\s+|\s+$/g,"");
}

function toYYYYMMDD(dateObj) {
	if (dateObj == 'NaN')
		return '';
	year = getMalupetYear(dateObj);
    month = pad(dateObj.getMonth()+1, 2);
    date = pad(dateObj.getDate(), 2);
    yyyymmdd = year + month + date + '';
    
    return yyyymmdd;
}

function toDateObj(YYYYMMDD) {
	year = YYYYMMDD.substr(0,4);
	month = parseInt(YYYYMMDD.substr(4,2), 10) - 1 ;
	date = YYYYMMDD.substr(6,2);
	
	dateObj = new Date(year, month, date);
	
	return dateObj;
}

function pad(number, length) {
    var str = '' + number;
    while (str.length < length) {
        str = '0' + str;
    }
    return str;
}

function getMalupetYear(dateObj) {
	var thisYear = dateObj.getFullYear();
//	if (thisYear < 2000)  {
//		thisYear = ('' + thisYear).substring(0,3);
//		thisYear = parseInt(thisYear) + 1900;
//	}
	return thisYear;
}

function validateResponse(jsonResponse) {
	var obj = _.unserialize(jsonResponse);
	if (obj.fail == 'true') {
		//linb.pop("responseCheck", obj.globals, "OK");
		var globals = new Array();
        globals = obj.globals;
        if (globals!=null && globals[0]!="")alert(globals[0]);
        else alert("Error. Invalid name entry.");
	} else if (obj.isSessionExpired != null && obj.isSessionExpired == 'true') {
		window.location = "../../login/jsp/login.jsp";
	}
	if (obj.message != null) {
		window.location = "../../login/jsp/exception.jsp";
	}
}

function validateSigmaResponse(resObj) {
	if (resObj.isSessionExpired == 'true') {
		window.location = "../../login/jsp/login.jsp";
	} 
	if (resObj.message != null) {
		window.location = "../../login/jsp/exception.jsp";
	}
}

function IsNumeric(sText)

{
   var ValidChars = "0123456789.";
   var IsNumber=true;
   var Char;

   for (i = 0; i < sText.length && IsNumber == true; i++) 
      { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         }
      }
   return IsNumber;
   
}

function isDate(dateStr) {

	var datePat = /^(\d{4})\/(\d{1,2})\/(\d{1,2})$/;
	var matchArray = dateStr.match(datePat); // is the format ok?
	if (matchArray == null) {
		//alert("Please enter date as either mm/dd/yyyy or mm-dd-yyyy.");
		return false;
	}
	
	//alert(_.serialize(matchArray));
	
	// p@rse date into variables
	year = parseInt(matchArray[1], 10); // year
	month = parseInt(matchArray[2], 10); // month
	day = parseInt(matchArray[3], 10); // day
	
//	alert('day: ' + day);
//	alert('month: ' + month);
//	alert('year: ' + year);
	if (month < 1 || month > 12) { // check month range
		//alert("Month must be between 1 and 12.");
		return false;
	}
	
	if (day < 1 || day > 31) {
		//alert("Day must be between 1 and 31.");
		return false;
	}
	
	if ((month==4 || month==6 || month==9 || month==11) && day==31) {
		//alert("Month "+month+" doesn`t have 31 days!");
		return false;
	}
	
	if (month == 2) { // check for february 29th
	var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
		if (day > 29 || (day==29 && !isleap)) {
		//alert("February " + year + " doesn`t have " + day + " days!");
		return false;
		}
	}
	
	if (parseFloat(year) > 1900  &&  parseFloat(year) <  2100) 
		return true;
	else
		return false;
	return true; // date is valid
}

function toJapaneseCurrency(nStr) {
	//return "&yen;" + addCommas(nStr);
	return "¥" + addCommas(nStr);
}

function toPercentage(nStr) {
	//return "&yen;" + addCommas(nStr);
	return addCommas(nStr) + "%";
}

function addCommas(nStr) {
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}

function isDigit(number) {
	var reg = /\d/;
	if(reg.test(number) == true) {
		return true;
	}
	else {
		return false;
	}
}

function setFocus(txtfield) {
	var alias = txtfield.getDomId();
	linb(alias).nextFocus();
}

function checkUnsavedChanges(){
	var f = document.getElementById('dataTableIframe');
	if(f!=null) {
		if(f.contentWindow.isDirty()){
			alert(linb.Locale[linb.getLang()].app.caption['alertUnsavedChanges']);
			return true;
		}
	}
	return false;
}

function discover(obj) {
    var msg = "";
    for (o in obj) {
        msg += "\t" + o;
    }    
    alert(msg);
}

function discoverFull(obj) {
    var msg = "";
    for (o in obj) {
        msg += "\t" + o + "=" + eval("obj." + o);
    }    
    alert(msg);
}

function retrieveInfoMessage(jsonResponse){
    var obj = _.unserialize(jsonResponse);
    if (obj.infoMsg && obj.infoMsg.trim() != ""){
        return obj.infoMsg;
    }
	return "";
}