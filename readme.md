#State-Base Control Flow Engine
基于状态的控制流引擎
## 写在前面
项目本来目的是为了实现无状态的状态机,嗯主要是搞状态机。

应用一段时间之后，发现无状态的情况下，状态机的很多功能都不容易实现。

该项目越搞越像基于状态的控制流，离初衷越来越远。

而且无状态状态机，名字本身就自相矛盾，索性就叫无状态状态机好了。

## 项目简介

致力于通过状态控制程序执行的流程,适用于状态敏感的场景。

关于状态机的概念基本来自Spring StateMachine.

相比于Spring StateMachine 更注重流程控制。

## 使用文档

尽量贴近标准uml状态机使用流程。

### 基本概念解释

先上个状态图：

![choice](https://www.planttext.com/api/plantuml/img/bLFDIiD06BplK-IO27q1AQKnNJLeqz0a1x4bX6HBWsq2xURWBGV1eaABY8W_gEYf-0CUjBJYqpIhVGkJvgV15j7RPcQ-sPtvIBwDjHPscWs0JTn0b5wtJHq1IMRBChLCJLcW2Ah8yWN_crpsoSdpU7G6E5RWu2OCGE_jnUkUajSEY5G-4AbA8XGIpRixSSz16QugGa5bbG8lyyAQpqrSbpnyU4-tv6Om7Zp62adXE3WT3WMaq_CEhe7_91o_KxKjjOLgZcMe1h9sgMmsn7CvVogIfFWeLYQJesQHk39aPKXxdGjlD6IIh9bjmyOO6LLVdCeQ7kamBrEncXhMwvHiDb79cHKLt13PY-mM7PR1W8JRNge6NkkGsw2_zllapasHIMV1WGvRIz7p_KK8LoeMUPactTvuU1zMniGB-F_WJtNWiBZqnAS01S4FUtZyERhwpMPkln4Wif94UxqZijUDnnVVwEz6QzV3tKGrnvjOqVGTsHOrq_Z3RD9qnJ9GJNCQ6EIHPGH_mpS0)

State : 状态。Start,UN_PAID等，这些都是状态。

Event: 事件。箭头连线上的标识，CREATE,PAY等都是事件

Guard: 守卫。代表响应事件的条件，达成条件方可执行。上图 CANCEL 事件下方的 match Timeout代表达成TimeOut条件才会相应。

Action:动作。事件响应结束要执行的动作，需要pass Guard。图中未标出。

Transition:变换。一次完整的变换，包含Guard条件判断，transit 状态转移，Action动作执行。

1. External Transition。外部变换，一定会发生状态转移。
2. Internal Transition。内部变换，不发生状态转移。上图CHECK事件，并未改变状态
3. Choice Transition。条件变换，是External变换的特例，类似if-else，会根据多个Guard动态选择满足条件的分支。上图REFUND_DENNY事件之后的菱形代表发生choice分支，match BackToPaid会返回PAID State，否者default OPENED

Pseudo State: 伪状态。比如Choice Transition中发生分叉的Choice节点就是伪状态。伪状态不是真实存在的状态，用于一些特殊的用途。

1. choice state。用于分支选择。上图菱形节点
2. history state。用于历史状态记录。见下图。HISTORY节点即为历史节点，会关注PAID,OPENED的状态并记录，在响应REFUND_DENNY事件之后，会恢复之前记录的历史状态，依据历史动态路由到PAID or OPENED

![](https://www.planttext.com/api/plantuml/img/SoWkIImgAStDuIh9BCb9LSZ8B2x9Bwha2d3qT56mKdYwVTEpAUkVx9u14_20o2iuLIfuU0Q7-0T5Sla7kFgvGXK_tTFmic4MGWYByY1Njr0_btZ7K1VF44y_Tw2YbmqDpzRiUxfkuRC5EvxiM0jJ4HpgxEmADWMYu5d7XATTyxcWvW25OMv5Y1IbfWMN99Qa5bj5Wzohem1nI2p4zRgwTWfmbu4O6X3dQYAaCHq75N7sz_Nr3D5urZ_XoQub4FTemhn9kaPqrI0JeBw8zeX5Q7PnzVEBr7ZMFUdvxhduz6A465GWm34uMEFfX9vdBVqmxGwfUIcWw0O0)

### 功能特性

#### 标准功能

支持基于java代码进行状态机Model的配置

支持标准的Guard -> Transite -> Action 流转模型

支持External,Internal两种Transition

支持ChoiceState,HistoryState,EntranceState

支持复合状态，通过StateGroup实现

支持通过EventListener,Interceptor进行扩展


#### 扩展功能
支持通过StateMachineModel生成puml
支持webConsole,展示StateMachine MetaInfo

### 项目结构

statemachine-core				 状态机核心逻辑

statemachine-extend			状态机扩展逻辑，支持状态机管理

statemachine-spring			 状态机与Spring集成

statemachine-webserver	 状态机Web-Controller

statemachine-spring-boot-starter 启动入口

statemachine-demo			状态机demo


### QuickStart
1. 新增依赖

   ```xml
   <dependency>
      <groupId>com.doubao</groupId>
      <artifactId>statemachine-spring-boot-starter</artifactId>
      <version>0.0.7-SNAPSHOT</version>
   </dependency>
   ```

2. 定义model

    1. 事件

    ```java
    public enum Events {
       CANCEL("取消"),
       CREATE("创建"),
       PAY("支付"),
       OPEN("开通"),
       CHECK("自检"),
       REFUND_COMMIT("提交退款"),
       REFUND_DENNY("拒绝退款"),
       REFUND_PASS("同意退款");
       @Getter
       private final String desc;
   
       Events(String desc) {
          this.desc = desc;
       }
    }
    ```
    2. 状态

    ```java
    public enum States {
       CANCELED("已取消", 1),
       START("start", 2),
       UN_PAID("未支付", 3),
       PAID("已支付", 4),
       OPENING("开通中", 5),
       OPENED("已开通", 6),
       REFUNDING("退款中", 7),
       REFUND_AUDITING("退款审核中", 8),
       REFUND_SUCCEED("退款成功", 9),
       //***********************
       //choice
       //***********************
       choice_on_open("choice_open", 1),
       choice_on_refund_pass("choice_refund_pass", 1),
       choice_on_refund_deny("choice_refund_deny", 1),
       //***********************
       //history
       //***********************
       /**
        * history focus on paid and opened
        */
       HISTORY("history", 1);
   
       @Getter
       private final String desc;
       @Getter
       private final int code;
   
       States(String desc, int code) {
          this.desc = desc;
          this.code = code;
       }
   
    }
    ```

3. 全局配置

   ```java
   @Configuration
   @StateMachineScan({"config.package.path"})
   public class StateMachineConfig {
       /**
       *仅用于状态展示
       */
   	@Bean
   	public StateTranslator<States> stateTranslator() {
   		return new DefaultStateTranslator();
   	}
       /**
       *仅用于事件
       */
   	@Bean
   	public EventTranslator<States> eventTranslator() {
   		return new DefaultEventTranslator();
   	}
   }
   ```

4. 状态机配置

    ```java
    @MachineConfig(id="simple",name = "简单状态机配置")
    public class SimpleConfig  extends StateMachineConfigAdapter<States, Events, Void> {
       @Override
       public boolean support(TestOrderInfo content) {
          return content.getTestOrderMain().getType().equals(OrderTypes.SIMPLE);
       }
    
       @Override
       public void configureStates(StateMachineStateConfigurer<States, Events, Void> stateConfigurer) {
          stateConfigurer.withStates()
             .startState(States.START);
       }
       @Override
       public void configureTransitions(
          StateMachineTransitionConfigurer<States, Events, Void> transitionConfigurer) {
          transitionConfigurer
             .withSource(States.START)
             //create
             .external(Events.CREATE, States.UN_PAID);
          transitionConfigurer
             .withSource(States.UN_PAID)
             //cancel
             .external(Events.CANCEL, States.CANCELED, new TestAction())
             //pay
             .external(Events.PAY, States.PAID)
             //check
             .internal(Events.CHECK, context -> System.out.println("check only,don't change state"));
    
          transitionConfigurer
             .withSource(States.PAID)
             //open
             .external(Events.OPEN, States.OPENED);
    
       }
    }
    ```

5. Fire

   ```java
   @Autowired
   protected StateMachineManager machineManager;
   
   public void testSelectById() {
       StateMachine<States, Events, Void> stateMachine = getById("simple");
       Message<States, Events, Void> message = Message.of(States.START, Events.CREATE, null);
       Result<States, Events, Void> fire = stateMachine.fire(message);
   }
   ```

具体细节请参考statemachine-demo
