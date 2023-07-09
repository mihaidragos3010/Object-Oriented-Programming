package DataBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

public class Stream {

//    Arguments
    protected Integer type;
    protected Integer Id;
    protected Integer genre;
    protected Long views;
    protected Integer streamerId;
    protected Long length;
    protected Long date;
    protected String name;
//    Builders
    public Stream(String[] line){
        this.type = Integer.valueOf(line[0]);
        this.Id = Integer.parseInt(line[1]);
        this.genre = Integer.valueOf(line[2]);
        this.views = Long.parseLong(line[3]);
        this.streamerId = Integer.parseInt(line[4]);
        this.length = Long.parseLong(line[5]);
        this.date = Long.parseLong(line[6]) * 1000L;
        this.name = line[7];
    }
//    Methods

    public Integer getType() {
        return type;
    }
    public Integer getId() {
        return Id;
    }
    public Integer getGenre() {
        return genre;
    }
    public Long noOfListenings() {
        return views;
    }
    public Integer getStreamerId() {
        return streamerId;
    }
    public String getLength() {
        Long seconds,minutes,hours;
        Long lengthFormat = length;
        hours = lengthFormat / 3600;
        lengthFormat = lengthFormat % 3600;
        minutes = lengthFormat / 60;
        lengthFormat = lengthFormat % 60;
        seconds = lengthFormat;
        if(hours == 0L)
            return String.format("%02d:%02d", minutes, seconds);
        else
            return String.format("%02d:%02d:%02d",hours, minutes, seconds);
    }
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }
    public String getName() {
        return name;
    }

    public void listen(){
        views++;
    }

//    Classes
    public static class ViewsComparator implements Comparator<Stream> {
        @Override
        public int compare(Stream stream1, Stream stream2) {
            if(stream1.views < stream2.views)
                return 1;
            if(stream1.views > stream2.views)
                return -1;
            return 0;
        }
    }

    public static class DateComparator implements Comparator<Stream> {
        @Override
        public int compare(Stream stream1, Stream stream2) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date1=null,date2=null;
            try {
                date1 = sdf.parse(stream1.getDate());
                date2 = sdf.parse(stream2.getDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if(date1.before(date2))
                return 1;
            if(date1.after(date2))
                return -1;
            if(date1.equals(date2)){
                if(stream1.views < stream2.views)
                    return 1;
                if(stream1.views > stream2.views)
                    return -1;
            }
            return 0;
        }
    }

}
