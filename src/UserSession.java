public class UserSession {
    private int UserId;
    private String UserName;
    private String Role;

    public UserSession(int userId, String userName, String role) {
        UserId = userId;
        UserName = userName;
        Role = role;
    }

    public int getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public String getRole() {
        return Role;
    }
}
