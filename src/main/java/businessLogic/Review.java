package businessLogic;

public class Review {

    private String reviewComment;
    private int reviewGrade;


    public Review(String reviewComment, int reviewGrade) {
        this.reviewComment = reviewComment;
        this.reviewGrade = reviewGrade;
    }

    public Review(int reviewGrade) {
        this.reviewGrade = reviewGrade;
    }

    public int getReviewGrade() {
        return reviewGrade;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    @Override
    public String toString() {
        return "Grade: " + getReviewGrade() + "." + getReviewComment();
    }

    //---------------------------------
    public void setReviewGrade(int grade) {
        this.reviewGrade = grade;
    }

    public void setReviewComment(String comment) {
        this.reviewComment = comment;
    }
}
