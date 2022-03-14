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
        `<div id="modalBox" class="modalContent">
            <span id="modalCloseButton" class="closeButton">&times;</span>
            <div id="modalContent">
            </div>
        </div>`);
    
    $("#modalCloseButton").click(()=> {
        $("#mainModal").fadeOut(100);
    });
    
    $("#mainModal").fadeIn( 200, 
        ajaxPost("public", {action: "Login"}, (result) => {
            $("#modalContent").text(result);
        })
    );
}

function logout() {
    alert("logout");
}




