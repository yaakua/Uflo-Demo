package poc;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;

/**
 * @author Jacky.gao
 * @since 2016年12月13日
 */
public class EndNodeEventHandler implements NodeEventHandler {
	private JdbcTemplate jdbcTemplate;
	public void enter(Node node, ProcessInstance processInstance,Context context) {
		String businessId=processInstance.getBusinessId();
		String sql="update FEEDBACK_USER set END_=? where ITEM_ID_=?";
		jdbcTemplate.update(sql,new Object[]{true,businessId});
	}

	public void leave(Node node, ProcessInstance processInstance,Context context) {
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
