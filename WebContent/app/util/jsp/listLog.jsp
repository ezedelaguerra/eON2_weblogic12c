<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
  href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
  rel="stylesheet">
<title>eON List Logs</title>
</head>

<body>
  <div class="container">
    <form class="form-inline" action="listLogs.do" method="post">
      <div>
        <label for="password" class="control-label">Security Password: </label>
        <input id="password" name="password" type="password"
          class="form-control" placeholder="Security Password" />
        <button class="btn btn-info" onclick="getLogList()">Get Log
          List</button>
      </div>
      <input type="hidden" id="selectedFile" name="selectedFile" />
    </form>

    <c:forEach items="${errorList}" var="errorItem">
      <p />${errorItem}
    </c:forEach>
    <c:if test="${empty errorList}">
      <div class="container">
        <div class="container">
          <div>Type-in the file name to filter the list</div>
          <input id="filter" name="filter" type="text"
            class="filter form-control" placeholder="Filter Log List" />

          <div>Enter the password before clicking the link to download the
            file</div>
          <table class="table table-hover table-bordered" style="width: 50%">
            <thead>
              <tr>
                <td>Log Files</td>
              </tr>
            </thead>
            <tbody class="searchable">
              <c:forEach items="${list}" var="fileList">
                <tr>
                  <td><a href="#"
                      onclick="submitForm('${fileList.fileId}')">
                      ${fileList.filePath}</a></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </c:if>

  </div>

  <script src="http://code.jquery.com/jquery.js" ></script>
  <script
    src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
  <script>
			function submitForm(fileId) {
				var selectedFileIdElement = document
						.getElementById('selectedFile');
				selectedFileIdElement.value = fileId;
				document.forms[0].submit();
				clearSelectedFile();
				clearPassword();
			}

			function getLogList() {
				clearSelectedFile();
				document.forms[0].submit();
				clearPassword();
			}

			function clearPassword() {
				var passwordElement = document.getElementById('password');
				passwordElement.value = "";
			}

			function clearSelectedFile() {
				var selectedFileIdElement = document
						.getElementById('selectedFile');
				selectedFileIdElement.value = "";
			}
			$(document).ready(function() {
				$('#filter').keyup(function() {
					var rex = new RegExp($(this).val(), 'i');
					$('.searchable tr').hide();
					$('.searchable tr').filter(function() {
						return rex.test($(this).text());
					}).show();
				});
			});
		</script>

</body>
</html>

