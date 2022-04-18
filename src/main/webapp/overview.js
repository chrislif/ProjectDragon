"use strict";

class DnDClass {
    constructor(classID, name, level) {
        this.classID = classID;
        this.name = name;
        this.level = level;
    }
}

class CharacterStat {
    constructor(constitution, strength, dexterity, wisdom, intelligence, charisma) {
        this.constitution = constitution;
        this.strength = strength;
        this.dexterity = dexterity;
        this.wisdom = wisdom;
        this.intelligence = intelligence;
        this.charisma = charisma;
    }
}

class Character {
    constructor(name, race, healthMax, currentHealth, account, stat, dndClassList) {
        this.name = name;
        this.race = race;
        this.healthMax = healthMax;
        this.currentHealth = currentHealth;
        this.account = account;
        this.stat = stat;
        this.dndClassList = dndClassList;
    }
}

function loadOverview() {
    var currentUser = JSON.parse(sessionStorage.getItem("currentUser"));

    $("#mainContent").html(overviewHTML);
    $("#overviewTitle").html(`${currentUser.accountName} Overview`);

    loadCharacters(currentUser);

    $("#showCharacterModalButton").click(showCreateCharacterForm);
}

function loadCharacters(currentUser) {
    ajaxCall("CharacterList", {
        'user': JSON.stringify(currentUser)
        }, "POST", handleCharacterListResult);
}

function handleCharacterListResult(response) {
    var characterList = JSON.parse(response);

    $("#overviewCharacterList").html("");
    characterList.forEach((character) => {
        var dndClassName = "";
        var dndClassLevel = 0;

        character.dndClassList.forEach((dndClass) => {
            dndClassName += " " + dndClass.name;
            dndClassLevel += dndClass.level;
        });

        $("#overviewCharacterList").append(`
            <form action="DisplayCharacter" method="GET">
                <input type="hidden" name="characterID" value="${character.characterID}">
                <button type="Submit" class="characterButton">
                    <div>
                        <p>Name : ${character.name}</p>
                        <p>Class : ${dndClassName}</p>
                        <p>Level : ${dndClassLevel}</p>
                    </div>
                </button>
            </form>`);
    });    
}

function showCreateCharacterForm() {
    $("#mainModal").html(newCharacterModalHTML);

    $("#createCharacterButton").click(createCharacter);
    $("#modalCloseButton").click(hideModal);

    $("#addClass").click(addClassInput);

    showModal();
}

function createCharacter() {
    var currentUser = JSON.parse(sessionStorage.getItem("currentUser"));

    var newClassList = retrieveClassList();

    var newStats = new CharacterStat(
                        $('#characterConstitution').val(),
                        $('#characterStrength').val(),
                        $('#characterDexterity').val(),
                        $('#characterWisdom').val(),
                        $('#characterIntelligence').val(),
                        $('#characterCharisma').val());

    var newCharacter = new Character(
                        $('#characterName').val(),
                        $('#characterRace').val(),
                        $('#maximumHealth').val(),
                        $('#currentHealth').val(),
                        currentUser,
                        newStats,
                        newClassList);

    ajaxCall("AddCharacter", {
            'character': JSON.stringify(newCharacter)
        }, "POST", handleCharacterCreateResult);
}

function handleCharacterCreateResult(response) {
    var currentUser = JSON.parse(sessionStorage.getItem("currentUser"));
    loadCharacters(currentUser);

    hideModal();
    console.log(response);
}

function addClassInput() {
    $("#classList").append(classInputHTML);
}

function retrieveClassList() {
    var classArray = [];

    $(".classDiv").each(function(i, element) {
        var newClass = new DnDClass(
                            $(element).find("select").find(":selected").val(),
                            $(element).find("select").find(":selected").text(),
                            $(element).find("input").val());
        classArray.push(newClass);
    });

    return classArray;
}

