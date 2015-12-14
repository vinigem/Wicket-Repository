package com.vini.vo;

import java.io.Serializable;

public class ReportVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String message;
    private String browserName;
    private int browserVersion;
    private int browserHeight;
    private int browserWidth;
    private int screenHeight;
    private int screenWidth;
    private String screenshotFilePath;
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getBrowserName() {
        return browserName;
    }
    
    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }
    
    public int getBrowserVersion() {
        return browserVersion;
    }
    
    public void setBrowserVersion(int browserVersion) {
        this.browserVersion = browserVersion;
    }
    
    public int getBrowserHeight() {
        return browserHeight;
    }
    
    public void setBrowserHeight(int browserHeight) {
        this.browserHeight = browserHeight;
    }
    
    public int getBrowserWidth() {
        return browserWidth;
    }
    
    public void setBrowserWidth(int browserWidth) {
        this.browserWidth = browserWidth;
    }
    
    public int getScreenHeight() {
        return screenHeight;
    }
    
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
    
    public int getScreenWidth() {
        return screenWidth;
    }
    
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }
    
    @Override
    public String toString() {
        return "Message: " + message
                + "\n Browser Info [Name: " + browserName + ", Version: " + browserVersion + "]"
                + "\n Browser Dimensions [Height: " + browserHeight + ", Width: " + browserWidth + "]"
                + "\n Screen Dimensions [Height: " + screenHeight + ", Width: " + screenWidth + "]";
    }
    
    public String getScreenshotFilePath() {
        return screenshotFilePath;
    }
    
    public void setScreenshotFilePath(String screenshotFilePath) {
        this.screenshotFilePath = screenshotFilePath;
    }
    
    
    
    
}
