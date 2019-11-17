package comp5216.sydney.edu.au.lextra.Login;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "userID")
    private int userID;//ID as primary key

    @ColumnInfo (name = "email")
    public String email;// Email

    @ColumnInfo (name = "password")
    public String password;//Password

    @ColumnInfo (name = "avatar")
    public String avatar;//Avatar of user

    @ColumnInfo (name = "bookmark")
    private String bookmark;// bookmark of course
    //Constructor
    public User(String email, String password, String avatar,String bookmark) {
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.bookmark=bookmark;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }
}
