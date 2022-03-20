"use strict";

$(document).ready(() => {
    $("#login").click(login);

    $(window).click(function(e) {
        if (e.target.id === "mainModal") {
            $("#mainModal").fadeOut(100);
        }
    });
});

var ajaxPost = (url, data, callback) => {
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        dataType: "JSON",
        success: callback,
        error: function (e) {
            alert(this.url);
        }
    });
};

function login() {
    $("#mainModal").html(
        `<div id="modalBox" class="modalBox">
            <span id="modalCloseButton" class="closeButton">&times;</span>
            <div id="modalHeader" class="modalHeader">
                <h2>Login</h2>
            </div>
            <br>
            <div id="modalContent" class="modalContent">
                <p>
                    test
                </p>
            </div>
        </div>`);
    
    $("#modalCloseButton").click(()=> {
        $("#mainModal").fadeOut(100);
    });
    
    $("#mainModal").fadeIn(200);
}

function logout() {
    alert("logout");
}




