package poc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;

/**
 * 根据质检员获取质检组长
 * @author Jacky.gao
 * @since 2016年12月13日
 */
public class QALeaderAssignmentHandler implements AssignmentHandler {

	public Collection<String> handle(TaskNode taskNode,ProcessInstance processInstance, Context context) {
		String inspector=(String)context.getProcessService().getProcessVariable("inspector", processInstance);
		String leader="QALeader1";
		if(inspector.equals("user1")){
			leader="QALeader1";
		}else{
			leader="QALeader2";
		}
		List<String> list=new ArrayList<String>();
		list.add(leader);
		return list;
	}

}
