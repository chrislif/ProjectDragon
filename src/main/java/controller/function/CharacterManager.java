package controller.function;

import data.CharacterDB;
import model.Account;
import model.Character;

import java.sql.SQLException;
import java.util.ArrayList;

public class CharacterManager {
    public static Boolean createCharacter(Character newCharacter, ArrayList<String> errorList) {
        try {
            return CharacterDB.createCharacter(newCharacter);
        }
        catch (SQLException ex) {
            errorList.add(ex.getMessage());
            return false;
        }
    }

    public static ArrayList<Character> getCharacterListForAccount(Account user, ArrayList<String> errorList) {
        try {
            return CharacterDB.getCharacterListForAccount(user);
        }
        catch (SQLException ex) {
            errorList.add(ex.getMessage());
            return null;
        }
    }

    public static Character getCharacterByID(int characterID, ArrayList<String> errorList) {
        try {
            return CharacterDB.getCharacter(characterID);
        }
        catch (SQLException ex) {
            errorList.add(ex.getMessage());
            return null;
        }
    }

    public static Boolean deleteCharacter(Character character, ArrayList<String> errorList) {
        try {
            return CharacterDB.deleteCharacter(character);
        }
        catch (SQLException ex) {
            errorList.add(ex.getMessage());
            return false;
        }
    }
}
