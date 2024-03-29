# 学习笔记

`author: nie`

`update: 2023-01-18`

## 一 、Docker(idea集成)

### 1.1    步骤

``` 
 1、修改pom文件后   利用maven打包(注意pom文件中的ip地址)
 2、注意打包生成的jar包和dockerfile 中的 copy指令的jar包是否一致 
 3、package后会生成一个镜像
 4、指定运行端口和绑定端口和ip地址(docker容器地址)
 5、直接运行
```



### 1.2    注意事项

```java
 1、更改完配置后  打包之前在idea里重新连接 docker容器
 2、idea连接docker时 设置ca证书则需要通过https连接  未设置则通过tcp连接
 3、出现日志乱码首先查看容器内部日志
 4、进入容器报错找不到地址、可能是因为镜像原因不一致导致
    - docker exec -it container-test /bin/bash
	- docker exec -it container-test /bin/sh
	- docker exec -it container-test /bin/csh
```





## 二 、Redis

### 2.1  常用数据类型

```
- String  :  正常存储
- Set     :  无序集合  key值相同 可以一直向当前key添加数据  如果数据重复则覆盖 返回0 反之 返回1
- Hash    :  相当于java 中的map  可以向相同key中添加数据 一次添加一个map 或者 put一个(key,value)
- List    :  可以选择向列表头部或者尾部添加数据 如果添加在链表中会影响效率 添加在首尾效率更快 取值可以用key及 index 取值                  返回列表中 下标为 index 的值
```



### 2.2  连接方式

#### 2.2.1 default(单机)



#### 2.2.2   主从模式

```java
- 同步: 从服务器向主服务器发送请求,主服务器收到请求后生成RDB快照文件,发送给从服务器
- 命令传播: 主服务器做修改操作后将命令缓存,并发送给从服务器,从服务器载入命令,保持数据跟主服务器一致
- 优点: 读写分离、提高了可用性、解决了单机故障、主从数据复制期间节点是非阻塞状态、依然可用
- 缺点: 主节点宕机后需要手动切换节点、同时部分数据无法同步到从服务器、多个从节点宕机后、重启启动后大量同步命令导致主节点			   io压力增大、在线扩容复杂
```

​     

#### 2.2.3 sentinel(哨兵) 

1、主要作用

```
-  弥补主从模式中主节点宕机后需要手动切换节点、主要作用用于监控集群、自动切换主备、完成集群故障转移当主服务器故障时、从服务器会停     止复试、会在从服务器选举出一个当做主服务器、同时等待主服务器再次上线 也可以将主动将主变从、将从变主
```

2、故障转移

```
-  sentinel实例每秒都会ping集群一次、如果某个回复的时间超过了down-after-milliseconds配置的值、那么该实例就会认为集群节点下线、当集群中其他sentinel实例也确认他为主管下线时、就变为客观下线、如果主服务器被标记为主观下线、则sentinel其他实例每秒确认一次mater是否下线、当足够的实例认为他主观下线、则master为客观下线
```

3、优点

```
-  哨兵模式基于主从、所以主从模式优点都具备、哨兵具备主从切换和故障转移、集群可用性更高
```

4、缺点

```
-  在线扩容复杂
```

#### 2.2.4 cluster(集群)

​     采用Sharding技术、使用分布式存储、每台机器节点存储不同内容

1、分片原理

```
-  数据分片采用hash slot, 集群中有16384个哈希槽,每个key通过CRC16校验后对16384取模来决定放在哪个槽中、当存取redis key时,会根据CRC16算法判断key在哪个节点中
```



2、复制原理

```
-  引用了主从复制模型,一个主节点对应一个或多个从节点、当半数主节点ping其中一个主机点都超时、则认为该主节点宕机、当主节点宕机时、会启用从节点、当主、从节点都宕机、则该集群无法提供服务
```

### 2.3 注意事项

