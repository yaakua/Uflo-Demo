package poc;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.context.support.WebApplicationContextUtils;

import poc.model.FeedbackType;
import poc.model.FeedbackUser;
import poc.model.Item;
import poc.model.Opinion;
import test.TestFilter;

import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.StartProcessInfo;
import com.bstek.uflo.service.TaskService;

/**
 * @author Jacky.gao
 * @since 2016年12月12日
 */
public class POCServlet extends HttpServlet{
	private static final long serialVersionUID = -1696311963823175508L;
	private JdbcTemplate jdbcTemplate;
	private ApplicationContext applicationContext;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		applicationContext=WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		jdbcTemplate=(JdbcTemplate)applicationContext.getBean("jdbcTemplate");
	}
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String methodName=req.getParameter("method");
		if(StringUtils.isBlank(methodName)){
			throw new ServletException("Method cannot be null.");
		}
		try{
			Method method=this.getClass().getMethod(methodName, new Class<?>[]{HttpServletRequest.class,HttpServletResponse.class});			
			method.invoke(this, new Object[]{req,resp});
		}catch(Exception ex){
			throw new ServletException(ex);
		}
	}
	public void firstSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long taskId=Long.valueOf(req.getParameter("taskId"));
		String path=req.getParameter("path");
		TaskService taskService=(TaskService)applicationContext.getBean(TaskService.BEAN_ID);
		taskService.start(taskId);
		taskService.complete(taskId, path);
	}
	
	public void completeTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long taskId=Long.valueOf(req.getParameter("taskId"));
		String taskName=req.getParameter("taskName");
		String feedbackType=req.getParameter("feedbackType");
		String businessId=req.getParameter("businessId");
		String desc=req.getParameter("desc");
		String loginUser=(String)req.getSession().getAttribute(TestFilter.LOGIN_USERNAME);
		
		String sql="insert into OPINION(ID_,USER_,FEEDBACK_TYPE_,DESC_,ITEM_ID_) values(?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{UUID.randomUUID().toString(),loginUser,feedbackType,desc,businessId});
		
		String path=null;
		if(taskName.startsWith("任务4")){
			if(feedbackType.equals(FeedbackType.confirm.name())){
				path="to 结束";
			}else{
				path="to 分支";
			}
		}
		
		TaskService taskService=(TaskService)applicationContext.getBean(TaskService.BEAN_ID);
		taskService.start(taskId);
		if(path==null){
			taskService.complete(taskId);			
		}else{
			taskService.complete(taskId,path);						
		}
	}
	
	/**
	 * 加载反馈结果
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadOpinions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String itemId=req.getParameter("itemId");
		String sql="select ID_,USER_,FEEDBACK_TYPE_,DESC_ from OPINION where ITEM_ID_=?";
		List<Opinion> opinions=jdbcTemplate.query(sql,new Object[]{itemId},new RowMapper<Opinion>(){
			public Opinion mapRow(ResultSet rs, int rowNum) throws SQLException {
				Opinion opinion=new Opinion();
				opinion.setDesc(rs.getString("DESC_"));
				opinion.setFeedbackType(FeedbackType.valueOf(rs.getString("FEEDBACK_TYPE_")));
				opinion.setUser(rs.getString("USER_"));
				return opinion;
			}
		});
		writeObjectToJson(resp, opinions);
	}
	
	/**
	 * 查询待办
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadTodo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String loginUser=(String)req.getSession().getAttribute(TestFilter.LOGIN_USERNAME);
		TaskService taskService=(TaskService)applicationContext.getBean(TaskService.BEAN_ID);
		TaskQuery query=taskService.createTaskQuery();
		query.addAssignee(loginUser);
		List<Task> tasks=query.list();
		for(Task task:tasks){
			buildFeedbackUser(task);
		}
		List<FeedbackUser> users=loadFeedbackUsers(loginUser,false);
		List<Item> items=new ArrayList<Item>();
		List<Item> notFeedbackItems=new ArrayList<Item>();
		for(FeedbackUser user:users){
			Item item=queryItem(user.getItemId());
			items.add(item);
			if(!user.isFeedback()){
				notFeedbackItems.add(item);
			}
		}
		for(Item item:items){
			for(Task task:tasks){
				if(item.getId().equals(task.getBusinessId())){
					item.setTaskId(task.getId());
					item.setUrl(task.getUrl());
					item.setTaskName(task.getTaskName());
					break;
				}
			}			
		}
		for(Item item:notFeedbackItems){
			if(item.getTaskId()>0){
				continue;
			}
			items.remove(item);
		}
		writeObjectToJson(resp, items);
	}
	
	/**
	 * 查询历史任务
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loadHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String loginUser=(String)req.getSession().getAttribute(TestFilter.LOGIN_USERNAME);
		List<FeedbackUser> users=loadFeedbackUsers(loginUser,true);
		List<Item> items=new ArrayList<Item>();
		for(FeedbackUser user:users){
			items.add(queryItem(user.getItemId()));
		}
		writeObjectToJson(resp, items);
	}
	
	
	private List<FeedbackUser> loadFeedbackUsers(String user,boolean end){
		String sql="select ITEM_ID_,FEEDBACK_ from FEEDBACK_USER where USER_=? and END_=?";
		List<FeedbackUser> users=jdbcTemplate.query(sql, new Object[]{user,end},new RowMapper<FeedbackUser>(){
			public FeedbackUser mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				FeedbackUser user=new FeedbackUser();
				user.setItemId(rs.getString("ITEM_ID_"));
				user.setFeedback(rs.getBoolean("FEEDBACK_"));
				return user;
			}
		});
		return users;
	}
	
	@SuppressWarnings({"deprecation" })
	private void buildFeedbackUser(Task task){
		String taskName=task.getTaskName();
		String itemId=task.getBusinessId();
		String user=task.getAssignee();
		boolean feedback=true;
		if(taskName.indexOf("反馈")==-1){
			feedback=false;
		}
		boolean exist=false;
		String sql="select count(*) from FEEDBACK_USER where USER_=? and ITEM_ID_=?";
		int existCount=jdbcTemplate.queryForObject(sql, new Object[]{user,itemId},Integer.class);
		if(existCount>0){
			exist=true;
			sql="select count(*) from FEEDBACK_USER where USER_=? and ITEM_ID_=? and FEEDBACK_=?";
			int feedbackCount=jdbcTemplate.queryForObject(sql, new Object[]{user,itemId,true},Integer.class);
			if(feedbackCount>0){
				return;				
			}
		}
		if(exist){
			sql="update FEEDBACK_USER set FEEDBACK_=? where USER_ = ? and ITEM_ID_=?";
			jdbcTemplate.update(sql, new Object[]{true,user,itemId});						
		}else{
			sql="insert into FEEDBACK_USER(ID_,USER_,ITEM_ID_,FEEDBACK_,END_) values(?,?,?,?,?)";
			jdbcTemplate.update(sql, new Object[]{UUID.randomUUID().toString(),user,itemId,feedback,false});			
		}
	}
	
	private Item queryItem(final String businessId){
		String sql="select ID_,USER_NAME,JOB_CODE,CHANNEL,ASSIGN_DATE_,PROCESSINSTANCE_ID_ from ITEM where ID_=?";
		Item item=jdbcTemplate.queryForObject(sql, new Object[]{businessId},new RowMapper<Item>(){
			public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
				Item item=new Item();
				item.setId(businessId);
				item.setAssignDate(rs.getDate("ASSIGN_DATE_"));
				item.setJobCode(rs.getString("JOB_CODE"));
				item.setUsername(rs.getString("USER_NAME"));
				item.setChannel(rs.getString("CHANNEL"));
				item.setProcessInstanceId(rs.getLong("PROCESSINSTANCE_ID_"));
				return item;
			}
		});
		return item;
	}
	
	
	/**
	 * 开启流程
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void startProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id=req.getParameter("id");
		String username=req.getParameter("username");
		String jobCode=req.getParameter("jobCode");
		String channel=req.getParameter("channel");
		
		Map<String,Object> variables=new HashMap<String,Object>();
		variables.put("jobCode", jobCode);
		variables.put("channel", channel);
		String loginUser=(String)req.getSession().getAttribute(TestFilter.LOGIN_USERNAME);
		StartProcessInfo startProcessInfo=new StartProcessInfo(loginUser);
		startProcessInfo.setBusinessId(id);
		startProcessInfo.setVariables(variables);
		startProcessInfo.setCompleteStartTask(true);
		
		ProcessService ps=(ProcessService)applicationContext.getBean(ProcessService.BEAN_ID);
		ProcessInstance pi=ps.startProcessByName("入司申请流程", startProcessInfo);
		
		String sql="insert into ITEM(ID_,USER_NAME,JOB_CODE,CHANNEL,ASSIGN_DATE_,PROCESSINSTANCE_ID_) values(?,?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[]{id,username,jobCode,channel,new Date(),pi.getId()});
	}
	
	private void writeObjectToJson(HttpServletResponse resp,Object obj) throws ServletException, IOException{
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setContentType("text/json");
		resp.setCharacterEncoding("UTF-8");
		ObjectMapper mapper=new ObjectMapper();
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		OutputStream out = resp.getOutputStream();
		try {
			mapper.writeValue(out, obj);
		} finally {
			out.flush();
			out.close();
		}
	}
}
