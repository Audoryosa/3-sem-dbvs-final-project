<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="../common/header.jsp" %>
    <title>Parduoti namą</title>
</head>
<body>
<h2>Užpildykite formą</h2>
<hr/>
<form:form action="sellOwnersHouse" modelAttribute="house" method="POST">
    <form:hidden path="address"/>
    <table class="table col-lg-4">

        <p class="lead"><strong>Parduoti namą adresu <cite>${house.address}</cite>: </strong></p>
        <span class="lead"><strong>Dabartinis savininkas: ${owner} </strong></span>

        <tr>
            <td><label data-toggle="tooltip" data-placement="bottom" title="Showing people that have no houses yet">Galimi agentai (stažas):</label></td>
            <td>
                <select name="chosenAgent">
                    <c:forEach items="${agents}" var="agent">
                        <option value="${agent.id}">${agent.name} ${agent.surname} (${agent.experience})</option>
                    </c:forEach>
                </select>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <blockquote class="blockquote text-center">
                    <footer class="blockquote-footer">Jei norite parduoti namą, turite jam priskirti agentą</footer>
                </blockquote>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input class="btn btn-success" type="submit" value="<c:out value='Parduoti' />"/></td>
        </tr>
        </tbody>
    </table>
</form:form>

    <hr/>

    <div>
        <a class="btn btn-info" href="${pageContext.request.contextPath}/"><c:out
                value="Go back"/></a>
    </div>


<%@ include file="../common/footer.jsp" %>
</body>
</html>