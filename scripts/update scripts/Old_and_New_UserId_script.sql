select  USER_ID, NAME, SHORTNAME, USER_ID_OLD as OLD_EON_ID
from eon_users a
where a.USER_ID_OLD is not null