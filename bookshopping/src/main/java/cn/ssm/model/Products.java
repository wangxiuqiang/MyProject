package cn.ssm.model;

public class Products {
	private String id;
	private String name;
	private Long price;
	private String category;
	private Integer pnum;
	private String imgurl;
	private String description;
	private Integer state;
	private String cbtime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getPnum() {
		return pnum;
	}
	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCbtime() {
		return cbtime;
	}
	public void setCbtime(String cbtime) {
		this.cbtime = cbtime;
	}
	@Override
	public String toString() {
		return "Products [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + ", pnum="
				+ pnum + ", imgurl=" + imgurl + ", description=" + description + ", state=" + state + ", cbtime="
				+ cbtime + "]";
	}
	
	

}