```
-  集成redis后 先将redis自带的序列化方法换成Jackson2JsonRedisSerializer序列方法
-  根据常用程度考虑是否编写工具类
-  redis 不支持 localdatetime 时间格式 需要在配置文件中格式化 或者通过注解的形式解决问题
-  设置数据类型过期时间时 : String类型会覆盖掉之前过期时间(修改时不设置时间会默认没有过期时间);Hash类型修改单个item时不设置过    期时间 会不去修改时间 设置过期时间会覆盖之前的时间
-  redis 三种模式在Java的yml配置文件中对应的配置方式不同
```

### 2.4 常见问题及解决方案

#### 2.4.1 缓存击穿

```java
-  概念: 指缓存中不存在但数据库存在的数据，访问量过大后导致数据库压力增大
-  解决方案: 设置热点数据不过期、接口限流与熔断降级、采用布隆过滤器
```

#### 2.4.2 缓存穿透

```java
-  概念: 指缓存和数据库中都不存在数据，由于缓存是查询数据库后写入的，在数据库中查询不到则不会写入，导致每次请求都去数据库中去查询，失去了缓存的意义
-  解决方案: 增加接口校验，防止恶意请求、缓存和数据库中都没有取到的数据可以将value，设置为null，并设置过期时间但不能太长(30s)左右
```

#### 2.4.3 缓存雪崩

```java
-  概念: 缓存中的数据大批量到期失效，从而导致请求直接查询数据库，导致数据库压力增大
-  解决方案: 缓存的过期时间设置随机、如果缓存数据库是分布式的，将数据均匀分布在不同数据库中，设置热点数据永不过期
```

#### 2.4.4 布隆过滤器

```java
-  概念: 类似于HashSet 用于判断某个元素是否在集合中  引入maven依赖后即可使用
```



## 三、 RabbitMQ

### 3.1  模式

#### 3.1.1  fanout 模式

```
步骤 :
     1,创建一个fanout交换机
     2,创建两个队列
     3,将两个队列与fanout交换机进行绑定
     4,发送消息时两个队列都会收到消息 (注意不要监听同一个队列)
```



#### 3.1.2  direct模式

```
步骤 :
     1,创建一个direct共用交换机
     2,创建两个队列
     3,将交换机和2个队列及其路由绑定
     4,创建生产者指定交换机及路由及数据
     5,监听队列  消费信息
ps : 发送消息时必须指定交换机和路由key
```



#### 3.1.3  topic模式

```
步骤 :
     1,创建一个topic共用交换机
     2,创建三个队列便于测试
     3,将队列与交换机与通配符路由(routing.demo.* 或者 routing.# 或者 #.demo.*)绑定
     4,创建生产者指定交换机及路由及数据
     5,监听队列  消费信息
理解 :
     发送消息时路由是固定的 根据绑定关系选择一个或多个符合条件的队列 然后去队列中消费集合
ps  :
     通配符路由是写在 步骤第三步中
     监听的队列和发送消息时 路由和队列是没有通配符匹配的
```



#### 3.1.4  headers模式

```
步骤 :
     1,创建一个headers共用交换机
     2,创建两个个队列便于测试
     3,将队列与交换机与headers绑定 并设置headers参数
     4,创建生产者指定交换机设置头部及数据
     5,监听队列  消费信息
理解 :
     发送消息时根据你设置的头部消息和绑定队列时设置的头部对比  any 为其中一条符合就可以  all则需要所有头部匹配
ps  :
     whereAll或whereAny中 只要参数接map后面必须是.match  参数是多个字符串则接 .exist、 exist方法只是把传入的字符串key 变      成的map的key值 map的value值为空
```



### 3.2  延时队列

#### 3.2.1  时间轮

采用时间轮 + rabbitmq 延时队列实现定时上下架

```
原理 :
	   1——60中的质数(包含60,60作用是时间差超过60 的都丢入60) ,一个死信交换机 每个质数作为一个路由key,及队列,发送时对比时间差	   判断丢入哪个队列中,例如65 就丢入 60中 然后监听正常队列  消息到该队列中 由于还有五分钟时间差就继续丢入死信队列中 key 值为      质数5 然后在正常队列中消费时发现没有时间差就直接消费
```



