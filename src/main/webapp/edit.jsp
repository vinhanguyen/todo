<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Tasks</title></head>
<body>

<h1>Edit Task</h1>

<form action="<c:url value="/tasks/edit" />" method="post">
  <input type="hidden" name="id" value="${task.id}">
  <input type="text" name="description" value="${task.description}">
  <input type="submit" value="Submit">
</form>

</body>
</html>