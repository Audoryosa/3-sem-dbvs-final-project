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

    <title>Namų sąrašas</title>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#"><c:out value="Namų detalesnė informacija"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/">Namai <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Kiti sąrašai
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/houses">Namų sąrašas</a>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/owners">Savininkų sąrašas</a>
                    </div>
                </li>
            </ul>
            <form:form class="form-inline my-2 my-lg-0" action="searchHouseInfo" method="GET">
                <input class="form-control mr-sm-2" type="search" placeholder="Paieška" aria-label="Paieška" name="ownersSearchInput" id="search">

                <script>
                    $(document).ready(function () {
                        $("#search").autocomplete({
                            source: "${pageContext. request. contextPath}/houseDataSuggestions"
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
                <th><c:out value="Adresas"/></th>
                <th><c:out value="Kaina"/></th>
                <th><c:out value="Savininko vardas"/></th>
                <th><c:out value="Savininko pavardė"/></th>
                <th><c:out value="Agento pavardė"/></th>
            </tr>
            </thead>

            <c:forEach var="tempHouse" items="${houseData}">

                <tr>
                    <td> ${tempHouse.address}</td>
                    <td> ${tempHouse.price}</td>
                    <td> ${tempHouse.ownerName}</td>
                    <td> ${tempHouse.ownerSurname}</td>
                    <td> ${tempHouse.agentSurname}</td>
                </tr>
            </c:forEach>
        </table>

    </div>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</body>
</html>