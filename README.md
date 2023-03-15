# json-convertor

> 这是一个简单的JSON 转换组件

## mvn 引入
```
<dependency>
	<groupId>io.github.shmilyhe</groupId>
	<artifactId>json-convert</artifactId>
	<version>1.0.0</version>
</dependency>
```

## 快速开始

```
        String json ="{\"name":\"eric\"}";
        String commands ="set($.age,12)\r\n"
        +"set($.group[4],1)\r\n"
        +"set($.ext,$.addr)\r\n"
        +"move($,$.data)\r\n"
        +"remove($.age)";
        //JsonConvertor 请创建为单实列不要重复创建，每一组commands 只需创建一个JsonConvertor
        JsonConvertor jc = new JsonConvertor(commands);
        String dest = jc.convert(json);
        System.out.println(dest);
```
## 转换指令

###  变量$ 与表达式
“$”代表JSON 的根
### 对像操作
$.data 代表JSON 的data属性

### 数组操作  
$.data[0] 代表JSON 的data属性的第0 个值

### 指令set
给JSON 设置值

#### 给JSON 添加一个常量值

下面这条指令会给JSON 的code 属性设置为200，如果JSON没有code属性将会自动创建
```
set($.code,200)
```

#### 把JSON 的属性值赋给其它属性
下面这条指令会给JSON 的test 属性设置为data.name的值，如果JSON没有code属性将会自动创建
```
set($.test,$.data.name)
//{"data":{"name":"eric"}}==>{"test":"eric","data":{"name":"eric"}}
```
#### 往JSON 数组添加值
```
set($.data[3],1)
//{"data":[0]}==>{"data":[0,null,null,1]}
```

### move 指令
移动属性

#### 修改属性名字
下面这条指令会给JSON 的test 属性名 改为 test2
```
move($.test,$.test2)
//{"test":1}==>{"test2":1}
```

#### 把JSON 往下移
下面这条指令会给JSON的移到 data 下面

```
move($,$.data)
//{"name":"eric"}==>{"data":{"name":"eric"}}
```

### remove 指令
移除某个属性

```
remove($.data)
//{"data":"eric"}==>{}
```

# 计划支持的特性
1、支持从其服务中添加属性
2、支持运算表达式包括：字符串连接、数值运算、逻辑运算
3、支持输入输出流（不生成字符串）




