package poc;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.listener.TaskListener;
import com.bstek.uflo.process.node.TaskNode;

/**
 * @author Jacky.gao
 * @since 2016年12月15日
 */
public class Task5Listener implements TaskListener {
	private JdbcTemplate jdbcTemplate;
	public boolean beforeTaskCreate(Context context,
			ProcessInstance processInstance, TaskNode node) {
		return false;
	}

	public void onTaskCreate(Context context, Task task) {
		buildFeedbackUser(task);
	}

	public void onTaskComplete(Context context, Task task) {
	}
	
	private void buildFeedbackUser(Task task){
		String itemId=task.getBusinessId();
		String user=task.getAssignee();
		String sql="update FEEDBACK_USER set FEEDBACK_=? where USER_ = ? and ITEM_ID_=?";
		jdbcTemplate.update(sql, new Object[]{true,user,itemId});
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
