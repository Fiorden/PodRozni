package com.fjord.podrozni;

import java.io.Serializable;

import  com.fjord.podrozni.R.string.*;




/**
 * Created by fjord on 27.03.2017.
 */

public class Events implements Serializable {

    private String id;
    private String title;
    private String image;
    private String map;

    private String info;
    private String info_short;

    private String start;
    private String end;


    private Boolean remind = false;

    public Events(String id, String title, String image, String info, String info_short, String start, String end, String map){

        this.setId(id);
        this.setTitle(title);
        this.setImage(image);
        this.setInfo(info);
        this.setInfoShort(info_short);
        this.setStart(start);
        this.setEnd(end);
        this.setMap(map);
    }


    public int getId() {return Integer.parseInt(id);}

    public void setId(String id) {this.id = id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {

        if (image.contains("http"))
            this.image = image;
        else
        this.image = "http://festiwalpodrozni.pl/"+image;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {

        if (map.contains("http"))
            this.map = map;
        else
            this.map = "http://festiwalpodrozni.pl/"+map;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfoShort() {
        return info_short;
    }

    public void setInfoShort(String info_short) {
        this.info_short = info_short;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Boolean getRemind() {return remind;}

    public void setRemind(Boolean remind) {this.remind = remind;}
}
