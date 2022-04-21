"use strict";

class Account {
    constructor(accountID, accountName, email, isAdmin) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.email = email;
        this.isAdmin = isAdmin;
    }
}

function checkAdmin(currentUser) {
    if (currentUser.isAdmin) {
        displayAdminOverview(currentUser);
    }
    else {
        removeAdminOverview();
    }
}

function removeAdminOverview() {
    $("#secondaryContent").html(``).hide();
}

function displayAdminOverview(currentUser) {
    $("#secondaryContent").append(adminOverviewHTML).show();

    ajaxCall("AccountList", {
        'user': JSON.stringify(currentUser)
    }, "POST", handleAdminOverviewResult);
}

function handleAdminOverviewResult(response) {
    var accountList = JSON.parse(response);

    $("#accountList").html(``);
    accountList.forEach(displayAccountButton)
}

function displayAccountButton(account) {
    $("#accountList").append(`
        <div>
            <button class="accountButton" id="accountButton" data-account='${JSON.stringify(account)}'>
                <div>
                    <p>${account.accountName}</p>
                </div>
            </button>
        </div>`);
}

var adminOverviewHTML = `
    <h2>Admin Overview</h2>
    <div class="subContent whiteBackground grid4Wrapper" id="accountList"></div>`;
