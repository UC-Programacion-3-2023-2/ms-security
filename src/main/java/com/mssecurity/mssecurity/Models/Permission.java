package com.mssecurity.mssecurity.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class Permission {
    @Id
    private String _id;
    private String url;
    private String method;
    private String menuItem;

    public Permission(String url, String method, String menuItem) {
        this.url = url;
        this.method = method;
        this.menuItem = menuItem;
    }

    public String get_id() {
        return _id;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }
}
