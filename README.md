# HelloWorld API — SpringBoot 3.2.0 + Spring Security

笔试题-后端 的答案。一个 SpringBoot 3.2.0 项目：

- `GET /hello` — 需要 Spring Security 认证，返回字符串 `Hello World`；
- `POST /api/login` — 用户名密码登录接口，默认账号 `test` / `123456`，登录成功签发 JWT。

## 技术栈

- Java 17
- Spring Boot 3.2.0
- Spring Security 6.2（无状态 JWT 认证）
- jjwt 0.12.3

## 快速开始

```bash
# 1. 构建并启动（需 JDK 17+）
mvn spring-boot:run

# 或打包运行
mvn clean package
java -jar target/hello-world-api-1.0.0.jar
```

## 接口说明与验证

### 1. 登录获取 token

```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"123456"}'
```

响应：

```json
{"token":"<jwt>","tokenType":"Bearer"}
```

### 2. 携带 token 访问受保护的 HelloWorld 接口

```bash
curl http://localhost:8080/hello \
  -H "Authorization: Bearer <jwt>"
```

响应：

```
Hello World
```

### 3. 未认证访问会被拒绝

```bash
curl -i http://localhost:8080/hello
# HTTP 403 Forbidden
```

## 项目结构

```
src/main/java/com/d5data/exam/
├── ExamApplication.java                 # 启动类
├── config/SecurityConfig.java           # Spring Security 配置（无状态 + JWT 过滤器）
├── controller/
│   ├── HelloController.java             # GET /hello → "Hello World"
│   ├── AuthController.java              # POST /api/login → 签发 JWT
│   └── dto/                             # 请求/响应 DTO（record）
└── security/
    ├── JwtService.java                  # JWT 生成与校验
    ├── JwtAuthenticationFilter.java     # 解析 Bearer token 并注入认证
    └── UserDetailsServiceImpl.java      # 内存用户 test/123456
```

## 设计要点

- **无状态（STATELESS）**：JWT 承载身份，服务端不存会话，便于水平扩展。
- **登录接口放行，其余全部鉴权**：`/api/login` 与 `/error` 白名单，`/hello` 等其余端点必须认证。
- **BCrypt 密码编码**：内存用户密码用 `BCryptPasswordEncoder` 编码，登录时由
  `DaoAuthenticationProvider` 校验。
- **JWT 密钥与有效期外部化**：位于 `application.yml` 的 `app.jwt.*`，生产环境应通过环境变量覆盖。
