import java.sql.SQLException;
    public interface LoginInterface {
        UserSession loginUser(String username, String password) throws Exception;
        void PasswordChange(int id,String nPassword) throws SQLException ;
        boolean logout(int UserId);

}
