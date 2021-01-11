<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Auto Download Schedule Maintenance</title>
	</head>
	<body>
		<form method="post" action="/eON/confirmJob.do" >

			<p style="color:red;">${response}</p>

			<table>
				<tr>
					<td colspan="2">Confirm Job</td>
				</tr>
				<tr>
					<td>User Id: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.username}"/>
						<input id = "username" name = "username" type = "hidden" value="${ads.username}"/>
					</td>
				</tr>
				<tr>
					<td>Time: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.executionTime}"/>
						<input id = "executionTime" name = "executionTime" type = "hidden" value="${ads.executionTime}"/>
					</td>
				</tr>
				<tr>
					<td>Date Last Download: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.dateLastDownloadStr}"/>
						<input id = "dateLastDownloadStr" name = "dateLastDownloadStr" type = "hidden" value="${ads.dateLastDownloadStr}"/>
					</td>
				</tr>
				<tr>
					<td>Lead Time: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.leadTime}"/>
						<input id = "leadTime" name = "leadTime" type = "hidden" value="${ads.leadTime}"/>
					</td>
				</tr>
				<tr>
					<td>Exception Dates: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.exceptDate}"/>
						<input id = "exceptDate" name = "exceptDate" type = "hidden" value="${ads.exceptDate}"/>
					</td>
				</tr>
				<tr>
					<td>Selected Seller ID: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.seller}"/>
						<input id = "seller" name = "seller" type = "hidden" value="${ads.seller}"/>
					</td>
				</tr>
				<tr>
					<td>Selected Buyer ID: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.buyer}"/>
						<input id = "buyer" name = "buyer" type = "hidden" value="${ads.buyer}"/>
					</td>
				</tr>
				<tr>
					<td>Sheet Type: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.sheetTypeDesc}"/>
						<input id = "sheetTypeId" name = "sheetTypeId" type = "hidden" value="${ads.sheetTypeId}"/>
						<input id = "sheetTypeDesc" name = "sheetTypeDesc" type = "hidden" value="${ads.sheetTypeDesc}"/>
					</td>
				</tr>
				<tr>
					<td>Category: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.categoryDesc}"/>
						<input id = "categoryId" name = "categoryId" type = "hidden" value="${ads.categoryId}"/>
					</td>
				</tr>
				<tr>
					<td>Has Quantity: </td>
					<td>
						<input type = "checkbox" disabled="disabled" ${ads.hasQtyUI} />
						<input id = "hasQtyUI" name = "hasQtyUI" type = "hidden" value="${ads.hasQtyUI}"/>
					</td>
				</tr>
				<tr>
					<td>FTP_IP: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.ftpIp}"/>
						<input id = "ftpIp" name = "ftpIp" type = "hidden" value="${ads.ftpIp}"/>
					</td>
				</tr>
				<tr>
					<td>FTP_ID: </td>
					<td>
						<input type = "text" disabled="disabled" value="${ads.ftpId}"/>
						<input id = "ftpId" name = "ftpId" type = "hidden" value="${ads.ftpId}"/>
					</td>
				</tr>
				<tr>
					<td>FTP_PW: </td>
					<td>
						<input id = "ftpPw" name = "ftpPw" type = "hidden" value="${ads.ftpPw}"/>
					</td>
				</tr>
				<tr>
					<td>Confirm FTP_PW: </td>
					<td>
						<input id = "confirmFtpPw" name = "confirmFtpPw" type = "hidden" value="${ads.confirmFtpPw}"/>
					</td>
				</tr>
			</table>
			
			<c:if test="${empty response}">
				<input type = "submit" value = "Confirm" />
			</c:if>
			
		</form>
	</body>
</html>