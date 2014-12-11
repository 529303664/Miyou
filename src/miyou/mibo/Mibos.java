package miyou.mibo;

import java.io.Serializable;
import java.util.List;

import android.graphics.Bitmap;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Mibos extends BmobObject implements Serializable {

	private String headUserName="";//用户名
	private String MiboTime;//秘博的时间
	private String content = "";//秘博的内容
	private Integer favorCount = 0;//秘博的赞数
	private int commentCount = 0;//秘博的评论数
	private boolean isOpentoAll = true;//判断秘博是否对所有用户可见
	private boolean friendCommentOnly = true;//判断秘博是否只让朋友阅读
	private BmobFile pic = null;//秘博的网络图片对象
	private MessageType type = MessageType.TEXTANDPICTURE;//判断秘博是否包含图片
	private int parentId = 0;//秘博的父ObjectId（Bmob）
	private Bitmap headBitmap = null, backgroundBitmap = null;//分别为秘博的用户头像和图片
	private List<Mibos> comment;//秘博的评论

	public enum MessageType {
		TEXT, TEXTANDPICTURE
	}

	public Mibos() {
		/*
		 * //设置bmob服务器上的表格名称 this.setTableName("");
		 */
	}

	// 测试用的构造
	public Mibos(String headUserName,String content, Integer favorCount, int commentCount) {
		this.headUserName = headUserName;
		this.content = content;
		this.favorCount = favorCount;
		this.commentCount = commentCount;
	}

	/**
	 * @return the favorCount
	 */
	public Integer getFavorCount() {
		return favorCount;
	}

	/**
	 * @param favorCount
	 *            the favorCount to set
	 */
	public void setFavorCount(Integer favorCount) {
		this.favorCount = favorCount;
	}

	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the zanCount
	 */
	public Integer getZanCount() {
		return favorCount;
	}

	/**
	 * @param zanCount
	 *            the zanCount to set
	 */
	public void setZanCount(Integer zanCount) {
		this.favorCount = zanCount;
	}

	/**
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount
	 *            the commentCount to set
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
	 * @param isOpentoAll
	 *            the isOpentoAll to set
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
	 * @param friendCommentOnly
	 *            the friendCommentOnly to set
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
	 * @param pic
	 *            the pic to set
	 */
	public void setPic(BmobFile pic) {
		this.pic = pic;
	}

	/**
	 * @return the type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(MessageType type) {
		this.type = type;
	}

	/**
	 * @return the headBitmap
	 */
	public Bitmap getHeadBitmap() {
		return headBitmap;
	}

	/**
	 * @param headBitmap
	 *            the headBitmap to set
	 */
	public void setHeadBitmap(Bitmap headBitmap) {
		this.headBitmap = headBitmap;
	}

	/**
	 * @return the backgroundBitmap
	 */
	public Bitmap getBackgroundBitmap() {
		return backgroundBitmap;
	}

	/**
	 * @param backgroundBitmap
	 *            the backgroundBitmap to set
	 */
	public void setBackgroundBitmap(Bitmap backgroundBitmap) {
		this.backgroundBitmap = backgroundBitmap;
	}

	/**
	 * @return the comment
	 */
	public List<Mibos> getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(List<Mibos> comment) {
		this.comment = comment;
	}

	/**
	 * @return the headUserName
	 */
	public String getHeadUserName() {
		return headUserName;
	}

	/**
	 * @param headUserName the headUserName to set
	 */
	public void setHeadUserName(String headUserName) {
		this.headUserName = headUserName;
	}

	/**
	 * @return the miboTime
	 */
	public String getMiboTime() {
		return MiboTime;
	}

	/**
	 * @param miboTime the miboTime to set
	 */
	public void setMiboTime(String miboTime) {
		MiboTime = miboTime;
	}

}
