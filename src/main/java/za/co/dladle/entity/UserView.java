package za.co.dladle.entity;

public class UserView {
    private String name;
    private Double rating;
    private String profilePicture;

    public UserView(String name, Double rating, String profilePicture) {
        this.name = name;
        this.rating = rating;
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
