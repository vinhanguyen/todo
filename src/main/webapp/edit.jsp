<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Tasks</title></head>
<style>

.error {
  color: red;
}

</style>
<body>

<h1>Edit Task</h1>

<c:if test="${not empty errors}">
<h2 class="error">Errors:</h2>
<ul class="error">
  <c:forEach var="error" items="${errors}">
  <li>${error}</li>
  </c:forEach>
</ul>
</c:if>

<form action="<c:url value="/tasks/edit" />" method="post">
  <input type="hidden" name="id" value="${task.id}">
  <input type="text" name="description" value="${task.description}">
  <input type="submit" value="Submit">
</form>

</body>
</html>