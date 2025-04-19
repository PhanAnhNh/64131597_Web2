package model;

public class Post {
	private static int count = 1;
    private int id;
    private String title;
    private String CategoryId;

    public Post() {}
    public Post(String title, String CategoryId) {
        this.id = count++;
        this.title = title;
        this.CategoryId = CategoryId;
    }
    

	public String getCategoryId() {
		return CategoryId;
	}
    
    public static int getCount() {
		return count;
	}
    
    public int getId() {
		return id;
	}
    
    public String getTitle() {
		return title;
	}
    
    public void setCategoryId(String categoryId) {
		CategoryId = categoryId;
	}  
	
	public static void setCount(int count) {
		Post.count = count;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
