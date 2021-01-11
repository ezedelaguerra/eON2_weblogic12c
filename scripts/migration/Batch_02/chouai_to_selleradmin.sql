update eon_users set role_id = 2 where username = 'nobeoka' and role_id = 6;

update eon_admin_member set dealing_pattern_relation_id = 10003 where admin_id in (
select user_id 
from eon_users
where  username = 'nobeoka')
and dealing_pattern_relation_id = 10004;

commit;