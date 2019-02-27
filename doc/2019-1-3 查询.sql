-- 查询用户信息
SELECT * FROM sys_user
-- 原始查询
SELECT `id`,  `name`, `create_id`, `create_time`, `discribe`,  `manager_id`, `check` FROM sys_department ORDER BY id DESC

-- 部门信息查询
SELECT a.id,a.name,b.username AS creater,a.create_time,c.username AS manager,a.discribe,a.check FROM sys_department a LEFT JOIN sys_user b ON a.create_id = b.user_id 
LEFT JOIN sys_user c ON a.manager_id = c.user_id 
WHERE a.name LIKE '%技术%' and b.username LIKE '%admin%' AND c.username LIKE '%admin%' AND a.create_time BETWEEN '2019-01-02 17:44:37' AND '2019-01-02 17:44:38'

-- 查询总数
select count(1) count FROM sys_department a LEFT JOIN sys_user b ON a.create_id = b.user_id LEFT JOIN sys_user c ON a.manager_id = c.user_id WHERE 1 = 1 
select count(1) count  FROM sys_department a LEFT JOIN sys_user b ON a.create_id = b.user_id LEFT JOIN sys_user c ON a.manager_id = c.user_id WHERE 1 = 1

-- SQL
SELECT u.user_id, u.org_id, ( SELECT name FROM sys_org o WHERE o.org_id = u.org_id ) AS orgName, u.username, u.email, u.mobile, u.status, u.remark, u.gmt_create FROM sys_user u ORDER BY u.user_id DESC limit 0 ,10 
-- SQL
SELECT a.id,a.name,b.username AS creater,a.create_time,c.username AS manager,a.discribe,a.check FROM sys_department a LEFT JOIN sys_user b ON a.create_id = b.user_id LEFT JOIN sys_user c ON a.manager_id = c.user_id WHERE 1 = 1 limit 0 ,10


SELECT a.id,a.name,a.create_id,a.create_time,a.discribe,a.manager_id,a.check from sys_department a
SELECT id,name,create_id,create_time,discribe,manager_id,check FROM sys_department WHERE id = 1 

UPDATE sys_department SET create_time = NOW()

SELECT
			u.user_id,
			u.org_id,
			(
				SELECT 
					name 
				FROM 
					sys_org o 
				WHERE 
					o.org_id = u.org_id
			) AS orgName,
			u.username,
			u.email,
			u.mobile,
			u.status,
			(SELECT name FROM sys_department d WHERE d.id = u.dept_id) AS deptName,
			u.remark,
			u.gmt_create
		FROM
			sys_user u

SELECT u.user_id, u.org_id, ( SELECT name FROM sys_org o WHERE o.org_id = u.org_id ) AS orgName, u.username, u.email, u.mobile, u.status, (SELECT name FROM sys_department d WHERE d.id = u.dept_id) AS deptName, u.remark, u.gmt_create FROM sys_user u ORDER BY u.user_id DESC limit 0 ,10 

SELECT a.id,a.name FROM  sys_department a
SELECT u.user_id, u.org_id, ( SELECT name FROM sys_org o WHERE o.org_id = u.org_id ) AS orgName, u.username, u.email, u.mobile, u.status, (SELECT name FROM sys_department d WHERE d.id = u.dept_id) AS deptName, u.remark, u.gmt_create FROM sys_user u ORDER BY u.user_id DESC limit 0 ,10 

SELECT a.id,a.name FROM sys_department a 

-- 查询某个部门的所有员工信息
SELECT u.user_id,u.username FROM sys_user u WHERE u.dept_id = 1

SELECT u.user_id,u.username FROM sys_user u WHERE u.dept_id = 1


SELECT a.id,a.name,b.username AS creater,a.create_time,c.username AS manager,a.discribe,a.check FROM sys_department a LEFT JOIN sys_user b ON a.create_id = b.user_id LEFT JOIN sys_user c ON a.manager_id = c.user_id WHERE 1 = 1 limit 0 ,10 

SELECT a.id,a.name,a.create_id,b.username AS creater,a.create_time,a.manager_id,c.username AS manager,a.discribe,a.check 
		FROM sys_department a
		LEFT JOIN sys_user b ON a.create_id = b.user_id
		LEFT JOIN sys_user c ON a.manager_id = c.user_id
		WHERE 1 = 1


SELECT * from (
SELECT u.user_id,
			u.org_id,
			(
				SELECT 
					name 
				FROM 
					sys_org o 
				WHERE 
					o.org_id = u.org_id
			) AS orgName,
			u.username,
			u.email,
			u.mobile,
			u.status,
			(SELECT name FROM sys_department d WHERE d.id = u.dept_id) AS deptName,
			u.remark,
			u.gmt_create,b.* FROM sys_user u LEFT JOIN sys_department b ON b.id = u.dept_id ) a WHERE

SELECT
			u.user_id,
			u.org_id,
			(
				SELECT 
					name 
				FROM 
					sys_org o 
				WHERE 
					o.org_id = u.org_id
			) AS orgName,
			u.username,
			u.password,
			u.email,
			u.mobile,
			u.remark,
			u.status,
			u.dept_id,
			(SELECT name FROM sys_department d WHERE d.id = u.dept_id) AS deptName,
		FROM 
			sys_user u
		WHERE
			u.user_id = 1

