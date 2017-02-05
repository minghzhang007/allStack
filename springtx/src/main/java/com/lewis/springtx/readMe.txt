Spring将事物管理分成两类：
1.编程式事务管理
	需要手动编写代码进行事务管理（很少使用）
2.声明式事物管理
	2.1 基于TransactionProxyFactoryBean的方式（很少使用），
	 需要为每个进行事务管理的类，配置一个TransactionProxyFactoryBean进行增强。
	2.2 基于AspectJ的XML方式（经常使用）
	    一旦配置好之后，不需要修改代码
	2.3 基于注解方式（经常使用） 配置简单，需要在业务层类上添加@Transactional注解