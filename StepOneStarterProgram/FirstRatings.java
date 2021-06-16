
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;
import org.apache.commons.csv.*;
import java.io.Reader;
import java.nio.file.*;
import java.io.IOException;

public class FirstRatings {

    public List<Movie> loadMovies(String filename) {
        // Process every record from given CSV file
        // Return a list of movie information found
        List<Movie> movies = new ArrayList<Movie>();
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        try(
        Reader reader = Files.newBufferedReader(Paths.get(filename));
        

        CSVParser csvParser = new CSVParser(reader, format);) {
            for (CSVRecord csv : csvParser) {
                movies.add(new Movie(csv.get(0), csv.get(1), csv.get(2), csv.get(4),
                                     csv.get(5), csv.get(3), csv.get(7), Integer.parseInt(csv.get(6))));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return movies;
    }
    

    public List<Rater> loadRaters(String filename) {
        List<Rater> raters = new ArrayList<Rater>();
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        try(
            Reader reader = Files.newBufferedReader(Paths.get(filename));
            CSVParser csvParser = new CSVParser(reader, format);
        ) {
            String prevId = "";
            Rater rater = null;
            for (CSVRecord rec : csvParser) {
                if (!rec.get(0).equals(prevId)) {
                    prevId = rec.get(0);
                    if (rater != null) {
                        raters.add(rater);
                    }
                    rater = new Rater(rec.get(0));
                    rater.addRating(rec.get(1), Double.parseDouble(rec.get(2)));
                } else {
                    rater.addRating(rec.get(1), Double.parseDouble(rec.get(2)));
                }
            }
            if (rater != null) {
                raters.add(rater);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return raters;
    }

}
