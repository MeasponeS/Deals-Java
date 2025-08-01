# API 文档

本文档提供了 Smart Reminder 应用的 API 接口信息。

## 认证模块

基础路径: `/api/auth`

### 1. 用户登录

- **路径:** `/signin`
- **方法:** `POST`
- **描述:** 使用户登录并获取一个用于后续请求认证的 JWT。
- **请求体:** `LoginRequest`
  ```json
  {
    "username": "your_username",
    "password": "your_password"
  }
  ```
- **成功响应:** `JwtResponse`
  ```json
  {
    "token": "jwt_token_string"
  }
  ```

### 2. 用户注册

- **路径:** `/signup`
- **方法:** `POST`
- **描述:** 创建一个新用户账户。
- **请求体:** `SignupRequest`
  ```json
  {
    "username": "new_username",
    "password": "new_password",
    "securityQuestion": "Your security question",
    "securityAnswer": "Your security answer"
  }
  ```
- **成功响应:**
  ```
  User registered successfully!
  ```

### 3. 用户登出

- **路径:** `/signout`
- **方法:** `POST`
- **描述:** 用户登出。由于 JWT 是无状态的，客户端需要自行删除 token。
- **成功响应:**
  ```
  User logged out successfully!
  ```

### 4. 忘记密码

- **路径:** `/forgot-password`
- **方法:** `POST`
- **描述:** 验证用户的安全问题和答案以启动密码恢复流程。出于安全原因，此接口不会直接返回密码。
- **请求体:** `ForgotPasswordRequest`
  ```json
  {
    "username": "your_username",
    "securityQuestion": "Your security question",
    "securityAnswer": "Your security answer"
  }
  ```
- **成功响应:**
  ```
  Password recovery is not fully implemented due to security concerns with encoded passwords. But your credentials are correct.
  ```

### 5. 修改密码

- **路径:** `/change-password`
- **方法:** `POST`
- **描述:** 修改用户的密码。
- **请求体:** `ChangePasswordRequest`
  ```json
  {
    "username": "your_username",
    "oldPassword": "current_password",
    "newPassword": "new_password"
  }
  ```
- **成功响应:**
  ```
  Password updated successfully!
  ```

---

## 待办事项模块

基础路径: `/api/todos`

### 1. 获取待办列表

- **路径:** `/`
- **方法:** `GET`
- **描述:** 获取当前用户的待办事项列表，支持按标题和状态筛选。
- **查询参数:**
  - `title` (string, 可选): 按标题模糊搜索。
  - `status` (string, 可选): 按状态筛选 (`IN_PROGRESS`, `COMPLETED`, `EXPIRED`)。
- **成功响应:** `List<TodoResponse>`
  ```json
  [
    {
      "id": 1,
      "title": "Buy milk",
      "content": "Need to buy milk for breakfast.",
      "status": "IN_PROGRESS",
      "createdAt": "2023-10-27T10:00:00",
      "dueAt": "2023-10-28T09:00:00",
      "reminderAt": "2023-10-28T08:00:00",
      "urgency": "HIGH"
    }
  ]
  ```

### 2. 按日期分组获取待办列表

- **路径:** `/by-date`
- **方法:** `GET`
- **描述:** 获取当前用户的所有待办事项，并按日期分组。
- **成功响应:** `List<TodoGroupByDateResponse>`
  ```json
  [
    {
      "date": "2025-08-01",
      "todoList": [
        {
          "id": 1,
          "title": "Meeting with team",
          "content": "Discuss project progress.",
          "status": "IN_PROGRESS",
          "createdAt": "2025-07-30T10:00:00",
          "dueAt": "2025-08-01T14:00:00",
          "reminderAt": "2025-08-01T13:00:00",
          "urgency": "HIGH"
        }
      ]
    },
    {
      "date": "2025-08-02",
      "todoList": [
        // ... more todos
      ]
    }
  ]
  ```

### 3. 创建待办事项

- **路径:** `/`
- **方法:** `POST`
- **描述:** 创建一个新的待办事项。
- **请求体:** `TodoRequest`
  ```json
  {
    "title": "New Todo",
    "content": "Details of the new todo.",
    "dueAt": "2023-11-01T18:00:00",
    "reminderAt": "2023-11-01T17:00:00",
    "urgency": "MEDIUM"
  }
  ```
- **成功响应:** `TodoResponse`

### 4. 更新待办事项

- **路径:** `/{id}`
- **方法:** `PUT`
- **描述:** 更新一个已存在的待办事项。
- **路径参数:**
  - `id` (long): 待办事项的 ID。
- **请求体:** `TodoRequest`
- **成功响应:** `TodoResponse`

### 5. 标记为已完成

- **路径:** `/{id}/complete`
- **方法:** `PUT`
- **描述:** 将一个待办事项的状态标记为“已完成”。
- **路径参数:**
  - `id` (long): 待办事项的 ID。
- **成功响应:** `TodoResponse`

### 6. 删除待办事项

- **路径:** `/{id}`
- **方法:** `DELETE`
- **描述:** 删除一个待办事项。
- **路径参数:**
  - `id` (long): 待办事项的 ID。
- **成功响应:** `200 OK`

---

## 备忘录模块

基础路径: `/api/memos`

### 1. 获取备忘录列表

- **路径:** `/`
- **方法:** `GET`
- **描述:** 获取当前用户的备忘录列表，支持按标题模糊搜索。
- **查询参数:**
  - `title` (string, 可选): 按标题模糊搜索。
- **成功响应:** `List<MemoDto>`
  ```json
  [
    {
      "id": 1,
      "title": "My Memo",
      "content": "This is a sample memo.",
      "tags": "work,important",
      "createdAt": "2023-10-27T10:00:00",
      "updatedAt": "2023-10-27T11:00:00"
    }
  ]
  ```

### 2. 创建备忘录

- **路径:** `/`
- **方法:** `POST`
- **描述:** 创建一个新的备忘录。
- **请求体:** `MemoDto`
  ```json
  {
    "title": "New Memo",
    "content": "Content of the new memo.",
    "tags": "personal,ideas"
  }
  ```
- **成功响应:** `MemoDto`

### 3. 更新备忘录

- **路径:** `/{id}`
- **方法:** `PUT`
- **描述:** 更新一个已存在的备忘录。
- **路径参数:**
  - `id` (long): 备忘录的 ID。
- **请求体:** `MemoDto`
- **成功响应:** `MemoDto`

### 4. 删除备忘录

- **路径:** `/{id}`
- **方法:** `DELETE`
- **描述:** 删除一个备忘录。
- **路径参数:**
  - `id` (long): 备忘录的 ID。
- **成功响应:** `200 OK`

---

## 字典模块

基础路径: `/api/dictionaries`

### 1. 获取字典项列表

- **路径:** `/`
- **方法:** `GET`
- **描述:** 获取字典项列表，支持按类型筛选。
- **查询参数:**
  - `type` (string, 可选): 按字典类型进行筛选。
- **成功响应:** `List<Dictionary>`
  ```json
  [
    {
      "id": 1,
      "name": "高",
      "code": "high",
      "type": "urgency"
    },
    {
      "id": 2,
      "name": "中",
      "code": "medium",
      "type": "urgency"
    }
  ]
  ```
