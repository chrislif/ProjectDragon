"use strict";

function loadOverview() {
    var currentUser = JSON.parse(sessionStorage.getItem("currentUser"));

    $("#mainContent").html(overviewHTML);
    $("#overviewTitle").html(`${currentUser.accountName} Overview`);

    loadCharacters(currentUser);

    $("#showCharacterModalButton").click(showCreateCharacterForm);
}

function loadCharacters(currentUser) {

}

function showCreateCharacterForm() {
    $("#mainModal").html(newCharacterModalHTML);

    $("#createCharacterButton").click(createCharacter);
    $("#modalCloseButton").click(hideModal);

    showModal();
}

function createCharacter() {
    var currentUser = JSON.parse(sessionStorage.getItem("currentUser"));

    ajaxCall("Character", 
        {
            'user': JSON.stringify(currentUser),
            'characterName': $('#characterName').val()
        }, 
        "POST", handleCharacterCreateResult)
}

function handleCharacterCreateResult(response) {

    hideModal();

    console.log(response);
}

var overviewHTML = `
    <h2 id='overviewTitle'></h2>
    <div id='overviewCharacterList' class="subContent whiteBackground">
    </div>
    <button type="button" id="showCharacterModalButton" class="styledButton">Create Character</button>`;

var newCharacterModalHTML = `
    <div id="modalBox" class="modalBox whiteBackground">
        <span id="modalCloseButton" class="closeButton">&times;</span>
        <div id="modalHeader" class="modalHeader primaryBackground secondaryText">
            <h2>Create New Character</h2>
        </div>
        <br>
        <div id="modalContent" class="modalContent">
            <label for="characterName" class="styledInputLabel primaryText">Name</label>
            <br>
            <input type="text" id="characterName" name="characterName" class="styledInput">
            <br>
            <button type="button" id="createCharacterButton" class="styledButton">Create</button>
            <br>
        </div>
    </div>`;