public class User {

    private int userID;
    private String userName;
    private String userPass;

    User() {

    }
    User(String userName, String userPass) {
        //this.userID = userID;
        this.userName = userName;
        this.userPass = userPass;
    }

    public int getUserID(){
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @Override
    public String toString() {
        return userName;
    }
}
