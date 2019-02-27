# 根据账号查询用户的信息

SELECT
  u.id             u_id,
  u.org_id         u_org_Id,
  u.account,
  u.username,
  u.staff_id       u_staff_id,
  u.staff_type     u_staff_type,
  u.staff_position u_staff_position,
  u.staff_title    u_staff_title,
  u.tel,
  u.email,
  u.status         u_status,
  u.description    u_description,
  u.gmt_create     u_gmt_create,
  r.id             r_id,
  r.name           r_name,
  r.org_id         r_org_id,
  r.description    r_description,
  r.gmt_create     r_gmt_create,

  p.id             p_id,
  p.org_id         p_org_id,
  p.name           p_name,
  p.url            url,
  p.per_type       p_type,
  p.menu_sort      p_menu_sort,
  p.parent_id      p_parent_id,
  p.available      p_available,
  p.gmt_create     p_gmt_create
FROM sys_user AS u
  LEFT JOIN sys_user_role AS ur ON u.id = ur.uid
  LEFT JOIN sys_role AS r ON r.id = ur.rid
  LEFT JOIN sys_role_permission AS rp ON r.id = rp.rid
  LEFT JOIN sys_permission AS p ON rp.pid = p.id
WHERE u.account = 'admin' and p.per_type='menu'
ORDER BY p.parent_id , p.menu_sort