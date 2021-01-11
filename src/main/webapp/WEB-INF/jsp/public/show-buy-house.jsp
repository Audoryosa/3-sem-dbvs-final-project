<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../common/header.jsp" %>
    <title>Pirkti namą</title>
</head>
<body>
<h2>Užpildykite formą</h2>
<hr/>
<form:form action="sellHouse" modelAttribute="house" method="POST">
    <form:hidden path="address"/>
    <table class="table col-lg-4">

        <p class="lead"><strong>Pirkti namą adresu: <cite>${house.address}</cite>. Kaina: ${house.price} €</strong></p>

        <tr>
            <td><label data-toggle="tooltip" data-placement="bottom" title="Žmonės, kurie dar neturi namų">Galimi žmonės:</label></td>
            <td>
                <select name="chosenOwner">
                    <c:forEach items="${suggestedOwners}" var="owner">
                        <option value="${owner.id}">${owner.name} ${owner.surname}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>

        <tr>
            <td></td>
            <td><input class="btn btn-success" type="submit" value="<c:out value='Parduoti namą tam žmogui' />"/></td>
        </tr>
        </tbody>
    </table>
</form:form>

    <hr/>

    <div>
        <a class="btn btn-info" href="${pageContext.request.contextPath}/"><c:out
                value="Grįžti atgal"/></a>
    </div>


<%@ include file="../common/footer.jsp" %>
</body>
</html>