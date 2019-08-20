package domain;

public class User {
	private String did;
	private String aid;
	private long appstartts;
	private long appstopts;
	private long devicestartts;
	private long devicestopts;
	private long gents;
	private String appstartdate;
	private String devicestartdate;
	private long timeLimit;
	private int Appdiff;
	private int Devdiff;
	final int duration = 6000; //1 mins
	final int Appduration = 30000;//5 mins
	final int devduration = 30000;//5 mins
	
	public User() {}
//	app Using time constructor
	public User(String did,String aid,long appstartts,long appstopts,long gents,long timeLimit,String appstartdate,int Appdiff) {
		this.did = did;
		this.aid = aid;
		this.appstartts = appstartts;
		this.appstopts = appstopts;
		this.gents = gents;
		this.timeLimit = timeLimit;
		this.appstartdate = appstartdate;
		this.Appdiff = Appdiff;
	}
	
//	Device Using time constructor
	public User(String did,String aid,long devicestartts,long devicestopts,long gents,long timeLimit,String devicestartdate,String appstartdate,int Devdiff) {
		this.did = did;
		this.aid = aid;
		this.devicestartts = devicestartts;
		this.devicestopts = devicestopts;
		this.gents = gents;
		this.timeLimit = timeLimit;
		this.devicestartdate = devicestartdate;
		this.appstartdate = appstartdate;
		this.Devdiff = Devdiff;
	}
		
		public User(String did, String aid, long appstartts, long appstopts, long devicestartts, long devicestopts, long gents,
			String appstartdate, String devicestartdate, long timeLimit, int appdiff, int devdiff) {
		super();
		this.did = did;
		this.aid = aid;
		this.appstartts = appstartts;
		this.appstopts = appstopts;
		this.devicestartts = devicestartts;
		this.devicestopts = devicestopts;
		this.gents = gents;
		this.appstartdate = appstartdate;
		this.devicestartdate = devicestartdate;
		this.timeLimit = timeLimit;
		this.Appdiff = appdiff;
		this.Devdiff = devdiff;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public long getAppstartts() {
		return appstartts;
	}
	public void setAppstartts(long appstartts) {
		this.appstartts = appstartts;
	}
	public long getAppstopts() {
		return appstopts;
	}
	public void setAppstopts(long appstopts) {
		this.appstopts = appstopts;
	}
	public long getDevicestartts() {
		return devicestartts;
	}
	public void setDevicestartts(long devicestartts) {
		this.devicestartts = devicestartts;
	}
	public long getDevicestopts() {
		return devicestopts;
	}
	public void setDevicestopts(long devicestopts) {
		this.devicestopts = devicestopts;
	}
	public long getGents() {
		return gents;
	}
	public void setGents(long gents) {
		this.gents = gents;
	}
	public String getAppstartdate() {
		return appstartdate;
	}
	public void setAppstartdate(String appstartdate) {
		this.appstartdate = appstartdate;
	}
	public String getDevicestartdate() {
		return devicestartdate;
	}
	public void setDevicestartdate(String devicestartdate) {
		this.devicestartdate = devicestartdate;
	}	
	public long getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String ApptoString() {
//		单位分钟
		long gap = 0;
		if(appstopts == 0 || (appstopts-appstartts) == Appduration) {
			if(gents <= timeLimit) {
				gap = (gents + Appdiff) - appstartts;				
			}else {
				gap = timeLimit - appstartts;
			}
		}else {
			gap = appstopts - appstartts;
		}
		return did + " " + aid + " " + appstartdate + " " + gap/duration;
	}
	
	public String DevicetoString() {
//		单位分钟.
		long gap = 0;
//		if(devicestopts == 0 || (devicestopts-devicestartts) == devduration || (devicestopts-devicestartts) == 0) {
////			gap = (gents + Devdiff) - devicestartts;
//			gap = gents - devicestartts;
//		}else {
//			System.out.println("Dev else");
//			gap = devicestopts-devicestartts;
//		}
		gap = (gents + Devdiff) - devicestartts;
//		gap = gents - devicestartts;
		return did + " " + devicestartdate + " " + gap/duration;
	}
}
