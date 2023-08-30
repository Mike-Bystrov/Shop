package database;

public class User {
    public String login;
    public char[] password;

    public User(String login, char[] password) {
        this.login = login;
        this.password = password;
    }
}
