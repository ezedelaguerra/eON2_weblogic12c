<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Auto Download Schedule Maintenance</title>
		
		<script type="text/javaScript">

			function utilTrim(str){
				return str.replace(/^\s+|\s+$/g,"");
			}
		    function validateForm () {

				var isValid = true;

				var username = document.jobForm.username.value;
				var executionTime = document.jobForm.executionTime.value;
				var dateLastDownloadStr = document.jobForm.dateLastDownloadStr.value;
				var leadTime = document.jobForm.leadTime.value;
				var exceptDate = document.jobForm.exceptDate.value;
				var seller = document.jobForm.seller.value;
				var buyer = document.jobForm.buyer.value;
				var ftpIp = document.jobForm.ftpIp.value;
				var ftpId = document.jobForm.ftpId.value;
				var ftpPw = document.jobForm.ftpPw.value;
				var confirmFtpPw = document.jobForm.confirmFtpPw.value;

				if (utilTrim(username) == "" || utilTrim(executionTime) == "" ||
					utilTrim(dateLastDownloadStr) == "" ||	utilTrim(leadTime) == "" ||
					uutilTrim(confirmFtpPw) == "" ||	utilTrim(seller) == "" ||
					utilTrim(buyer) == "" ||	utilTrim(ftpIp) == "" ||
					utilTrim(ftpId) == "" ||	utilTrim(ftpId) == "") {
					
					alert("All fields should be populated");
					isValid = false;
				} 

				if (ftpPw != confirmFtpPw) {
					alert("Confirm password does not match");
					isValid = false;
				}
				
		    	return isValid;
		    }
	    </script>
		
	</head>
	<body>
		<form id="jobForm" name="jobForm" method="post" action="/eON/addJob.do" onsubmit="return validateForm();">

			<table>
				<tr>
					<td colspan="2"><b>Add Job</b></td>
				</tr>
				<tr>
					<td>Username: </td>
					<td> <input id = "username" name = "username" type = "text"  /> </td>
				</tr>
				<tr>
					<td>Time: </td>
					<td> <input id = "executionTime" name = "executionTime" type = "text"  /> </td>
				</tr>
				<tr>
					<td>Date Last Download: </td>
					<td> <input id = "dateLastDownloadStr" name = "dateLastDownloadStr" type = "text"  /> </td>
				</tr>
				<tr>
					<td>Lead Time: </td>
					<td> <input id = "leadTime" name = "leadTime" type = "text"  maxlength="5" /> </td>
				</tr>
				<tr>
					<td>Exception Dates: </td>
					<td> <input id = "exceptDate" name = "exceptDate" type = "text"  /> </td>
				</tr>
				<tr>
					<td>Selected Seller ID: </td>
					<td> <input id = "seller" name = "seller" type = "text"  /> </td>
				</tr>
				<tr>
					<td>Selected Buyer ID: </td>
					<td> <input id = "buyer" name = "buyer" type = "text"  /> </td>
				</tr>
				<tr>
					<td>Sheet Type: </td>
					<td>
						<select name="sheetTypeDesc">
							<c:forEach items="${sheets}" var="sheet" varStatus="item">
								<option value="${sheet.description}">${sheet.description}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Category: </td>
					<td>
						<select name="categoryId">
							<c:forEach items="${categories}" var="cat" varStatus="item">
								<option value="${cat.categoryId}">${cat.description}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Has Quantity: </td>
					<td> <input id = "hasQtyUI" name = "hasQtyUI" type = "checkbox" /> </td>
				</tr>
				<tr>
					<td>FTP_IP: </td>
					<td> <input id = "ftpIp" name = "ftpIp" type = "text"  /> </td>
				</tr>
				<tr>
					<td>FTP_ID: </td>
					<td> <input id = "ftpId" name = "ftpId" type = "text"  /> </td>
				</tr>
				<tr>
					<td>FTP_PW: </td>
					<td> <input id = "ftpPw" name = "ftpPw" type = "password"  /> </td>
				</tr>
				<tr>
					<td>Confirm FTP_PW: </td>
					<td> <input id = "confirmFtpPw" name = "confirmFtpPw" type = "password"  /> </td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				
				<tr>
					<td>Security Password: </td>
					<td> <input id = "password" name = "password" type = "password"  /> </td>
				</tr>
			</table>
			&nbsp; <br/> 
			<input type = "submit" value = "Add Job"/>
		</form>
	</body>
</html>