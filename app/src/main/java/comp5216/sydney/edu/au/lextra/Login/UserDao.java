package comp5216.sydney.edu.au.lextra.Login;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> listAll();

    @Insert
    void insert(User user);

    @Insert
    void insertALl(User... users);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("SELECT password FROM users WHERE email = :email")
    String selectUserPassword(String email);

    @Query("UPDATE users SET password = :password WHERE email = :email")
    void updatePassword(String email, String password);

    @Query("SELECT avatar FROM users WHERE email = :email")
    String selectUserAvatar(String email);

    @Query("UPDATE users SET avatar = :path WHERE email = :email")
    void updateUserAvatar(String email, String path);

    @Query("SELECT bookmark FROM users WHERE email = :email")
    String selectBookmark(String email);

    @Query("UPDATE users SET bookmark = :bookmark WHERE email = :email")
    void updateBookmark(String email, String bookmark);
}
