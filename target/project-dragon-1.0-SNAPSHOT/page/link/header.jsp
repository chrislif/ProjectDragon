<%-- 
    Document   : header
    Created on : Mar 5, 2022, 4:18:11 PM
    Author     : chris
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project Dragon Character Creator</title>
        <link rel="stylesheet" href="page/link/style.css">
    </head>
    <body>
        <nav>
            <div class="navbar primaryBackground">
                <ul class="navlist">
                    <div class="navSection">
                        <h1 class="secondaryText">
                            Dragon Character Creator
                        </h1>
                    </div>
                    <div class="navSection">
                        <li>
                            <input type="button" class="navbutton secondaryBackground" id="createAccount" value="Create Account">
                        </li>
                        <li>
                            <input type="button" class="navbutton secondaryBackground" id="login" value="Login">
                        </li>
                    </div>
                </ul>
            </div>
        </nav>