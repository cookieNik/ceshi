一.ceshi包
==
1.做了对springboot的controller、service、dao、mapper基于mockBean做了单元测试，和集成测试.<br/>
2.testJunit4做了基于springbootTest和Junit4的集成测试，该测试demo会自主向下的调用相关接口和服务。<br/>
3.controller、service、dao、mapper做了分层次的单元测试使用mockBean虚拟环境，各个层次业务单独测试，自定义依赖调用的接口的返回结果，更加集中在当前层次   的业务。<br/>
4.testController通过内部注入WebApplicationContext，然后构建MockMvc实例实现调用controller接口。<br/>
5.testControllerwithoutWeb通过使用@AutoConfigureMockMvc注解可以获得自动配置的MockMvc实例<br/>
6.testControllerwithWebMvcTest通过使用@WebMvcTest(UserOutControl.class)、@AutoConfigureMybatis、@Import({UserService.class,UserDao.class})实现对controller接口的调用<br/>
7.做了基于@MybatisTest的mapper、dao层的单元测试，测试结果通过配置可以设置操作或者不操作真实数据库<br/>
----
二.dbunit包
==
1.dbunit包中补充做了springboot+H2+DBUnit单元测试，支持不修改真实数据库，在备份数据库上做的增查操作，有个问题就是数据库数据备份的时候，需要手动添加数据xml配置文件<br/>
