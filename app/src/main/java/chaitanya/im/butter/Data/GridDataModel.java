package chaitanya.im.butter.Data;

public class GridDataModel {

    String _movieName;
    Integer _id;
    String _posterURL;
    String _extraInfo;

    public GridDataModel(String movieName, String posterURL, String extraInfo, Integer id) {
        _movieName = movieName;
        _id = id;
        _posterURL = posterURL;
        _extraInfo = extraInfo;
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

    public String getExtraInfo() {
        return _extraInfo;
    }
}
