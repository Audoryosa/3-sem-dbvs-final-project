<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">

    <script src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Owner Page</title>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#"><c:out value="Savininkų sąrašas"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/">Namai <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <input type="button"onclick="window.location.href='showAddOwnerForm'; return false;" class="nav-link" value="Užregistruoti naują savininką"/>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Kiti sąrašai
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/houses">Namų sarašas</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/houseData">Detalus namų sąrašas</a>
                    </div>
                </li>
            </ul>
            <form:form class="form-inline my-2 my-lg-0" action="searchOwners" method="GET">
                <input class="form-control mr-sm-2" type="search" placeholder="Paieška" aria-label="Paieška" name="ownersSearchInput" id="search">

                <script>
                    $(document).ready(function () {
                        $("#search").autocomplete({
                            source: "${pageContext. request. contextPath}/ownersSuggestions"
                        });
                    });
                </script>

                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Ieškoti</button>
            </form:form>
        </div>
    </nav>

    <div id="content">
        <table class="table table-stripped table-hover">
            <thead class="table-dark">
                <tr>
                    <th><c:out value="Id"/></th>
                    <th><c:out value="Vardas"/></th>
                    <th><c:out value="Pavardė"/></th>
                    <th><c:out value="Adresas"/></th>
                    <th><c:out value="Šeimos dydis"/></th>
                    <th><c:out value="Pajamos"/></th>
                    <th><c:out value="Veiksmas"/></th>
                </tr>
            </thead>

<%--            <c:if test="${not error.isSuccess}">--%>
<%--                <script>--%>
<%--                    alert(${error.errorMessage});--%>
<%--                </script>--%>
<%--            </c:if>--%>

            <c:forEach var="tempOwner" items="${owners}">

                <c:url var="updateLink" value="/showFormUpdate">
                    <c:param name="id" value="${tempOwner.id}"/>
                </c:url>

                <c:url var="deleteLink" value="/deleteOwner">
                    <c:param name="ownerId" value="${tempOwner.id}"/>
                </c:url>

                <tr>
                    <td> ${tempOwner.id}</td>
                    <td> ${tempOwner.name}</td>
                    <td> ${tempOwner.surname}</td>
                    <td> ${tempOwner.address}</td>
                    <td> ${tempOwner.familySize}</td>
                    <td> ${tempOwner.income}</td>
                    <td><a href="${updateLink}"><c:out value="Atnaujinti"/></a> | <a href="${deleteLink}"
                         onclick="if (!(confirm('Ar tikrai norite ištrinti?'))) return false;">
                        <c:out value="Ištrinti"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>