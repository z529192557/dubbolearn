# dubbolearn
dubbo version 2.7.3
App.java,启动类，启动后，可以使用 Dubbo Consumer 向本机20819端口发送dubbo请求

## dubbo学习
- NettyServer.java 
   该java启动一个NettyServer来接受20819端口上的TCP数据流
- StreamDecoder.java  
   该 java 描述了如何 解析一个dubbo的协议，包含细节