var overviewHTML = `
    <h2 id='overviewTitle'></h2>
    <div id='overviewCharacterList' class="subContent grid4Wrapper whiteBackground">
    </div>
    <button type="button" id="showCharacterModalButton" class="styledButton">Create Character</button>`;

var classInputHTML = `
    <div class="classDiv">
        <label for="classSelector" class="styledInputLabel primaryText">Class</label>
        <select name="classSelector" id="classSelector">
            <option value="1">Warrior</option>
            <option value="2">Wizard</option>
            <option value="3">Rogue</option>
            <option value="4">Ranger</option>
            <option value="5">Bard</option>
            <option value="6">Paladin</option>
            <option value="7">Cleric</option>
            <option value="8">Warlock</option>
            <option value="9">Monk</option>
            <option value="10">Druid</option>
        </select>

        <label for="classLevel" class="styledInputLabel primaryText">Level</label>
        <input type="text" id="classLevel" name="classLevel" class="styledInputShort classLevel" value="1">
    </div>
`;

var newCharacterModalHTML = `
    <div id="modalBox" class="modalBox whiteBackground">
        <span id="modalCloseButton" class="closeButton">&times;</span>
        <div id="modalHeader" class="modalHeader primaryBackground secondaryText">
            <h2>Create New Character</h2>
        </div>
        <br>
        <div id="modalContent" class="modalContent">
            <div class="grid2Wrapper">
                <div class="gridSubContent">
                    <label for="characterName" class="styledInputLabel primaryText">Character Name</label>
                    <input type="text" id="characterName" name="characterName" class="styledInput">
                    <br>

                    <label for="characterRace" class="styledInputLabel primaryText">Character Race</label>
                    <input type="text" id="characterRace" name="characterRace" class="styledInput">
                </div>

                <div class="gridSubContent">
                    <label class="styledInputLabel primaryText">Health</label>
                    <br>
                    <input type="text" id="currentHealth" name="currentHealth" class="styledInputShort">
                    <span>/</span>
                    <input type="text" id="maximumHealth" name="maximumHealth" class="styledInputShort">
                </div>
            </div>
            <hr>

            <div class="grid3Wrapper" id="characterStats">
                <div class="gridSubContent">
                    <label for="characterConstitution" class="styledInputLabel primaryText">Constitution</label>
                    <br>
                    <input type="text" id="characterConstitution" name="characterConstitution" class="styledInputShort" value="10">
                </div>

                <div class="gridSubContent">
                    <label for="characterStrength" class="styledInputLabel primaryText">Strength</label>
                    <br>
                    <input type="text" id="characterStrength" name="characterStrength" class="styledInputShort" value="10">
                </div>

                <div class="gridSubContent">
                    <label for="characterDexterity" class="styledInputLabel primaryText">Dexterity</label>
                    <br>
                    <input type="text" id="characterDexterity" name="characterDexterity" class="styledInputShort" value="10">
                </div>

                <div class="gridSubContent">
                    <label for="characterWisdom" class="styledInputLabel primaryText">Wisdom</label>
                    <br>
                    <input type="text" id="characterWisdom" name="characterWisdom" class="styledInputShort" value="10">
                </div>

                <div class="gridSubContent">
                    <label for="characterIntelligence" class="styledInputLabel primaryText">Intelligence</label>
                    <br>
                    <input type="text" id="characterIntelligence" name="characterIntelligence" class="styledInputShort" value="10">
                </div>

                <div class="gridSubContent">
                    <label for="characterCharisma" class="styledInputLabel primaryText">Charisma</label>
                    <br>
                    <input type="text" id="characterCharisma" name="characterCharisma" class="styledInputShort" value="10">
                </div>
            </div>
            <hr>

            <div class="gridSubContent" id="classList">
                ${classInputHTML}
            </div>
            <button type="button" id="addClass" class="styledButton">Add Class</button>

            <hr>

            <button type="button" id="createCharacterButton" class="styledButton">Create</button>
            <br>
        </div>
    </div>`;


