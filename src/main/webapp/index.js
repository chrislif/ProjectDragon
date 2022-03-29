"use strict";

$(document).ready(() => {
    $("#login").click(showLoginForm);
    $("#createAccount").click(showCreateAccountForm);

    $(window).click(function(e) {
        if (e.target.id === "mainModal") {
            hideModal();
        }
    });
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

function loginUser() {
    $("#loginButton").attr('disabled', 'disabled');
    ajaxCall("Login", 
        {
            'email': $("#emailLogin").val(), 
            'password': $("#passwordLogin").val() 
        }, 
        "POST", handleLoginResult);
    
}

function createUser() {
    $("#createFinalButton").attr('disabled', 'disabled');
    if ($("#newAccountPassword").val() === $("#passwordCheck").val()) {
        ajaxCall("Account", 
            {
                'email': $("#newAccountEmail").val(), 
                'name': $("#newAccountName").val(), 
                'password': $("#newAccountPassword").val()
            }, 
            "POST", handleAccountCreateResult);
        $("#passwordErrorSpan").html("");
    } 
    else {
        $("#passwordErrorSpan").html("Passwords are not the same");
        if ($("#createFinalButton").attr('disabled')) $("#createFinalButton").removeAttr('disabled');
    }
}

function handleAccountCreateResult(response) {
    var result = JSON.parse(response); 

    hideModal();
    console.log(result);
}

function handleLoginResult(response) {
    var result = JSON.parse(response);

    hideModal();
    console.log(result);
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
            <input type="password" id="newAccountPassword" name="newAccountPassword" class="styledInput"><span id="passwordErrorSpan"></span>
            <br>
            <label for="passwordCheck" class="styledInputLabel primaryText">Re-enter Pasword</label>
            <br>
            <input type="password" id="passwordCheck" name="passwordCheck" class="styledInput">
            <br>
            <button type="button" id="createFinalButton" class="styledButton">Create</button>
        </div>
    </div>`;
