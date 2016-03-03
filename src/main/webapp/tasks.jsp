<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%-- import the core tags from jstl --%>
<!DOCTYPE html>
<html>
<head><title>Tasks</title></head>
<body>

<h1>Tasks</h1>

<%-- create ordered list --%>
<ol>
<%-- use forEach tag to loop through tasks request attribute from servlet --%>
<c:forEach var="task" items="${tasks}">
  <li>${task.description}</li><%-- print current task's description --%>
</c:forEach>
</ol>

</body>
</html>