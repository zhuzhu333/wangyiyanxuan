package com.kgc.consumer.wx;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Campbell on 2019/10/18 12:26
 */
@Component
@ConfigurationProperties(prefix = "wx")
public class WxModel {

    private String codeUri;
    private String appid;
    private String redirectUri;
    private String accessTokenUri;
    private String secret;
    private String userInfoUri;

    public String getCodeUri() {
        return codeUri;
    }

    public void setCodeUri(String codeUri) {
        this.codeUri = codeUri;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getAccessTokenUri() {
        return accessTokenUri;
    }

    public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public void setUserInfoUri(String userInfoUri) {
        this.userInfoUri = userInfoUri;
    }

    public String reqCodeUrl(){
        StringBuffer sb = new StringBuffer(getCodeUri());
        sb.append("appid=").append(getAppid()).append("&").append("redirect_uri=").append(getRedirectUri())
                .append("&").append("response_type=").append("code").append("&").append("scope=")
                .append("snsapi_userinfo").append("&state=STATE#wechat_redirect");
        return sb.toString();
    }

    public String reqAccessTokenUrl(String code){
        StringBuffer sb = new StringBuffer(getAccessTokenUri());
        sb.append("appid=").append(getAppid()).append("&").append("secret=").append(getSecret())
                .append("&").append("code=").append(code).append("&grant_type=authorization_code");
        return sb.toString();
    }

    public String reqUserInfoUrl(String accessToken,String openId){
        StringBuffer sb = new StringBuffer(getUserInfoUri());
        sb.append("access_token=").append(accessToken).append("&").append("openid=").append(openId);
        return sb.toString();
    }
}
