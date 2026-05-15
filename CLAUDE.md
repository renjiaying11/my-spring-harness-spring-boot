# SpringBoot 用户积分系统

## 项目信息

- **框架**: SpringBoot 2.7.18
- **JDK**: Java 8
- **数据库**: MySQL (10.253.96.92 / hy-1224)
- **构建工具**: Maven
- **前端**: Thymeleaf

## 开发规范

### Harness Engineering 流程

所有新需求必须按照 Harness Engineering 流程开发：

1. **需求分析** - 理解业务需求，明确功能点
2. **技术设计** - 数据库设计、API 设计、架构设计
3. **代码实现** - 按分层架构开发
4. **测试验证** - API 测试、功能验证
5. **代码提交** - Git 提交并推送

### 分层架构

```
Controller → Service → Repository → Entity
```

- **Controller**: API 接口 + 页面路由
- **Service**: 业务逻辑处理
- **Repository**: 数据访问（Spring Data JPA）
- **Entity**: 数据实体
- **DTO**: 数据传输对象

### 代码规范

- 使用 Lombok 简化代码
- 实体类放在 `entity` 包
- DTO 类放在 `dto` 包
- Service 类必须有 `@Service` 注解
- Controller API 使用 `/api/` 前缀
- 页面路由不加前缀

### 数据库规范

- 表名使用下划线命名（如 `user_points`）
- 字段名使用下划线命名（如 `created_at`）
- `ddl-auto` 设置为 `none`，表结构变更需手动执行 SQL

### 测试要求

- 新功能必须测试验证后再提交
- 积分计算等核心逻辑需要单元测试

## 现有功能

| 功能 | API | 页面 |
|-----|-----|------|
| 用户注册 | POST `/api/users` | `/register` |
| 下单购物 | POST `/api/orders` | `/order` |
| 积分查询 | GET `/api/points/{userId}` | `/points` |
| 积分排行榜 | GET `/api/leaderboard/top100` | `/leaderboard` |

## 运行命令

```bash
# 编译
mvn clean compile

# 运行
mvn spring-boot:run

# 打包
mvn clean package -DskipTests

# 运行 jar
java -jar target/demo-1.0.0.jar
```