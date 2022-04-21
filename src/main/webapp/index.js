"use strict";

$(document).ready(() => {
    var currentUser = JSON.parse(sessionStorage.getItem('currentUser'));

    if (currentUser == null) {
        $("#navButtonSection").html(navButtonSectionLoggedOutHTML);
        $("#login").click(showLoginForm);
        $("#createAccount").click(showCreateAccountForm);
    } 
    else {
        $("#navButtonSection").html(navButtonSectionLoggedInHTML);
        $("#logout").click(logoutUser);

        loadOverview();
    }
});

function showLoginForm() {
    $("#mainModal").html(loginModalHtml);
    
    $("#loginButton").click(loginUser);
    $("#modalCloseButton").click(hideModal);
    
    showModal();
}

function showCreateAccountForm() {
    $("#mainModal").html(createAccountModalHtml);

    $("#createFinalButton").click(createUser);
    $("#modalCloseButton").click(hideModal);
    
    showModal();
}

function hideModal() {
    $("#mainModal").fadeOut(200);
}

function showModal() {
    $("#mainModal").fadeIn(200);
}

function createUser() {
    $("#createFinalButton").attr('disabled', 'disabled');
    if ($("#newAccountPassword").val() === $("#passwordCheck").val()) {
        ajaxCall("Account", {
                'email': $("#newAccountEmail").val(), 
                'name': $("#newAccountName").val(), 
                'password': $("#newAccountPassword").val()
            }, "POST", handleAccountCreateResult);
        $("#accountErrorSpan").html("");
    } 
    else {
        $("#accountErrorSpan").html("Passwords are not the same");
        if ($("#createFinalButton").attr('disabled')) $("#createFinalButton").removeAttr('disabled');
    }
}

function handleAccountCreateResult(response) {
    var result = JSON.parse(response);

    if (result.hasOwnProperty('accountID')) {
        $("#navButtonSection").html(navButtonSectionLoggedInHTML);
        $("#logout").click(logoutUser);

        sessionStorage.setItem('currentUser', JSON.stringify(result));

        hideModal();

        loadOverview();
    }
    else {
        $("#accountErrorSpan").html("Error - Check console");

        $("#newAccountPassword").val('');
        $("#passwordCheck").val('');

        if ($("#createFinalButton").attr('disabled')) $("#createFinalButton").removeAttr('disabled');
    }
}

function loginUser() {
    $("#loginButton").attr('disabled', 'disabled');
    ajaxCall("Login", {
            'email': $("#emailLogin").val(), 
            'password': $("#passwordLogin").val() 
        }, "POST", handleLoginResult);
}

function handleLoginResult(response) {
    var result = JSON.parse(response);

    if (result.hasOwnProperty('accountID')) {
        $("#navButtonSection").html(navButtonSectionLoggedInHTML);
        $("#logout").click(logoutUser);

        sessionStorage.setItem('currentUser', JSON.stringify(result));

        hideModal();

        loadOverview();
    }
    else {
        $("#loginErrorSpan").html(`Invalid Credentials`);
        $("#passwordLogin").val('');
        console.log(response);

        if ($("#loginButton").attr('disabled')) $("#loginButton").removeAttr('disabled');
    }
}

function logoutUser() {
    sessionStorage.setItem('currentUser', null);

    $("#navButtonSection").html(navButtonSectionLoggedOutHTML);

    $("#login").click(showLoginForm);
    $("#createAccount").click(showCreateAccountForm);

    removeAdminOverview();
    $("#mainContent").html(defaultMainContent);
}

var ajaxCall = (url, data, type, callback) => {
    $.ajax({
        type: type,
        url: url,
        data: data,
        dataType: "JSON",
        success: callback,
        error: function (e) {
            console.log(e);
        }
    });
};

var loginModalHtml = `
    <div id="modalBox" class="modalBox loginBox whiteBackground">
        <span id="modalCloseButton" class="closeButton">&times;</span>
        <div id="modalHeader" class="modalHeader primaryBackground secondaryText">
            <h2>Login</h2>
        </div>
        <br>
        <div id="modalContent" class="modalContent">
            <label for="emailLogin" class="styledInputLabel primaryText">Email</label>
            <br>
            <input type="text" id="emailLogin" name="emailLogin" class="styledInput">
            <br>
            <label for="passwordLogin" class="styledInputLabel primaryText">Pasword</label>
            <br>
            <input type="password" id="passwordLogin" name="passwordLogin" class="styledInput">
            <br>
            <button type="button" id="loginButton" class="styledButton">Login</button>
            <br>
            <span id="loginErrorSpan" style="color:red"></span>
        </div>
    </div>`;

var createAccountModalHtml = `
    <div id="modalBox" class="modalBox createBox whiteBackground">
        <span id="modalCloseButton" class="closeButton">&times;</span>
        <div id="modalHeader" class="modalHeader primaryBackground secondaryText">
            <h2>Create New Account</h2>
        </div>
        <br>
        <div id="modalContent" class="modalContent">
            <label for="newAccountEmail" class="styledInputLabel primaryText">Email</label>
            <br>
            <input type="text" id="newAccountEmail" name="newAccountEmail" class="styledInput">
            <br>
            <label for="newAccountName" class="styledInputLabel primaryText">Account Name</label>
            <br>
            <input type="text" id="newAccountName" name="newAccountName" class="styledInput">
            <br>
            <label for="newAccountPassword" class="styledInputLabel primaryText">Pasword</label>
            <br>
            <input type="password" id="newAccountPassword" name="newAccountPassword" class="styledInput">
            <br>
            <label for="passwordCheck" class="styledInputLabel primaryText">Re-enter Pasword</label>
            <br>
            <input type="password" id="passwordCheck" name="passwordCheck" class="styledInput">
            <br>
            <button type="button" id="createFinalButton" class="styledButton">Create</button>
            <br>
            <span id="accountErrorSpan" style="color:red"></span>
        </div>
    </div>`;

var navButtonSectionLoggedInHTML = `
    <li>
        <input type="button" class="navbutton secondaryBackground" id="logout" value="Logout">
    </li>`;

var navButtonSectionLoggedOutHTML = `
    <li>
        <input type="button" class="navbutton secondaryBackground" id="createAccount" value="Create Account">
    </li>
    <li>
        <input type="button" class="navbutton secondaryBackground" id="login" value="Login">
    </li>`;

var defaultMainContent = `            
    <h2>Welcome to Project Dragon!</h2>
    <div class="subContent whiteBackground">
        <p>
            This project is a web application that can be used to create and manage characters for the Table Top Role Playing Game (TTRPG) Dungeons and Dragons!
        </p>

        <p>
            Please Create an Account or Login to Continue!
        </p>
    </div>`;
