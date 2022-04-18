<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/page/link/header.jsp"/>
    <main>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
        <script type="text/javascript" src="page/characterManager.js"></script>

        <div id="mainContent" class="mainContent whiteBackground">
            <h2>Character Overview</h2>
            <div class="subContent whiteBackground">
                <p>
                    This is an individual character page
                </p>
            </div>

            <form action="Overview" method="GET">
                <button type="Submit" class="styledButton">To Overview</button>
            </form>
        </div>

        <div id="mainModal" class="modalBackground"></div>
        </div>
    </main>
<jsp:include page="/page/link/footer.jsp"/>
