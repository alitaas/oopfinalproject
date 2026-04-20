public class screening {

    private int id;
    private int movieId;
    private String screeningTime;
    private String hall;

    public screening(int id, int movieId, String screeningTime, String hall) {
        this.id = id;
        this.movieId = movieId;
        this.screeningTime = screeningTime;
        this.hall = hall;
    }

    public int getId() {
        return id;
    }

    public String getScreeningTime() {
        return screeningTime;
    }

    public String getHall() {
        return hall;
    }

}
