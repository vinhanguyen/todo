<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%-- import the core tags from jstl --%>
<!DOCTYPE html>
<html>
<head><title>Tasks</title></head>
<body>

<%-- display user request attribute set in servlet --%>
<h1>${user}'s tasks</h1>

<%-- display any errors in list --%>
<c:if test="${not empty errors}">
<p style="color: red;">${errors}</p>
</c:if>

<%-- form to submit task --%>
<form action="<c:url value="/tasks" />" method="post">
  <input type="text" name="description">
  <input type="submit" value="Add">
</form>

<%-- create ordered list --%>
<ol>
<%-- use forEach tag to loop through tasks request attribute from servlet --%>
<c:forEach var="task" items="${tasks}">
  <li>${task.description}</li><%-- print current task's description --%>
</c:forEach>
</ol>

</body>
</html>