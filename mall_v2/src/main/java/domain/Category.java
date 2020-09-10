package domain;

public class Category {
	private String cid;
	private String cname;
	private int isvalid;

	public int getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(int isvalid) {
		this.isvalid = isvalid;
	}

	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Category() {
		// TODO Auto-generated constructor stub
	}
	public Category(String cid, String cname) {
		super();
		this.cid = cid;
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "Category{" +
				"cid='" + cid + '\'' +
				", cname='" + cname + '\'' +
				", isvalid=" + isvalid +
				'}';
	}
}
