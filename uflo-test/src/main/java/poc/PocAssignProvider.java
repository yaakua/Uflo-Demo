package poc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.assign.Entity;
import com.bstek.uflo.process.assign.PageQuery;

/**
 * @author Jacky.gao
 * @since 2016年12月12日
 */
public class PocAssignProvider implements AssigneeProvider {

	public boolean isTree() {
		return false;
	}

	public String getName() {
		return "POC角色";
	}

	public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {
		List<Entity> list=new ArrayList<Entity>();
		pageQuery.setPageIndex(1);
		pageQuery.setPageSize(30);
		pageQuery.setResult(list);
		
		list.add(new Entity("branchAuditor","分公司审核人员"));
		list.add(new Entity("headAuditor","总公司审核人员"));
		list.add(new Entity("branchManagerAuditor","分公司领导审核人员"));
		list.add(new Entity("branchUser","分公司录入人员"));
		pageQuery.setRecordCount(list.size());
	}

	public Collection<String> getUsers(String entityId, Context context,ProcessInstance processInstance) {
		List<String> list=new ArrayList<String>();
		list.add(entityId);
		return list;
	}

	public boolean disable() {
		return false;
	}
}
