
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.util.stream.Collectors;
import javafx.util.Pair;

public class SecondRatings {
    private List<Movie> myMovies;
    private List<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviesFile, String ratingsFile) {
        FirstRatings firstRatings = new FirstRatings();
        myMovies = firstRatings.loadMovies(moviesFile);
        myRaters = firstRatings.loadRaters(ratingsFile);
    }
    
    public int getMovieSize() {
        return myMovies.size();
    }   
    
    
    public int getRaterSize() {
        return myRaters.size();
    }
    
    
    public double getAverageByID(String movieID, int minimalRaters) {
        List<Double> ratings = myRaters.
            stream().
            filter(rater -> rater.getItemsRated().contains(movieID)).
            map(rater -> rater.getRating(movieID)).
            collect(Collectors.toList());
        if (ratings.size() >= minimalRaters) {
            return ratings.stream().
                mapToDouble(rating -> rating).
                average().getAsDouble();
        }
        return 0.0;   
    }
    
    
    public List<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        return myMovies.stream().
            map(movie -> 
                new Pair<String, Double>(movie.getID(),
                    getAverageByID(movie.getID(),
                    minimalRaters))).
            filter(pair -> pair.getValue() > 0.0).
            map(pair -> new Rating(pair.getKey(), pair.getValue())).
            collect(Collectors.toList());
    }
    
    public String getTitle(String id) {
        Optional<String> title = myMovies.stream().
            filter(movie -> movie.getID().equals(id))
            .map(movie -> movie.getTitle()).
            findFirst();
        if (title.isPresent())
            return title.get();
        else 
            return "!!!ID was not found!!!";
    }
    
    public String getID(String title) {
        Optional<String> id = myMovies.stream().
            filter(movie -> movie.getTitle().equals(title))
            .map(movie -> movie.getID()).
            findFirst();
        if (id.isPresent())
            return id.get();
        else 
            return "!!NO SUCH TITLE!!!";
    }
    
    

}