-- 查询用户信息 如果check = 1 查询 当前人员是否为管理员，是查询部门下的所有人员，否则查询自己
SELECT u.user_id,
			u.org_id,
			o.name AS orgName,
			u.username,
			u.password,
			u.email,
			u.mobile,
			u.remark,
			u.status,
			u.dept_id,
			d.name AS deptName,
			d.check,
			d.manager_id
FROM 
			sys_user u  LEFT JOIN sys_org o ON o.org_id = u.org_id LEFT JOIN sys_department d on d.id = u.dept_id WHERE u.user_id = 2


SELECT u.user_id, u.username, u.password, u.email, u.mobile, u.status, u.dept_id, (SELECT name FROM sys_department d WHERE d.id = u.dept_id) AS deptName FROM sys_user u WHERE u.username = 'admin' 


SELECT * FROM sys_menu

 SELECT r.role_id, r.org_id, ( SELECT name FROM sys_org o WHERE o.org_id = r.org_id ) AS orgName, 
r.role_name, r.role_sign, r.remark, r.gmt_create FROM sys_role r WHERE r.user_id_create = 1 ORDER BY r.role_id ASC limit 0 ,10

SELECT DISTINCT r.role_sign FROM sys_user_role ur LEFT JOIN sys_role r ON ur.role_id = r.role_id LEFT JOIN sys_user u ON ur.user_id = u.user_id WHERE u.user_id = 2 
SELECT DISTINCT m.perms FROM sys_user_role ur LEFT JOIN sys_role_menu rm ON ur.role_id = rm.role_id LEFT JOIN sys_menu m ON rm.menu_id = m.menu_id WHERE ur.user_id = 2
SELECT r.role_id, r.org_id, ( SELECT name FROM sys_org o WHERE o.org_id = r.org_id ) AS orgName, r.role_name, r.role_sign, r.remark FROM sys_role r WHERE r.role_id = 2 
SELECT menu_id FROM sys_role_menu WHERE role_id = 2
SELECT org_id FROM sys_role_org WHERE role_id = 2




SELECT a.id,a.name,a.create_id,b.username AS creater,a.create_time,a.manager_id,c.username AS manager,
a.discribe,a.check FROM sys_department a LEFT JOIN sys_user b ON a.create_id = b.user_id 
LEFT JOIN sys_user c ON a.manager_id = c.user_id WHERE a.id = 3 

-- 查询当前用户所在部门下的所有用户信息
SELECT user_id,username FROM sys_user WHERE dept_id = 3

SELECT u.user_id, u.org_id, ( SELECT name FROM sys_org o WHERE o.org_id = u.org_id ) AS orgName, u.username, u.email, u.mobile, u.status, u.dept_id, (SELECT name FROM sys_department d WHERE d.id = u.dept_id) AS deptName, u.remark, u.gmt_create FROM sys_user u ORDER BY u.user_id DESC limit 0 ,10


SELECT a.id,a.name FROM  sys_department a ORDER BY  a.id DESC


-- 权限关系修改
-- 查询当前用户信息 
SELECT DISTINCT r.role_sign FROM sys_user_role ur LEFT JOIN sys_role r ON ur.role_id = r.role_id LEFT JOIN sys_user u ON ur.user_id = u.user_id WHERE u.user_id = 2 
-- 查询当前用户下的菜单
SELECT DISTINCT m.perms FROM sys_user_role ur LEFT JOIN sys_role_menu rm ON ur.role_id = rm.role_id LEFT JOIN sys_menu m ON rm.menu_id = m.menu_id WHERE ur.user_id = 2 
-- 查询当前权限的信息
SELECT r.role_id, r.org_id, ( SELECT name FROM sys_org o WHERE o.org_id = r.org_id ) AS orgName, r.role_name, r.role_sign, r.remark FROM sys_role r WHERE r.role_id = 4
-- 查询当前权限可操作的菜单编号
SELECT menu_id FROM sys_role_menu WHERE role_id = 4 
-- 查询当前权限下的可操作机构号
SELECT org_id FROM sys_role_org WHERE role_id = 4 

SELECT m.menu_id, m.parent_id, ( SELECT p.name FROM sys_menu p WHERE p.menu_id = m.parent_id ) AS parentName, 
m.name, m.url, m.perms, m.type, m.icon, m.order_num FROM sys_menu m 
LEFT JOIN sys_role_menu r ON r.menu_id = m.menu_id WHERE r.role_id = 4 
ORDER BY m.order_num ASC


SELECT m.menu_id, m.parent_id, ( SELECT p.name FROM sys_menu p WHERE p.menu_id = m.parent_id ) AS parentName, 
m.name, m.url, m.perms, m.type, m.icon, m.order_num FROM sys_menu m 
LEFT JOIN sys_role_menu r ON r.menu_id = m.menu_id WHERE r.role_id = 1 
ORDER BY m.order_num ASC

SELECT m.menu_id, m.parent_id, ( SELECT p.name FROM sys_menu p WHERE p.menu_id = m.parent_id ) AS parentName, 
m.name, m.url, m.perms, m.type, m.icon, m.order_num FROM sys_menu m 
ORDER BY m.order_num ASC






SELECT
			role_id
		FROM
			sys_user_role
		WHERE
			user_id = 3



