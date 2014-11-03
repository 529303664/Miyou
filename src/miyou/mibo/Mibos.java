package miyou.mibo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Mibos extends BmobObject {

	private String content;
	private Integer zanCount;
	private int commentCount;
	private boolean isOpentoAll;
	private boolean friendCommentOnly;
	private BmobFile pic;
	private MessageType type;
	
	public Mibos(){
		this.setTableName("");
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the zanCount
	 */
	public Integer getZanCount() {
		return zanCount;
	}

	/**
	 * @param zanCount the zanCount to set
	 */
	public void setZanCount(Integer zanCount) {
		this.zanCount = zanCount;
	}

	/**
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	
	
	/**
	 * @return the isOpentoAll
	 */
	public boolean isOpentoAll() {
		return isOpentoAll;
	}

	/**
	 * @param isOpentoAll the isOpentoAll to set
	 */
	public void setOpentoAll(boolean isOpentoAll) {
		this.isOpentoAll = isOpentoAll;
	}

	/**
	 * @return the friendCommentOnly
	 */
	public boolean isFriendCommentOnly() {
		return friendCommentOnly;
	}

	/**
	 * @param friendCommentOnly the friendCommentOnly to set
	 */
	public void setFriendCommentOnly(boolean friendCommentOnly) {
		this.friendCommentOnly = friendCommentOnly;
	}

	/**
	 * @return the pic
	 */
	public BmobFile getPic() {
		return pic;
	}

	/**
	 * @param pic the pic to set
	 */
	public void setPic(BmobFile pic) {
		this.pic = pic;
	}
	
	public enum MessageType{
		TEXT,TEXTANDPICTURE
	}
	
}
