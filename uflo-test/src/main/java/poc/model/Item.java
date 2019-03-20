package poc.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jacky.gao
 * @since 2016年12月12日
 */
@Entity
@Table(name="ITEM")
public class Item {
	/**
	 * 编号
	 */
	@Id
	@Column(name="ID_",length=60)
	private String id;

	/**
	 * 姓名
	 */
	@Column(name="USER_NAME",length=100)
	private String username;

	/**
	 * 职级
	 */
	@Column(name="JOB_CODE",length=20)
	private String jobCode;

	/**
	 * 渠道
	 */
	@Column(name="CHANNEL",length=20)
	private String channel;

	/**
	 *
	 */
	@Column(name="ASSIGN_DATE_")
	private Date assignDate;//分派日期

	
	@Column(name="ERROR_CODE_",length=30)
	@Enumerated(EnumType.STRING)
	private ErrorCode errorCode;//差错类型
	
	@Column(name="PROCESSINSTANCE_ID_")
	private long processInstanceId;
	
	@Transient
	private long taskId;
	
	@Transient
	private String url;
	
	@Transient
	private String taskName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
