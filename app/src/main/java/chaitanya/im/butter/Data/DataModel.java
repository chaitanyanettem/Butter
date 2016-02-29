package chaitanya.im.butter.Data;

public class DataModel {

    String _movieName;
    Integer _id;
    String _posterURL;

    public DataModel(String movieName, String posterURL, Integer id) {
        _movieName = movieName;
        _id = id;
        _posterURL = posterURL;
    }

    public String getMovieName() {
        return _movieName;
    }

    public String getPosterURL() {
        return _posterURL;
    }

    public Integer getId() {
        return _id;
    }
}
