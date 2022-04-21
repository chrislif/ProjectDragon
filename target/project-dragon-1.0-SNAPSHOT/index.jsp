<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/page/link/header.jsp"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
    <script type="text/javascript" src="index.js"></script>
    <script type="text/javascript" src="overview.js"></script>
    <script type="text/javascript" src="admin.js"></script>

    <main>
        <div id="mainContent" class="mainContent whiteBackground">
            <h2>Welcome to Project Dragon!</h2>
            <div class="subContent whiteBackground">
                <p>
                    This project is a web application that can be used to create and manage characters for the Table Top Role Playing Game (TTRPG) Dungeons and Dragons!
                </p>

                <p>
                    Please Create an Account or Login to Continue!
                </p>
            </div>
        </div>

        <div hidden id="secondaryContent" class="mainContent whiteBackground"></div>

        <div id="mainModal" class="modalBackground"></div></div>
    </main>
<jsp:include page="/page/link/footer.jsp"/>
