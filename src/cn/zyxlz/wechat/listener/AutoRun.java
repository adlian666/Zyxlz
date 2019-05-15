package cn.zyxlz.wechat.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.zyxlz.wechat.utils.Time;

/**
 * Application Lifecycle Listener implementation class AutoRun
 *
 */
public class AutoRun implements ServletContextListener {

    /**
     * Default constructor. 
     */
	private Timer timer = null;  
    public AutoRun() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	timer.cancel();  
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	timer=new Timer(true); 
    	timer.schedule(new Time(),0,1000*60*100);//延迟0秒，每100分钟执行一次DisplayDate()  
    }
	
}
