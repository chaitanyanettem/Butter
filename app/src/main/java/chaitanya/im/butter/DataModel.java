package chaitanya.im.butter;

public class DataModel {

    String _movieName;
    int _id;
    String _posterURL;

    public DataModel(String movieName, String posterURL, int id) {
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

    public int getId() {
        return _id;
    }
}
