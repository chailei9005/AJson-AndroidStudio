# AJson-AndroidStudio

JSON解析框架 主要由JSON类调用
JSON解析类
  组包（组成json字符串）统一使用 JSON.toJsonString(T t)
  解包 （json字符串解成javaBean对象）
  List<T> parseArray(String json, Class<T> entityClazz)
  T parseObject(JSONObject json, Class<T> clazz)
  T parseObject(String json, Class<T> clazz)
  T parseObject(JSONObject json, Class<T> clazz, CombinationType combinationType)
  T parseObject(String json, Class<T> clazz, CombinationType combinationType)
  
  combinationType 是为我们公司的需求而产生的，正常json可以不使用。
  
View 注入框架
    在Activity 的onCreate方法 加注解
      @ContentView(R.layout.activity_main),
      并在onCreate方法内调用 ViewUtils.inject(this)
    Fragment 的onCreateView方法上加注解
      @ContentView(R.layout.fragment_main),
      并在onCreateView内调用ViewUtils.inject(this)
    或者不加注解时，在onCreateView方法内调用 
      View view = inflater.inflater(R.layout.fragment_main, null);
      ViewUtils.inject(this, view)
     如果不写@ContentView(R.layout.activity_main)， 而是setContentView(R.layout.activity_main)的话，
     那么ViewUtils.inject(this)必须在setContentView之后才调用。
     

 本框架会把对象的每个一级字段当作数据库的字段保存到数据库中，就算新增了字段也没有关系，数据库自己会增加新的字段，<br>增删改查都非常方便，并且多线程执行也没有问题，
 通过一级字段查找可以得到二级的内容，但是二级的字段是不可以当作查询条件。 
 比如  User 对象 里有userId 和 Address, Address里有regionId， <br>那么只能通过userId查询得到User对象再取Address对象，而不能通过regionId得到User对象
 但是偶尔会出现一些问题，待检查修复，目前总的情况是比较稳定的
 使用方法 
 AIIDBManager dbManager = new AIIDBManager(this);
 dbManager.save(对象);
 dbManager.save(List集合);
 dbManager.findAll(对象.class);
 dbManager.findAll(对象.class, "name=?",new String[]{"张三"});
 dbManager.findFirst(对象.class, "name=?",new String[]{"张三"});
 dbManager.delete(对象.class, "name=?",new String[]{"张三"});
 dbManager.deleteAll(对象.class);
 dbManager.deleteById(id);

 注解 
 Unique 唯一标识 
 @Table 修改表名 默认保存对象的类名
 @Column 修改存储字段名，默认保存字段的字段名 
 @NotNull 不允许为空标识
 
 
 我本人也还是菜鸟，希望和大家一起学习
