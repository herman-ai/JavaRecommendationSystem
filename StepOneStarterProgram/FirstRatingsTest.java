
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;


/**
 * Write a description of FirstRatingsTest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FirstRatingsTest {
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    FirstRatings firstRatings;
    
    @Before
    public void setUp() {
        firstRatings = new FirstRatings();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() {
    }
    
    @Test
    public void testLoadMovies() {
    // System.out.println("Working Directory = " + System.getProperty("user.dir"));
    List<Movie> movies = firstRatings.loadMovies("data/ratedmoviesfull.csv");
    System.out.println("Number of movies loaded = " + movies.size());
    
    // movies.forEach((movie) -> {
    //    System.out.println(movie);
    // });
    
    List<Movie> comedyMovies = movies.stream()
        .filter(movie -> Arrays.asList(movie.getGenres().split("\\s*,\\s*")).contains("Comedy"))
        .collect(Collectors.toList());
        
    System.out.println("Number of Comedy movies = " + comedyMovies.size());
        
    List<Movie> longMovies = movies.stream()
        .filter(movie -> movie.getMinutes() > 150)
        .collect(Collectors.toList());
    System.out.println("Movies longer than 150 minutes = " + longMovies.size());
    
    Map<String, Long> directorMovies = movies.stream()
                            .map(movie -> Arrays.asList(movie.getDirector().split("\\s*,\\s*")))
                            .flatMap(director_list -> director_list.stream())
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                            
    directorMovies = directorMovies
                            .entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (v1, v2) -> {throw new IllegalStateException();},
                                LinkedHashMap::new
                                ));
    Long maxMovies = directorMovies.entrySet().stream()
                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                    .get().getValue();
                                
    System.out.println("Maximum number of movies by any director = " + maxMovies);
    directorMovies.entrySet()
                  .stream()
                  .filter(entry -> entry.getValue() == maxMovies)
                  .forEach(e -> System.out.println(e.getKey()));
    }
    
    
    @Test
    public void testLoadRaters() {
        List<Rater> raters = firstRatings.loadRaters("data/ratings.csv");
        System.out.println("Total number of raters = " + raters.size());
        
        /*
        raters.forEach((rater) -> {
            System.out.println(rater.getID() + ":" + rater.numRatings() + " ratings");
            rater.getItemsRated().forEach((movie) -> {
                System.out.println(movie + ":" + rater.getRating(movie));
            });
        });
        */
        String id = "193";
        raters.stream()
            .filter(rater -> rater.getID().equals(id))
            .forEach(rater -> System.out.println("Number of ratings by user id " + id + " = " + rater.numRatings()));
        
        Rater maxRater = raters.stream()
                        .max((r1, r2) -> r1.numRatings() > r2.numRatings() ? 1 : -1)
                        .get();
        System.out.println("Maximum number of ratings by any rater = " + maxRater.numRatings() + 
                            ", following raters rated the max number of movies:");
        
        
        raters.stream()
            .filter(rater -> rater.numRatings() == maxRater.numRatings())
            .forEach(rater -> System.out.println(rater.getID()));
        
            
        // Find the number of ratings a particular movie  has:
        String movieId = "1798709";
        Long numRatings = raters.stream()
                            .filter(rater -> rater.getItemsRated().contains(movieId))
                            .count();
        System.out.println("Number of ratings for " + movieId + " = " + numRatings);
        
        // how many different movies have been rated by all these raters
        Long numDistinctMovies = raters.stream()
                            .map(rater -> rater.getItemsRated())
                            .flatMap(items -> items.stream())
                            .distinct()
                            .count();
        System.out.println("Number of distinct movies rated = " + numDistinctMovies);
    }

}
