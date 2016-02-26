package chaitanya.im.butter.Data;

import java.util.List;

public class MovieNowPlaying {
    String _page;
    List<MovieResults> _results;

    public String get_page(){
        return _page;
    }

    public List<MovieResults> get_results(){
        return _results;
    }
}
