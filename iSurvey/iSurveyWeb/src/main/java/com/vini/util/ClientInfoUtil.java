package com.vini.util;

import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.request.WebClientInfo;

import com.vini.vo.ReportVO;

public class ClientInfoUtil {
    
    private ClientInfoUtil() {
        
    }
    
    /**
     * Method to extract client info for the report
     * @param clientInfo
     * @param report
     */
    public static void addClientInfo(WebClientInfo clientInfo, ReportVO report) {
        ClientProperties properties = clientInfo.getProperties();
        
        if(properties.isBrowserChrome()){
            report.setBrowserName("Chrome");
        }else if(properties.isBrowserInternetExplorer()){
            report.setBrowserName("Internet Explorer");
        }else if(properties.isBrowserMozillaFirefox()){
            report.setBrowserName("Mozilla Firefox");
        }else if(properties.isBrowserMozilla()){
            report.setBrowserName("Mozilla");
        }else if(properties.isBrowserOpera()){
            report.setBrowserName("Opera");
        }else if(properties.isBrowserSafari()){
            report.setBrowserName("Safari");
        }
        
        report.setBrowserVersion(properties.getBrowserVersionMajor());
        report.setBrowserHeight(properties.getBrowserHeight());
        report.setBrowserWidth(properties.getBrowserWidth());
        report.setScreenHeight(properties.getScreenHeight());
        report.setScreenWidth(properties.getScreenWidth());
    }
    
}
