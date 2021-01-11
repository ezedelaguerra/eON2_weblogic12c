<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
	
		<c:url value="/deleteJob.do" var="delete_job_url">
			<c:param name="scheduleId"></c:param>
		</c:url>
		
		<c:url value="/reloadJobs.do" var="reload_job_url" />
		
		<c:url value="/details.do" var="details_url">
			<c:param name="scheduleId"></c:param>
		</c:url>
	
		<script type="text/javaScript">
			function onStart (scheduleId) {
				alert("start " + scheduleId);
			}
			
			function onStop (scheduleId) {
				alert("stop " + scheduleId);
			}

			function onDelete (scheduleId) {
				var res = confirm("Are you sure you want to delete this job?");
				if (res) {
					var url = "${delete_job_url}" + scheduleId;
					url = url + "&password=" + document.getElementById("password").value;	
					//alert(url);
					location.href = url;
				}
			}

			function onReloadSchedule () {
				var url = "${reload_job_url}";
				location.href = url;
			}

			function onDetails (scheduleId) {
				var url = "${details_url}" + scheduleId;
				url = url + "&password=" + document.getElementById("password").value;
				location.href = url;
			}
		</script>
		
		<title>Auto Download Schedule Maintenance</title>
	</head>

	<body>
	
		<c:if test="${status eq 1}">
			<p style="color: red;">Done reloading.</p>
		</c:if>
	
		<table>
				<tr>
					<td>Security Password: </td>
					<td> <input id = "password" name = "password" type = "password"  /> </td>
				</tr>
		</table>
		<table border="1">
			<thead style="font-weight: bold">
				<tr>
					<td rowspan="2">User ID</td>
					<td rowspan="2">Execution Time</td>
					<td rowspan="2">Date Last Download</td>
					<td rowspan="2">Sheet Type ID</td>
					<td rowspan="2">Lead Time</td>
					<td rowspan="2">Last Run Date</td>
					<td rowspan="2">IP Address</td>
					<td colspan="3" align="center">Action</td>
				</tr>
				<tr>
					<td colspan="3" align="center">
						<c:choose>
							<c:when test="${status eq 1}">
								<input type = "button" value = "Reload All Schedules" onclick="onReloadSchedule()" disabled="disabled"/>
							</c:when>
							<c:otherwise>
								<input type = "button" value = "Reload All Schedules" onclick="onReloadSchedule()"/>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="autoDl" varStatus="rowItem">
					<tr bgcolor="${autoDl.color}">
						<td><a href="#" id="${autoDl.scheduleCsvId}" onClick="onDetails(this.id);">${autoDl.username}</td>
						<td>${autoDl.executionTime}</td>
						<td>${autoDl.dateLastDownload}</td>
						<td>${autoDl.sheetTypeId}</td>
						<td>${autoDl.leadTime}</td>
						<td>${autoDl.lastRunDate}</td>
						<td>${autoDl.ftpIp}</td>
						<td>
							<input id = "${autoDl.scheduleCsvId}" type = "button" value = "Start" onclick="onStart(this.id)" disabled="disabled"/>
							<input id = "${autoDl.scheduleCsvId}" type = "button" value = "Stop" onclick="onStop(this.id)" disabled="disabled"/>
							<input id = "${autoDl.scheduleCsvId}" type = "button" value = "Delete" onclick="onDelete(this.id)"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		
	</body>
</html>

