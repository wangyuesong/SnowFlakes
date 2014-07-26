package com.tongji.collaborationteam.pagecontrollers; 
  
import org.junit.runner.RunWith;  
//import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;  
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
import org.springframework.test.context.transaction.TransactionConfiguration;  
import org.springframework.transaction.annotation.Transactional;  
  
/** 
 * @author <a href="mailto:zlex.dongliang@gmail.com">梁栋</a> 
 * @version 1.0 
 * @since 1.0 
 */  
@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)  
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)  
public abstract class AbstractTestCase extends  AbstractTransactionalJUnit4SpringContextTests
       {  
  
}  