<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html>
	<body>
	
	    <!-- Phonebook form to enter new entries -->
		<div>
			<form action="sdcphonebook" method="post">
				Name: <input type="text" name="name" /><br/>
				Phonenumber: <input type="text" name="phonenumber" /><br/>
				<input type="submit" name="send" value="Send" /> 
			</form>
		</div>
	
	    <!-- Area to display content -->
		<div>
			<table border="1">
				<tr>
					<th>Name</th>
					<th>Phonenumber</th>
				</tr>
				<c:forEach items="${phonebookEntryList}" var="entry">
					<tr>
						<td>${entry.name}</td>
						<td>${entry.phonenumber}</td>
					</tr>
				</c:forEach> 
			</table>
		</div>
	</body>
</html>