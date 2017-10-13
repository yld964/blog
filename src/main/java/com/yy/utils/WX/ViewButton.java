package com.yy.utils.WX;

public class ViewButton extends Button {
    private final static String type="view";
    private String url;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
