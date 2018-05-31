package sample.shuxiang.customer;

import java.util.Date;

public class DummyCampaignInfo {
    public String title;
    public Date holdingTime;
    public String faceImageUrl;
    public String holdingAddress;
    public String status;
    public int registered;
    public int totalAllowed;
    public String detailPageUrl;

    public DummyCampaignInfo(String title, String faceImageUrl, String detailPageUrl, Date holdingTime,
	    String holdingAddress, String status, int signUppedNumber, int totalAllowed) {
	super();
	this.title = title;
	this.faceImageUrl = faceImageUrl;
	this.detailPageUrl = detailPageUrl;
	this.holdingTime = holdingTime;
	this.holdingAddress = holdingAddress;
	this.status = status;
	this.registered = signUppedNumber;
	this.totalAllowed = totalAllowed;
    }

}
