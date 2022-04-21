package data;

import data.AccountDB;
import model.Character;
import model.DnDClass;
import model.Account;
import model.CharacterStat;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CharacterDB {
    public static Boolean createCharacter(Character character) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet resultSet = null;

        String query 
                = "INSERT INTO dragon.character (characterName, characterRace, strength, dexterity, wisdom, intelligence, charisma, constitution, currentHealth, maxHealth, accountID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, character.getName());
        statement.setString(2, character.getRace());
        statement.setInt(3, character.getCharacterStat().getStrength());
        statement.setInt(4, character.getCharacterStat().getDexterity());
        statement.setInt(5, character.getCharacterStat().getWisdom());
        statement.setInt(6, character.getCharacterStat().getIntelligence());
        statement.setInt(7, character.getCharacterStat().getCharisma());
        statement.setInt(8, character.getCharacterStat().getConstitution());
        statement.setInt(9, character.getCurrentHealth());
        statement.setInt(10, character.getHealthMax());
        statement.setInt(11, character.getAccount().getAccountID());

        try {
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();

            resultSet.next();
            String keyValue = resultSet.getString(1);
            character.setCharacterID(Integer.parseInt(keyValue));

            for(DnDClass dndClass : character.getDnDClassList()) {
                createCharacterClassConnections(character, dndClass);
            }

            return true;
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.close();
                    statement.close();
                }
            } 
            catch (SQLException ex) {
                throw ex;
            }
            pool.freeConnection(connection);
        }   
    }

    public static void createCharacterClassConnections(Character character, DnDClass dndClass) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query 
                = "INSERT INTO characterClass (characterID, classID, level) "
                + "VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, character.getCharacterID());
        statement.setInt(2, dndClass.getClassID());
        statement.setInt(3, dndClass.getLevel());

        try {
            statement.executeUpdate();
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } 
            catch (SQLException ex) {
                throw ex;
            }
            pool.freeConnection(connection);
        }  
    }

    public static ArrayList<Character> getCharacterListForAccount(Account account) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet resultSet = null;

        ArrayList<Character> characterList = new ArrayList();

        String query 
                = "SELECT * FROM dragon.character "
                + "WHERE accountID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, account.getAccountID());

        try {
            resultSet = statement.executeQuery();

            Character character;
            while(resultSet.next()) {
                int characterID = resultSet.getInt("characterID");

                CharacterStat charStats = new CharacterStat(
                                            resultSet.getInt("constitution"), 
                                            resultSet.getInt("strength"), 
                                            resultSet.getInt("dexterity"), 
                                            resultSet.getInt("wisdom"), 
                                            resultSet.getInt("intelligence"), 
                                            resultSet.getInt("charisma"));
                
                ArrayList<DnDClass> dndClassList = getClassListOfID(characterID);

                character = new Character(
                                    characterID, 
                                    resultSet.getString("characterName"), 
                                    resultSet.getString("characterRace"), 
                                    resultSet.getInt("maxHealth"), 
                                    resultSet.getInt("currentHealth"),
                                    account, 
                                    charStats, 
                                    dndClassList);

                characterList.add(character);
            }
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.close();
                    statement.close();
                }
            } 
            catch (SQLException ex) {
                throw ex;
            }
            pool.freeConnection(connection);
        }  
        return characterList;
    }

    public static ArrayList<DnDClass> getClassListOfID(int characterID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet resultSet = null;
        
        ArrayList<DnDClass> dndClassList = new ArrayList();

        String query 
                = "SELECT * FROM dragon.characterClass "
                + "INNER JOIN class ON characterClass.classID = class.classID "
                + "WHERE characterID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, characterID);

        try {
            resultSet = statement.executeQuery();

            DnDClass dndClass;
            while(resultSet.next()) {
                dndClass = new DnDClass(
                                resultSet.getInt("classID"), 
                                resultSet.getString("name"), 
                                resultSet.getInt("level"));

                dndClassList.add(dndClass);
            }
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.close();
                    statement.close();
                }
            } 
            catch (SQLException ex) {
                throw ex;
            }
            pool.freeConnection(connection);
        }  
        return dndClassList;
    }

    public static Character getCharacter(int searchCharacterID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        ResultSet resultSet = null;
        Character character = null;

        String query 
                = "SELECT * FROM dragon.character "
                + "WHERE characterID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, searchCharacterID);

        try {
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int characterID = resultSet.getInt("characterID");

                CharacterStat charStats = new CharacterStat(
                                            resultSet.getInt("constitution"), 
                                            resultSet.getInt("strength"), 
                                            resultSet.getInt("dexterity"), 
                                            resultSet.getInt("wisdom"), 
                                            resultSet.getInt("intelligence"), 
                                            resultSet.getInt("charisma"));
                
                ArrayList<DnDClass> dndClassList = getClassListOfID(characterID);
                Account account = AccountDB.getAccount(resultSet.getInt("accountID"));

                character = new Character(
                                    characterID, 
                                    resultSet.getString("characterName"), 
                                    resultSet.getString("characterRace"), 
                                    resultSet.getInt("maxHealth"), 
                                    resultSet.getInt("currentHealth"),
                                    account, 
                                    charStats, 
                                    dndClassList);
            }
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.close();
                    statement.close();
                }
            } 
            catch (SQLException ex) {
                throw ex;
            }
            pool.freeConnection(connection);
        }  
        return character;
    }

    public static Boolean deleteCharacter(Character character) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query 
                = "DELETE FROM dragon.character "
                + "WHERE characterID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, character.getCharacterID());

        try {
            if (removeCharacterClass(character)) {
                statement.executeUpdate();
                return true;
            }
            return false;
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } 
            catch (SQLException ex) {
                throw ex;
            }
            pool.freeConnection(connection);
        }  
    }

    public static Boolean removeCharacterClass(Character character) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query 
                = "DELETE FROM dragon.characterClass "
                + "WHERE characterID = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, character.getCharacterID());

        try {
            statement.executeUpdate();
            return true;
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } 
            catch (SQLException ex) {
                throw ex;
            }
            pool.freeConnection(connection);
        }  
    }
}
