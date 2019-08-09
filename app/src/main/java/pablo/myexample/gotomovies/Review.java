package pablo.myexample.gotomovies;

public class Review {

    private String review;
    private String author;

    Review(){}

    public Review(String review, String author) {
        this.review = review;
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
