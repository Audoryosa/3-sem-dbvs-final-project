<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../common/header.jsp" %>
    <title>Atnaujinti savininko informaciją</title>
</head>
<body>
<h2>Užpildykite formą</h2>
<hr/>

<form:form action="updateOwner" modelAttribute="owner" method="POST">
    <form:hidden path="id"/>

    <table class="table col-lg-4">
        <tbody>

        <tr>
            <td><label><c:out value="Vardas"/>:</label></td>
            <td><form:input required="required" path="name"/></td>
        </tr>

        <tr>
            <td><label><c:out value="Pavardė"/>:</label></td>
            <td><form:input required="required" path="surname"/></td>
        </tr>

        <tr>
            <td><label><c:out value="Adresas"/>:</label></td>
            <td><form:input required="required" path="address"/></td>
        </tr>

        <tr>
            <td><label><c:out value="Šeimos dydis"/>:</label></td>
            <td><form:input required="required" path="familySize"/></td>
        </tr>

        <tr>
            <td><label><c:out value="Pajamos"/>:</label></td>
            <td><form:input required="required" path="income"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input class="btn btn-success" type="submit" value="<c:out value='Išsaugoti' />"/></td>
        </tr>
        </tbody>
    </table>

    <hr/>

    <div>
        <a class="btn btn-info" href="${pageContext.request.contextPath}/owners"><c:out
                value="Grįžti atgal"/></a>
    </div>

</form:form>

<%@ include file="../common/footer.jsp" %>
</body>
</html>