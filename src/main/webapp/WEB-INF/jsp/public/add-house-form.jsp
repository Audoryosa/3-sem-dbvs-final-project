<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../common/header.jsp" %>
    <title>Užregistruoti naują namą</title>
</head>
<body>
<h2>Užpldykite formą</h2>
<hr/>

<form:form action="saveHouse" modelAttribute="house" method="POST">
    <form:hidden path="agentId"/>
    <form:hidden path="ownerId"/>

    <table class="table col-lg-4">

        <tr>
            <td><label><c:out value="Adresas"/>:</label></td>
            <td><form:input required="required" path="address"/></td>
        </tr>

        <tr>
            <td><label><c:out value="Plotas"/>:</label></td>
            <td><form:input required="required" path="size"/></td>
        </tr>

        <tr>
            <td><label><c:out value="Kaina"/>:</label></td>
            <td><form:input required="required" path="price"/></td>
        </tr>

        <tr>
            <td><label><c:out value="Statybos metai"/>:</label></td>
            <td><form:input required="required" path="buildYear"/></td>
        </tr>

        <tr>
            <td></td>
            <td><input class="btn btn-success" type="submit" value="<c:out value='Išsaugoti' />"/></td>
        </tr>
        </tbody>
    </table>

    <hr/>

    <div>
        <a class="btn btn-info" href="${pageContext.request.contextPath}/houses"><c:out
                value="Grįžti atgal"/></a>
    </div>

</form:form>

<%@ include file="../common/footer.jsp" %>
</body>
</html>