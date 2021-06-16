
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import org.junit.Test;

public class MovieRunnerAverageTest {

    @Test
    public void printAverageRatings() {
        SecondRatings secondRatings = new SecondRatings(
                    "data/ratedmoviesfull.csv",
                    "data/ratings.csv");
        System.out.println("Number of movies = " + secondRatings.getMovieSize());
        System.out.println("Number of raters = " + secondRatings.getRaterSize());;
        
        secondRatings.getAverageRatings(12).stream().
            sorted((r1, r2) -> r1.getValue() > r2.getValue() ? 1 : -1).
            forEach(r1 -> 
                System.out.println(secondRatings.getTitle(r1.getItem()) + " : " +
                    r1.getValue()));
            
    }
    
    //@Test 
    public void getAverageRatingOneMovie() {
        SecondRatings secondRatings = new SecondRatings(
                    "data/ratedmoviesfull.csv",
                    "data/ratings.csv");
        String title = "Vacation";
        String id = secondRatings.getID(title);
        double rating = secondRatings.getAverageByID(id, 0);
        System.out.println("For the given movie");
        System.out.println(title + " : " + rating);
    }

}
