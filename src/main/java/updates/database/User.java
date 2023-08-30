package updates.database;

import updates.Roles;

public class User {
    private String login;
    private String password;
    private Roles role;

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {return role.toString();}

    public User(String login, String password, Roles role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
