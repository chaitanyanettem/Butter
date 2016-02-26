package chaitanya.im.butter.Data;

public class MovieNowPlayingResults {
    String _poster_path;
    Boolean _adult;
    String _overview;
    String _release_date;
    int[] _genre_ids;
    int _id;
    String _original_title;
    String _original_language;
    String _title;
    String _backdrop_path;
    Float _popularity;
    int _vote_count;
    Boolean _video;
    Float _vote_average;

    public String get_poster_path() {
        return _poster_path;
    }

    public Boolean get_adult() {
        return _adult;
    }

    public String get_overview() {
        return _overview;
    }

    public String get_release_date() {
        return _release_date;
    }

    public int[] get_genre_ids() {
        return _genre_ids;
    }

    public int get_id() {
        return _id;
    }

    public String get_original_title() {
        return _original_title;
    }

    public String get_original_language() {
        return _original_language;
    }

    public String get_title() {
        return _title;
    }

    public String get_backdrop_path() {
        return _backdrop_path;
    }

    public Float get_popularity() {
        return _popularity;
    }

    public int get_vote_count() {
        return _vote_count;
    }

    public Boolean get_video() {
        return _video;
    }

    public Float get_vote_average() {
        return _vote_average;
    }
}
