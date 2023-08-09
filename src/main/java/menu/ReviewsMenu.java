package menu;

import facade.Facade;
import input.UserInput;

public class ReviewsMenu {

    static final String EOL = System.lineSeparator();
    static final Facade facade = Launcher.facade;

    public static void reviewsMenu() throws Exception {

        final String REVIEWS_MENU = "Reviews options menu:" + EOL +
                "0. Return to Main Menu." + EOL +
                "1. Create a review for an Item." + EOL +
                "2. Print a specific review of an Item." + EOL +
                "3. Print all reviews of an Item." + EOL +
                "4. Print mean grade of an Item." + EOL +
                "5. Print all comments of an Item." + EOL +
                "6. Print all registered reviews." +EOL +
                "7. Print item(s) with most reviews." + EOL +
                "8. Print item(s) with least reviews." + EOL +
                "9. Print item(s) with best mean review grade." + EOL +
                "10. Print item(s) with worst mean review grade." + EOL;

        int userAnswer;

        do {
            UserInput.printInfo(REVIEWS_MENU);
            userAnswer = UserInput.readInt("Input option Number: ");

            switch (userAnswer) {

                case 0:
                    Launcher.mainMenu();
                    break;

                case 1: // Create a review for an Item
                    String itemIdToReview = UserInput.readString("Type id of an item to give a feedback: ");
                    String reviewComment = UserInput.readString("Write your comment: ");
                    int grade = UserInput.readInt("Please, give your grade to an item: ");
                    if (reviewComment.isEmpty()) {
                        facade.reviewItem(itemIdToReview, grade);
                    } else {
                        facade.reviewItem(itemIdToReview, reviewComment, grade);
                    }
                    break;

                case 2: // Print a specific review of an Item
                    String itemIdToPrintReview = UserInput.readString("Type id of an item to print a review: ");
                    int reviewNumber = UserInput.readInt("Type a number of a review: ");
                    facade.getPrintedItemReview(itemIdToPrintReview, reviewNumber);
                    break;

                case 3: // Print all reviews of an Item
                    String idToPrintReviews = UserInput.readString("Type id of an item to print reviews");
                    facade.getPrintedReviews(idToPrintReviews);
                    break;

                case 4: // Print mean grade of an Item
                    String idToGetMeanGrade = UserInput.readString("Type id of an item to get the mean grade: ");
                    facade.getItemMeanGrade(idToGetMeanGrade);
                    break;

                case 5: // Print all comments of an Item
                    String idToPrintComments = UserInput.readString("Type id of an item to get all comments printed: ");
                    facade.getItemCommentsPrinted(idToPrintComments);
                    break;

                case 6: // Print all registered reviews
                    facade.printAllReviews();
                    break;

                case 7: // Print item(s) with most reviews
                    facade.printMostReviewedItems();
                    break;

                case 8: // Print item(s) with least reviews
                    facade.printLeastReviewedItems();
                    break;

                case 9: // Print item(s) with best mean review grade
                    facade.printBestReviewedItems();
                    break;

                case 10: //  Print item(s) with worst mean review grade
                    facade.printWorseReviewedItems();
                    break;

                default:
                    UserInput.printInfo("Invalid menu option. Please type another option: ");
                    reviewsMenu();
                    break;

            }
        } while (userAnswer != 0);
    }
}
