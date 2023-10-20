# json-convertor

> 这是一个简单且高效的JSON 转换组件，适合现有的JSON 进行一些加工，如属性，改变层级结构等。在API适配的场景非常有用。



别外这个组件还提供了一个简单高效的[JSON组件，请点击这里](json.md "JSON组件")

## mvn 引入
```
<dependency>
	<groupId>io.github.shmilyhe</groupId>
	<artifactId>json-convert</artifactId>
	<version>2.1.0</version>
</dependency>
```

## 快速开始

给定一个原始的JSON
见文件testfile/test1.json
[测试JSON，请点击这里](testfile/test1.json "测试JSON")

转换脚本
见文件testfile/test1.script
[测试脚本，请点击这里](testfile/test1.script "测试脚本")
测试代码
```
import io.shmilyhe.convert.JsonConvertor;
import io.shmilyhe.convert.tools.ResourceReader;

public class TestJsonConvertor{

    public static void main(String []args){
        String json =ResourceReader.read("testfile/test1.json");
        String commands =ResourceReader.read("testfile/test1.script");
        JsonConvertor jc = new JsonConvertor(commands);
        String dest = jc.convert(json);
        System.out.println(dest);
    }
}
```
转换的结果为：
```
{
    "data": {
        "ext": 15129,
        "abc": true,
        "fromvar": 226935.0,
        "odd": [1,3,5,7,9],
        "persons": [
            {"name":"eric1","age":12},
            {"name":"eric2","age":24},
            {"a":[3],"name":"eric3","age":6},
            {"name":"eric4","age":18},
            {"name":"eric5","age":30},
            {"a":[6],"name":"eric6","age":12},
            {"name":"eric7","age":24},
            {"name":"eric8","age":36}
        ],
        "tmp": 1,
        "name": "eric",
        "att5": "203333",
        "id": 1,
        "addr": {
            "province": "gd",
            "ctiy": "gz"
        },
        "success1": false,
        "group":["g19999","g29999","null9999","null9999",10000]
    }
}
```




## 转换指令

### 属性操作
“.”代表JSON 的根
.data 代表JSON 的data属性

```
.name = "eric"
```

### 变量操作
不以 "." 开头的是变量
变量与性属的区别 ：属性会在JSON转换结果里出现，而变量只是中间过程，不会在JSON中体现

```
tmp = 0
```
### 变量作用域
1.代码块内"{}"可以访问 代码块外的变量 
2.代码块外不能访问代码块内的变量
3.变量第一次赋值，就相当于做了声明

### 数组操作  
.data[0] 代表JSON 的data属性的第0 个值

### 赋值
给JSON 设置值

#### 给JSON 添加一个常量值

下面这条指令会给JSON 的code 属性设置为200，如果JSON没有code属性将会自动创建
```
.code=200
```

#### 把JSON 的属性值赋给其它属性
下面这条指令会给JSON 的test 属性设置为data.name的值，如果JSON没有code属性将会自动创建
```
.test = .data.name
//{"data":{"name":"eric"}}==>{"test":"eric","data":{"name":"eric"}}
```
#### 往JSON 数组添加值
```
.data[3] = 1
//{"data":[0]}==>{"data":[0,null,null,1]}
```

### move 指令
移动属性

#### 修改属性名字
下面这条指令会给JSON 的test 属性名 改为 test2
```
move(.test,.test2)
//{"test":1}==>{"test2":1}
```

#### 把JSON 往下移
下面这条指令会给JSON的移到 data 下面

```
move(.,.data)
//{"name":"eric"}==>{"data":{"name":"eric"}}
```

### del 指令
移除某个属性

```
del(.data)
//{"data":"eric"}==>{}
```

### 分枝
if (表达式){
        操作指令
}

例：
```
if (.name == "eric" ){
        .name=.name+"0000"
}

//{"name":"eric"}==>{"name":"eric0000"}
```

### 遍历
each(属性|变量){
        操作指令
}
说明：遍历代码块的"." 代表被遍历对像。如果在遍历块内要访问上层属性可以先将上层的属性赋给一个变量

示例：
```
removecount=0
each(.persons){
        # 移除18岁以下的人员
        if (.age<18){
                removecount=removecount+1
                del(.)
        }
}

.removes = removecount

```
### 退出
函数：exit,
退出转换，exit 后面的逻辑将不再执行。当多个脚本合并执行时，后面的脚本出不会执行。

示例：
```
.name="Alice"

if .name == "Alice" {
    exit()
}
.name="Bob"

```
执行的结果为：
```
{"name":"Alice"}
```

### 强制全局
函数：global
变量 强制为全局
示例：
```
max=0;
each(.persons){
        # 移除18岁以下的人员
        if (.age>max){
                max=.age
                global(maxage,max);
        }
}
```

### 命名空间
函数：namespace
设置消息的命名空间，在执行器外可以获取命令空间

示例：
```

if messageType == "online" {
    namespace("onOffLine");
}

```



# 计划支持的特性
* 1.支持输入输出流（不生成字符串）
* 2.自定义函数




