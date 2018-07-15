package cn.ssm.model;
/**
 * 分页的实体
 * @author Administrator
 *
 */
public class PageBean {
     //每页中显示的记录数
	private Integer pageSize;
	//显示的页数
	private Integer page;
	//总记录数
	private Integer count;
	//偏移量
	private Integer pageNum;
	//总页数
    private Integer pageCount;
    
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		if(page == null || page < 1) {
			this.page = 1;
		}
		else if(page > pageCount) {
			this.page = pageCount;
		}
		else {
			this.page = page;
		}
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	//总页数
	public void setPageCount() {
		//总页数= 总记录数/每页显示的记录数
		if(count % pageSize == 0) {
			pageCount = count / pageSize;
		}else {
			pageCount = count / pageSize + 1;
		}
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum() {
		pageNum = (page - 1) * pageSize;
	}
	public PageBean() {
		
	}
    public PageBean(Integer pageSize,Integer page,Integer count) {
    	this.pageSize = pageSize;
		this.count = count;
		//总页数
		setPageCount();
		//页数
		setPage(page);
		//
		setPageNum();
	}
}