~~原理(废弃) :
	 创建一个死信交换机 并设置消息过期时间  在消息过期时转发给普通队列中  监听并消费普通队列
步骤(废弃) :
     1,创建一个死信队列并设置消息过期时间(也可以不设置 在发送消息时通过时间函数去设置) 指定消息过期后转发的交换机及其路由key、创建一个普通队列
     2,创建一个死信交换机和一个普通交换机
     3,将死信队列和死信交换机和死信路由key 绑定、将普通队列和普通交换机和普通路由key 绑定
     4,发送消息(如果创建死信队列时 未指定过期时间则需要在发送时通过函数去指定过期时间)
     5,监听普通队列~~

```
原理 :
	创建普通队列并设置队列中消息的过期时间和转发的交换机，在消息过期时转发给指定的交换机，监听并消费交换机绑定的队列 
步骤 : 
	1,创建普通队列和死信队列,普通队列中设置消息过期时间(如不设置可以在发送消息时指定)、交换机、路由key ;
	2,创建一个普通交换机和一个死信交换机
	3,将普通队列和普通交换机和普通路由key 绑定 、将死信队列和死信交换机和死信路由key 绑定
	4,发送消息到普通队列(消息过期后会投递到绑定的交换机中)
	5,监听死信队列
     
```

### 3.3  Java和Python端互传消息

```
 planA :
        暂时采用fanout方式创建一个交换机绑定两个队列 利用特性将产生的消息通过交换机发送到所绑定的队列中 然后进行消费
```

 

## 四、Mybatis Plus 

1、 逻辑删除

```
  yml中配置logic-delete-value和logic-not-delete-value的值,实体类字段加上@TableLogic注解
```



2、 数据库字段对应枚举

      yml中配置type-enums-package属性并指定扫描的包 枚举类实现IEnum<Integer>  实体类字段类型对应枚举类 枚举类中需要展示的字段加上@JsonValue注解



## 五、Java + Spring

### 5.1  Async(异步)

```
    1:主方法无需加@Async 注解
    2:调用方法需要加入@Async注解 主方法不能和调用方法在同一个类
    3:使用异步时需在启动类开启@EnableAsync 注解
```



### 5.2  Aspect(切面)

#### 5.2.1  步骤

```
    1:导入aop 依赖
    2:注解上使用@Documented,@Target,@Retention 并指定属性
      - @Documented 起标识作用 可以使在javaDoc中生成
      - @Target 作用域 例如@Target(ElementType.METHOD) method指作用在方法上
      - @Retention 注解的生命周期 例如@Retention(RetentionPolicy.RUNTIME)  RUNTIME代表始终有效
    3:
      - 定义切面类,类上需要加上@Aspect 注解 和@Component注解注入spring容器中  @Order(1)注解作用是定义切面执行顺序 数值越         小、执行顺序越高
      - @Pointcut("@annotation(com.java.xxx)") 标识切点方法并指定注解路径
      - @Around("切点方法名")  入参必须以ProceedingJoinPoint为第一个参数 必须有proceedingJoinPoint.proceed() 走向下          一个方法
      - @Before("切点方法名")  入参是JoinPoint 修改其中参数  方法结束后会自动带给下一方法
```





#### 5.2.2  (注解)切面执行顺序

```
  -  方法正常: @Around->@Before->请求方法->@AfterReturning->@After->@Around
  -  方法异常: @Around->@Before->请求方法->@AfterThrowing->@After
  -  多个切面执行顺序:@Around->@Before->@Around->@Before->请求方法->@AfterReturning->@After->@Around
                   ->@AfterReturning->@After->@Around
```



#### 5.2.3    注意事项

```
多个切面执行顺序 可以理解为 是将后执行的切面当作一个方法区
```



