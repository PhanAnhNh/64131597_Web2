package model;

public class Page {
	private static int count = 1;
    private int id;
    private String pageName;
    private String keyword;
    private String content;
    private int parentPageId;

    public Page(String pageName, String keyword, String content, int parentPageId) {
        this.id = count++;
        this.pageName = pageName;
        this.keyword = keyword;
        this.content = content;
        this.parentPageId = parentPageId;
    }
	public int getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public static int getCount() {
		return count;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public String getPageName() {
		return pageName;
	}
	
	public int getParentPageId() {
		return parentPageId;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public static void setCount(int count) {
		Page.count = count;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
	public void setParentPageId(int parentPageId) {
		this.parentPageId = parentPageId;
	}
}
