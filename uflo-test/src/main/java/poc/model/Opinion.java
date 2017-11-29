package poc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jacky.gao
 * @since 2016年12月12日
 */
@Entity
@Table(name="OPINION")
public class Opinion {
	@Id
	@Column(name="ID_",length=60)
	private String id;
	
	@Column(name="ITEM_ID_",length=60)
	private String itemId;
	
	@Column(name="USER_",length=60)
	private String user;//反馈人
	
	@Column(name="FEEDBACK_TYPE_",length=20)
	@Enumerated(EnumType.STRING)
	private FeedbackType feedbackType;//反馈结果
	
	@Column(name="DESC_")
	private String desc;//描述

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public FeedbackType getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(FeedbackType feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