### 5.3  垃圾回收(GC)

1、新生代

```
保存刚创建的对象、占用内存比较小的对象 、会频繁的进行MinorGC、细分为三个区Eden、SurvivorFrom、SurvivorTo、默认比例为 8:1:1

- Eden区 : 保存刚刚创建的对象 、当该区内存不够时就会触发MinorGC(标记-复制算法)、对新生代进行一次垃圾回收  ;
- SurvivorFrom区和SurvivorTo区 : GC开始时、对象只会存在于Eden区和SurvivorFrom区、一次GC后会将存活的对象放入SurvivorTo区并将其年龄+1 、然后清空前两个区的对象、当对象年龄达到15则放入老年代 ;
```



2、老年代

```
存放大对象或者存活久的对象、稳定、不会进行频繁的MajorGC(标记-清除算法)、在MajorGC前都会执行一次MinorGC、 当新生代进入老年代内存不够时或声明较大对象时会触发MajorGC、当老年代也没有内存分配给新进入的对象时则会抛出OOM异常
```



3、永久代

```
主要存放Class和Meta(元数据的信息)、Class在加载时放入永久代中
```



### 5.4 spring boot 启动顺序

<img src="https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/6ba8bf5c8177430b8f462f35948d1c74~tplv-k3u1fbpfcp-zoom-in-crop-mark:4536:0:0:0.awebp?" alt="img" style="zoom: 67%;" />



```java
执行顺序 :
	bean 初始化完成  ->  所有实现ApplicationListener<ContextRefreshedEvent> 的类  -> 所有实现CommandLineRunner 的类
    
ps : 实现CommandLineRunner比实现ApplicationListener更加灵活 可以在前者类上使用@Order注解来表名执行顺序
```







#### 5.4.1 @SpringBootApplication

```java
-  @SpringBootConfiguration
   继承了Configuration，表示当前是注解类
    
-  @EnableAutoConfiguration
   开启springboot的注解功能，springboot的四大神器之一，其借助@import的帮助
    
-  @ComponentScan
   basePackages属性配置扫描路径,不写默认当前类路径


```



#### 5.4.2  new SpringApplication()

```java
this.resourceLoader = resourceLoader;
Assert.notNull(primarySources, "PrimarySources must not be null");
this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
// 判断当前程序类型
this.webApplicationType = WebApplicationType.deduceFromClasspath();
// 使用SpringFactoriesLoader 实例化所有可用的初始器
setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
// 使用SpringFactoriesLoader 实例化所有可用的监听器
setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
// 配置应用主方法所在类
this.mainApplicationClass = deduceMainApplicationClass();
```



#### 5.4.3  SpringApplication.run()

```java
		// 开启时钟计时
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		// spirng 上下文
		ConfigurableApplicationContext context = null;
		// 启动异常报告容器
		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
		// 开启设置，让系统模拟不存在io设备
		configureHeadlessProperty();
		// 初始化SpringApplicationRunListener 监听器，并进行封装
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting();
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
            // Environment 的准备 
			ConfigurableEnvironment environment = prepareEnvironment(listeners,applicationArguments);
			configureIgnoreBeanInfo(environment);
            // banner
			Banner printedBanner = printBanner(environment);
            // 创建上下文实例
			context = createApplicationContext();
            // 异常播报器，默认有org.springframework.boot.diagnostics.FailureAnalyzers
			exceptionReporters = getSpringFactoriesInstances(
					SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
             // 容器初始化
			prepareContext(context, environment, listeners, applicationArguments,printedBanner);
            // 刷新上下文容器 
			refreshContext(context);
            // 给实现类留的钩子，这里是一个空方法。
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
			}
			listeners.started(context);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, null);
			throw new IllegalStateException(ex);
		}
		return context;
```



### 5.5 线程池

#### 5.5.1 Executors(不推荐)

