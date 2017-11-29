package test;

import org.hibernate.SessionFactory;
import org.springframework.transaction.PlatformTransactionManager;

import com.bstek.uflo.env.EnvironmentProvider;

/**
 * @author Jacky.gao
 * @since 2016年12月7日
 */
public class TestEnvironmentProvider implements EnvironmentProvider {
	private SessionFactory sessionFactory;
	private PlatformTransactionManager platformTransactionManager;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}
	
	public void setPlatformTransactionManager(
			PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String getLoginUser() {
		Object username=RequestHolder.getRequest().getSession().getAttribute(TestFilter.LOGIN_USERNAME);
		return (String)username;
	}

	public String getCategoryId() {
		return null;
	}
}
