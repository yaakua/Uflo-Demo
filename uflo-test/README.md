## 部署方式
- 本地安装好mysql数据库
- 修改WEB-INF/context.xml 文件当中的数据库配置
- 修改WEB-INF/config.properties 当中的流程配置文件存放路径
- 使用tomcat或者jetty启动服务，注意上下文一定是/uflo-test ，原因是test.TestFilter这个类加了过滤器，只对这个上下文生效，也可以改代码
- 启动后访问http://localhost:8080/uflo-test/index.html 首页
- 首页中有4个账号，随意使用一个，用户名和密码一致登录即可，如：branchUser 密码：branchUser。
- 登录后可以点击“流程配置页面” 打开在线流程配置界面，在上面可以直接打开流程，前提是你的流程配置文件以及存放在 config.properties当中配置的路径下
    - 可以将process/目录下的文件 Copy到 config.properties配置当中配置的uflo.defaultFileStoreDir指向的路径

## 开发注意
-  业务流程与工作流的绑定需要通过代码实现，具体示例参见poc.POCServlet类，通常原理如下
    - 流程配置完成后，需要部署，部署的目的是让工作流引擎能找到该模板，通过“流程名称”来查找
    ```

    	ProcessService ps=(ProcessService)applicationContext.getBean(ProcessService.BEAN_ID);
    		ProcessInstance pi=ps.startProcessByName("入司申请流程", startProcessInfo);
    ```
    - 流程中涉及到的环节进行状态更新时，可以通过调用如下代码实现：
    
    ```

    	TaskService taskService=(TaskService)applicationContext.getBean(TaskService.BEAN_ID);
    		Task task = taskService.getTask(taskId);
    		//只有创建状态才开启
    		if(task.getState().equals(TaskState.Created) || task.getState().equals(TaskState.Reserved)){
    			taskService.start(taskId);
    		}
    		taskService.complete(taskId);
    ```