```java
- newCachedThreadPool 
    创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收线程，则新建线程  
    调用new ThreadPoolExecutor
- newFixedThreadPool 
    创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    调用new ThreadPoolExecutor
- newScheduledThreadPool 
    创建一个定长线程池，支持定时及周期性任务执行。
    调用new ThreadPoolExecutor
- newSingleThreadExecutor
    创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
    调用new ScheduledThreadPoolExecutor
```

#### 5.5.2  ThreadPoolExecutor

```java
参数介绍:
   - corePoolSize   核心线程池大小
   - maximumPoolSize  最大线程池大小
   - keepAliveTime    线程最大空闲时间
   - unit            时间单位
   - workQueue      线程等待队列
   - threadFactory   线程创建工厂
   - handler     拒绝策略
  
```



#### 5.5.3  ScheduledThreadPoolExecutor

```java
参数介绍:
   - corePoolSize   核心线程池大小
   - threadFactory   线程创建工厂
   - handler     拒绝策略
常用方法介绍:
   - schedule(Runnable command,long delay, TimeUnit unit)   指定command任务将在delay延迟后执行
   - schedule(Callable<V> callable,long delay, TimeUnit unit)  指定callable任务将在delay延迟后执行
   - scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit)   initialDelay + period 开始， initialDelay + n * period 处执行
   - scheduleWithFixedDelay(Runnable command,long initialDelay,long delay,TimeUnit unit);   创建并执行一个在给定初始延迟后首次启用的定期操作，上一次任务执行完成到下一次任务开始都存在 delay的延迟 、如果任务在任一一次执行时遇到异常，就会取消后续执行、否则，只能通过程序来显式取消或终止该任务

```



#### 5.5.4 注意事项

```java
- callable  和 runnable的区别
	Runnable 接口 run 方法无返回值；Callable 接口 call 方法有返回值，是个泛型，和Future、FutureTask配合可以用来获取异步执行的结果
	Runnable 接口 run 方法只能抛出运行时异常，且无法捕获处理；Callable 接口 call 方法允许抛出异常，可以获取异常信息
```






## 六、设计模式

### 6.1  创建型模式

#### 6.1.1 单例模式

```
-  说明: 意图是一个对象只有一个实例、并且提供一个访问该对象的全局节点
-  特征: 将默认构造函数设为私有，防止其他对象使用单例类的new运算符 、新建一个静态构建方法作为构造函数。该函数会调用私有构造函           数来创建对象，并将其保存在一个静态成员变量中。 此后所有对于该函数的调用都将返回这一缓存对象。    
-  单线程: 隐藏无参构造并通过静态方法构建实例
-  多线程: 在多线程中会出错可能同时调用不同的构建方法且返回不同的实例
-  安全单例:采用延迟加载的线程安全单例、对参数 加上volatile关键字、创建对象方法中如果对象值已经存在则返回存在值、不存在则创建           对象并赋值
```



#### 6.1.2 工厂方法模式

```
-  说明: 在父类提供一个创建对象的方法，允许子类决定实例化的对象类型
-  特征:  所有产品必须遵循同一个接口 接口必须声明对所有产品有意义的方法
```

### 6.2 参考文档

>设计模式参考文档  https://refactoringguru.cn/design-patterns/singleton/java/example#example-2



## 七、 监控工具

### 7.1 arthas

#### 7.1.1 linux上运行

```java
- 利用curl 命令下载arthas.jar
- java -jar arthas.jar {pid}  利用arthas 监控jar包
```



#### 7.1.2 docker中运行

```java
- dockerFile中添加arthas到容器中
- 运行容器
- 到arthas 挂载路径中启动arthas 并监听jar
```



#### 注意事项

```java
- docker中运行arthas时 会出现无法监听pid为1的jar,通过更换jdk源为eclipse-temurin:8-jdk-alpine或空跑一个任务占用pid为1的运行任务解决
```



#### 参考文档

> linux中运行   https://arthas.aliyun.com/doc/quick-start.html   

> docker中运行   https://www.cnblogs.com/alisystemsoftware/p/17107089.html