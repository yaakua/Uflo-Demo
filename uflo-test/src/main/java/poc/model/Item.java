package poc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Jacky.gao
 * @since 2016年12月12日
 */
@Entity
@Table(name="ITEM")
public class Item {
	@Id
	@Column(name="ID_",length=60)
	private String id;//申请件编号
	
	@Column(name="STORE_",length=100)
	private String store;//门店
	
	@Column(name="ASSIGN_DATE_")
	private Date assignDate;//分派日期
	
	@Column(name="INSPECTOR_")
	private String inspector;//质检人员
	
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

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
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

	public String getInspector() {
		return inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
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

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}
