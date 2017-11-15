*********数据库连接配置********
1. 设置Maven pom.xml文件
	找到DB配置段，设置对应的数据依赖和连接池依赖
	如： 		
			<!-- DB -->
			<dependency>
				<groupId>mysql</groupId>
				<artifactId>mysql-connector-java</artifactId>
				<version>5.1.35</version>
				<scope>runtime</scope>
			</dependency>
			<dependency>
				<groupId>c3p0</groupId>
				<artifactId>c3p0</artifactId>
				<version>0.9.1.2</version>
			</dependency>
			
2. 设置数据库连接的参数
	src->main->resource->spring->jdbc.properties
	如： 
	driver=com.mysql.jdbc.Driver
	url=jdbc:mysql://localhost:3306/ems?useUnicode=true&characterEncoding=utf8
	user=root
	password=123456

3. 整合spring、数据库、DAO层
   src->main->resource->spring->spring-dao.xml