<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Auto Download Schedule Maintenance</title>
		
	</head>
	<body>
		<form id="jobForm" name="jobForm" method="post" action="/eON/list.do">
			<table>
				<tr>
					<td colspan="2"><b>Job Details</b></td>
				</tr>
				<tr>
					<td>Username: </td>
					<td>${autoDl.username}</td>
				</tr>
				<tr>
					<td>Time: </td>
					<td>${autoDl.executionTime}</td>
				</tr>
				<tr>
					<td>Date Last Download: </td>
					<td>${autoDl.dateLastDownloadStr}</td>
				</tr>
				<tr>
					<td>Lead Time: </td>
					<td>${autoDl.leadTime}</td>
				</tr>
				<tr>
					<td>Exception Dates: </td>
					<td>${autoDl.exceptDate}</td>
				</tr>
				<tr>
					<td>Selected Seller ID: </td>
					<td>${autoDl.seller}</td>
				</tr>
				<tr>
					<td>Selected Buyer ID: </td>
					<td>${autoDl.buyer}</td>
				</tr>
				<tr>
					<td>Sheet Type: </td>
					<td>${autoDl.sheetTypeDesc}</td>
				</tr>
				<tr>
					<td>Category: </td>
					<td>${autoDl.categoryDesc}
					<c:if test="${autoDl.categoryDesc == null || autoDl.categoryDesc == ''}">ALL</c:if>
					</td>
				</tr>
				<tr>
					<td>Has Quantity: </td>
					<td> <input id = "hasQtyUI" name = "hasQtyUI" type = "checkbox" disabled 
						<c:if test="${autoDl.hasQty == '1'}">
						checked="checked"
						</c:if>
						/> 
					</td>
				</tr>
				<tr>
					<td>FTP_IP: </td>
					<td>${autoDl.ftpIp}</td>
				</tr>
				<tr>
					<td>FTP_ID: </td>
					<td>${autoDl.ftpId}</td>
				</tr>
			</table>
			&nbsp; <br/> 
			<input type = "submit" value = "Close"/>
		</form>
	</body>
</html>