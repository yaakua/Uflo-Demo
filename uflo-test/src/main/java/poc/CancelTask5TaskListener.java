package poc;

import java.util.List;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.listener.TaskListener;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.TaskService;

/**
 * @author Jacky.gao
 * @since 2016年12月13日
 */
public class CancelTask5TaskListener implements TaskListener {
	private TaskService taskService;
	public boolean beforeTaskCreate(Context context,
			ProcessInstance processInstance, TaskNode node) {
		return false;
	}

	public void onTaskCreate(Context context, Task task) {
	}

	public void onTaskComplete(Context context, Task task) {
		TaskQuery query=taskService.createTaskQuery();
		List<Task> tasks=query.rootProcessInstanceId(task.getRootProcessInstanceId()).list();
		for(Task t:tasks){
			if(!t.getTaskName().startsWith("任务5:反馈")){
				continue;
			}
			taskService.start(t.getId());
			taskService.complete(t.getId());
		}
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
}
