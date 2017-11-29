package poc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jacky.gao
 * @since 2016年12月12日
 */
@Entity
@Table(name="FEEDBACK_USER")
public class FeedbackUser {
	@Id
	@Column(name="ID_",length=60)
	private String id;
	@Column(name="USER_",length=60)
	private String user;
	@Column(name="ITEM_ID_",length=60)
	private String itemId;
	@Column(name="FEEDBACK_")
	private boolean feedback;
	@Column(name="END_")
	private boolean end;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public boolean isFeedback() {
		return feedback;
	}
	public void setFeedback(boolean feedback) {
		this.feedback = feedback;
	}
	public boolean isEnd() {
		return end;
	}
	public void setEnd(boolean end) {
		this.end = end;
	}
}
