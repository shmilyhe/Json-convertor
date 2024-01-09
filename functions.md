#  数据转换语言DRL


## 字符串

### 字符串截取 String.substring
String.substring(str,start,end)
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|str|需要截取的字符串| |
|start|开始|只有start参数且>0 时代表，从start截到结束。只有start时，start<0时，表示从开始截到最后start位  |
|end|截取结束|正数代表截取到结束体，负数代表截取到后几位 |


示例：
```
str="12345";
s1=String.substring(str,1,4)// 结果234
s2=String.substring(str,-1)// 结果1234
s3=String.substring(str,1)// 结果2345
s3=String.substring(str,1,-1)// 结果234

```


### 转小写 String.toLowerCase
String.toLowerCase(str）
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|str|转换的字符串| |


示例：
```
str=String.toLowerCase("ABC");// abc

```

### 转大写 String.toUpperCase
String.toUpperCase(str）
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|str|转换的字符串| |


示例：
```
str=String.toUpperCase("abc");// ABC

```

###  string 转Byte[]
String.getBytes(str)
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|str|字符串）| |


示例：
```
bytes=String.getBytes("abc");

```

###  Byte[] 转 string
string(bytes)
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|bytes|byte 序列（数组）| |


示例：
```
bytes=String.getBytes("abc");
str=string(bytes);//abc

```

## 时间

### Date.parse(obj)
其它值转时间
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|obj|字符串| |

示例：
```
date=Date.parse("2023-06-01 11:12:00")
date2=Date.parse("2023/06/01")
date3=Date.parse(1704761406724)

```

### Date.format(date,format)
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |
|format|格式| |

示例：
```
str=Date.format(now,"YYYY-MM-dd HH:mm:ss")

```

### Date.format(date,format)
格式化
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |
|format|格式| |

示例：
```
str=Date.format(now,"YYYY-MM-dd HH:mm:ss")

```

### Date.getWeek(date)
获取时间在一年的第几个星期
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |


示例：
```
week=Date.getWeek(now)

```

### Date.getDay(date)
获取星期几
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |


示例：
```
week=Date.getDay(now)

```

### Date.getDate(date)
获取日
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |


示例：
```
week=Date.getDate(now)

```
### Date.getMonth(date)
获取月份
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |


示例：
```
month=Date.getMonth(now)

```
### Date.getYear(date)
获取年份
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |


示例：
```
year=Date.getYear(now)

```

### Date.getHour(date)

参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |


示例：
```
Date.getHour(now)

```

### Date.getMinute(date)

参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |


示例：
```
Date.getMinute(now)

```

### Date.getSecond(date)

参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |


示例：
```
Date.getSecond(now)

```


### Date.getTime(date)
获取时间耗秒数
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|date|时间| |


示例：
```
timestamp=Date.getTime(now)

```

## 数字

### number(obj)
其它值转成数字
参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|obj|其它值| |


示例：
```
num=number("1.2");

```

### Number.byteValue(obj)

参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|obj|其它值| |


示例：
```
byte= Number.byteValue(65)

```

### Number.intValue(obj)

参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|obj|其它值| |


示例：
```
num= Number.intValue(65)

```

### Number.longValue(obj)

参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|obj|其它值| |


示例：
```
num= Number.longValue(65)

```

### Number.doubleValue(obj)

参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|obj|其它值| |


示例：
```
num= Number.doubleValue(65)

```

## 正则表达式

文档编写中...
```
Regex.group
Regex.test
Regex.replaceAll
```


## 其它常用函数

### 长度len(obj)

参数说明：
|参数｜说明｜使用说明｜
|----|----|-----|
|obj|其它值| |


示例：
```
length= len([1,2,3,4]);
length= len("1234");

```

### Bytes 
文档编写中...
```
Byte.fromHex
Byte.toHex
```

### Maps

文档编写中...
```
Maps.camelCase
Maps.unixLike
Maps.get
```

### Json

文档编写中...
```
JSON.parse
JSON.stringify
```

### Base64

文档编写中...
```
Base64.decode
Base64.encode
Base64.decodeString
```

### HTTP

文档编写中...
```
http.get
http.post
http.request
```
